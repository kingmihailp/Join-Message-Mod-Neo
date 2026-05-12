package net.kingmihailp.joinmessagemod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(JoinMessageMod.MOD_ID)
public class JoinMessageMod {

    public static final String MOD_ID = "joinmessagemod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public JoinMessageMod(IEventBus modEventBus, ModContainer modContainer) {
        ModConfig.load();

        NeoForge.EVENT_BUS.addListener(this::onPlayerJoin);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLeave);
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        JoinLeaveHandler.handleJoin(event.getEntity());
    }

    private void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        JoinLeaveHandler.handleLeave(event.getEntity());
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        ReloadCommand.register(event.getDispatcher());
    }
}
