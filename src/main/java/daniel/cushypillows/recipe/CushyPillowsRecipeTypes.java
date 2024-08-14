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
    private static final Map<RecipeType<?>, RecipeSerializer<?>> SERIALIZERS = new HashMap<>();

    public static final RecipeType<PillowDecorationRecipe> PILLOW_DECORATION = registerRecipeType("pillow_decoration");

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String id) {
        Identifier identifier = new Identifier(CushyPillows.MOD_ID, id);
        RecipeType<T> type = Registry.register(Registries.RECIPE_TYPE, identifier, new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.toString();
            }
        });

         RecipeSerializer<PillowDecorationRecipe> serializer = RecipeSerializer.register(identifier.toString(), new SpecialRecipeSerializer<>(PillowDecorationRecipe::new));
         SERIALIZERS.put(type, serializer);

         return type;
    }

    public static <T extends Recipe<?>> RecipeSerializer<T> getSerializer(RecipeType<T> type) {
        return (RecipeSerializer<T>) SERIALIZERS.get(type);
    }

    public static void initialize() {

    }
}
