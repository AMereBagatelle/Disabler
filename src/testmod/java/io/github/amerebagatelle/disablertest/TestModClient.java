package io.github.amerebagatelle.disablertest;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.fabricmc.api.ClientModInitializer;

public class TestModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DisableListenerRegistry.register("testmod", "example_feature", TestModClient::toggle);

    }

    public static void toggle(boolean value) {
        System.out.println(value);
    }
}
