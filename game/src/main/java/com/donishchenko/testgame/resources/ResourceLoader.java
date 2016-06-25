package com.donishchenko.testgame.resources;

import com.donishchenko.testgame.Application;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourceLoader {

    public static float SCALE = 0.5f;
    private static final HashMap<String, HashMap<String, Object>> assets = new HashMap<>();

    static {
        loadCommonResources();
        loadEffectsResources();

        // Human units
        loadResourcesForUnit("Human Soldier");
        loadResourcesForUnit("Human Archer");

        // Orc units
        loadResourcesForUnit("Orc Soldier");
        loadResourcesForUnit("Orc Archer");
        loadResourcesForUnit("Orc Soldier Elite");
    }

    public static Resources getResources(String key) {
        return new Resources(assets.get(key));
    }

    public static void loadResourcesForUnit(String unitName) {
        try {
            InputStreamReader rin = new InputStreamReader(Application.class.getResourceAsStream("/units/properties/" + unitName + ".json"));
            BufferedReader br = new BufferedReader(rin);

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();

            ObjectMapper mapper = new ObjectMapper();

            PhysicsModel physicsModel = new PhysicsModel();
            mapper.readerForUpdating(physicsModel).readValue(result);

            GraphicsModel graphicsModel = new GraphicsModel();
            mapper.readerForUpdating(graphicsModel).readValue(result);

            HashMap<String, Object> props = new HashMap<>();
            props.put("physicsModel", physicsModel);
            props.put("graphicsModel", graphicsModel);

            assets.put(unitName, props);

            System.out.println("Success load for: " + unitName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCommonResources() {
        HashMap<String, Object> commonAssets = new HashMap<>();

        BufferedImage background0 = ImageUtils.loadImage("/background/backgroundLayer_0.png");
        BufferedImage background1 = ImageUtils.loadImage("/background/backgroundLayer_1.png");
        commonAssets.put("backgroundLayer_0", background0);
        commonAssets.put("backgroundLayer_1", background1);

        assets.put("common", commonAssets);
    }

    private static void loadEffectsResources() {
        HashMap<String, Object> effectsAssets = new HashMap<>();

        ArrayList<BufferedImage> bloodSprites = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            BufferedImage img = ImageUtils.loadImage("/blood/blood_" + i + ".png", SCALE);
            bloodSprites.add(img);
        }
        effectsAssets.put("bloodList", bloodSprites);

        assets.put("effects", effectsAssets);
    }

}
