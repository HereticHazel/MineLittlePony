package com.minelittlepony.client.render.entity;

import javax.annotation.Nonnull;

import com.minelittlepony.client.mixin.IResizeable;
import com.minelittlepony.client.model.entity.ModelGuardianPony;
import com.minelittlepony.client.render.entity.RenderPonyMob.Proxy;
import com.minelittlepony.client.render.entity.feature.LayerHeldPonyItem;
import com.minelittlepony.client.render.entity.feature.LayerHeldPonyItemMagical;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.GuardianEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.Identifier;

public class RenderPonyGuardian extends GuardianEntityRenderer {

    public static final Identifier SEAPONY = new Identifier("minelittlepony", "textures/entity/seapony.png");

    private final Proxy<GuardianEntity, ModelGuardianPony> ponyRenderer;

    public RenderPonyGuardian(EntityRenderDispatcher manager, EntityRendererRegistry.Context context) {
        super(manager);

        features.clear();
        ponyRenderer = new Proxy<GuardianEntity, ModelGuardianPony>(features, manager, new ModelGuardianPony()) {
            @Override
            public Identifier findTexture(GuardianEntity entity) {
                return SEAPONY;
            }

            @Override
            protected LayerHeldPonyItem<GuardianEntity, ModelGuardianPony> createItemHoldingLayer() {
                return new LayerHeldPonyItemMagical<>(this);
            }
        };
        model = ponyRenderer.getModel();
    }

    @Override
    @Nonnull
    public final Identifier getTexture(GuardianEntity entity) {
        return ponyRenderer.getTextureFor(entity);
    }

    @Override
    protected void scale(GuardianEntity entity, MatrixStack stack, float ticks) {
        ponyRenderer.scale(entity, stack, ticks);
    }

    @Override
    public void render(GuardianEntity entity, float entityYaw, float tickDelta, MatrixStack stack, VertexConsumerProvider renderContext, int lightUv) {
        IResizeable resize = (IResizeable)entity;
        EntityDimensions origin = resize.getCurrentSize();

        // aligns the beam to their horns
        resize.setCurrentSize(EntityDimensions.changing(origin.width, entity instanceof ElderGuardianEntity ? 6 : 3));

        super.render(entity, entityYaw, tickDelta, stack, renderContext, lightUv);

        // The beams in RenderGuardian leave lighting disabled, so we need to change it back. #MojangPls
        RenderSystem.enableLighting();
        resize.setCurrentSize(origin);
    }

    public static class Elder extends RenderPonyGuardian {

        public Elder(EntityRenderDispatcher manager, EntityRendererRegistry.Context context) {
            super(manager, context);
        }

        @Override
        protected void scale(GuardianEntity entity, MatrixStack stack, float ticks) {
            super.scale(entity, stack, ticks);
            stack.scale(2.35F, 2.35F, 2.35F);
        }
    }
}