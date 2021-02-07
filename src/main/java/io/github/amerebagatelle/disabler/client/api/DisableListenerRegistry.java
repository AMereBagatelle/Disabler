package io.github.amerebagatelle.disabler.client.api;

import io.github.amerebagatelle.disabler.common.util.Util;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class DisableListenerRegistry {
    private static final HashMap<Identifier, Consumer<Boolean>> listeners = new HashMap<>();

    /**
     * Registers a listener for disabling a feature.
     * By default does not disable until a response is received.
     *
     * @param modId The identifier of the mod to register under
     * @param feature The name of the feature to toggle
     * @param consumer A setter for the feature to toggle
     */
    public static void register(String modId, String feature, Consumer<Boolean> consumer) {
        listeners.put(new Identifier("disabler",modId + "/" + feature), consumer);
    }

    /**
     * Disables a feature based on its identifier.
     *
     * @param id The identifier for the feature to disable
     * @param disable Whether the feature should be disabled
     */
    public static void disable(Identifier id, boolean disable) {
        Consumer<Boolean> consumer = Util.getValueById(listeners, id);
        if(consumer != null) {
            consumer.accept(disable);
        }
    }

    public static HashMap<Identifier, Consumer<Boolean>> getListeners() {
        return listeners;
    }
}
