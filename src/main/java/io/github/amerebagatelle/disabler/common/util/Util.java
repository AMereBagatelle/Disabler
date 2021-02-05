package io.github.amerebagatelle.disabler.common.util;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Util {
    public static <T> T getValueById(HashMap<Identifier, T> map, Identifier id) {
        for (Map.Entry<Identifier, T> entry : map.entrySet()) {
            if(id.getPath().equals(entry.getKey().getPath())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
