package io.github.amerebagatelle.disablertest;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class TestModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DisableListenerRegistry.INSTANCE.register("testmod", "example_feature", TestModClient::toggle);

    }

    public static void toggle(boolean value) {
        System.out.println(value);
    }
}
