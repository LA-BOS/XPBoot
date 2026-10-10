package com.xpboost.enchantment;

import com.xpboost.XpBoostMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Nơi đăng ký tất cả enchant của mod.
 * Mỗi enchant cần một dòng RegistryObject như bên dưới.
 */
public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, XpBoostMod.MOD_ID);

    /**
     * Enchant "Experience Boost"
     * ID trong game: xpboost:experience_boost
     */
    public static final RegistryObject<Enchantment> EXPERIENCE_BOOST =
            ENCHANTMENTS.register("experience_boost", ExperienceBoostEnchantment::new);
}
