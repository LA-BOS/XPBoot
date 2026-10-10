package com.xpboost.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * Enchant tăng XP khi giết mob bằng kiếm.
 *
 * Cấp 1: x2
 * Cấp 2: x4
 * Cấp 3: x6
 * Cấp 4: x8
 * Cấp 5: x10
 *
 * Ép được vào kiếm vanilla, vũ khí Epic Fight và vũ khí cận chiến của mod khác.
 * Không xuất hiện ở bàn phù phép, chỉ mua sách từ dân làng thủ thư.
 */
public class ExperienceBoostEnchantment extends Enchantment {

    public ExperienceBoostEnchantment() {
        // VERY_RARE = độ hiếm cao (giống Mending)
        // WEAPON = nhóm vũ khí cận chiến (kiếm)
        // MAINHAND = chỉ tính khi cầm ở tay chính
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /** Enchant có tối đa 5 cấp. */
    @Override
    public int getMaxLevel() {
        return 5;
    }

    /** Chi phí tối thiểu khi enchanted (dùng cho hệ thống enchant vanilla). */
    @Override
    public int getMinCost(int level) {
        return 15 + (level - 1) * 9;
    }

    /** Chi phí tối đa khi enchanted. */
    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 50;
    }

    /**
     * Treasure enchant = không xuất hiện ở bàn phù phép.
     * Thủ thư vẫn có thể bán (giống Mending).
     */
    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    /** Cho phép dân làng (thủ thư) bán sách có enchant này. */
    @Override
    public boolean isTradeable() {
        return true;
    }

    /**
     * Không xuất hiện trong rương / câu cá / loot ngẫu nhiên.
     * Mục tiêu: chỉ lấy được từ thủ thư.
     */
    @Override
    public boolean isDiscoverable() {
        return false;
    }

    /** Không cho xuất hiện ở bàn phù phép. */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    /** Cho phép ép lên vũ khí cận chiến (vanilla + mod, kể cả qua đe). */
    @Override
    public boolean canEnchant(ItemStack stack) {
        return WeaponHelper.canReceiveExperienceBoost(stack);
    }

    /**
     * Hệ số nhân XP theo cấp enchant.
     * Cấp 1 -> x2, cấp 2 -> x4, cấp 3 -> x6, cấp 4 -> x8, cấp 5 -> x10
     */
    public static float getXpMultiplier(int level) {
        if (level <= 0) {
            return 1.0f;
        }
        return 2.0f * level;
    }
}
