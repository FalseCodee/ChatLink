package me.falsecode.chatlink.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerQuitEvent

class GuiListener : Listener {

    @EventHandler
    fun onInventoryClick(event:InventoryClickEvent) {
        val gui = GuiManager.getGui(event.whoClicked as Player)
        gui?.onInventoryClick(event, gui)
        gui?.buttons?.get(event.rawSlot)?.execute(event)
    }

    @EventHandler
    fun onInventoryClose(event:InventoryCloseEvent) {
        val gui = GuiManager.getGui(event.player as Player)
        gui?.onInventoryClose(event, gui)
    }

    @EventHandler
    fun onInventoryOpen(event:InventoryOpenEvent) {
        val gui = GuiManager.getGui(event.player as Player)
        gui?.onInventoryOpen(event, gui)
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) {
        GuiManager.guiHashMap.remove(event.player.uniqueId)
    }
}