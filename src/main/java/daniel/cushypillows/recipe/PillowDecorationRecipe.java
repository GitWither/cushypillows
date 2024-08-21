package daniel.cushypillows.recipe;

import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.item.PillowItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class PillowDecorationRecipe extends SpecialCraftingRecipe {

    public PillowDecorationRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack pillowStack = ItemStack.EMPTY;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack currentItem = input.getStackInSlot(i);

            if (currentItem.isEmpty()) continue;

            if (currentItem.getItem() instanceof BannerItem) {
                if (!bannerStack.isEmpty()) {
                    return false;
                }

                bannerStack = currentItem;

                continue;
            }

            if (currentItem.getItem() instanceof PillowItem) {
                if (!pillowStack.isEmpty()) {
                    return false;
                }

                BannerPatternsComponent bannerPatternsComponent = bannerStack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
                if (!bannerPatternsComponent.layers().isEmpty()) {
                    return false;
                }

                pillowStack = currentItem;

                continue;
            }

            return false;
        }

        return !pillowStack.isEmpty() && !bannerStack.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack currentItem = input.getStackInSlot(i);

            if (currentItem.isEmpty()) continue;

            if (currentItem.getItem() instanceof BannerItem) {
                bannerStack = currentItem;
                break;
            }
        }


        BannerItem bannerItem = (BannerItem) bannerStack.getItem();
        ItemStack pillowStack = new ItemStack(PillowBlock.getForColor(bannerItem.getColor()).asItem());

        pillowStack.set(DataComponentTypes.BANNER_PATTERNS, bannerStack.get(DataComponentTypes.BANNER_PATTERNS));

        return pillowStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CushyPillowsRecipeTypes.PILLOW_DECORATION_SERIALIZER;
    }
}
