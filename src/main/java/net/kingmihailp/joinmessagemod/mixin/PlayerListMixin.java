package net.kingmihailp.joinmessagemod.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @Redirect(
        method = "placeNewPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    private void suppressVanillaJoinMessage(PlayerList instance, Component message, boolean overlay) {
        // Vanilla join message suppressed; custom message sent by JoinLeaveHandler via PlayerLoggedInEvent
    }

    @Redirect(
        method = "remove",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    private void suppressVanillaLeaveMessage(PlayerList instance, Component message, boolean overlay) {
        // Vanilla leave message suppressed; custom message sent by JoinLeaveHandler via PlayerLoggedOutEvent
    }
}
