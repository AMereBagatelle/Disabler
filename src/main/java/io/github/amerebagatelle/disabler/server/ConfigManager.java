package io.github.amerebagatelle.disabler.server;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.Unpooled;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    public static final ConfigManager INSTANCE = new ConfigManager();

    private final HashMap<Identifier, Boolean> configs = new HashMap<>();
    private Gson gson = new Gson();

    private ConfigManager() {
        loadConfig();
    }

    private void loadConfig() {
        JsonObject object = null;

        try {
            Path configPath = FabricLoader.getInstance().getConfigDir().resolve("disabler-rules.json");
            object = gson.fromJson(Arrays.toString(Files.readAllBytes(configPath)), JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                String mod = entry.getKey();
                for (Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                    configs.put(new Identifier(mod + "/" + entry2.getKey()), entry.getValue().getAsBoolean());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PacketByteBuf getResponse(Identifier query) {
        if(configs.containsKey(query)) {
            boolean value = configs.get(query);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(value);
            return buf;
        }
        return null;
    }
}
