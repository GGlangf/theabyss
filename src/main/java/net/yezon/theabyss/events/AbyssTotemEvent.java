package net.yezon.theabyss.events;

import net.yezon.theabyss.particle.EndSwordPTParticle;
import net.yezon.theabyss.item.TotemOfAbyssItem;
import net.yezon.theabyss.TheabyssMod;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import java.util.Random;
import java.util.Map;

public class AbyssTotemEvent {

	public static void executeEvent(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TheabyssMod.LOGGER.warn("Failed to load dependency entity for Event AbyssTotem!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				TheabyssMod.LOGGER.warn("Failed to load dependency itemstack for Event AbyssTotem!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1) < 5) {
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) 24);
			if (world.isRemote()) {
				Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(TotemOfAbyssItem.block));
			}
			if ((entity instanceof PlayerEntity) ? ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(TotemOfAbyssItem.block)) : false) {
				if (itemstack.getItem() == TotemOfAbyssItem.block) {
					{
						ItemStack _ist = itemstack;
						if (_ist.attemptDamageItem((int) 25, new Random(), null)) {
							_ist.shrink(1);
							_ist.setDamage(0);
						}
					}
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos((int) x, (int) y, (int) z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
						SoundCategory.NEUTRAL, (float) 1, (float) 1, false);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(EndSwordPTParticle.particle, x, y, z, (int) 10, 0.5, 0.5, 0.5, 1);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, (int) 1, 0.5, 0.5, 0.5, 1);
			}
			if (world instanceof ServerWorld) {
				((ServerWorld) world).spawnParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, (int) 2, 0.5, 0.5, 0.5, 1);
			}
		}
	}
}
