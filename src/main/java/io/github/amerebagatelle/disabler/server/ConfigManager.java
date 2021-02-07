package io.github.amerebagatelle.disabler.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import io.github.amerebagatelle.disabler.common.util.Util;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private static final HashMap<Identifier, Boolean> configs = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setLenient().create();

    public static void loadConfig() {
        // clear configs so that new ones can be loaded
        configs.clear();

        JsonObject object;

        try {
            Path configPath = FabricLoader.getInstance().getConfigDir().resolve("disabler-rules.json");
            BufferedReader reader = new BufferedReader(new FileReader(configPath.toFile()));
            object = gson.fromJson(reader, JsonObject.class);
            reader.close();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                String mod = entry.getKey();
                for (Map.Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
                    configs.put(new Identifier("disabler", mod + "/" + entry2.getKey()), entry2.getValue().getAsBoolean());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PacketByteBuf getResponse(Identifier query) {
        Boolean value = Util.getValueById(configs, query);
        if(value != null) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(value);
            return buf;
        }
        return null;
    }
}
