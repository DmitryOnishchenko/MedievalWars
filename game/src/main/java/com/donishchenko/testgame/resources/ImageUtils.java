package com.donishchenko.testgame.resources;

import com.donishchenko.testgame.Application;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {

    // obtain the current system graphical settings
    private static GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice().getDefaultConfiguration();

    /**
     * Load single image
     *
     * @param path
     * @return
     */
    public static BufferedImage loadImage(String path) {
        return loadImage(path, 1);
    }

    /**
     * Load single image and resize it
     * @param path
     * @param scale
     * @return
     */
    public static BufferedImage loadImage(String path, double scale) {
        try {
            URL url = Application.class.getResource(path);
            BufferedImage img = ImageIO.read(url);

            return resize(img, scale);
        } catch (IOException ex) {
            System.out.println("Load image error: " + path);
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Resize image
     * @param source
     * @param scale
     * @return
     */
    public static BufferedImage resize(BufferedImage source, double scale) {
        if (scale == 1) {
            return source;
        }

        int newWidth = (int) (source.getWidth() * scale);
        int newHeight = (int) (source.getHeight() * scale);

        BufferedImage resizedImage = gc.createCompatibleImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.setAccelerationPriority(1);

        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(source, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return resizedImage;
    }

    /**
     * Load single image and spit it
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage[] loadImageTiles(String path, int width, int height) {
        return loadImageTiles(path, width, height, 1);
    }

    public static BufferedImage[] loadImageTiles(String path, int width, int height, double scale) {
        try {
            URL url = Application.class.getResource(path);
            BufferedImage img = ImageIO.read(url);

            int total = img.getWidth() / width;
            BufferedImage[] array = new BufferedImage[total];

            for (int i = 0; i < total; i++) {
                BufferedImage subImage = img.getSubimage(i * width, 0, width, height);
                array[i] = resize(subImage, scale);
            }

            return array;
        } catch (IOException ex) {
            System.out.println("Load image error: " + path);
            ex.printStackTrace();
        }

        return null;
    }

    public static BufferedImage[] loadImageTiles(String path, int width, int height, double scale, int start, int end) {
        try {
            URL url = Application.class.getResource(path);
            BufferedImage img = ImageIO.read(url);

            int total = end - start;
            BufferedImage[] array = new BufferedImage[total];

            for (int i = start; i < total; i++) {
                BufferedImage subImage = img.getSubimage(i * width, 0, width, height);
                array[i] = resize(subImage, scale);
            }

            return array;
        } catch (IOException ex) {
            System.out.println("Load image error: " + path);
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Convert BufferedImage to compatible image
     * @param source
     * @return
     */
    public static BufferedImage toCompatibleImage(BufferedImage source) {
        int transparency = source.getColorModel().getTransparency();
        BufferedImage copy = gc.createCompatibleImage(source.getWidth(), source.getHeight(), transparency);
        copy.setAccelerationPriority(1);

        // create a graphics context
        Graphics2D g2 = copy.createGraphics();

        // copy image
        g2.drawImage(source, 0, 0, null);
        g2.dispose();

        return copy;
    }

    /**
     * Test method: checks image hardware acceleration
     * @param image
     * @return
     */
    public static boolean isAccelerated(BufferedImage image) {
        return image.getCapabilities(gc).isAccelerated();
    }

    public static BufferedImage flipHorizontally(BufferedImage source) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-source.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return toCompatibleImage(op.filter(source, null));
    }

    public static BufferedImage[] flipHorizontally(BufferedImage[] sprites) {
        BufferedImage[] copy = new BufferedImage[sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            copy[i] = flipHorizontally(sprites[i]);
        }
        return copy;
    }

//    public static VolatileImage convertToVolatileImage(BufferedImage bimage) {
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
//
//        VolatileImage vimage = createVolatileImage(bimage.getWidth(), bimage.getHeight(), Transparency.TRANSLUCENT);
//
//        Graphics2D g = null;
//
//        // This uses the same code as from Code Example 5, but replaces the try block.
//        try {
//            g = vimage.createGraphics();
//
//            // These commands cause the Graphics2D object to clear to (0,0,0,0).
//            g.setComposite(AlphaComposite.Src);
//            g.setColor(Color.BLACK);
//            g.clearRect(0, 0, vimage.getWidth(), vimage.getHeight()); // Clears the image.
//
//            g.drawImage(bimage,null,0,0);
//        } finally {
//            // It's always best to dispose of your Graphics objects.
//            g.dispose();
//        }
//
//        return vimage;
//    }

    //    public static VolatileImage createVolatileImage(int width, int height, int transparency) {
//        VolatileImage image = gc.createCompatibleVolatileImage(width, height, transparency);
//
//        int valid = image.validate(gc);
//
//        if (valid == VolatileImage.IMAGE_INCOMPATIBLE) {
//            image = createVolatileImage(width, height, transparency);
//            return image;
//        }
//
//        return image;
//    public static void flipHorizontally(VolatileImage[] sprites) {
//        for (int i = 0; i < sprites.length; i++) {
//            VolatileImage image = sprites[i];
//            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
//            tx.translate(-image.getWidth(null), 0);
//            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//            BufferedImage img = op.filter(image.getSnapshot(), null);
//            sprites[i] = convertToVolatileImage(img);
//}

}
