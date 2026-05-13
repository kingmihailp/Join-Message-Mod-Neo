package net.kingmihailp.joinmessagemod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerList.class)
public class PlayerListMixin {

    @WrapOperation(
        method = "placeNewPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"
        )
    )
    private void suppressVanillaJoinMessage(PlayerList instance, Component message, boolean overlay, Operation<Void> original) {
        // @WrapOperation is composable: other mods wrapping the same call still work.
        // Vanilla join message suppressed; custom message sent by JoinLeaveHandler via PlayerLoggedInEvent.
    }
}
