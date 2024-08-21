package daniel.cushypillows.block.entity;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.PillowBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class PillowBlockEntity extends BlockEntity {
    public static final String PATTERNS_KEY = "Patterns";
    public static final String PATTERN_KEY = "Pattern";
    public static final String COLOR_KEY = "Color";

    private DyeColor baseColor;
    private BannerPatternsComponent patterns = BannerPatternsComponent.DEFAULT;
    private long lastSquishTime;

    public PillowBlockEntity(BlockPos pos, BlockState state) {
        super(CushyPillowsBlockEntities.PILLOW, pos, state);
        this.baseColor = ((PillowBlock)state.getBlock()).getColor();
    }

    public long getLastSquishTime() {
        return lastSquishTime;
    }

    public BannerPatternsComponent getPatterns() {
        return this.patterns;
    }

    public DyeColor getBaseColor() {
        return this.baseColor;
    }

    public void readFrom(ItemStack stack, DyeColor baseColor) {
        this.baseColor = baseColor;
        this.readComponents(stack);
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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.patterns.equals(BannerPatternsComponent.DEFAULT)) {
            nbt.put(PATTERNS_KEY, BannerPatternsComponent.CODEC.encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this.patterns).getOrThrow());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains(PATTERNS_KEY)) {
            BannerPatternsComponent.CODEC.parse(
                    registryLookup.getOps(NbtOps.INSTANCE),
                    nbt.get(PATTERNS_KEY)
            ).resultOrPartial(patterns -> CushyPillows.LOGGER.error("Failed to parse banner patterns: '{}'", patterns))
                    .ifPresent(patterns -> this.patterns = patterns);
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createNbt(registryLookup);
    }

    @Override
    protected void readComponents(BlockEntity.ComponentsAccess components) {
        super.readComponents(components);
        this.patterns = components.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(DataComponentTypes.BANNER_PATTERNS, this.patterns);
    }

    public void squish() {
        if (this.world != null && !this.world.isClient()) {
            this.world.addSyncedBlockEvent(this.getPos(), this.getCachedState().getBlock(), Block.NOTIFY_NEIGHBORS, 0);
        }
    }

    public ItemStack getPickStack() {
        ItemStack pillowStack = new ItemStack(BannerBlock.getForColor(this.baseColor));
        pillowStack.applyComponentsFrom(this.createComponentMap());

        return pillowStack;
    }

    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        nbt.remove(PATTERNS_KEY);
    }
}
