package net.kingmihailp.joinmessagemod.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    // In 1.21.1 the leave message is broadcast inside onDisconnect, not inside PlayerList.remove
    @Redirect(
        method = "onDisconnect",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    private void suppressVanillaLeaveMessage(PlayerList instance, Component message, boolean overlay) {
        // Vanilla leave message suppressed; custom message sent by JoinLeaveHandler via PlayerLoggedOutEvent
    }
}
