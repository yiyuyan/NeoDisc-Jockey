package cn.ksmcbrigade.dj;

import semmiedev.disc_jockey.Main;

public final class NeoDiscJockeyMod {
    public static final String MOD_ID = "disc_jockey";

    public static void init() {
        new Main().onInitializeClient();
    }
}
