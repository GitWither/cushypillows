package daniel.cushypillows.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import daniel.cushypillows.CushyPillows;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.entity.CushyPillowsEntities;
import daniel.cushypillows.item.CushyPillowsItems;
import daniel.cushypillows.recipe.CushyPillowsRecipeTypes;
import daniel.cushypillows.recipe.PillowDecorationRecipe;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.*;

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
        pack.addProvider(PillowLootTableProvider::new);
    }

    public static class PillowLootTableProvider extends FabricBlockLootTableProvider {
        protected PillowLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            List<Block> pillows = Lists.newArrayList(
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

            for (Block pillow : pillows) {
                addDrop(pillow, LootTable.builder()
                        .pool(this.addSurvivesExplosionCondition(
                                pillow,
                                LootPool.builder()
                                        .rolls(ConstantLootNumberProvider.create(1.0f))
                                        .with(ItemEntry.builder(pillow)
                                                .apply(CopyComponentsLootFunction.builder(CopyComponentsLootFunction.Source.BLOCK_ENTITY)
                                                        .include(DataComponentTypes.BANNER_PATTERNS)
                                                )
                                        )
                                )
                        )
                );
            }
        }
    }

    private static class PillowAdvancementProvider extends FabricAdvancementProvider {
        protected PillowAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generateAdvancement(RegistryWrapper.WrapperLookup lookup, Consumer<net.minecraft.advancement.AdvancementEntry> consumer) {
            final AdvancementEntry husbandryRoot = Advancement.Builder.create().build(Identifier.of("husbandry/root"));

            AdvancementEntry root = Advancement.Builder.create()
                    .parent(husbandryRoot)
                    .display(
                            CushyPillowsItems.LIGHT_BLUE_PILLOW.asItem(),
                            Text.translatable("advancements.husbandry.pillow.root.title"),
                            Text.translatable("advancements.husbandry.pillow.root.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("get_pillows", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().items(
                            CushyPillowsItems.WHITE_PILLOW,
                            CushyPillowsItems.ORANGE_PILLOW,
                            CushyPillowsItems.MAGENTA_PILLOW,
                            CushyPillowsItems.LIGHT_BLUE_PILLOW,
                            CushyPillowsItems.YELLOW_PILLOW,
                            CushyPillowsItems.LIME_PILLOW,
                            CushyPillowsItems.PINK_PILLOW,
                            CushyPillowsItems.GRAY_PILLOW,
                            CushyPillowsItems.LIGHT_GRAY_PILLOW,
                            CushyPillowsItems.CYAN_PILLOW,
                            CushyPillowsItems.PURPLE_PILLOW,
                            CushyPillowsItems.BLUE_PILLOW,
                            CushyPillowsItems.BROWN_PILLOW,
                            CushyPillowsItems.GREEN_PILLOW,
                            CushyPillowsItems.RED_PILLOW,
                            CushyPillowsItems.BLACK_PILLOW
                    ).build()))
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
                    .build(Identifier.of(CushyPillows.MOD_ID, "husbandry/root"));

            ItemStack creeperPillow = new ItemStack(CushyPillowsItems.LIME_PILLOW);

            BannerPatternsComponent bannerPatternsComponent = new BannerPatternsComponent.Builder()
                    .add(lookup.getWrapperOrThrow(RegistryKeys.BANNER_PATTERN), BannerPatterns.CREEPER, DyeColor.BLACK).build();
            creeperPillow.set(DataComponentTypes.BANNER_PATTERNS, bannerPatternsComponent);

            AdvancementEntry craftPillowPattern = Advancement.Builder.create()
                    .parent(root)
                    .display(
                            creeperPillow,
                            Text.translatable("advancements.husbandry.pillow.pattern.title"),
                            Text.translatable("advancements.husbandry.pillow.pattern.desc"),
                            null,
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    .criterion("craft_pattern_pillow", RecipeCraftedCriterion.Conditions.create(Identifier.of(CushyPillowsRecipeTypes.PILLOW_DECORATION.toString())))
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                    .build(Identifier.of(CushyPillows.MOD_ID, "husbandry/pattern"));

            AdvancementEntry featherBurst = Advancement.Builder.create()
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
                                                                    .blocks(
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
                                                                    )
                                                    ),
                                            ItemPredicate.Builder.create()
                                    )
                    )
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                    .build(Identifier.of(CushyPillows.MOD_ID, "husbandry/feather_burst"));

            AdvancementEntry pillowFight = Advancement.Builder.create()
                    .parent(featherBurst)
                    .display(
                            CushyPillowsItems.RED_PILLOW,
                            Text.translatable("advancements.husbandry.pillow.pillow_fight.title"),
                            Text.translatable("advancements.husbandry.pillow.pillow_fight.desc"),
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
                    ).build(Identifier.of(CushyPillows.MOD_ID, "husbandry/pillow_fight"));

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
            FabricTagBuilder builder = getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, Identifier.of(CushyPillows.MOD_ID, "pillows")));

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
            Model templatePillow = new Model(Optional.of(Identifier.of(CushyPillows.MOD_ID, "item/template_pillow")), Optional.empty());
            blockStateModelGenerator.registerBuiltin(
                    Identifier.of(CushyPillows.MOD_ID, "block/pillow"),
                    CushyPillowsBlocks.WHITE_PILLOW
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
            Model parent = new Model(Optional.of(Identifier.of(CushyPillows.MOD_ID, "item/template_pillow")), Optional.empty());
        }
    }

    public static class PillowRecipeProvider extends FabricRecipeProvider {
        public PillowRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
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
                        .criterion(hasItem(wool), conditionsFromItem(wool.asItem()))
                        .showNotification(true)
                        .offerTo(exporter, Identifier.of(CushyPillows.MOD_ID, getRecipeName(pillow)));
            }

            ComplexRecipeJsonBuilder.create(PillowDecorationRecipe::new).offerTo(exporter, Identifier.of(CushyPillows.MOD_ID, "pillow_decoration"));
        }
    }
}
