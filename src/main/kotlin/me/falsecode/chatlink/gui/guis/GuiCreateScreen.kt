package me.falsecode.chatlink.gui.guis

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.Gui
import me.falsecode.chatlink.gui.IButton
import me.falsecode.chatlink.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiCreateScreen(plugin: Main, player: Player) : Gui(plugin, player, 45, "Chat Link") {
    init {
        init()
        openInventory()
    }
    private var identifier: String? = null
    set(value) {
        if(plugin.commandManager.isIdentifierAvailable(value)) {
            field = value
        }
    }
    private var commandName: String? = null
        set(value) {
            if(plugin.commandManager.isCommandNameAvailable(value)) {
                field = value
            }
        }
    private var message: String? = null
    private var link: String? = null
    private var hover: String? = null

    var currentAction: Action? = null

    override fun init() {
        super.init()
        setItems()
    }

    private fun setItems() {
        for(i in items.indices) {
            items[i] = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName("&0")
                .build()
        }
        setButton(9, ItemBuilder(Material.REDSTONE)
            .setName("&fSet Identifier")
            .setLore(listOf("", "&7Current:", "&f${identifier ?: "None"}"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.identifierButton.click", "&eType the identifier in chat."))
                currentAction = Action.IDENTIFIER
                player.closeInventory()
            }
        })
        setButton(11, ItemBuilder(Material.NAME_TAG)
            .setName("&fSet Name")
            .setLore(listOf("", "&7Current:", "&f${commandName ?: "None"}"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.nameButton.click", "&eType the name in chat."))
                currentAction = Action.NAME
                player.closeInventory()
            }
        })
        setButton(13, ItemBuilder(Material.OAK_SIGN)
            .setName("&fSet Message")
            .setLore(listOf("", "&7Current:", "&f${message?.split("\\n") ?: "None"}"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.messageButton.click", "&eType the message you want in chat."))
                currentAction = Action.MESSAGE
                player.closeInventory()
            }
        })
        setButton(15, ItemBuilder(Material.CHAIN)
            .setName("&fSet Link")
            .setLore(listOf("", "&7Current:", "&f${link ?: "None"}"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.linkButton.click", "&eType the link in chat."))
                currentAction = Action.LINK
                player.closeInventory()
            }
        })
        setButton(17, ItemBuilder(Material.END_ROD)
            .setName("&fSet Hover Text")
            .setLore(listOf("", "&7Current:", "&f${message?.split("\\n") ?: "None"}"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.hoverButton.click", "&eType the message you want in chat."))
                currentAction = Action.HOVER
                player.closeInventory()
            }
        })

        setButton(31, ItemBuilder(Material.EMERALD_BLOCK)
            .setName("&fCreate")
            .setLore(listOf("", "&7Creates the command"))
            .build(), object: IButton{
            override fun execute(event: InventoryClickEvent) {
                if(identifier == null || commandName == null || message == null || link == null || hover == null) {
                    player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.submit.nulls", "&cSome of the values are not set!"))
                    return
                }

                plugin.commandManager.addCommand(identifier!!, commandName!!, message!!, link!!, hover!!)
                plugin.commandManager.registerCommand(identifier!!)
                player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.submit.success", "&eSuccessfully made Command"))
                player.closeInventory()
            }
        })
        update()
    }

    fun onAction(message: String) {
        player.sendMessage(message)
        player.sendMessage(currentAction?.name ?: "None")
        when(currentAction) {
            Action.IDENTIFIER -> this.identifier = message.split(" ")[0]
            Action.NAME -> this.commandName = message.split(" ")[0]
            Action.MESSAGE -> this.message = message
            Action.LINK -> this.link = message
            Action.HOVER -> this.hover = message
        }

        currentAction = null

        clear()
        setItems()
        openInventory()
    }

    override fun onInventoryClick(event: InventoryClickEvent, gui: Gui) {
        event.isCancelled = true
    }

    override fun onInventoryClose(event: InventoryCloseEvent, gui: Gui) {
        if(currentAction == null) {
            super.onInventoryClose(event, gui)
        }


    }

    enum class Action {
        IDENTIFIER,
        NAME,
        MESSAGE,
        LINK,
        HOVER
    }
}