package io.github.amerebagatelle.disabler.mixin.server;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(CustomPayloadC2SPacket.class)
public interface CustomC2SPacketAccessor {
    @Accessor()
    Identifier getChannel();

    @Accessor()
    PacketByteBuf getData();
}
