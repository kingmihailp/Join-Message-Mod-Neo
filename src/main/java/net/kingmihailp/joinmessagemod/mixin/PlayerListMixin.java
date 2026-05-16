package net.kingmihailp.joinmessagemod.mixin;

import net.kingmihailp.joinmessagemod.JoinMessageMod;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    private static final Set<String> VANILLA_KEYS = Set.of(
        "multiplayer.player.joined",
        "multiplayer.player.joined.renamed",
        "multiplayer.player.left"
    );

    // @Inject is purely additive — no conflicts with any other mod.
    // We detect vanilla join/leave messages by their translation key, which is
    // reliable and has no timing/flag race conditions.
    @Inject(method = "broadcastSystemMessage", at = @At("HEAD"), cancellable = true)
    private void suppressVanillaJoinLeave(Component message, boolean overlay, CallbackInfo ci) {
        if (message.getContents() instanceof TranslatableContents tc
                && VANILLA_KEYS.contains(tc.getKey())) {
            ci.cancel();
            JoinMessageMod.LOGGER.debug("Suppressed vanilla message: {}", tc.getKey());
        }
    }
}
