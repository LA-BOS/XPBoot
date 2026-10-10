package com.xpboost;

import com.mojang.logging.LogUtils;
import com.xpboost.enchantment.ModEnchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Class chính của mod — Forge sẽ chạy class này khi game khởi động.
 * Tên trong @Mod phải trùng với mod_id trong gradle.properties (xpboost).
 */
@Mod(XpBoostMod.MOD_ID)
public class XpBoostMod {
    public static final String MOD_ID = "xpboost";
    public static final Logger LOGGER = LogUtils.getLogger();

    public XpBoostMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Đăng ký enchant vào game
        ModEnchantments.ENCHANTMENTS.register(modEventBus);

        LOGGER.info("XP Boost mod da tai thanh cong!");
    }
}
