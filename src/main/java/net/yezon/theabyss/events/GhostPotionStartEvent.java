package net.yezon.theabyss.events;

import net.yezon.theabyss.TheabyssMod;

import net.minecraft.world.GameType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

public class GhostPotionStartEvent {

	public static void executeEvent(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TheabyssMod.LOGGER.warn("Failed to load dependency entity for Event GhostPotionStart!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).setGameType(GameType.SPECTATOR);
	}
}
