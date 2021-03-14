package io.github.amerebagatelle.disabler.mixin.client;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Consumer;

@Mixin(ClientPlayNetworkHandler.class)
public class GameJoinMixin {
    /**
     * Sends query packets to the server.
     */
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        // Query for mods to disable
        for (Identifier identifier : DisableListenerRegistry.getListeners().keySet()) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeUuid(MinecraftClient.getInstance().player.getUuid());
            ClientPlayNetworking.send(identifier, buf);
        }
    }

    /**
     * Sets all features to enabled, when the server is left.
     */
    @Inject(method = "onDisconnected", at = @At("HEAD"))
    public void onDisconnected(Text reason, CallbackInfo ci) {
        for (Map.Entry<Identifier, Consumer<Boolean>> entry : DisableListenerRegistry.getListeners().entrySet()) {
            entry.getValue().accept(false);
        }
    }
}
