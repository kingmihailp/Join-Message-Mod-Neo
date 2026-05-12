package net.kingmihailp.joinmessagemod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ReloadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("jmmreload")
                .requires(src -> src.hasPermission(2))
                .executes(ctx -> {
                    ModConfig.reload(ctx.getSource().getServer());
                    ctx.getSource().sendSuccess(
                        () -> HexColorParser.parse("&#55FF55[JoinMessageMod] Config reloaded successfully!"),
                        true
                    );
                    return 1;
                })
        );
    }
}
