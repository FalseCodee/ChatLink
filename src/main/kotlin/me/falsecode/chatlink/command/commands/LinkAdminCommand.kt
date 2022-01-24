package me.falsecode.chatlink.command.commands

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.command.FalseCommand
import me.falsecode.chatlink.gui.guis.GuiHomeScreen
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LinkAdminCommand(plugin: Main) : FalseCommand(plugin, "linkadmin", "Admin Command for ChatLink", "/linkadmin or /la", listOf("la"), "chatlink.admin", "No Permission!") {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if(sender is Player)
            GuiHomeScreen(plugin, sender)
    }
}