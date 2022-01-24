package me.falsecode.chatlink.gui

import me.falsecode.chatlink.Main
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.collections.HashMap

abstract class Gui(plugin: Main, player:Player, size:Int, title:String) : IGui {
    val player:Player
    protected val inventory:Inventory
    protected val items:Array<ItemStack>
    val buttons:HashMap<Int, IButton>
    protected val plugin:Main
    init {
        GuiManager.guiHashMap.remove(player.uniqueId)
        this.player = player
        this.inventory = Bukkit.createInventory(player, size, title)
        this.items = Array(size) { ItemStack(Material.AIR, 1) }
        this.buttons = HashMap()
        this.plugin = plugin
    }

    override fun init() {
        GuiManager.addGui(this)
    }

    override fun setButton(slot:Int, button:IButton) {
        buttons[slot] = button
    }

    override fun setButton(slot: Int, itemStack: ItemStack, button: IButton) {
        setButton(slot, button)
        items[slot] = itemStack
    }

    fun clear() {
        Arrays.fill(items, ItemStack(Material.AIR, 1))
    }

    override fun switchScreen(gui: Gui) {
        GuiManager.addGui(gui)
    }

    protected fun openInventory() {
        player.openInventory(inventory)
    }

    override fun update() {
       inventory.contents = items
    }

    override fun onInventoryClose(event: InventoryCloseEvent, gui: Gui) {
       Bukkit.getScheduler().runTaskLater(plugin, player::updateInventory, 1L)
        GuiManager.guiHashMap.remove(player.uniqueId)
    }
}