package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;

public class TMOEntities {
    public static final EntityType<SmallDragonFireballEntity> SMALL_DRAGON_FIREBALL;
    public static final EntityType<FireballAreaEffectCloudEntity> FIREBALL_AREA_EFFECT_CLOUD;

    static {
        SMALL_DRAGON_FIREBALL = FabricEntityTypeBuilder.<SmallDragonFireballEntity>create(SpawnGroup.MISC, SmallDragonFireballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
        FIREBALL_AREA_EFFECT_CLOUD = FabricEntityTypeBuilder.<FireballAreaEffectCloudEntity>create(SpawnGroup.MISC, FireballAreaEffectCloudEntity::new).fireImmune().dimensions(EntityDimensions.changing(6.0F, 0.5F)).trackable(10, 2147483647).build();
    }

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "small_dragon_fireball"), SMALL_DRAGON_FIREBALL);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "fireball_area_effect_cloud"), FIREBALL_AREA_EFFECT_CLOUD);
    }
}