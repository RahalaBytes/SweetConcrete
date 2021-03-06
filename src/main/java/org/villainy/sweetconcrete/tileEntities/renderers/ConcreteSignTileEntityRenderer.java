package org.villainy.sweetconcrete.tileEntities.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.Style;
import org.villainy.sweetconcrete.blocks.ConcreteSignBlock;
import org.villainy.sweetconcrete.client.Atlases;
import org.villainy.sweetconcrete.tileEntities.ConcreteSignTileEntity;

import java.util.List;

import static net.minecraft.state.properties.BlockStateProperties.*;

public class ConcreteSignTileEntityRenderer extends TileEntityRenderer<ConcreteSignTileEntity> {
    private final SignTileEntityRenderer.SignModel model = new SignTileEntityRenderer.SignModel();

    public ConcreteSignTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    private RenderMaterial getSignMaterial(Block block) {
        ConcreteSignBlock signBlock = (ConcreteSignBlock) block;
        return Atlases.getConcreteMaterial(signBlock.dyeColor);
    }

    @Override
    public void render(ConcreteSignTileEntity tileEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        final float scale = 0.6666667F;
        matrixStackIn.push();

        BlockState state = tileEntity.getBlockState();

        Direction facing = state.get(HORIZONTAL_FACING);
        AttachFace face = state.get(FACE);

        int rotAroundY = 0;
        int rotAroundX = 0;

        switch (face) {
            case CEILING:
                rotAroundX = 90;
                break;
            case FLOOR:
                rotAroundX = -90;
                break;
        }

        switch (facing) {
            case SOUTH:
                break;
            case NORTH:
                rotAroundY = 180;
                break;
            case EAST:
                rotAroundY = 90;
                break;
            case WEST:
                rotAroundY = -90;
                break;
        }

        matrixStackIn.translate(0.5, 0.5, 0.5);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotAroundY));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(rotAroundX));
        matrixStackIn.translate(0.0, -0.3125, -0.4375D);
        this.model.signStick.showModel = false;

        matrixStackIn.push();
        matrixStackIn.scale(scale, -scale, -scale);
        {
            RenderMaterial material = SignTileEntityRenderer.getMaterial(state.getBlock());
            IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);
            this.model.signBoard.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
            this.model.signStick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        }
        {
            IVertexBuilder ivertexbuilder = getSignMaterial(tileEntity.getBlockState().getBlock()).getBuffer(bufferIn, RenderType::getEntityCutout);
            this.model.signBoard.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
            this.model.signStick.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        }
        matrixStackIn.pop();
        FontRenderer fontrenderer = this.renderDispatcher.getFontRenderer();
        matrixStackIn.translate(0.0D, (double) 0.33333334F, (double) 0.046666667F);
        matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);

        int color = tileEntity.getTextColor().getTextColor();
        int red = (int) ((double) NativeImage.getRed(color) * 0.4D);
        int green = (int) ((double) NativeImage.getGreen(color) * 0.4D);
        int blue = (int) ((double) NativeImage.getBlue(color) * 0.4D);
        int adjustedColor = NativeImage.getCombined(0, blue, green, red);

        for (int line = 0; line < 4; ++line)
        {
            IReorderingProcessor ireorderingprocessor = tileEntity.func_242686_a(line, (text) -> {
                List<IReorderingProcessor> list = fontrenderer.trimStringToWidth(text, 90);
                return list.isEmpty() ? IReorderingProcessor.field_242232_a : list.get(0);
            });
            if (ireorderingprocessor != null) {
                float f3 = (float)(-fontrenderer.func_243245_a(ireorderingprocessor) / 2);
                fontrenderer.func_238416_a_(ireorderingprocessor, f3, (float)(line * 10 - 20), adjustedColor, false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
            }
        }

        matrixStackIn.pop();
    }
}