package io.github.amerebagatelle.disablertest;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class TestModClient implements ClientModInitializer {
    private static boolean toggle = false;

    @Override
    public void onInitializeClient() {
        DisableListenerRegistry.INSTANCE.register("testmod", "example_feature", TestModClient::toggle);
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(CommandManager.literal("testdisable").executes(c -> {
            System.out.println(toggle);
            return 1;
        })));
    }

    public static void toggle(boolean value) {
        toggle = value;
    }
}
