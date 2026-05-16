package net.kingmihailp.joinmessagemod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class JoinLeaveHandler {

    public static void handleJoin(Player player) {
        if (!ModConfig.joinEnabled) return;
        if (!(player instanceof ServerPlayer sp)) return;

        String raw = ModConfig.joinMessage.replace("{player}", player.getName().getString());
        Component msg = HexColorParser.parse(raw);
        sp.getServer().getPlayerList().broadcastSystemMessage(msg, false);
    }

    public static void handleLeave(Player player) {
        if (!ModConfig.leaveEnabled) return;
        if (!(player instanceof ServerPlayer sp)) return;

        String raw = ModConfig.leaveMessage.replace("{player}", player.getName().getString());
        Component msg = HexColorParser.parse(raw);
        sp.getServer().getPlayerList().broadcastSystemMessage(msg, false);
    }
}
