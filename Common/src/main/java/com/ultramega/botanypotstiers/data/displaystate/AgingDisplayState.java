package com.ultramega.botanypotstiers.data.displaystate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.darkhax.bookshelf.api.serialization.Serializers;
import com.ultramega.botanypotstiers.Constants;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class AgingDisplayState extends TransitionalDisplayState {
    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "aging");
    public static final DisplayStateSerializer<AgingDisplayState> SERIALIZER = new AgingDisplayState.Serializer();

    private final BlockState defaultState;

    public AgingDisplayState(BlockState defaultState) {
        super(calculatePhases(defaultState));
        this.defaultState = defaultState;
    }

    @Override
    public DisplayStateSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static IntegerProperty getAgeProperty(Block block) {
        if (block instanceof CropBlock crop) {
            return crop.getAgeProperty();
        }

        final Property<?> ageProperty = block.getStateDefinition().getProperty("age");

        if (ageProperty instanceof IntegerProperty intProp) {
            return intProp;
        }

        return null;
    }

    private static List<DisplayState> calculatePhases(BlockState defaultState) {
        final Block block = defaultState.getBlock();
        final List<DisplayState> phases = new ArrayList<>();

        final IntegerProperty ageProp = getAgeProperty(block);

        if (ageProp != null) {
            for (int age : ageProp.getPossibleValues()) {
                phases.add(new SimpleDisplayState(defaultState.setValue(ageProp, age)));
            }
        } else {
            phases.add(new SimpleDisplayState(defaultState));
        }

        return phases;
    }

    public static class Serializer implements DisplayStateSerializer<AgingDisplayState> {
        @Override
        public ResourceLocation getId() {
            return ID;
        }

        @Override
        public AgingDisplayState fromJSON(JsonElement json) {
            if (json instanceof JsonObject obj) {
                final BlockState defaultState = Serializers.BLOCK_STATE.fromJSON(obj, "block");

                if (defaultState == null) {
                    throw new JsonParseException("Could not read block! " + obj.get("block"));
                }

                return new AgingDisplayState(defaultState);
            }

            throw new JsonParseException("Expected AgingDisplayState to be a JSON object.");
        }

        @Override
        public JsonElement toJSON(AgingDisplayState toWrite) {
            final JsonObject json = new JsonObject();
            Serializers.BLOCK_STATE.toJSON(json, "block", toWrite.defaultState);
            return json;
        }

        @Override
        public AgingDisplayState fromByteBuf(FriendlyByteBuf buffer) {
            final BlockState block = Serializers.BLOCK_STATE.fromByteBuf(buffer);
            return new AgingDisplayState(block);
        }

        @Override
        public void toByteBuf(FriendlyByteBuf buffer, AgingDisplayState toWrite) {
            Serializers.BLOCK_STATE.toByteBuf(buffer, toWrite.defaultState);
        }

        @Override
        public Tag toNBT(AgingDisplayState toWrite) {
            // TODO
            return null;
        }

        @Override
        public AgingDisplayState fromNBT(Tag nbt) {
            // TODO
            return null;
        }
    }
}