package fun.wich.mixin;

import fun.wich.ChiseledBookshelfEnchanting;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public abstract class ChiseledBookshelfEnchanting_EnchantingTableBlockMixin {
	@Inject(method="canAccessPowerProvider", at=@At("RETURN"), cancellable=true)
	private static void AllowChiseledBookshelvesToActAsPowerProvider(World world, BlockPos tablePos, BlockPos providerOffset, CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) return;
		BlockState state = world.getBlockState(tablePos.add(providerOffset));
		if (state.getBlock() instanceof ChiseledBookshelfBlock) {
			if (world.getBlockState(tablePos.add(providerOffset.getX() / 2, providerOffset.getY(), providerOffset.getZ() / 2))
					.isIn(BlockTags.ENCHANTMENT_POWER_TRANSMITTER)) {
				MinecraftServer server;
				ServerWorld serverWorld = null;
				if (world instanceof ServerWorld) serverWorld = (ServerWorld) world;
				else if ((server = world.getServer()) != null) serverWorld = server.getWorld(world.getRegistryKey());
				int chiseledEnchantingBooks = 6;
				if (serverWorld != null) {
					chiseledEnchantingBooks = serverWorld.getGameRules().getInt(ChiseledBookshelfEnchanting.CHISELED_ENCHANTING_BOOKS);
				}
				int slots = 0;
				for (BooleanProperty property : ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES) {
					if (state.get(property)) slots++;
					if (slots >= chiseledEnchantingBooks) {
						cir.setReturnValue(true);
						return;
					}
				}
			}
		}
	}
}