package net.yezon.theabyss.events;

import net.yezon.theabyss.potion.AfterLifePotionEffect;
import net.yezon.theabyss.TheabyssMod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

public class ReturnToLifeEvent {

	public static void executeEvent(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				TheabyssMod.LOGGER.warn("Failed to load dependency entity for Event ReturnToLife!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).removePotionEffect(AfterLifePotionEffect.potion);
		}
	}
}
