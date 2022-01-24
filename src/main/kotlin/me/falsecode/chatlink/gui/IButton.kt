package me.falsecode.chatlink.gui

import org.bukkit.event.inventory.InventoryClickEvent

fun interface IButton {
    fun execute(event:InventoryClickEvent)
}