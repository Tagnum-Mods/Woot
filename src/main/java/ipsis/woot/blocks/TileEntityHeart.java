package ipsis.woot.blocks;

import ipsis.Woot;
import ipsis.woot.drops.generation.LootGenerator;
import ipsis.woot.factory.*;
import ipsis.woot.factory.progress.IProgessRecipe;
import ipsis.woot.factory.progress.MockRecipeUnitProvider;
import ipsis.woot.factory.progress.PowerRecipe;
import ipsis.woot.factory.progress.RFRecipeProgressTracker;
import ipsis.woot.factory.structure.FactoryConfig;
import ipsis.woot.factory.structure.FactoryConfigBuilder;
import ipsis.woot.factory.structure.FactoryLayout;
import ipsis.woot.factory.structure.locator.IMultiBlockMaster;
import ipsis.woot.util.helpers.ConnectedCapHelper;
import ipsis.woot.util.helpers.WorldHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityHeart extends TileEntity implements ITickable, IMultiBlockMaster {

    private SimpleTickTracker tickTracker;
    private FactoryLayout factoryLayout; // The factory blocks and where they are
    private FactoryConfig factoryConfig; // The configuration of the factory
    private SpawnRecipeConsumer spawnRecipeConsumer;
    private TrackingState trackingState = new TrackingState();
    private RFRecipeProgressTracker recipeProgressTracker;

    public TileEntityHeart() {

        // NOTE: this is called without a world on startup
        tickTracker = new SimpleTickTracker();
        tickTracker.setStructureTickCount(20);
        spawnRecipeConsumer = new SpawnRecipeConsumer();
    }

    @Override
    public void update() {

        // Only the server ticks
        if (WorldHelper.isClientWorld(world))
            return;

        // Cannot set this on create as the world may not be set
        if (factoryLayout == null) {
            factoryLayout = new FactoryLayout();
            factoryLayout.setWorldPos(getWorld(), getPos());
            factoryLayout.setDirtyLayout();
        }

        // Watch for fake, accelerated ticks
        if (!tickTracker.tick(getWorld()))
            return;

        factoryLayout.tick(tickTracker);

        if (factoryLayout.isFormed()) {

            if (factoryLayout.hasChanged()) {
                factoryConfig = FactoryConfigBuilder.create(factoryLayout);
                IProgessRecipe iProgessRecipe = new PowerRecipe();
                iProgessRecipe.setRecipe(120, 120);
                recipeProgressTracker = new RFRecipeProgressTracker(iProgessRecipe, new MockRecipeUnitProvider());
                factoryLayout.clearChanged();
            }

            // Redstone signal STOPS the machine
            if (!world.isBlockPowered(getPos())) {
                recipeProgressTracker.tick(tickTracker);
                if (recipeProgressTracker.isComplete()) {
                    if (spawnRecipeConsumer.consume()) {

                        LootGenerator.Setup setup = Woot.LOOT_GENERATOR.getNewSetup(
                                factoryConfig.getFakeMobKey(), factoryConfig.getLooting(),
                                1, getWorld().getDifficultyForLocation(getPos()));
                        setup.itemHandlers.addAll(ConnectedCapHelper.getConnectedItemHandlers(getWorld(), factoryConfig.getExportPos()));
                        setup.fuildHandlers.addAll(ConnectedCapHelper.getConnectedFluidHandlers(getWorld(), factoryConfig.getExportPos(), false));
                        Woot.LOOT_GENERATOR.generate(getWorld(), setup);
                    }
                    recipeProgressTracker.reset();
                }
            }
        }
    }

    /**
     * This is for misc state for things like BloodMagic, how much xp we have unused etc
     */
    private class TrackingState {
        private int storedXp = 0;
        public void setStoredXp(int v) { this.storedXp = v; }
    }

    /**
     * IMultiBlockMaster
     */
    public void interrupt() {
        if (factoryLayout != null)
            factoryLayout.setDirtyLayout();
    }
}
