package cn.ksmcbrigade.dj.fabric;

import cn.ksmcbrigade.dj.fabric.command.DiscjockeyCommand;
import net.fabricmc.api.ClientModInitializer;

import cn.ksmcbrigade.dj.NeoDiscJockeyMod;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import semmiedev.disc_jockey.gui.hud.BlocksOverlay;

import static semmiedev.disc_jockey.Main.PREVIEWER;
import static semmiedev.disc_jockey.Main.SONG_PLAYER;

public final class NeoDiscJockeyModFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        NeoDiscJockeyMod.init();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            DiscjockeyCommand.register(dispatcher);
        });

        ClientLoginConnectionEvents.DISCONNECT.register((handler, client) -> {
            PREVIEWER.stop();
            SONG_PLAYER.stop();
        });

        HudRenderCallback.EVENT.register(BlocksOverlay::render);
    }
}
