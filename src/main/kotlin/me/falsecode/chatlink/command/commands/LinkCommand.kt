package me.falsecode.chatlink.command.commands

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.command.FalseCommand
import me.falsecode.chatlink.utils.MessageUtils
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class LinkCommand(plugin: Main, identifier: String, name: String, message: String, link: String, hover: String = "") : FalseCommand(plugin, name, "Link Command", "/$name") {
    private val identifier: String
    private val commandName: String
    private val message: String
    private val link: String
    private val hover: String
    private val textComponent: TextComponent = TextComponent(MessageUtils.applyColor(message))
        get() {
            field.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, link)
            field.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(MessageUtils.applyColor(hover)))
            return field
        }
    init {
        this.identifier = identifier
        this.commandName = name
        this.message = message.replace("\\n", "\n")
        this.link = link
        this.hover = hover.replace("\\n", "\n")
    }
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if(sender is Player) {
            sender.spigot().sendMessage(textComponent)
        }
    }
}