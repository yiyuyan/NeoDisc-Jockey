package semmiedev.disc_jockey.mixin;

import cn.ksmcbrigade.dj.listeners.StartWorldTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import semmiedev.disc_jockey.Main;
import semmiedev.disc_jockey.SongLoader;
import semmiedev.disc_jockey.gui.screen.DiscJockeyScreen;

import java.util.function.BooleanSupplier;

import static semmiedev.disc_jockey.Main.*;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Shadow @Final private MinecraftClient client;

    @Unique
    private ClientWorld disc_jockey$prevWorld;

    @Inject(method = "playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZJ)V", at = @At("HEAD"), cancellable = true)
    private void makeNoteBlockSoundsOmnidirectional(double x, double y, double z, SoundEvent event, SoundCategory category, float volume, float pitch, boolean useDistance, long seed, CallbackInfo ci) {
        if (((Main.config.omnidirectionalNoteBlockSounds && SONG_PLAYER.running) || PREVIEWER.running) && event.getId().getPath().startsWith("block.note_block")) {
            ci.cancel();
            client.getSoundManager().play(new PositionedSoundInstance(event.getId(), category, volume, pitch, Random.create(seed), false, 0, SoundInstance.AttenuationType.NONE, 0, 0, 0, true));
        }
    }

    @Inject(method = "tick",at = @At("HEAD"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        if (disc_jockey$prevWorld != client.world) {
            PREVIEWER.stop();
            SONG_PLAYER.stop();
        }
        disc_jockey$prevWorld = client.world;

        if (openScreenKeyBind.wasPressed()) {
            if (SongLoader.loadingSongs) {
                client.inGameHud.getChatHud().addMessage(Text.translatable(Main.MOD_ID+".still_loading").formatted(Formatting.RED));
                SongLoader.showToast = true;
            } else {
                client.setScreen(new DiscJockeyScreen());
            }
        }
        for (StartWorldTick listener : TICK_LISTENERS) listener.onStartTick((ClientWorld) ((Object) this));
    }
}
