package daniel.cushypillows.recipe;

import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.item.PillowItem;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PillowDecorationRecipe extends SpecialCraftingRecipe {

    public PillowDecorationRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        ItemStack pillowStack = ItemStack.EMPTY;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack currentItem = inventory.getStack(i);

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

                if (BlockItem.getBlockEntityNbt(currentItem) != null) {
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
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack currentItem = inventory.getStack(i);

            if (currentItem.isEmpty()) continue;

            if (currentItem.getItem() instanceof BannerItem) {
                bannerStack = currentItem;
                break;
            }
        }


        BannerItem bannerItem = (BannerItem) bannerStack.getItem();
        ItemStack pillowStack = new ItemStack(PillowBlock.getForColor(bannerItem.getColor()).asItem());

        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(bannerStack);
        NbtCompound bannerData = nbtCompound == null ? new NbtCompound() : nbtCompound.copy();
        BlockItem.setBlockEntityNbt(pillowStack, CushyPillowsBlockEntities.PILLOW, bannerData);

        return pillowStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CushyPillowsRecipeTypes.getSerializer(this.getType());
    }
}
