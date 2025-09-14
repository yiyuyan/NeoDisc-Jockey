package semmiedev.disc_jockey;

import cn.ksmcbrigade.dj.listeners.StartWorldTick;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static final String MOD_ID = "disc_jockey";
    public static final MutableText NAME = Text.literal("Disc Jockey");
    public static final Logger LOGGER = LogManager.getLogger("Disc Jockey");
    public static final ArrayList<StartWorldTick> TICK_LISTENERS = new ArrayList<>();
    public static final Previewer PREVIEWER = new Previewer();
    public static final SongPlayer SONG_PLAYER = new SongPlayer();

    public static File songsFolder;
    public static Config config;
    public static ConfigHolder<Config> configHolder;

    public static KeyBinding openScreenKeyBind = new KeyBinding(MOD_ID+".key_bind.open_screen", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.category."+MOD_ID);

    public void onInitializeClient() {
        configHolder = AutoConfig.register(Config.class, JanksonConfigSerializer::new);
        config = configHolder.getConfig();

        songsFolder = new File("config/"+File.separator+MOD_ID+File.separator+"songs");
        if (!songsFolder.isDirectory()) songsFolder.mkdirs();

        SongLoader.loadSongs();

        KeyMappingRegistry.register(openScreenKeyBind);
    }
}
