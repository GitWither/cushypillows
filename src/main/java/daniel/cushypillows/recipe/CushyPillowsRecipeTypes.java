package daniel.cushypillows.recipe;

import daniel.cushypillows.CushyPillows;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class CushyPillowsRecipeTypes {
    public static final RecipeSerializer<PillowDecorationRecipe> PILLOW_DECORATION_SERIALIZER = registerRecipeSerializer("pillow_decoration", new SpecialRecipeSerializer<>(PillowDecorationRecipe::new));

    public static final RecipeType<PillowDecorationRecipe> PILLOW_DECORATION = registerRecipeType("pillow_decoration");


    private static <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String id, RecipeSerializer<T> serializer) {
        Identifier identifier = Identifier.of(CushyPillows.MOD_ID, id);

        return Registry.register(Registries.RECIPE_SERIALIZER, identifier, serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String id) {
        Identifier identifier = Identifier.of(CushyPillows.MOD_ID, id);

        return Registry.register(Registries.RECIPE_TYPE, identifier, new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.toString();
            }
        });
    }

    public static void initialize() {

    }
}
