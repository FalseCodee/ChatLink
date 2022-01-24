package me.falsecode.chatlink.utils

import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta

class ItemBuilder {
    private val itemStack:ItemStack
    private val meta:ItemMeta? get() = itemStack.itemMeta


    constructor(itemStack: ItemStack) {
        this.itemStack = itemStack
    }

    constructor() : this(Material.STONE)

    constructor(material: Material) : this(material, 1)

    constructor(material: Material, amount: Int) {
        this.itemStack = ItemStack(material, amount)
    }

    constructor(material: Material, amount: Int, name: String) : this(material, amount) {
        this.setName(name)
    }

    constructor(material: Material, amount: Int, name: String, lore: List<String>) : this(material, amount, name) {
        this.setLore(lore)
    }

    fun setMeta(meta:ItemMeta?):ItemBuilder {
        itemStack.itemMeta = meta
        return this
    }

    fun setName(name: String, color: Boolean = true):ItemBuilder {
        val meta = this.meta
        meta?.setDisplayName(if(color) MessageUtils.applyColor(name) else name)
        return setMeta(meta)
    }

    fun getName(): String {
        return meta?.displayName ?: ""
    }

    fun setLore(lore: List<String>, color: Boolean = true): ItemBuilder {
        val meta = this.meta
        meta?.lore = if(color) MessageUtils.applyColor(lore) else lore
        return setMeta(meta)
    }

    fun getLore(): List<String> {
        return meta?.lore ?: ArrayList()
    }

    fun setType(material:Material):ItemBuilder {
        this.itemStack.type = material
        return this
    }

    fun getType(): Material {
        return this.itemStack.type
    }

    fun addEnchant(enchantment: Enchantment, level:Int): ItemBuilder {
        this.itemStack.addUnsafeEnchantment(enchantment, level)
        return this;
    }

    fun shouldHideFlags(hide: Boolean, vararg flags: ItemFlag = ItemFlag.values()): ItemBuilder {
        val meta = this.meta
        if(hide)
            meta?.addItemFlags(*flags)
        else
            meta?.removeItemFlags(*flags)
        return setMeta(meta)
    }

    fun setUnbreakable(unbreakable: Boolean = true): ItemBuilder {
        val meta = this.meta
        meta?.isUnbreakable = unbreakable
        return setMeta(meta)
    }

    fun setColor(color: Color? = null): ItemBuilder {
        val meta = this.meta

        if(meta is LeatherArmorMeta) {
            meta.setColor(color)
            return setMeta(meta)
        }

        return this
    }

    fun build(): ItemStack {
        return itemStack
    }


}