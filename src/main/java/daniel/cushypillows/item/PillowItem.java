package daniel.cushypillows.item;

import daniel.cushypillows.block.entity.PillowBlockEntity;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound pillowNbt = BlockItem.getBlockEntityNbt(stack);

        if (pillowNbt == null || !pillowNbt.contains(PillowBlockEntity.PATTERNS_KEY)) return;

        NbtList patterns = pillowNbt.getList(PillowBlockEntity.PATTERNS_KEY, 10);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6 && i < patterns.size(); i++) {
            NbtCompound patternCompound = patterns.getCompound(i);

            DyeColor dyeColor = DyeColor.byId(patternCompound.getInt(PillowBlockEntity.COLOR_KEY));
            RegistryEntry<BannerPattern> registryEntry = BannerPattern.byId(patternCompound.getString(PillowBlockEntity.PATTERN_KEY));

            if (registryEntry == null) continue;

            Optional<RegistryKey<BannerPattern>> registryKeyOptional = registryEntry.getKey();
            if (registryKeyOptional.isEmpty()) continue;

            RegistryKey<BannerPattern> registrykey = registryKeyOptional.get();
            String translationKey = registrykey.getValue().toShortTranslationKey();

            builder.setLength(0);
            builder.append("block.minecraft.banner.");
            builder.append(translationKey);
            builder.append(".");
            builder.append(dyeColor.getName());

            Text patternText = Text.translatable(builder.toString()).formatted(Formatting.GRAY);

            tooltip.add(patternText);
        }
    }
}
