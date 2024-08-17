package daniel.cushypillows.util;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;

public record PatternEntry(RegistryEntry<BannerPattern> pattern, DyeColor color) {
}
