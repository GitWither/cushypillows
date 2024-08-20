package daniel.cushypillows.item;

import daniel.cushypillows.entity.PillowEntity;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PillowItem extends BlockItem {
    public PillowItem(Block block) {
        super(block, new Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        final ItemStack stackInHand = user.getStackInHand(hand);

        if (world.isClient) return TypedActionResult.pass(stackInHand);

        PillowEntity pillow = new PillowEntity(user, world);
        pillow.setItem(stackInHand);
        pillow.setVelocity(user, user.getPitch(), user.getYaw(), 0, 0.75F, 0);
        world.spawnEntity(pillow);

        if (!user.getAbilities().creativeMode) {
            stackInHand.decrement(1);
            user.getItemCooldownManager().set(this, 5);
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(stackInHand);
    }

    public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
        NbtCompound blockEntityNbt = BlockItem.getBlockEntityNbt(stack);

        if (blockEntityNbt == null || !blockEntityNbt.contains(BannerBlockEntity.PATTERNS_KEY)) return;

        NbtList patterns = blockEntityNbt.getList(BannerBlockEntity.PATTERNS_KEY, 10);

        for (int i = 0; i < patterns.size() && i < 6; ++i) {
            NbtCompound nbtCompound2 = patterns.getCompound(i);
            DyeColor dyeColor = DyeColor.byId(nbtCompound2.getInt(BannerBlockEntity.COLOR_KEY));
            RegistryEntry<BannerPattern> registryEntry = BannerPattern.byId(nbtCompound2.getString(BannerBlockEntity.PATTERN_KEY));

            if (registryEntry == null) continue;

            registryEntry.getKey().map((key) ->
                    key.getValue().toShortTranslationKey()).ifPresent((translationKey) ->
                    tooltip.add(Text.translatable("block.minecraft.banner." + translationKey + "." + dyeColor.getName()).formatted(Formatting.GRAY)));
        }
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendBannerTooltip(stack, tooltip);
    }
}
