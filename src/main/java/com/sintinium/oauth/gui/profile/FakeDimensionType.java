package com.sintinium.oauth.gui.profile;

import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class FakeDimensionType {

    public static DimensionType getInstance() {
        return DimensionType.create(OptionalLong.empty(), true, false, false, false, 1.0, false, false, false, false, false, 0, 16, 0, DimensionType.OVERWORLD_LOCATION.location(), DimensionType.OVERWORLD_EFFECTS, 1f);
    }

}
