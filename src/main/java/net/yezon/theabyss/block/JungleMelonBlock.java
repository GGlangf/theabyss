
package net.yezon.theabyss.block;

import net.yezon.theabyss.itemgroup.TheAbyssItemGroup;
import net.yezon.theabyss.item.JungleMelonItemItem;
import net.yezon.theabyss.TheAbyss;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.loot.LootContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import java.util.List;
import java.util.Collections;

@TheAbyss.Processor.Tag
public class JungleMelonBlock extends TheAbyss.Processor {
	@ObjectHolder("theabyss:jungle_melon")
	public static final Block block = null;

	public JungleMelonBlock(TheAbyss instance) {
		super(instance, 214);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items.add(() -> new BlockItem(block, new Item.Properties().group(TheAbyssItemGroup.tab)).setRegistryName(block.getRegistryName()));
	}

	public static class CustomBlock extends Block {
		public CustomBlock() {
			super(Block.Properties.create(Material.MISCELLANEOUS).sound(SoundType.CROP).hardnessAndResistance(0.6f, 10f).setLightLevel(s -> 5)
					.setNeedsPostProcessing((bs, br, bp) -> true).setEmmisiveRendering((bs, br, bp) -> true));
			setRegistryName("jungle_melon");
		}

		@Override
		public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return 15;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(JungleMelonItemItem.block, (int) (3)));
		}
	}
}
