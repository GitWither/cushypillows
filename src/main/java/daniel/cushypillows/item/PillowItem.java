package daniel.cushypillows.item;

import daniel.cushypillows.entity.PillowEntity;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class PillowItem extends BlockItem {
    public PillowItem(Block block) {
        super(block, new Settings().component(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT));
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BannerPatternsComponent bannerPatternsComponent = stack.get(DataComponentTypes.BANNER_PATTERNS);

        if (bannerPatternsComponent != null) {
            for(int i = 0; i < Math.min(bannerPatternsComponent.layers().size(), 6); ++i) {
                BannerPatternsComponent.Layer layer = bannerPatternsComponent.layers().get(i);
                tooltip.add(layer.getTooltipText().formatted(Formatting.GRAY));
            }
        }
    }
}
