package daniel.cushypillows.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import daniel.cushypillows.block.CushyPillowsBlocks;
import daniel.cushypillows.block.PillowBlock;
import daniel.cushypillows.block.entity.PillowBlockEntity;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    @Shadow @Final private BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    @Unique
    private final PillowBlockEntity renderPillow = new PillowBlockEntity(BlockPos.ORIGIN, CushyPillowsBlocks.WHITE_PILLOW.getDefaultState());


    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/block/Block.getDefaultState()Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER), cancellable = true)
    private void cushypillows$injectPillowItemRendering(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci, @Local Block block) {
        if (block instanceof PillowBlock pillowBlock) {
            this.renderPillow.readFrom(stack, pillowBlock.getColor());

            this.blockEntityRenderDispatcher.renderEntity(renderPillow, matrices, vertexConsumers, light, overlay);
            ci.cancel();
        }
    }
}
