
package net.yezon.theabyss.world.biome;

import net.yezon.theabyss.particle.SpursBiome01Particle;
import net.yezon.theabyss.block.StoneBlock;
import net.yezon.theabyss.block.BlaruLeavesBlock;
import net.yezon.theabyss.TheAbyss;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.ISeedReader;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.state.BooleanProperty;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import java.util.Set;
import java.util.Random;
import java.util.List;

@TheAbyss.Processor.Tag
public class AybssMountainBiome extends TheAbyss.Processor {
	public static Biome biome;

	public AybssMountainBiome(TheAbyss instance) {
		super(instance, 555);
		FMLJavaModLoadingContext.get().getModEventBus().register(new BiomeRegisterHandler());
	}

	private static class BiomeRegisterHandler {
		@SubscribeEvent
		public void registerBiomes(RegistryEvent.Register<Biome> event) {
			if (biome == null) {
				BiomeAmbience effects = new BiomeAmbience.Builder().setFogColor(-15851474).setWaterColor(-13421773).setWaterFogColor(-10066330)
						.withSkyColor(-15851474).withFoliageColor(-16777216).withGrassColor(-16764109)
						.setAmbientSound((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
								.getValue(new ResourceLocation("theabyss:blue_mountains_ambience")))
						.setAdditionsSound(new SoundAdditionsAmbience(
								(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("theabyss:random_fx")),
								0.0111D))
						.setMusic(new BackgroundMusicSelector((net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS
								.getValue(new ResourceLocation("theabyss:the_abyss_music")), 12000, 24000, true))
						.setParticle(new ParticleEffectAmbience(SpursBiome01Particle.particle, 0.03f)).build();
				BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder()
						.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(StoneBlock.block.getDefaultState(),
								StoneBlock.block.getDefaultState(), StoneBlock.block.getDefaultState())));
				biomeGenerationSettings.withStructure(StructureFeatures.STRONGHOLD);
				biomeGenerationSettings.withStructure(StructureFeatures.MINESHAFT);
				biomeGenerationSettings.withStructure(StructureFeatures.PILLAGER_OUTPOST);
				biomeGenerationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
						Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT)
								.withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 20))));
				DefaultBiomeFeatures.withCavesAndCanyons(biomeGenerationSettings);
				DefaultBiomeFeatures.withLavaAndWaterLakes(biomeGenerationSettings);
				MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
				biome = new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.7000000000000001f).scale(1f)
						.temperature(0.4f).downfall(0.2f).setEffects(effects).withMobSpawnSettings(mobSpawnInfo.copy())
						.withGenerationSettings(biomeGenerationSettings.build()).build();
				event.getRegistry().register(biome.setRegistryName("theabyss:blue_mountain"));
			}
		}
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
	}

	private static class CustomLeaveVineTreeDecorator extends LeaveVineTreeDecorator {
		public static final CustomLeaveVineTreeDecorator instance = new CustomLeaveVineTreeDecorator();
		public static com.mojang.serialization.Codec<LeaveVineTreeDecorator> codec;
		public static TreeDecoratorType tdt;
		static {
			codec = com.mojang.serialization.Codec.unit(() -> instance);
			tdt = new TreeDecoratorType(codec);
			tdt.setRegistryName("blue_mountain_lvtd");
			ForgeRegistries.TREE_DECORATOR_TYPES.register(tdt);
		}

		@Override
		protected TreeDecoratorType<?> func_230380_a_() {
			return tdt;
		}

		@Override
		protected void func_227424_a_(IWorldWriter ww, BlockPos bp, BooleanProperty bpr, Set<BlockPos> sbc, MutableBoundingBox mbb) {
			this.func_227423_a_(ww, bp, Blocks.VINE.getDefaultState(), sbc, mbb);
		}
	}

	private static class CustomTrunkVineTreeDecorator extends TrunkVineTreeDecorator {
		public static final CustomTrunkVineTreeDecorator instance = new CustomTrunkVineTreeDecorator();
		public static com.mojang.serialization.Codec<CustomTrunkVineTreeDecorator> codec;
		public static TreeDecoratorType tdt;
		static {
			codec = com.mojang.serialization.Codec.unit(() -> instance);
			tdt = new TreeDecoratorType(codec);
			tdt.setRegistryName("blue_mountain_tvtd");
			ForgeRegistries.TREE_DECORATOR_TYPES.register(tdt);
		}

		@Override
		protected TreeDecoratorType<?> func_230380_a_() {
			return tdt;
		}

		@Override
		protected void func_227424_a_(IWorldWriter ww, BlockPos bp, BooleanProperty bpr, Set<BlockPos> sbc, MutableBoundingBox mbb) {
			this.func_227423_a_(ww, bp, Blocks.VINE.getDefaultState(), sbc, mbb);
		}
	}

	private static class CustomCocoaTreeDecorator extends CocoaTreeDecorator {
		public static final CustomCocoaTreeDecorator instance = new CustomCocoaTreeDecorator();
		public static com.mojang.serialization.Codec<CustomCocoaTreeDecorator> codec;
		public static TreeDecoratorType tdt;
		static {
			codec = com.mojang.serialization.Codec.unit(() -> instance);
			tdt = new TreeDecoratorType(codec);
			tdt.setRegistryName("blue_mountain_ctd");
			ForgeRegistries.TREE_DECORATOR_TYPES.register(tdt);
		}

		public CustomCocoaTreeDecorator() {
			super(0.2f);
		}

		@Override
		protected TreeDecoratorType<?> func_230380_a_() {
			return tdt;
		}

		@Override
		public void func_225576_a_(ISeedReader p_225576_1_, Random p_225576_2_, List<BlockPos> p_225576_3_, List<BlockPos> p_225576_4_,
				Set<BlockPos> p_225576_5_, MutableBoundingBox p_225576_6_) {
			if (!(p_225576_2_.nextFloat() >= 0.2F)) {
				int i = p_225576_3_.get(0).getY();
				p_225576_3_.stream().filter((p_236867_1_) -> {
					return p_236867_1_.getY() - i <= 2;
				}).forEach((p_242865_5_) -> {
					for (Direction direction : Direction.Plane.HORIZONTAL) {
						if (p_225576_2_.nextFloat() <= 0.25F) {
							Direction direction1 = direction.getOpposite();
							BlockPos blockpos = p_242865_5_.add(direction1.getXOffset(), 0, direction1.getZOffset());
							if (Feature.isAirAt(p_225576_1_, blockpos)) {
								BlockState blockstate = BlaruLeavesBlock.block.getDefaultState();
								this.func_227423_a_(p_225576_1_, blockpos, blockstate, p_225576_5_, p_225576_6_);
							}
						}
					}
				});
			}
		}
	}
}
