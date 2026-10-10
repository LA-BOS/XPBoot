package com.xpboost.enchantment;

import com.xpboost.XpBoostMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;

/**
 * Quyết định vật phẩm nào được ép enchant Experience Boost.
 * Không phụ thuộc cứng vào mod khác: nhận kiếm/rìu vanilla,
 * vũ khí Epic Fight, và vũ khí mod có sát thương tay chính.
 */
public final class WeaponHelper {
    /**
     * Tag tùy chọn: thêm ID vật phẩm vào file này nếu một vũ khí nào đó
     * vẫn không ép được.
     */
    public static final TagKey<Item> EXTRA_WEAPONS = TagKey.create(
            Registries.ITEM,
            new ResourceLocation(XpBoostMod.MOD_ID, "experience_boost_weapons")
    );

    private WeaponHelper() {
    }

    public static boolean canReceiveExperienceBoost(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        // Không cho cuốc, xẻng, hoe
        if (item instanceof PickaxeItem || item instanceof ShovelItem || item instanceof HoeItem) {
            return false;
        }

        if (item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem) {
            return true;
        }

        if (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES) || stack.is(EXTRA_WEAPONS)) {
            return true;
        }

        // Epic Fight: Greatsword, Spear, Tachi, ... kế thừa WeaponItem
        if (isEpicFightWeapon(item)) {
            return true;
        }

        // Vũ khí mod khác: có thuộc tính sát thương khi cầm tay chính
        return stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE);
    }

    private static boolean isEpicFightWeapon(Item item) {
        for (Class<?> type = item.getClass(); type != null; type = type.getSuperclass()) {
            if ("yesman.epicfight.world.item.WeaponItem".equals(type.getName())) {
                return true;
            }
        }
        return false;
    }
}
