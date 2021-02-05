package io.github.amerebagatelle.disabler.client;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import io.github.amerebagatelle.disabler.common.util.Util;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Consumer;

public class DisableManager {
    public static final DisableManager INSTANCE = new DisableManager();

    public void disable(Identifier id, boolean disable) {
        Consumer<Boolean> consumer = Util.getValueById(DisableListenerRegistry.INSTANCE.listeners, id);
        if(consumer != null) {
            consumer.accept(disable);
        }
    }
}
