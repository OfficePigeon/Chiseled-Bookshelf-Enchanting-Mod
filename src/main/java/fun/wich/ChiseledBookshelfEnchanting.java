package fun.wich;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ChiseledBookshelfEnchanting implements ModInitializer {
	public static final GameRules.Key<GameRules.IntRule> CHISELED_ENCHANTING_BOOKS = GameRuleRegistry.register("chiseledEnchantingBooks", GameRules.Category.MISC, GameRuleFactory.createIntRule(6, 0, 6));

	@Override
	public void onInitialize() { }
}