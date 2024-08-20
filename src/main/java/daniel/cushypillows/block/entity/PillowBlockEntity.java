package daniel.cushypillows.block.entity;

import com.mojang.datafixers.util.Pair;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.util.PatternEntry;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PillowBlockEntity extends BlockEntity {
    public static final String PATTERNS_KEY = "Patterns";
    public static final String PATTERN_KEY = "Pattern";
    public static final String COLOR_KEY = "Color";

    private DyeColor baseColor;
    private NbtList patternListNbt;
    private List<PatternEntry> patterns;
    private long lastSquishTime;

    public PillowBlockEntity(BlockPos pos, BlockState state) {
        super(CushyPillowsBlockEntities.PILLOW, pos, state);
        this.baseColor = ((PillowBlock)state.getBlock()).getColor();
    }

    public long getLastSquishTime() {
        return lastSquishTime;
    }

    public List<PatternEntry> getPatterns() {
        if (this.patterns == null) {
            this.patterns = getPatternsFromNbt(this.patternListNbt);
        }
        return this.patterns;
    }

    public DyeColor getBaseColor() {
        return this.baseColor;
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

    public ItemStack getPickStack() {
        ItemStack bannerStack = new ItemStack(PillowBlock.getForColor(this.baseColor));

        if (this.patternListNbt != null && !this.patternListNbt.isEmpty()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.put(PATTERNS_KEY, this.patternListNbt.copy());
            BlockItem.setBlockEntityNbt(bannerStack, this.getType(), nbtCompound);
        }

        return bannerStack;
    }

    private static List<PatternEntry> getPatternsFromNbt(@Nullable NbtList patternListNbt) {
        ArrayList<PatternEntry> result = new ArrayList<>();

        if (patternListNbt == null) return result;

        for (int i = 0; i < patternListNbt.size(); ++i) {
            NbtCompound nbtCompound = patternListNbt.getCompound(i);
            RegistryEntry<BannerPattern> bannerPatternEntry = BannerPattern.byId(nbtCompound.getString(PATTERN_KEY));
            if (bannerPatternEntry == null) continue;

            int colorId = nbtCompound.getInt(COLOR_KEY);
            result.add(new PatternEntry(bannerPatternEntry, DyeColor.byId(colorId)));
        }

        return result;
    }
}
