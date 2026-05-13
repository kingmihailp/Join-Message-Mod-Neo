package net.kingmihailp.joinmessagemod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class JoinLeaveHandler {

    public static void handleJoin(Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (ModConfig.joinEnabled) {
            String raw = ModConfig.joinMessage.replace("{player}", player.getName().getString());
            Component msg = HexColorParser.parse(raw);
            // Send custom message BEFORE setting the flag so it isn't suppressed itself.
            sp.getServer().getPlayerList().broadcastSystemMessage(msg, false);
        }
        // Flag the very next broadcastSystemMessage call on this thread as the vanilla
        // join message and suppress it (works whether joinEnabled is true or false).
        SuppressFlag.set();
    }

    public static void handleLeave(Player player) {
        if (!(player instanceof ServerPlayer sp)) return;

        if (ModConfig.leaveEnabled) {
            String raw = ModConfig.leaveMessage.replace("{player}", player.getName().getString());
            Component msg = HexColorParser.parse(raw);
            sp.getServer().getPlayerList().broadcastSystemMessage(msg, false);
        }
        SuppressFlag.set();
    }
}
