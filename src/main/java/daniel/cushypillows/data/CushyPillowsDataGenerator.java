package daniel.cushypillows.data;

import com.google.common.collect.Maps;
import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.entity.CushyPillowsEntities;
import daniel.cushypillows.item.CushyPillowsItems;
import daniel.cushypillows.recipe.CushyPillowsRecipeTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.server.advancement.vanilla.VanillaAdvancementProviders;
import net.minecraft.data.server.advancement.vanilla.VanillaHusbandryTabAdvancementGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.AdvancementCommand;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CushyPillowsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        final FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(PillowTagProvider::new);
        pack.addProvider(PillowModelProvider::new);
        pack.addProvider(PillowRecipeProvider::new);
        pack.addProvider(PillowAdvancementProvider::new);
    }

    private static class PillowAdvancementProvider extends FabricAdvancementProvider {

        protected PillowAdvancementProvider(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateAdvancement(Consumer<Advancement> consumer) {
            final Advancement husbandryRoot = Advancement.Builder.create().build(new Identifier("husbandry/root"));

            Advancement root = Advancement.Builder.create()
                    .parent(husbandryRoot)
                    .display(
                            CushyPillowsItems.LIME_PILLOW.asItem(),
                            Text.translatable("advancements.husbandry.pillow.root.title"),
                            Text.translatable("advancements.husbandry.pillow.root.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("get_pillows", InventoryChangedCriterion.Conditions.items(CushyPillowsItems.WHITE_PILLOW))
                    .criteriaMerger(CriterionMerger.AND)
                    .build(new Identifier(CushyPillows.MOD_ID, "husbandry/root"));

            Advancement craftPillowPattern = Advancement.Builder.create()
                    .parent(root)
                    .display(
                            CushyPillowsItems.LIME_PILLOW.asItem(), // TODO: Put pillow with creeper pattern here
                            Text.translatable("advancements.husbandry.pillow.pattern.title"),
                            Text.translatable("advancements.husbandry.pillow.pattern.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("craft_pattern_pillow", RecipeCraftedCriterion.Conditions.create(new Identifier(CushyPillowsRecipeTypes.PILLOW_DECORATION.toString())))
                    .criteriaMerger(CriterionMerger.AND)
                    .build(new Identifier(CushyPillows.MOD_ID, "husbandry/pattern"));

            Advancement featherBurst = Advancement.Builder.create()
                    .parent(root)
                    .display(
                            Items.FEATHER.asItem(),
                            Text.translatable("advancements.husbandry.pillow.feather_burst.title"),
                            Text.translatable("advancements.husbandry.pillow.feather_burst.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("interact_pillow",
                            ItemCriterion.Conditions
                                    .createItemUsedOnBlock(
                                            LocationPredicate.Builder
                                                    .create()
                                                    .block(
                                                            BlockPredicate.Builder
                                                                    .create()
                                                                    .blocks(CushyPillowsBlocks.WHITE_PILLOW)
                                                                    .build()
                                                    ),
                                            ItemPredicate.Builder.create()
                                    )
                    )
                    .criteriaMerger(CriterionMerger.AND)
                    .build(new Identifier(CushyPillows.MOD_ID, "husbandry/feather_burst"));

            Advancement pillowFight = Advancement.Builder.create()
                    .parent(featherBurst)
                    .display(
                            CushyPillowsItems.RED_PILLOW,
                            Text.translatable("advancements.husbandry.pillow.pillow_fight.title"),
                            Text.translatable("advancements.husbandry.pillow.pillow_fight.description"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion(
                            "thrown_pillow",
                            PlayerHurtEntityCriterion.Conditions.create(
                                    DamagePredicate.Builder.create()
                                            .type(
                                                    DamageSourcePredicate.Builder.create()
                                                            .tag(TagPredicate.expected(DamageTypeTags.IS_PROJECTILE))
                                                            .directEntity(
                                                                    EntityPredicate.Builder.create()
                                                                            .type(CushyPillowsEntities.PILLOW_ENTITY)
                                                            )
                                            )
                            )
                    ).build(new Identifier(CushyPillows.MOD_ID, "husbandry/pillow_fight"));

            consumer.accept(root);
            consumer.accept(craftPillowPattern);
            consumer.accept(featherBurst);
            consumer.accept(pillowFight);
        }
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
            EnumMap<DyeColor, Block> dyeToWool = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
                map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
                map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
                map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
                map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
                map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
                map.put(DyeColor.LIME, Blocks.LIME_WOOL);
                map.put(DyeColor.PINK, Blocks.PINK_WOOL);
                map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
                map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
                map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
                map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
                map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
                map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
                map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
                map.put(DyeColor.RED, Blocks.RED_WOOL);
                map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
            });

            List<Item> dyes = Arrays.stream(DyeColor.values()).map(DyeItem::byColor).collect(Collectors.toUnmodifiableList());
            List<Item> pillows = Arrays.stream(DyeColor.values()).map(dye -> PillowBlock.getForColor(dye).asItem()).toList();

            RecipeProvider.offerDyeableRecipes(exporter, dyes, pillows, "pillows");

            for (DyeColor color : DyeColor.values()) {
                Block pillow = PillowBlock.getForColor(color);
                Block wool = dyeToWool.get(color);

                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, pillow)
                        .pattern("WFW")
                        .input('F', Items.FEATHER)
                        .input('W', wool.asItem())
                        .group("pillows")
                        .criterion(hasItem(wool), conditionsFromTag(ItemTags.WOOL))
                        .showNotification(true)
                        .offerTo(exporter, new Identifier(getRecipeName(pillow)));
            }
        }
    }
}
