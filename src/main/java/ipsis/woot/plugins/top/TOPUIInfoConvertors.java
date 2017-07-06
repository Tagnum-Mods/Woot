package ipsis.woot.plugins.top;

import ipsis.woot.tileentity.ui.FarmUIInfo;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.config.Config;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TOPUIInfoConvertors {

    public static void farmConvertor(FarmUIInfo farm, ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        probeInfo.text(TextFormatting.BLUE + "Tier: " + farm.tier.toString());

        /**
         * Power & Redstone state
         */

        // TODO this will be handled when the ForgeCaps are exposed
        probeInfo.progress(farm.powerStored, farm.powerCapacity,
                probeInfo.defaultProgressStyle().suffix("FP").numberFormat(Config.rfFormat));

        probeInfo.horizontal().item(new ItemStack(Items.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14)).text("State: " + (farm.isRunning ? "On" : "Off"));

        /**
         * Progress
         */

        if (farm.isRunning) {
            int p = (int)((100.0F / (float)farm.recipeTotalPower) * (float)farm.consumedPower);
            p = MathHelper.clamp(p, 0, 100);
            TextFormatting form = TextFormatting.GREEN;
            probeInfo.horizontal().item(new ItemStack(Items.COMPASS), probeInfo.defaultItemStyle().width(14).height(14)).text(form + "Progress: " + p + "%");
        }

        /**
         * Recipe
         */
        probeInfo.text(TextFormatting.GREEN + "Mob: " + farm.wootMob.getDisplayName());
        probeInfo.text(TextFormatting.GREEN + "Recipe: " + farm.recipeTotalPower + " " + farm.recipeTotalTime + " " + farm.recipePowerPerTick);

        /**
         * Drops
         */
        if (mode == ProbeMode.EXTENDED) {

            IProbeInfo vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(0xffffffff).spacing(0));
            IProbeInfo horizontal = null;
            int rows = 0;
            int idx = 0;
            for (ItemStack itemStack : farm.drops) {
                if (idx % 10 == 0) {
                    horizontal = vertical.horizontal(probeInfo.defaultLayoutStyle().spacing(0));
                    rows++;
                    if (rows > 4)
                        break;
                }

                horizontal.item(itemStack);
                idx++;
            }
        }

    }
}