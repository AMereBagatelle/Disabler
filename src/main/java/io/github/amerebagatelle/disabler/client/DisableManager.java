package io.github.amerebagatelle.disabler.client;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class DisableManager {
    public static final DisableManager INSTANCE = new DisableManager();

    public void disable(Identifier id, boolean disable) {
        Consumer<Boolean> consumer = DisableListenerRegistry.INSTANCE.listeners.get(id);
        consumer.accept(disable);
    }
}
