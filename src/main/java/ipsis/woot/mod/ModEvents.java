package ipsis.woot.mod;

import com.google.gson.JsonObject;
import ipsis.woot.Woot;
import ipsis.woot.factory.multiblock.MultiBlockTracker;
import ipsis.woot.loot.DropRegistry;
import ipsis.woot.server.command.WootCommand;
import ipsis.woot.shards.MobShardItem;
import ipsis.woot.simulation.FakePlayerPool;
import ipsis.woot.simulation.MobSimulator;
import ipsis.woot.simulation.Tartarus;
import ipsis.woot.util.FakeMob;
import ipsis.woot.util.FakeMobKey;
import ipsis.woot.util.helper.ItemEntityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.dimension.Dimension;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.util.List;

public class ModEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsEvent(LivingDropsEvent event) {

        /**
         * Entity->LivingEntity->MobEntity
         */

        if (!(event.getEntity() instanceof MobEntity))
            return;

        MobEntity mobEntity = (MobEntity)event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        if (damageSource == null)
            return;

        if (!FakePlayerPool.isFakePlayer(damageSource.getTrueSource()))
            return;

        // Cancel our fake spawns
        event.setCanceled(true);

        List<ItemStack> drops = ItemEntityHelper.convertToItemStacks(event.getDrops());
        FakeMobKey fakeMobKey = new FakeMobKey(new FakeMob(mobEntity), event.getLootingLevel());
        if (fakeMobKey.getMob().isValid())
            DropRegistry.get().learn(fakeMobKey, drops);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onLivingDeathEvent(LivingDeathEvent event) {

        // Only player kills
        if (!(event.getSource().getTrueSource() instanceof PlayerEntity))
            return;

        if (event.getEntityLiving() == null)
            return;

        PlayerEntity killer = (PlayerEntity)event.getSource().getTrueSource();
        LivingEntity victim = event.getEntityLiving();

        // Ignore fakeplayer
        if (killer instanceof FakePlayer)
            return;

        // Ignore player killing player
        if (victim instanceof PlayerEntity)
            return;

        if (!(victim instanceof MobEntity))
            return;

        FakeMob fakeMob = new FakeMob((MobEntity)victim);
        if (!fakeMob.isValid())
            return;

        MobShardItem.handleKill(killer, fakeMob);
    }

    private static final long MULTI_BLOCK_TRACKER_DELAY = 20;
    private static long lastWorldTick = 0;
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        Dimension dimension = event.world.getDimension();
        if (dimension.getType() != ModDimensions.tartarusDimensionType) {
            if (event.world.getGameTime() > lastWorldTick + MULTI_BLOCK_TRACKER_DELAY) {
                lastWorldTick += MULTI_BLOCK_TRACKER_DELAY;
                MultiBlockTracker.get().run(event.world);
            }
        } else {
            MobSimulator.get().tick(event.world);
        }
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        new WootCommand(event.getCommandDispatcher());

        // Force load the simulation world
        Tartarus.get().setWorld(DimensionManager.getWorld(event.getServer(), ModDimensions.tartarusDimensionType, true, true));
    }

    @SubscribeEvent
    public void onServerStop(final FMLServerStoppingEvent event) {

        JsonObject jsonObject = DropRegistry.get().toJson();

    }
}
