package com.xpboost.event;

import com.xpboost.enchantment.ExperienceBoostEnchantment;
import com.xpboost.enchantment.ModEnchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Lắng nghe sự kiện trong game.
 * Khi mob chết và rơi XP, nếu người chơi đang cầm kiếm có Experience Boost
 * thì nhân số XP lên theo cấp enchant.
 */
@Mod.EventBusSubscriber(modid = "xpboost")
public class ModEvents {

    @SubscribeEvent
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
        // Ai đã giết mob? (có thể là null nếu mob chết vì lửa, té, v.v.)
        Player player = event.getAttackingPlayer();
        if (player == null) {
            return;
        }

        // Lấy vật phẩm đang cầm ở tay chính
        ItemStack weapon = player.getMainHandItem();
        if (weapon.isEmpty()) {
            return;
        }

        // Kiểm tra kiếm có enchant Experience Boost không
        int level = EnchantmentHelper.getItemEnchantmentLevel(
                ModEnchantments.EXPERIENCE_BOOST.get(),
                weapon
        );

        if (level <= 0) {
            return;
        }

        // Nhân XP: cấp 1 = x2, cấp 2 = x4, cấp 3 = x6, cấp 4 = x8, cấp 5 = x10
        float multiplier = ExperienceBoostEnchantment.getXpMultiplier(level);
        int originalXp = event.getDroppedExperience();
        int boostedXp = Math.round(originalXp * multiplier);

        event.setDroppedExperience(boostedXp);
    }
}
