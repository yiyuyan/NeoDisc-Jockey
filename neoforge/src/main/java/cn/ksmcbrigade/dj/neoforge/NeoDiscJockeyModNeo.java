package cn.ksmcbrigade.dj.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import cn.ksmcbrigade.dj.NeoDiscJockeyMod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import semmiedev.disc_jockey.Config;
import semmiedev.disc_jockey.DiscjockeyCommand;
import semmiedev.disc_jockey.gui.hud.BlocksOverlay;

import static semmiedev.disc_jockey.Main.PREVIEWER;
import static semmiedev.disc_jockey.Main.SONG_PLAYER;

@Mod(NeoDiscJockeyMod.MOD_ID)
public final class NeoDiscJockeyModNeo {
    public NeoDiscJockeyModNeo() {
        // Run our common setup.
        NeoDiscJockeyMod.init();
        NeoForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,()-> (modContainer, parent) -> AutoConfig.getConfigScreen(Config.class, parent).get());
    }

    @SubscribeEvent
    public void command(RegisterClientCommandsEvent event){
        DiscjockeyCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onDisconnect(ClientPlayerNetworkEvent.LoggingOut event){
        PREVIEWER.stop();
        SONG_PLAYER.stop();
    }

    @SubscribeEvent
    public void render(RenderGuiEvent.Post event){
        BlocksOverlay.render(event.getGuiGraphics(),event.getPartialTick());
    }
}
