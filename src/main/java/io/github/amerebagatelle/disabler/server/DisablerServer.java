package io.github.amerebagatelle.disabler.server;

import net.fabricmc.api.DedicatedServerModInitializer;

public class DisablerServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ConfigManager.INSTANCE.loadConfig();
    }
}
