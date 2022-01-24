package me.falsecode.chatlink.managers.managers

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.command.commands.LinkCommand
import me.falsecode.chatlink.managers.Manager

class CommandManager(plugin: Main) : Manager(plugin, "commands") {

    fun addCommand(identifier: String, name: String, message: String, link: String, hover: String = "") {
        getConfig().set("$identifier.name", name)
        getConfig().set("$identifier.message", message)
        getConfig().set("$identifier.link", link)
        getConfig().set("$identifier.hover", hover)
    }
    
    fun registerCommand(identifier: String) {
        LinkCommand(getPlugin(),
            identifier,
            getConfig().getString("$identifier.name") ?: "link",
            getConfig().getString("$identifier.message") ?: "Click here!",
            getConfig().getString("$identifier.link") ?: "https://www.google.com",
            getConfig().getString("$identifier.hover") ?: "Click me!")
    }

    fun isIdentifierAvailable(identifier: String?): Boolean {
        if(identifier == null) return false
        return !getConfig().contains(identifier)
    }

    fun isCommandNameAvailable(commandName: String?): Boolean {
        if(commandName == null) return false
        for(key in getConfig().getConfigurationSection("")?.getKeys(false) ?: ArrayList<String>()) {
            if(getConfig().getString("$key.name").equals(commandName, true)) {
                return false
            }
        }
        return true
    }
    
    fun registerAllCommands() {
        for(key in getConfig().getConfigurationSection("")?.getKeys(false) ?: ArrayList<String>()) {
            registerCommand(key)
        }
    }

    fun removeCommand(identifier: String) {
        getConfig().set(identifier, null)
    }

    fun getAllIdentifiers() : List<String> {
        return getConfig().getConfigurationSection("")?.getKeys(false)?.toList() ?: ArrayList()
    }
}