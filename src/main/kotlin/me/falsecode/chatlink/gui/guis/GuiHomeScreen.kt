package me.falsecode.chatlink.gui.guis

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.Gui
import me.falsecode.chatlink.gui.IButton
import me.falsecode.chatlink.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class GuiHomeScreen(plugin: Main, player: Player) : Gui(plugin, player, 27, "Chat Link") {
    init {
        init()
        openInventory()
    }
    override fun init() {
        super.init()
        for(i in items.indices) {
            items[i] = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName("&0")
                .build()
        }
        setButton(11, ItemBuilder(Material.EMERALD_BLOCK)
            .setName(plugin.msgUtils.getOrSetDefault("gui.createButton.name","&fCreate new command."))
            .setLore(plugin.msgUtils.getOrSetDefault("gui.createButton.lore",listOf("", "&7Create and customize")))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.createButton.click", "&eGoing to creation page."))
                switchScreen(GuiCreateScreen(plugin, player))
            }
        })
        setButton(15, ItemBuilder(Material.REDSTONE_LAMP)
            .setName(plugin.msgUtils.getOrSetDefault("gui.editButton.name","&fEdit"))
            .setLore(plugin.msgUtils.getOrSetDefault("gui.editButton.lore",listOf("", "&7Edit or remove commands")))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.editButton.click", "&eGoing to edit page."))
            }
        })
        update()
    }

    override fun onInventoryClick(event: InventoryClickEvent, gui: Gui) {
        event.isCancelled = true
    }
}