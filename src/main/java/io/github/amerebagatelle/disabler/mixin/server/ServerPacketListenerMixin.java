package io.github.amerebagatelle.disabler.mixin.server;

import io.github.amerebagatelle.disabler.server.ConfigManager;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.impl.networking.CustomPayloadC2SPacketAccessor;
import net.fabricmc.fabric.mixin.networking.MixinCustomPayloadC2SPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPacketListenerMixin {
    @Shadow @Final private MinecraftServer server;

    /**
     * Accepts query packets from the client and checks whether there is a response.  If so, sends the response to the client.
     */
    @Inject(method = "onCustomPayload", at = @At("HEAD"))
    public void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        Identifier id = ((CustomPayloadC2SPacketAccessor)packet).getChannel();
        if(id.getNamespace().equals("disabler")) {
            PacketByteBuf buf = ((CustomPayloadC2SPacketAccessor) packet).getData();
            PlayerEntity player = server.getPlayerManager().getPlayer(buf.readUuid());
            PacketByteBuf reply = ConfigManager.getResponse(id);
            if (reply != null) {
                ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, id, reply);
            }
        }
    }
}
