package me.falsecode.chatlink

import me.falsecode.chatlink.command.commands.LinkAdminCommand
import me.falsecode.chatlink.command.commands.LinkCommand
import me.falsecode.chatlink.gui.GuiListener
import me.falsecode.chatlink.listeners.ChatListener
import me.falsecode.chatlink.managers.managers.CommandManager
import me.falsecode.chatlink.utils.MessageUtils
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    lateinit var msgUtils: MessageUtils
    lateinit var commandManager: CommandManager

    override fun onEnable() {
        msgUtils = MessageUtils(this)
        commandManager = CommandManager(this)
        commandManager.registerAllCommands()
        server.pluginManager.registerEvents(GuiListener(), this)
        server.pluginManager.registerEvents(ChatListener(this), this)
        LinkAdminCommand(this)
        saveManagers()
    }

    override fun onDisable() {
       saveManagers()
    }

    fun saveManagers() {
        commandManager.saveFile()
        msgUtils.saveFile()
    }

}