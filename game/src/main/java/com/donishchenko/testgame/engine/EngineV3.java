package com.donishchenko.testgame.engine;

import com.donishchenko.testgame.gamestate.GameStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import static com.donishchenko.testgame.config.EngineConstants.SHOW_INFO;

@Component("engineV3")
public class EngineV3 implements GameEngine {

    @Autowired @Qualifier("gameWindow")
    private GameWindow window;
    @Autowired
    private RenderThread renderThread;
    @Autowired
    private UpdateThread updateThread;
    @Autowired
    private InputThread inputThread;
    @Autowired
    private GameStateManager gsm;

    private BufferStrategy strategy;

    // globals used for FSEM tasks
    private GraphicsDevice gd;

    @Override
    public void init() {
        // TODO test BufferStrategy
        window.createBufferStrategy(2);
        strategy = window.getBufferStrategy();

        // TODO key listener
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                updateThread.keyEventQueue.offer(event);
            }

            @Override
            public void keyReleased(KeyEvent event) {
                updateThread.keyEventQueue.offer(event);
            }
        });

        gsm.init();
    }

    @Override
    public void start() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();

        if (!gd.isFullScreenSupported()) {
            onError("Full-screen exclusive mode not supported");
            System.exit(0);
        }
        // switch on FSEM
//        gd.setFullScreenWindow(window);

        window.setVisible(true);

        renderThread.start();
        updateThread.start();
//        inputThread.start();
    }

    @Override
    public void processInput(KeyEvent keyEvent) {
        gsm.processInput(keyEvent);
    }

    @Override
    public void update() {
        gsm.update();
    }

    @Override
    public void render() {
        if (!window.isFocused()) {
//            return;
        }
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();

                // Clear rect
                g2.clearRect(0, 0, window.getWidth(), window.getHeight());
                // Render to graphics
                gsm.render(g2);
                // ...

                if (SHOW_INFO) {
                    g2.setPaint(Color.WHITE);
                    g2.drawString(renderThread.report(), 900, 18);
                    g2.drawString(updateThread.report(), 1100, 18);
                }

                // Dispose the graphics
                g2.dispose();

                // Repeat the rendering if the drawing buffer contents
                // were restored
            } while (strategy.contentsRestored());

            // Display the buffer
            strategy.show();

            // Repeat the rendering if the drawing buffer was lost
        } while (strategy.contentsLost());
    }

    @Override
    public void onError(String errorMessage) {
        JOptionPane.showMessageDialog(window, errorMessage);
        System.exit(0);
    }

}
