/*
MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.merchantpug.toomanyorigins.mixin.fabric;

import io.github.apace100.origins.power.AttributePower;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttributePower.class)
public class AttributePowerMixin extends Power {
    @Unique float previousMaxHealth;
    @Unique float previousHealthPercent;
    @Unique float afterMaxHealth;

    public AttributePowerMixin(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    @Inject(method = "onAdded", at = @At("HEAD"), remap = false)
    private void onAddedHead(CallbackInfo ci) {
        if (!player.world.isClient) {
            previousMaxHealth = player.getMaxHealth();
            previousHealthPercent = player.getHealth() / previousMaxHealth;
        }
    }

    @Inject(method = "onAdded", at = @At("TAIL"), remap = false)
    private void onAddedTail(CallbackInfo ci) {
        if(!player.world.isClient) {
            afterMaxHealth = player.getMaxHealth();
            if(afterMaxHealth != previousMaxHealth) {
                player.setHealth(afterMaxHealth * previousHealthPercent);
            }
        }
    }

    @Inject(method = "onRemoved", at = @At("HEAD"), remap = false)
    private void onRemovedHead(CallbackInfo ci) {
        if (!player.world.isClient) {
            previousMaxHealth = player.getMaxHealth();
            previousHealthPercent = player.getHealth() / previousMaxHealth;
        }
    }

    @Inject(method = "onRemoved", at = @At("TAIL"), remap = false)
    private void onRemovedTail(CallbackInfo ci) {
        if(!player.world.isClient) {
            afterMaxHealth = player.getMaxHealth();
            if(afterMaxHealth != previousMaxHealth) {
                player.setHealth(afterMaxHealth * previousHealthPercent);
            }
        }
    }
}
