package com.minelittlepony.client.hdskins;

import com.minelittlepony.client.SkinsProxy;
import com.minelittlepony.client.pony.Pony;
import com.minelittlepony.hdskins.HDSkins;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.util.Identifier;

public class HDSkinsProxy extends SkinsProxy {

    @Override
    public Identifier getSkinTexture(GameProfile profile) {

        Identifier skin = HDSkins.getInstance().getTextures(profile).get(MinecraftProfileTexture.Type.SKIN);
        if (skin != null && Pony.getBufferedImage(skin) != null) {
            return skin;
        }
        return super.getSkinTexture(profile);
    }

    @Override
    public void parseSkins() {
        HDSkins.getInstance().getSkinParser().execute();
    }
}
