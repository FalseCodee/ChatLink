package me.falsecode.chatlink.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

interface IGui {
    fun setButton(slot:Int, button:IButton)

    fun setButton(slot:Int, itemStack:ItemStack, button:IButton)

    fun switchScreen(gui:Gui)

    fun update()

    fun init()

    fun onInventoryClick(event: InventoryClickEvent, gui:Gui) {}

    fun onInventoryClose(event: InventoryCloseEvent, gui:Gui) {}

    fun onInventoryOpen(event: InventoryOpenEvent, gui:Gui) {}
}