package com.minelittlepony.client.transform;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

import com.minelittlepony.client.model.IClientModel;

public class PostureElytra implements PonyPosture<EntityLivingBase> {
    @Override
    public void transform(IClientModel model, EntityLivingBase entity, double motionX, double motionY, double motionZ, float yaw, float ticks) {
        GlStateManager.rotatef(90, 1, 0, 0);
        GlStateManager.translatef(0, entity.isSneaking() ? 0.2F : -1, 0);
    }
}
