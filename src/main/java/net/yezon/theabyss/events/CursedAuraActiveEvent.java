package net.yezon.theabyss.events;

import net.yezon.theabyss.TheabyssMod;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.particles.ParticleTypes;

import java.util.Map;

public class CursedAuraActiveEvent {

	public static void executeEvent(Map<String, Object> dependencies) {
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		if (world instanceof ServerWorld) {
			((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE, x, y, z, (int) 10, 20, 10, 20, 0.01);
		}
		if (world instanceof ServerWorld) {
			((ServerWorld) world).spawnParticle(ParticleTypes.LAVA, x, y, z, (int) 10, 20, 10, 20, 0.01);
		}
	}
}
