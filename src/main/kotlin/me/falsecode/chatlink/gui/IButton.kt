package me.falsecode.chatlink.gui

import org.bukkit.event.inventory.InventoryClickEvent

interface IButton {
    fun execute(event:InventoryClickEvent)
}