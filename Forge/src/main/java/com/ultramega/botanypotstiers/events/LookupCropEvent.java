package com.ultramega.botanypotstiers.events;

import com.ultramega.botanypotstiers.block.TieredBlockEntityBotanyPot;
import com.ultramega.botanypotstiers.data.recipes.crop.Crop;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nullable;

@Cancelable
public class LookupCropEvent extends TieredBotanyPotEvent {
    final ItemStack stack;
    @Nullable
    private final Crop original;
    @Nullable
    private Crop result;

    public LookupCropEvent(Level level, BlockPos pos, TieredBlockEntityBotanyPot pot, ItemStack stack, Crop original) {
        super(level, pos, pot);
        this.stack = stack;
        this.original = original;
        this.result = original;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Nullable
    public Crop getOriginal() {
        return this.original;
    }

    @Nullable
    public Crop getLookupResult() {
        return this.result;
    }

    public void setLookupResult(@Nullable Crop newResult) {
        this.result = newResult;
    }
}