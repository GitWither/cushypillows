package daniel.cushypillows.block.entity;

import com.mojang.datafixers.util.Pair;
import daniel.cushypillows.block.PillowBlock;
import net.fabricmc.fabric.impl.transfer.transaction.TransactionManagerImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static net.minecraft.block.entity.BannerBlockEntity.PATTERNS_KEY;

public class PillowBlockEntity extends BlockEntity {
    private DyeColor baseColor;
    private NbtList patternListNbt;
    private List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns;
    private long lastSquishTime;

    public PillowBlockEntity(BlockPos pos, BlockState state) {
        super(CushyPillowsBlockEntities.PILLOW, pos, state);
        this.baseColor = ((PillowBlock)state.getBlock()).getColor();
    }

    public long getLastSquishTime() {
        return lastSquishTime;
    }

    public List<Pair<RegistryEntry<BannerPattern>, DyeColor>> getPatterns() {
        if (this.patterns == null) {
            this.patterns = BannerBlockEntity.getPatternsFromNbt(this.baseColor, this.patternListNbt);
        }
        return this.patterns;
    }

    public void readFrom(ItemStack stack, DyeColor baseColor) {
        this.baseColor = baseColor;
        this.readFrom(stack);
    }

    public void readFrom(ItemStack stack) {
        this.patternListNbt = BannerBlockEntity.getPatternListNbt(stack);
        this.patterns = null;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (this.world != null && type == Block.NOTIFY_NEIGHBORS) {
            this.lastSquishTime = this.world.getTime();
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.patternListNbt != null) {
            nbt.put(PATTERNS_KEY, this.patternListNbt);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.patternListNbt = nbt.getList(PATTERNS_KEY, NbtElement.COMPOUND_TYPE);
        this.patterns = null;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public void squish() {
        if (this.world != null && !this.world.isClient()) {
            this.world.addSyncedBlockEvent(this.getPos(), this.getCachedState().getBlock(), Block.NOTIFY_NEIGHBORS, 0);
        }
    }
}
