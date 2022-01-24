package me.falsecode.chatlink.command

import org.bukkit.command.CommandSender

interface IFalseCommand {
    fun execute(sender:CommandSender, args: Array<out String>)
}