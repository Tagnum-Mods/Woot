package ipsis.woot.common.configuration;

import ipsis.woot.util.FakeMob;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Woot has config values that can have an override for specific mobs.
 * Therefore configuration should not be pulled from here but from
 *
 * WootConfig and
 * ConfigHelper
 */

public class Config {

    static final Logger LOGGER = LogManager.getLogger();
    public static final String TAG = "woot.configgui.";

    public static class Common {

        public final IntValue SIMULATION_TICKS;
        public final IntValue SIMULATION_MOB_COUNT;
        public final IntValue SIMULATION_TICKS_PER_SIM_TICK;
        public final IntValue SIMULATION_CELLS_PER_SIM_TICK;
        public final ForgeConfigSpec.BooleanValue TICK_ACCEL;

        private final ForgeConfigSpec.ConfigValue<List<String>> MOB_OVERRIDES;
        public final ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_FULL_MOD;
        public final ForgeConfigSpec.ConfigValue<List<String>> CAPTURE_BLACKLIST_ENTITY;
        public final ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_FULL_MOD;
        public final ForgeConfigSpec.ConfigValue<List<String>> LEARN_BLACKLIST_ITEM;
        public final ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_FULL_MOD;
        public final ForgeConfigSpec.ConfigValue<List<String>> GENERATE_BLACKLIST_ITEM;
        public final ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_FULL_MOD;
        public final ForgeConfigSpec.ConfigValue<List<String>> SHARD_BLACKLIST_ENTITY;

        private final IntValue MASS_COUNT;
        private final IntValue SPAWN_TICKS;
        private final IntValue UNITS_PER_HEALTH;
        private final IntValue MOB_SHARD_KILLS;
        private final IntValue TIER_1_MAX_UNITS;
        private final IntValue TIER_2_MAX_UNITS;
        private final IntValue TIER_3_MAX_UNITS;
        private final IntValue TIER_4_MAX_UNITS;
        private final IntValue TIER_5_MAX_UNITS;
        private final IntValue TIER_1_UNITS_PER_TICK;
        private final IntValue TIER_2_UNITS_PER_TICK;
        private final IntValue TIER_3_UNITS_PER_TICK;
        private final IntValue TIER_4_UNITS_PER_TICK;
        private final IntValue TIER_5_UNITS_PER_TICK;

        public final String MASS_COUNT_TAG = "massCount";
        public final String SPAWN_TICKS_TAG = "spawnTicks";
        public final String MOB_HEALTH_TAG = "mobHealth";
        public final String UNITS_PER_HEALTH_TAG = "unitsPerHealth";
        public final String MOB_TIER_TAG = "mobTier";
        public final String MOB_SHARD_KILLS_TAG = "mobShardKill";

        public final String TIER_1_MAX_UNITS_TAG = "tier1MaxUnits";
        public final String TIER_2_MAX_UNITS_TAG = "tier2MaxUnits";
        public final String TIER_3_MAX_UNITS_TAG = "tier3MaxUnits";
        public final String TIER_4_MAX_UNITS_TAG = "tier4MaxUnits";
        public final String TIER_5_MAX_UNITS_TAG = "tier5MaxUnits";

        public final String TIER_1_UNITS_PER_TICK_TAG = "tier1UnitsPerTick";
        public final String TIER_2_UNITS_PER_TICK_TAG = "tier2UnitsPerTick";
        public final String TIER_3_UNITS_PER_TICK_TAG = "tier3UnitsPerTick";
        public final String TIER_4_UNITS_PER_TICK_TAG = "tier4UnitsPerTick";
        public final String TIER_5_UNITS_PER_TICK_TAG = "tier5UnitsPerTick";

        /**
         * Cells
         */
        private final IntValue CELL_1_CAPACITY;
        private final IntValue CELL_1_MAX_TRANSFER;
        private final IntValue CELL_2_CAPACITY;
        private final IntValue CELL_2_MAX_TRANSFER;
        private final IntValue CELL_3_CAPACITY;
        private final IntValue CELL_3_MAX_TRANSFER;

        public final String CELL_1_CAPACITY_TAG = "cell1Capacity";
        public final String CELL_2_CAPACITY_TAG = "cell2Capacity";
        public final String CELL_3_CAPACITY_TAG = "cell3Capacity";
        public final String CELL_1_MAX_TRANSFER_TAG = "cell1MaxTransfer";
        public final String CELL_2_MAX_TRANSFER_TAG = "cell2MaxTransfer";
        public final String CELL_3_MAX_TRANSFER_TAG = "cell3MaxTransfer";

        /**
         * Upgrades
         */
        private final IntValue CAPACITY_1;
        private final IntValue CAPACITY_2;
        private final IntValue CAPACITY_3;
        private final IntValue EFFICIENCY_1;
        private final IntValue EFFICIENCY_2;
        private final IntValue EFFICIENCY_3;
        private final IntValue MASS_COUNT_1;
        private final IntValue MASS_COUNT_2;
        private final IntValue MASS_COUNT_3;
        private final IntValue RATE_1;
        private final IntValue RATE_2;
        private final IntValue RATE_3;

        public final String CAPACITY_1_TAG = "capacity1ControllerCount";
        public final String CAPACITY_2_TAG = "capacity2ControllerCount";
        public final String CAPACITY_3_TAG = "capacity3ControllerCount";
        public final String EFFICIENCY_1_TAG = "efficiency1Reduction";
        public final String EFFICIENCY_2_TAG = "efficiency2Reduction";
        public final String EFFICIENCY_3_TAG = "efficiency3Reduction";
        public final String MASS_COUNT_1_TAG = "mass1MobCount";
        public final String MASS_COUNT_2_TAG = "mass2MobCount";
        public final String MASS_COUNT_3_TAG = "mass3MobCount";
        public final String RATE_1_TAG = "rate1Increase";
        public final String RATE_2_TAG = "rate2Increase";
        public final String RATE_3_TAG = "rate3Increase";

        final List<String> VALID_OVERRIDE_TAGS = new ArrayList<String>(){{
            add(RATE_1_TAG);
            add(RATE_2_TAG);
            add(RATE_3_TAG);
            add(MASS_COUNT_1_TAG);
            add(MASS_COUNT_2_TAG);
            add(MASS_COUNT_3_TAG);
            add(EFFICIENCY_1_TAG);
            add(EFFICIENCY_2_TAG);
            add(EFFICIENCY_3_TAG);
            add(MASS_COUNT_TAG);
            add(SPAWN_TICKS_TAG);
            add(MOB_HEALTH_TAG);
            add(UNITS_PER_HEALTH_TAG);
            add(MOB_TIER_TAG);
            add(MOB_SHARD_KILLS_TAG);
        }};

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Common configuration settings")
                    .push("common");

            String TAG2 = "simulationTicks";
            SIMULATION_TICKS = builder
                    .comment("Number of ticks between mob simulations")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 40, 20, 20 * 60);

            TAG2 = "simulationMobCount";
            SIMULATION_MOB_COUNT = builder
                    .comment("Number of simulated mobs to learn from")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 500,100, 5000);
            TAG2 = "simulationTickPerSimTick";
            SIMULATION_TICKS_PER_SIM_TICK = builder
                    .comment("Number of ticks per tick of the simulator")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 10, 1, 20 * 60 );
            TAG2 = "simulationCellsPerSimTick";
            SIMULATION_CELLS_PER_SIM_TICK = builder
                    .comment("Number of mobs to simulate per simulator tick")
                    .translation(TAG + TAG2)
                    .defineInRange(TAG2, 8, 1, 128);

            TAG2 = "tickAcceleration";
            TICK_ACCEL = builder
                    .comment("Allow tick acceleration to be used on the factory")
                    .translation(TAG + TAG2)
                    .define(TAG2, true);

            builder.push("blacklist");
            {
                TAG2 = "captureFullMod";
                CAPTURE_BLACKLIST_FULL_MOD = builder
                        .comment("Do not capture any entity from the following mods")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_CAPTURE_BLACKLIST_FULL_MOD);

                TAG2 = "captureEntity";
                CAPTURE_BLACKLIST_ENTITY = builder
                        .comment("Do not capture the following entities")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_CAPTURE_BLACKLIST_ENTITY);

                TAG2 = "learnFullMod";
                LEARN_BLACKLIST_FULL_MOD = builder
                        .comment("Do not learn items from the following mods")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_LEARN_BLACKLIST_FULL_MOD);
                TAG2 = "learnItem";
                LEARN_BLACKLIST_ITEM = builder
                        .comment("Do not learn the following items")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_LEARN_BLACKLIST_ITEM);

                TAG2 = "generateFullMod";
                GENERATE_BLACKLIST_FULL_MOD = builder
                        .comment("Do not generate items from the following mods")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_GENERATE_BLACKLIST_FULL_MOD);
                TAG2 = "generateItem";
                GENERATE_BLACKLIST_ITEM = builder
                        .comment("Do not generate the following items")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_GENERATE_BLACKLIST_ITEM);
                TAG2 = "shardFullMod";
                SHARD_BLACKLIST_FULL_MOD = builder
                        .comment("Do not allow shard creation with entities from the following mods")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_SHARD_BLACKLIST_FULL_MOD);
                TAG2 = "shardEntity";
                SHARD_BLACKLIST_ENTITY = builder
                        .comment("Do not allow shard creation with the following entities")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_SHARD_BLACKLIST_ENTITY);
            }
            builder.pop(); // mob

            builder.push("mob");
            {
                TAG2 = "mobOverrides";
                MOB_OVERRIDES = builder
                        .comment("A list of mob specific factory configuration values")
                        .translation(TAG + TAG2)
                        .define(TAG2, Defaults.DEFAULT_MOB_OVERRIDES);
            }
            builder.pop(); // mob

            builder.push("cell");
            {
                CELL_1_CAPACITY = builder
                        .comment("Storage capacity of a basic cell")
                        .translation(TAG + CELL_1_CAPACITY_TAG)
                        .worldRestart()
                        .defineInRange(CELL_1_CAPACITY_TAG, 10000, 1, Integer.MAX_VALUE);
                CELL_2_CAPACITY = builder
                        .comment("Storage capacity of an advanced cell")
                        .translation(TAG + CELL_2_CAPACITY_TAG)
                        .worldRestart()
                        .defineInRange(CELL_2_CAPACITY_TAG, 100000, 1, Integer.MAX_VALUE);
                CELL_3_CAPACITY = builder
                        .comment("Storage capacity of a premium cell")
                        .translation(TAG + CELL_3_CAPACITY_TAG)
                        .worldRestart()
                        .defineInRange(CELL_3_CAPACITY_TAG, Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
                CELL_1_MAX_TRANSFER = builder
                        .comment("Max transfer rate (per-side) of a basic cell")
                        .translation(TAG + CELL_1_MAX_TRANSFER_TAG)
                        .worldRestart()
                        .defineInRange(CELL_1_MAX_TRANSFER_TAG, 1000, 1, Integer.MAX_VALUE);
                CELL_2_MAX_TRANSFER = builder
                        .comment("Max transfer rate (per-side) of an advanced cell")
                        .translation(TAG + CELL_2_MAX_TRANSFER_TAG)
                        .worldRestart()
                        .defineInRange(CELL_2_MAX_TRANSFER_TAG, 1000, 1, Integer.MAX_VALUE);
                CELL_3_MAX_TRANSFER = builder
                        .comment("Max transfer rate (per-side) of a premium cell")
                        .translation(TAG + CELL_3_MAX_TRANSFER_TAG)
                        .worldRestart()
                        .defineInRange(CELL_3_MAX_TRANSFER_TAG, Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
            }

            builder.push("factory");
            {
                MASS_COUNT = builder
                        .comment("Number of mobs to spawn")
                        .translation(TAG + MASS_COUNT_TAG)
                        .defineInRange(MASS_COUNT_TAG, 1, 1, 100);

                SPAWN_TICKS = builder
                        .comment("Number of ticks to spawn a mob")
                        .translation(TAG + SPAWN_TICKS_TAG)
                        .defineInRange(SPAWN_TICKS_TAG, 16 * 20, 1, 65535);
                UNITS_PER_HEALTH = builder
                        .comment("Number of units for each health")
                        .translation(TAG + UNITS_PER_HEALTH_TAG)
                        .defineInRange(UNITS_PER_HEALTH_TAG, 1, 1, 65535);
                MOB_SHARD_KILLS = builder
                        .comment("Number of kills to program the shard")
                        .translation(TAG + MOB_SHARD_KILLS_TAG)
                        .defineInRange(MOB_SHARD_KILLS_TAG, 5, 1, 65535);

                TIER_1_MAX_UNITS = builder
                        .comment("Max units for a tier 1 mob")
                        .translation(TAG + TIER_1_MAX_UNITS_TAG)
                        .defineInRange(TIER_1_MAX_UNITS_TAG, 20, 5, 65535);
                TIER_2_MAX_UNITS = builder
                        .comment("Max units for a tier 2 mob")
                        .translation(TAG + TIER_2_MAX_UNITS_TAG)
                        .defineInRange(TIER_2_MAX_UNITS_TAG, 40, 5, 65535);
                TIER_3_MAX_UNITS = builder
                        .comment("Max units for a tier 3 mob")
                        .translation(TAG + TIER_3_MAX_UNITS_TAG)
                        .defineInRange(TIER_3_MAX_UNITS_TAG, 60, 5, 65535);
                TIER_4_MAX_UNITS = builder
                        .comment("Max units for a tier 4 mob")
                        .translation(TAG + TIER_4_MAX_UNITS_TAG)
                        .defineInRange(TIER_4_MAX_UNITS_TAG, Integer.MAX_VALUE, 5, Integer.MAX_VALUE);
                TIER_5_MAX_UNITS = builder
                        .comment("Max units for a tier 5 mob")
                        .translation(TAG + TIER_5_MAX_UNITS_TAG)
                        .defineInRange(TIER_5_MAX_UNITS_TAG, Integer.MAX_VALUE, 5, Integer.MAX_VALUE);

                builder.push("cost");
                {
                    TIER_1_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 1 factory")
                            .translation(TAG + TIER_1_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_1_UNITS_PER_TICK_TAG, 5, 1, Integer.MAX_VALUE);
                    TIER_2_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 2 factory")
                            .translation(TAG + TIER_2_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_2_UNITS_PER_TICK_TAG, 10, 1, Integer.MAX_VALUE);
                    TIER_3_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 3 factory")
                            .translation(TAG + TIER_3_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_3_UNITS_PER_TICK_TAG, 20, 1, Integer.MAX_VALUE);
                    TIER_4_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 4 factory")
                            .translation(TAG + TIER_4_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_4_UNITS_PER_TICK_TAG, 30, 1, Integer.MAX_VALUE);
                    TIER_5_UNITS_PER_TICK = builder
                            .comment("Units per tick cost to run a Tier 5 factory")
                            .translation(TAG + TIER_5_UNITS_PER_TICK_TAG)
                            .defineInRange(TIER_5_UNITS_PER_TICK_TAG, 40, 1, Integer.MAX_VALUE);
                }
                builder.pop(); // cost

                builder.push("upgrades");
                {
                    CAPACITY_1 = builder
                            .comment("Number of supported controllers for capacity 1 upgrade")
                            .translation(TAG + CAPACITY_1_TAG)
                            .defineInRange(CAPACITY_1_TAG, 2, 1, 4);
                    CAPACITY_2 = builder
                            .comment("Number of supported controllers for capacity 2 upgrade")
                            .translation(TAG + CAPACITY_2_TAG)
                            .defineInRange(CAPACITY_2_TAG, 3, 1, 4);
                    CAPACITY_3 = builder
                            .comment("Number of supported controllers for capacity 3 upgrade")
                            .translation(TAG + CAPACITY_3_TAG)
                            .defineInRange(CAPACITY_3_TAG, 4, 1, 4);

                    EFFICIENCY_1 = builder
                            .comment("Percentage reduction for efficiency 1 upgrade")
                            .translation(TAG + EFFICIENCY_1_TAG)
                            .defineInRange(EFFICIENCY_1_TAG, 15, 1, 100);
                    EFFICIENCY_2 = builder
                            .comment("Percentage reduction for efficiency 2 upgrade")
                            .translation(TAG + EFFICIENCY_2_TAG)
                            .defineInRange(EFFICIENCY_2_TAG, 25, 1, 100);
                    EFFICIENCY_3 = builder
                            .comment("Percentage reduction for efficiency 3 upgrade")
                            .translation(TAG + EFFICIENCY_3_TAG)
                            .defineInRange(EFFICIENCY_3_TAG, 30, 1, 100);

                    MASS_COUNT_1 = builder
                            .comment("Number of mobs to spawn for mass 1 upgrade")
                            .translation(TAG + MASS_COUNT_1_TAG)
                            .defineInRange(MASS_COUNT_1_TAG, 2, 1, 100);
                    MASS_COUNT_2 = builder
                            .comment("Number of mobs to spawn for mass 2 upgrade")
                            .translation(TAG + MASS_COUNT_2_TAG)
                            .defineInRange(MASS_COUNT_2_TAG, 4, 1, 100);
                    MASS_COUNT_3 = builder
                            .comment("Number of mobs to spawn for mass 3 upgrade")
                            .translation(TAG + MASS_COUNT_3_TAG)
                            .defineInRange(MASS_COUNT_3_TAG, 6, 1, 100);

                    RATE_1 = builder
                            .comment("Percentage reduction in spawn time for rate 1 upgrade")
                            .translation(TAG + RATE_1_TAG)
                            .defineInRange(RATE_1_TAG, 20, 1, 99);
                    RATE_2 = builder
                            .comment("Percentage reduction in spawn time for rate 2 upgrade")
                            .translation(TAG + RATE_2_TAG)
                            .defineInRange(RATE_2_TAG, 50, 1, 99);
                    RATE_3 = builder
                            .comment("Percentage reduction in spawn time for rate 3 upgrade")
                            .translation(TAG + RATE_3_TAG)
                            .defineInRange(RATE_3_TAG, 75, 1, 99);
                }
                builder.pop(); // upgrades
            }
            builder.pop(); // factory
            builder.pop(); // common
        }
    }

    public static class Client {

        public final ForgeConfigSpec.DoubleValue LAYOUT_OPACITY;
        public final ForgeConfigSpec.DoubleValue LAYOUT_SIZE;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client configuration settings")
                    .push("client");

            LAYOUT_OPACITY = builder
                    .comment("Opacity of the layout blocks")
                    .translation("woot.configgui.layoutOpacity")
                    .defineInRange("layoutOpacity", 0.95D, 0.10D, 1.00D);

            LAYOUT_SIZE = builder
                    .comment("Size of the layout blocks")
                    .translation("woot.configgui.layoutSize")
                    .defineInRange("layoutSize", 0.35D, 0.10D, 1.0D);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void loadFromConfig() {

        LOGGER.debug("Loading configuration");
        for (String s : Config.COMMON.MOB_OVERRIDES.get()) {
            String[] parts = s.split(",");
            if (parts.length != 3) {
                LOGGER.error(s + " == INVALID");
            } else {
                String mob = parts[0];
                String param = parts[1];
                if (COMMON.VALID_OVERRIDE_TAGS.contains(param)) {
                    try {
                        int v = Integer.valueOf(parts[2]);
                        FakeMob fakeMob = new FakeMob(mob);
                        if (fakeMob.isValid()) {
                            MobOverride.get().addIntOverride(fakeMob, WootConfig.ConfigKey.getByString(param), v);
                            //addIntMapping(fakeMob, param, v);
                        }
                        else
                            LOGGER.error(s + " == INVALID (invalid mob)");
                    } catch (NumberFormatException e) {
                        LOGGER.error(s + " == INVALID (invalid value)");
                    }
                } else {
                    LOGGER.error(s + " == INVALID (unknown param)");
                }
            }
        }
        Policy.get().loadFromConfig();

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_COUNT, Config.COMMON.MASS_COUNT.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.SPAWN_TICKS, Config.COMMON.SPAWN_TICKS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MOB_SHARD_KILLS, Config.COMMON.MOB_SHARD_KILLS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.UNITS_PER_HEALTH, Config.COMMON.UNITS_PER_HEALTH.get());
        // MOB_TIER is an override or calculated, never a default value
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_1_MAX_UNITS, Config.COMMON.TIER_1_MAX_UNITS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_2_MAX_UNITS, Config.COMMON.TIER_2_MAX_UNITS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_3_MAX_UNITS, Config.COMMON.TIER_3_MAX_UNITS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_4_MAX_UNITS, Config.COMMON.TIER_4_MAX_UNITS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_5_MAX_UNITS, Config.COMMON.TIER_5_MAX_UNITS.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_1_UNITS_PER_TICK, Config.COMMON.TIER_1_UNITS_PER_TICK.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_2_UNITS_PER_TICK, Config.COMMON.TIER_2_UNITS_PER_TICK.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_3_UNITS_PER_TICK, Config.COMMON.TIER_3_UNITS_PER_TICK.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_4_UNITS_PER_TICK, Config.COMMON.TIER_4_UNITS_PER_TICK.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.TIER_5_UNITS_PER_TICK, Config.COMMON.TIER_5_UNITS_PER_TICK.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_1_CAPACITY, Config.COMMON.CELL_1_CAPACITY.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_2_CAPACITY, Config.COMMON.CELL_2_CAPACITY.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_3_CAPACITY, Config.COMMON.CELL_3_CAPACITY.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_1_MAX_TRANSFER, Config.COMMON.CELL_1_MAX_TRANSFER.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_2_MAX_TRANSFER, Config.COMMON.CELL_2_MAX_TRANSFER.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CELL_3_MAX_TRANSFER, Config.COMMON.CELL_3_MAX_TRANSFER.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_1, Config.COMMON.CAPACITY_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_2, Config.COMMON.CAPACITY_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.CAPACITY_3, Config.COMMON.CAPACITY_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_1, Config.COMMON.EFFICIENCY_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_2, Config.COMMON.EFFICIENCY_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.EFFICIENCY_3, Config.COMMON.EFFICIENCY_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_1, Config.COMMON.MASS_COUNT_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_2, Config.COMMON.MASS_COUNT_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.MASS_3, Config.COMMON.MASS_COUNT_3.get());

        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_1, Config.COMMON.RATE_1.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_2, Config.COMMON.RATE_2.get());
        WootConfig.get().putIntConfig(WootConfig.ConfigKey.RATE_3, Config.COMMON.RATE_3.get());
    }

}
