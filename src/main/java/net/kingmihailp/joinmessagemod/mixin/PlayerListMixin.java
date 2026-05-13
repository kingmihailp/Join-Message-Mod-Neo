package net.kingmihailp.joinmessagemod.mixin;

import net.kingmihailp.joinmessagemod.SuppressFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    // @Inject is always additive — multiple mods injecting at the same point
    // never conflict with each other, unlike @Redirect / @WrapOperation.
    @Inject(method = "broadcastSystemMessage", at = @At("HEAD"), cancellable = true)
    private void suppressFlaggedVanillaMessage(Component message, boolean overlay, CallbackInfo ci) {
        if (SuppressFlag.consume()) {
            ci.cancel();
        }
    }
}
