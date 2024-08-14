package daniel.cushypillows.data;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import daniel.cushypillows.item.CushyPillowsItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CushyPillowsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        final FabricDataGenerator.Pack pack = dataGenerator.createBuiltinResourcePack(new Identifier(CushyPillows.MOD_ID, "builtin"));

        pack.addProvider(PillowTagProvider::new);
        pack.addProvider(PillowModelProvider::new);
        pack.addProvider(PillowRecipeProvider::new);
    }

    private static class PillowTagProvider extends FabricTagProvider.BlockTagProvider {

        public PillowTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            FabricTagBuilder builder = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier(CushyPillows.MOD_ID, "pillows")));

            builder.add(
                    CushyPillowsBlocks.WHITE_PILLOW,
                    CushyPillowsBlocks.ORANGE_PILLOW,
                    CushyPillowsBlocks.MAGENTA_PILLOW,
                    CushyPillowsBlocks.LIGHT_BLUE_PILLOW,
                    CushyPillowsBlocks.YELLOW_PILLOW,
                    CushyPillowsBlocks.LIME_PILLOW,
                    CushyPillowsBlocks.PINK_PILLOW,
                    CushyPillowsBlocks.GRAY_PILLOW,
                    CushyPillowsBlocks.LIGHT_GRAY_PILLOW,
                    CushyPillowsBlocks.CYAN_PILLOW,
                    CushyPillowsBlocks.PURPLE_PILLOW,
                    CushyPillowsBlocks.BLUE_PILLOW,
                    CushyPillowsBlocks.BROWN_PILLOW,
                    CushyPillowsBlocks.GREEN_PILLOW,
                    CushyPillowsBlocks.RED_PILLOW,
                    CushyPillowsBlocks.BLACK_PILLOW
            );
        }
    }

    private static class PillowModelProvider extends FabricModelProvider {
        private PillowModelProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            Model templatePillow = new Model(Optional.of(new Identifier(CushyPillows.MOD_ID, "item/template_pillow")), Optional.empty());
            blockStateModelGenerator.registerBuiltin(
                    new Identifier(CushyPillows.MOD_ID, "block/pillow"),
                    Blocks.OAK_PLANKS
            ).includeWithItem(templatePillow,
                    CushyPillowsBlocks.WHITE_PILLOW,
                    CushyPillowsBlocks.ORANGE_PILLOW,
                    CushyPillowsBlocks.MAGENTA_PILLOW,
                    CushyPillowsBlocks.LIGHT_BLUE_PILLOW,
                    CushyPillowsBlocks.YELLOW_PILLOW,
                    CushyPillowsBlocks.LIME_PILLOW,
                    CushyPillowsBlocks.PINK_PILLOW,
                    CushyPillowsBlocks.GRAY_PILLOW,
                    CushyPillowsBlocks.LIGHT_GRAY_PILLOW,
                    CushyPillowsBlocks.CYAN_PILLOW,
                    CushyPillowsBlocks.PURPLE_PILLOW,
                    CushyPillowsBlocks.BLUE_PILLOW,
                    CushyPillowsBlocks.BROWN_PILLOW,
                    CushyPillowsBlocks.GREEN_PILLOW,
                    CushyPillowsBlocks.RED_PILLOW,
                    CushyPillowsBlocks.BLACK_PILLOW
            );
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            Model parent = new Model(Optional.of(new Identifier(CushyPillows.MOD_ID, "item/template_pillow")), Optional.empty());
        }
    }

    public static class PillowRecipeProvider extends FabricRecipeProvider {
        public PillowRecipeProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, CushyPillowsItems.WHITE_PILLOW)
                    .pattern("WFW")
                    .pattern(" D ")
                    .input('F', Items.FEATHER)
                    .input('D', Items.WHITE_DYE)
                    .input('W', Items.WHITE_WOOL)
                    .criterion(hasItem(Items.FEATHER), conditionsFromItem(Items.FEATHER))
                    .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                    .criterion(hasItem(Items.WHITE_WOOL), conditionsFromItem(Items.WHITE_WOOL))
                    .offerTo(exporter, new Identifier(getRecipeName(CushyPillowsItems.WHITE_PILLOW)));
        }
    }
}
