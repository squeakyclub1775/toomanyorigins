package io.github.merchantpug.toomanyorigins.util;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.registry.TMOEntityGroups;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.explosion.Explosion;

public class TMODataTypes {
    public static final SerializableDataType<EntityGroup> TMO_ENTITY_GROUP =
            SerializableDataType.mapped(EntityGroup.class, HashBiMap.create(ImmutableMap.of(
                    "smiteable", TMOEntityGroups.SMITEABLE,
                    "player_undead", TMOEntityGroups.PLAYER_UNDEAD
            )));
    public static final SerializableDataType<Explosion.DestructionType> EXPLOSION_BEHAVIOR =
            SerializableDataType.mapped(Explosion.DestructionType.class, HashBiMap.create(ImmutableMap.of(
                    "none", Explosion.DestructionType.NONE,
                    "break", Explosion.DestructionType.BREAK,
                    "destroy", Explosion.DestructionType.DESTROY
            )));
    public static final SerializableDataType<UseAction> EAT_ACTION =
            SerializableDataType.mapped(UseAction.class, HashBiMap.create(ImmutableMap.of(
                    "eat", UseAction.EAT,
                    "drink", UseAction.DRINK
            )));
    public static final SerializableDataType<Hand> HAND =
            SerializableDataType.mapped(Hand.class, HashBiMap.create(ImmutableMap.of(
                    "mainhand", Hand.MAIN_HAND,
                    "offhand", Hand.OFF_HAND
            )));
    public static final SerializableDataType<FoodComponent> FOOD_COMPONENT = SerializableDataType.compound(FoodComponent.class, new SerializableData()
                    .add("hunger", SerializableDataType.INT)
                    .add("saturation", SerializableDataType.FLOAT)
                    .add("meat", SerializableDataType.BOOLEAN, false)
                    .add("always_edible", SerializableDataType.BOOLEAN, false)
                    .add("snack", SerializableDataType.BOOLEAN, false),
            (data) -> {
                FoodComponent.Builder builder = new FoodComponent.Builder().hunger(data.getInt("hunger")).saturationModifier(data.getFloat("saturation"));
                if (data.getBoolean("meat")) {
                    builder.meat();
                }
                if (data.getBoolean("always_edible")) {
                    builder.alwaysEdible();
                }
                if (data.getBoolean("snack")) {
                    builder.snack();
                }
                return builder.build();
            },
            (data, fc) -> {
                SerializableData.Instance inst = data.new Instance();
                inst.set("hunger", fc.getHunger());
                inst.set("saturation", fc.getSaturationModifier());
                inst.set("meat", fc.isMeat());
                inst.set("always_edible", fc.isAlwaysEdible());
                inst.set("snack", fc.isSnack());
                return inst;
            });
}