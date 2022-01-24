package me.falsecode.chatlink.listeners

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.GuiManager
import me.falsecode.chatlink.gui.guis.GuiCreateScreen
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.scheduler.BukkitRunnable

class ChatListener(plugin: Main) : Listener {
    val plugin: Main

    init {
        this.plugin = plugin
    }
    @EventHandler
    private fun onChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message
        val gui = GuiManager.getGui(player)
        player.sendMessage("${gui != null && gui is GuiCreateScreen}")
        if(gui != null && gui is GuiCreateScreen) {
            event.isCancelled = true
            object: BukkitRunnable() {
                override fun run() {
                    gui.onAction(message)
                }

            }.runTask(plugin)
        }
    }
}