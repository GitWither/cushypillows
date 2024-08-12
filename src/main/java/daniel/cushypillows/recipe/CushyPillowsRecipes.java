package daniel.cushypillows.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShieldDecorationRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;

public final class CushyPillowsRecipes {
    public static final RecipeSerializer<PillowDecorationRecipe> PILLOW_DECORATION = RecipeSerializer.register("crafting_special_pillow_decoration", new SpecialRecipeSerializer<>(PillowDecorationRecipe::new));
}
