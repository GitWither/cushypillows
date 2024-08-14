package daniel.cushypillows.data;

import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.entity.CushyPillowsBlockEntities;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CushyPillowsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        final FabricDataGenerator.Pack pack = dataGenerator.createBuiltinResourcePack(new Identifier(CushyPillows.MOD_ID, "builtin"));

        pack.addProvider(PillowModelProvider::new);
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
}
