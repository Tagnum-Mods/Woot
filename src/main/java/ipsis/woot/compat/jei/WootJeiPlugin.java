package ipsis.woot.compat.jei;

import ipsis.woot.Woot;
import ipsis.woot.crafting.SqueezerRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class WootJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Woot.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new SqueezerRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addRecipes(SqueezerRecipe.recipeList, SqueezerRecipeCategory.UID);
    }
}