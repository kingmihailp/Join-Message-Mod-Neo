package net.kingmihailp.joinmessagemod;

import com.google.gson.*;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.loading.FMLPaths;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ModConfig {

    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("joinmessagemod.json");

    public static boolean joinEnabled = true;
    public static String joinMessage = "&#00FF7F{player} joined the server";

    public static boolean leaveEnabled = true;
    public static String leaveMessage = "&#FF4444{player} left the server";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }
        try (Reader reader = new InputStreamReader(new FileInputStream(CONFIG_PATH.toFile()), StandardCharsets.UTF_8)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();

            if (obj.has("join_enabled"))  joinEnabled  = obj.get("join_enabled").getAsBoolean();
            if (obj.has("join_message"))  joinMessage  = obj.get("join_message").getAsString();
            if (obj.has("leave_enabled")) leaveEnabled = obj.get("leave_enabled").getAsBoolean();
            if (obj.has("leave_message")) leaveMessage = obj.get("leave_message").getAsString();

        } catch (Exception e) {
            JoinMessageMod.LOGGER.error("Failed to load config, using defaults", e);
        }
    }

    public static void save() {
        JsonObject obj = new JsonObject();
        obj.addProperty("join_enabled",  joinEnabled);
        obj.addProperty("join_message",  joinMessage);
        obj.addProperty("leave_enabled", leaveEnabled);
        obj.addProperty("leave_message", leaveMessage);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(CONFIG_PATH.toFile()), StandardCharsets.UTF_8)) {
            GSON.toJson(obj, writer);
        } catch (Exception e) {
            JoinMessageMod.LOGGER.error("Failed to save config", e);
        }
    }

    public static void reload(MinecraftServer server) {
        load();
        JoinMessageMod.LOGGER.info("JoinMessageMod config reloaded");
    }
}
