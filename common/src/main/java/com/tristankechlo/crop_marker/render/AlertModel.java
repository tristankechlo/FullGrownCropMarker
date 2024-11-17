package com.tristankechlo.crop_marker.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class AlertModel extends Model {

    private final ModelPart root;

    public AlertModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
    }

    public void animate(float partialTick) {
        root.yRot = (partialTick * 0.05f) % 360;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int var3, int var4, int var5) {
        this.root.render(poseStack, consumer, var3, var4, var5);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -10.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 0)
                        .addBox(-1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

}
