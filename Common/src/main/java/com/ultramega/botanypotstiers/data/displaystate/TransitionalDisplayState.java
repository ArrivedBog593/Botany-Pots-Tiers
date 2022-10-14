package com.ultramega.botanypotstiers.data.displaystate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ultramega.botanypotstiers.Constants;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class TransitionalDisplayState extends DisplayState {
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "transitional");
    public static final DisplayStateSerializer<TransitionalDisplayState> SERIALIZER = new TransitionalDisplayState.Serializer();

    public final List<DisplayState> phases;

    public TransitionalDisplayState(List<DisplayState> phases) {
        this.phases = phases;
    }

    @Override
    public DisplayStateSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements DisplayStateSerializer<TransitionalDisplayState> {
        @Override
        public TransitionalDisplayState fromJSON(JsonElement json) {
            if (json instanceof JsonObject obj) {
                return new TransitionalDisplayState(DisplayState.SERIALIZER.fromJSONList(obj, "phases"));
            }

            throw new JsonParseException("Expected a JSON object.");
        }

        @Override
        public JsonElement toJSON(TransitionalDisplayState toWrite) {
            final JsonObject obj = new JsonObject();
            DisplayState.SERIALIZER.toJSONList(obj, "phases", toWrite.phases);
            return obj;
        }

        @Override
        public TransitionalDisplayState fromByteBuf(FriendlyByteBuf buffer) {
            return new TransitionalDisplayState(DisplayState.SERIALIZER.fromByteBufList(buffer));
        }

        @Override
        public void toByteBuf(FriendlyByteBuf buffer, TransitionalDisplayState toWrite) {
            DisplayState.SERIALIZER.toByteBufList(buffer, toWrite.phases);
        }

        @Override
        public Tag toNBT(TransitionalDisplayState toWrite) {
            // TODO NBT doesn't support optional yet
            return null;
        }

        @Override
        public TransitionalDisplayState fromNBT(Tag nbt) {
            return null;
        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }
    }
}