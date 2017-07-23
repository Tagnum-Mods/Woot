package ipsis.woot.init;

import ipsis.woot.block.*;
import ipsis.woot.reference.Reference;
import ipsis.woot.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockWoot blockAnvil;
    public static BlockWoot blockFactoryHeart;
    public static BlockWoot blockStygianIron;
    public static BlockWoot blockUpgrade;
    public static BlockWoot blockUpgradeB;
    public static BlockWoot blockStructure;
    public static BlockWoot blockFactoryController;
    public static BlockWoot blockLayout;
    public static BlockWoot blockProxy;
    public static BlockWoot blockExtender;

    public static void preInit() {

    }

    public static void init() {

        blockAnvil = new BlockWootAnvil();
        blockFactoryHeart = new BlockMobFactoryHeart();
        blockStygianIron = new BlockStygianIron();
        blockUpgrade = new BlockMobFactoryUpgrade();
        blockUpgradeB = new BlockMobFactoryUpgradeB();
        blockStructure = new BlockMobFactoryStructure();
        blockFactoryController = new BlockMobFactoryController();
        blockLayout = new BlockLayout();
        blockProxy = new BlockMobFactoryProxy();
        blockExtender = new BlockMobFactoryExtender();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {

        ModBlocks.blockAnvil.initModel();
        ModBlocks.blockFactoryHeart.initModel();
        ModBlocks.blockStygianIron.initModel();
        ModBlocks.blockUpgrade.initModel();
        ModBlocks.blockUpgradeB.initModel();
        ModBlocks.blockStructure.initModel();
        ModBlocks.blockFactoryController.initModel();
        ModBlocks.blockLayout.initModel();
        ModBlocks.blockProxy.initModel();
        ModBlocks.blockExtender.initModel();
    }

    public static void registerTileEntities() {

        String modkey = "tile." + Reference.MOD_ID + "_";

        GameRegistry.registerTileEntity(TileEntityMobFactoryHeart.class, modkey + BlockMobFactoryHeart.BASENAME);
        GameRegistry.registerTileEntity(TileEntityAnvil.class, modkey + BlockWootAnvil.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryStructure.class, modkey + BlockMobFactoryStructure.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryUpgrade.class, modkey + BlockMobFactoryUpgrade.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryController.class, modkey + BlockMobFactoryController.BASENAME);
        GameRegistry.registerTileEntity(TileEntityLayout.class, modkey + BlockLayout.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryProxy.class, modkey + BlockMobFactoryProxy.BASENAME);
        GameRegistry.registerTileEntity(TileEntityMobFactoryExtender.class, modkey + BlockMobFactoryExtender.BASENAME);
    }

}
