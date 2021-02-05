package io.github.amerebagatelle.disabler.mixin.client;

import io.github.amerebagatelle.disabler.client.api.DisableListenerRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class GameJoinMixin {
    @Inject(method = "onGameJoin", at = @At("TAIL"))
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        // Query for mods to disable
        for (Identifier identifier : DisableListenerRegistry.INSTANCE.listeners.keySet()) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeUuid(MinecraftClient.getInstance().player.getUuid());
            ClientSidePacketRegistry.INSTANCE.sendToServer(identifier, buf);
        }
    }
}
