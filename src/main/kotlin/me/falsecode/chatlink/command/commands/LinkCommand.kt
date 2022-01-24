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

class LinkCommand(plugin: Main, identifier: String, name: String, message: String, link: String, hover: String = "") : FalseCommand(plugin, name, "Link Command", "/$name") {
    private val identifier: String
    private var commandName: String
    private var message: String
    private var link: String
    private var hover: String
    private val textComponent: TextComponent
        get() {
            this.commandName = plugin.commandManager.getConfig().getString("$identifier.name") ?: "None"
            this.message = plugin.commandManager.getConfig().getString("$identifier.message")?.replace("\\n", "\n") ?: "None"
            this.link = plugin.commandManager.getConfig().getString("$identifier.link") ?: "None"
            this.hover = plugin.commandManager.getConfig().getString("$identifier.hover")?.replace("\\n", "\n") ?: "None"

            val text = TextComponent(MessageUtils.applyColor(message))
            text.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, link)
            text.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(MessageUtils.applyColor(hover)))
            return text
        }
    init {
        this.identifier = identifier
        this.commandName = name
        this.message = message.replace("\\n", "\n")
        this.link = link
        this.hover = hover.replace("\\n", "\n")
    }
    override fun execute(sender: CommandSender, args: Array<out String>) {
        if(plugin.commandManager.isIdentifierAvailable(identifier)) {
            sender.sendMessage(plugin.msgUtils.getOrSetDefault("command.noLongerExists", "This command no longer exists."))
            return
        }
        if(sender is Player) {
            sender.spigot().sendMessage(textComponent)
        }
    }
}