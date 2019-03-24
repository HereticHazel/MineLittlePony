package com.minelittlepony.client.render.entities;

import javax.annotation.Nonnull;

import com.minelittlepony.client.model.ModelWrapper;
import com.minelittlepony.client.model.entities.ModelSeapony;
import com.minelittlepony.client.render.RenderPonyMob;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class RenderPonyGuardian extends RenderGuardian {

    public static final ResourceLocation SEAPONY = new ResourceLocation("minelittlepony", "textures/entity/seapony.png");

    private static final ModelWrapper MODEL_WRAPPER = new ModelWrapper(new ModelSeapony());

    private RenderPonyMob.Proxy<EntityGuardian> ponyRenderer;

    public RenderPonyGuardian(RenderManager manager) {
        super(manager);
        mainModel = MODEL_WRAPPER.getBody();

        ponyRenderer = new RenderPonyMob.Proxy<EntityGuardian>(layerRenderers, manager, MODEL_WRAPPER) {
            @Override
            public ResourceLocation getTexture(EntityGuardian entity) {
                return RenderPonyGuardian.this.getTexture(entity);
            }
        };
    }

    @Override
    @Nonnull
    protected final ResourceLocation getEntityTexture(EntityGuardian entity) {
        return ponyRenderer.getTextureFor(entity);
    }

    @Override
    protected void preRenderCallback(EntityGuardian entity, float ticks) {
        ponyRenderer.preRenderCallback(entity, ticks);
    }

    @Override
    public void doRender(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
        float origin = entity.height;

        // aligns the beam to their horns
        entity.height = entity instanceof EntityElderGuardian ? 6 : 3;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        // The beams in RenderGuardian leave lighting disabled, so we need to change it back. #MojangPls
        GlStateManager.enableLighting();
        entity.height = origin;
    }

    protected ResourceLocation getTexture(EntityGuardian entity) {
        return SEAPONY;
    }

    public static class Elder extends RenderPonyGuardian {

        public Elder(RenderManager manager) {
            super(manager);
        }

        @Override
        protected void preRenderCallback(EntityGuardian entity, float ticks) {
            super.preRenderCallback(entity, ticks);
            GlStateManager.scalef(2.35F, 2.35F, 2.35F);
        }
    }
}
