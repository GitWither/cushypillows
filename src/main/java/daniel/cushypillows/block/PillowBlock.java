package daniel.cushypillows.block;

import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.block.entity.PillowBlockEntity;
import daniel.cushypillows.particle.CushyPillowsParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class PillowBlock extends BlockWithEntity {
    private static final Map<DyeColor, Block> COLORED_PILLOWS = new EnumMap<>(DyeColor.class);

    public static final IntProperty ROTATION = Properties.ROTATION;
    public static final BooleanProperty ATTACHED = Properties.ATTACHED;
    public static final BooleanProperty TRIMMED = BooleanProperty.of("trimmed");

    private static final VoxelShape DEFAULT = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 5.0, 14.0);
    private static final VoxelShape BED = DEFAULT.offset(0, -2, 0);

    private final DyeColor color;

    protected PillowBlock(DyeColor color) {
        super(Settings.create()
                .breakInstantly()
                .hardness(1.0f)
                .mapColor(MapColor.WHITE)
                .sounds(BlockSoundGroup.WOOL)
                .burnable()
        );

        this.setDefaultState(
                this.stateManager.getDefaultState()
                        .with(ROTATION, 0)
                        .with(ATTACHED, false)
                        .with(TRIMMED, false)
        );
        this.color = color;
        COLORED_PILLOWS.put(color, this);
    }

    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    private void bounceEntity(Entity entity) {
        Vec3d vel = entity.getVelocity();

        if (vel.y < 0.0) {
            double y = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vel.x, -vel.y * 0.66f * y, vel.z);
        }
    }

    public DyeColor getColor() {
        return color;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.MAIN_HAND && player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
            world.setBlockState(pos, state.with(TRIMMED, !state.get(TRIMMED)));

            return ActionResult.SUCCESS;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof PillowBlockEntity pillowBlockEntity)) {
            return ActionResult.PASS;
        }

        world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        pillowBlockEntity.squish();
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    (ParticleEffect) CushyPillowsParticleTypes.FEATHERS,
                    pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f,
                    3,
                    0.2, 0, 0.2, 0
            );
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw() + 180.0f))
                .with(ATTACHED, ctx.getWorld().getBlockState(ctx.getBlockPos().down()).isIn(BlockTags.BEDS))
                .with(TRIMMED, false);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(ATTACHED) ? BED : DEFAULT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
        builder.add(ATTACHED);
        builder.add(TRIMMED);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, CushyPillowsBlockEntities.PILLOW).ifPresent(pillow -> pillow.readFrom(itemStack));
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PillowBlockEntity(pos, state);
    }

    public static Block getForColor(DyeColor color) {
        return COLORED_PILLOWS.getOrDefault(color, CushyPillowsBlocks.WHITE_PILLOW);
    }
}
