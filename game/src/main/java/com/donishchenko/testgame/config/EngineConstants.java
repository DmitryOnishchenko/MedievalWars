package com.donishchenko.testgame.config;

public class EngineConstants {

    /* Update properties */
    public static final int TPS = 50;
    public static final int NANO_PER_TICK = 1_000_000_000 / TPS;

    /* Render properties */
    public static final int MAX_FPS = 60;
    public static final int NANO_PER_FRAME = 1_000_000_000 / MAX_FPS;
    public static final boolean USE_FPS_LIMIT = true;

    public static boolean SHOW_INFO = true;
    public static boolean DEBUG = false;

}
