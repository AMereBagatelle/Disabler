package io.github.amerebagatelle.disabler.mixin.client;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPacketListenerMixin {
    /**
     * Accepts disable packets from the server.
     */
    @Inject(method = "onCustomPayload", at = @At("HEAD"))
    public void onCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if(packet.getChannel().getNamespace().equals("disabler")) {
            PacketByteBuf buf = packet.getData();
            boolean disable = buf.readBoolean();
            DisableListenerRegistry.disable(packet.getChannel(), disable);
        }
    }
}
