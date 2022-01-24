package me.falsecode.chatlink.command

import me.falsecode.chatlink.Main
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

abstract class FalseCommand(
    protected val plugin: Main,
    name: String,
    description: String,
    usageMessage: String,
    aliases: List<String> = ArrayList(),
    permission: String? = null,
    permissionMessage: String? = null
) : Command(name, description, usageMessage, aliases), IFalseCommand {
    protected val subCommands:HashMap<String, IFalseSubCommand> = HashMap()
    protected val tabComplete:HashMap<Int, MutableList<String>> = HashMap()

    init {
        lazy {
            setPermission(permission)
            setPermissionMessage(permissionMessage)
        }
        register()
    }

    override fun execute(sender: CommandSender, command: String, args: Array<out String>): Boolean {
        if(!sender.hasPermission(permission.orEmpty())) {
            sender.sendMessage(permissionMessage.orEmpty())
            return true
        }

        if(args.isNotEmpty()) {
            val subCommand = subCommands[args[0].lowercase()]
            if(subCommand != null) {
                try {
                    subCommand.execute(sender, command, args.copyOfRange(1, args.size))
                } catch (exc: CommandUsageException) {
                    sender.sendMessage(exc.message)
                }
                return true
            }
        }

        try {
            execute(sender, args)
        } catch (exc : CommandUsageException) {
            sender.sendMessage(exc.message)
        }
        return false
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        val list = ArrayList<String>(tabComplete.getOrDefault(args.size, ArrayList()))
        val toRemove = ArrayList<String>()

        for(i in 0 until list.size) {
            if(args.size < 2) break
            if(list[i].contains("-->")) {
                val split = list[i].split("-->")
                if(args[args.size-2].equals(split[0], true)) {
                    list[i] = split[1]
                } else {
                    toRemove.add(list[i])
                }
            }
        }
        for(str:String in toRemove) {
            list.remove(str)
        }
        return list
    }

    fun addSubCommand(subCommand:IFalseSubCommand) {
        if(!subCommands.containsKey(subCommand.getName().lowercase())) {
            addTabComplete(1, subCommand.getName())
        }
        subCommands[subCommand.getName().lowercase()] = subCommand
    }

    fun addTabComplete(integer:Int, completion:String) {
        val newTabs = tabComplete.getOrDefault(integer, ArrayList())
        newTabs.add(completion)
        tabComplete[integer] = newTabs
    }

    fun addTabComplete(integer:Int, prevCompletion:String, completion:String) {
        val newTabs = tabComplete.getOrDefault(integer, ArrayList())
        newTabs.add("$prevCompletion-->$completion")
        tabComplete[integer] = newTabs
    }


    private fun register() {
        try {
            val bukkitCommandMap = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
            bukkitCommandMap.isAccessible = true
            val commandMap = bukkitCommandMap.get(Bukkit.getServer()) as CommandMap
            commandMap.register(plugin.name, this)
        } catch (exc:Exception) {
            exc.printStackTrace()
        }
    }
}