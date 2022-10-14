package com.ultramega.botanypotstiers.events;

import com.ultramega.botanypotstiers.block.TieredBlockEntityBotanyPot;
import com.ultramega.botanypotstiers.data.recipes.potinteraction.PotInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nullable;

@Cancelable
public class LookupInteractionEvent extends TieredBotanyPotEvent {
    private final BlockState state;
    private final Player player;
    private final InteractionHand hand;
    private final ItemStack stack;
    @Nullable
    private final PotInteraction original;
    @Nullable
    private PotInteraction result;

    public LookupInteractionEvent(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, TieredBlockEntityBotanyPot pot, PotInteraction original) {
        super(level, pos, pot);
        this.state = state;
        this.player = player;
        this.hand = hand;
        this.stack = stack;
        this.original = original;
        this.result = original;
    }

    public BlockState getBlockState() {
        return this.state;
    }

    public Player getPlayer() {
        return this.player;
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Nullable
    public PotInteraction getOriginal() {
        return this.original;
    }

    @Nullable
    public PotInteraction getLookupResult() {
        return this.result;
    }

    public void setLookupResult(@Nullable PotInteraction newResult) {
        this.result = newResult;
    }
}