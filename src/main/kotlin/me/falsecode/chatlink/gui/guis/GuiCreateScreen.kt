package me.falsecode.chatlink.gui.guis

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.Gui
import me.falsecode.chatlink.utils.ItemBuilder
import me.falsecode.chatlink.utils.MessageUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class GuiCreateScreen(plugin: Main, player: Player, isEdit: Boolean) : Gui(plugin, player, 45, "Chat Link") {
    private val isEdit: Boolean
    init {
        this.isEdit = isEdit
        init()
        openInventory()
    }
    private var identifier: String? = null
    set(value) {
        if(isEdit || plugin.commandManager.isIdentifierAvailable(value)) {
            field = value
        }
    }
    private var commandName: String? = null
        set(value) {
            if(isEdit || plugin.commandManager.isCommandNameAvailable(value)) {
                field = value
            }
        }
    private var message: String? = null
    private var link: String? = null
    private var hover: String? = null

    private var currentAction: Action? = null

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
        if(!isEdit) {
            setButton(
                9, ItemBuilder(Material.REDSTONE)
                    .setName("&fSet Identifier")
                    .setLore(listOf("", "&7Current:", "&f${identifier ?: "None"}"))
                    .build()
            ) {
                player.sendMessage(
                    plugin.msgUtils.getOrSetDefault(
                        "gui.create.identifierButton.click",
                        "&eType the identifier in chat."
                    )
                )
                currentAction = Action.IDENTIFIER
                player.closeInventory()
            }
            setButton(
                11, ItemBuilder(Material.NAME_TAG)
                    .setName("&fSet Name")
                    .setLore(listOf("", "&7Current:", "&f${commandName ?: "None"}"))
                    .build()
            ) {
                player.sendMessage(
                    plugin.msgUtils.getOrSetDefault(
                        "gui.create.nameButton.click",
                        "&eType the name in chat."
                    )
                )
                currentAction = Action.NAME
                player.closeInventory()
            }
        }

        setButton(13 - if(isEdit) 2 else 0, ItemBuilder(Material.OAK_SIGN)
            .setName("&fSet Message")
            .setLore(listOf("", "&7Current:", "&f${message?.split("\\n") ?: "None"}"))
            .build()
        ) {
            player.sendMessage(
                plugin.msgUtils.getOrSetDefault(
                    "gui.create.messageButton.click",
                    "&eType the message you want in chat."
                )
            )
            currentAction = Action.MESSAGE
            player.closeInventory()
        }
        setButton(15 - if(isEdit) 2 else 0, ItemBuilder(Material.CHAIN)
            .setName("&fSet Link")
            .setLore(listOf("", "&7Current:", "&f${link ?: "None"}"))
            .build()
        ) {
            player.sendMessage(
                plugin.msgUtils.getOrSetDefault(
                    "gui.create.linkButton.click",
                    "&eType the link in chat."
                )
            )
            currentAction = Action.LINK
            player.closeInventory()
        }
        setButton(17 - if(isEdit) 2 else 0, ItemBuilder(Material.END_ROD)
            .setName("&fSet Hover Text")
            .setLore(listOf("", "&7Current:", "&f${hover?.split("\\n") ?: "None"}"))
            .build()
        ) {
            player.sendMessage(
                plugin.msgUtils.getOrSetDefault(
                    "gui.create.hoverButton.click",
                    "&eType the message you want in chat."
                )
            )
            currentAction = Action.HOVER
            player.closeInventory()
        }

        if(isEdit)
            setButton(30, ItemBuilder(Material.REDSTONE_BLOCK)
                .setName("&cRemove")
                .setLore(listOf(""))
                .build()
            ) {
                if (identifier != null) {
                    player.sendMessage(MessageUtils.applyColor("&cCommand '$identifier' has been removed."))
                    plugin.commandManager.removeCommand(identifier!!)
                    player.closeInventory()
                }
            }

        setButton(if(isEdit) 32 else 31, ItemBuilder(Material.EMERALD_BLOCK)
            .setName("&fCreate")
            .setLore(listOf("", "&7Creates the command"))
            .build())
            {
                if(identifier == null || commandName == null || message == null || link == null || hover == null) {
                    player.sendMessage(plugin.msgUtils.getOrSetDefault("gui.create.submit.nulls", "&cSome of the values are not set!"))
                } else {
                    plugin.commandManager.addCommand(identifier!!, commandName!!, message!!, link!!, hover!!)
                    if(!isEdit) plugin.commandManager.registerCommand(identifier!!)
                    player.sendMessage(
                        plugin.msgUtils.getOrSetDefault(
                            "gui.create.submit.success",
                            "&eSuccessfully Updated Command"
                        ),
                        "(Some effects only work after a restart.)"
                    )
                    player.closeInventory()
                }
            }
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

    fun build(identifier: String): GuiCreateScreen {
        this.identifier = identifier
        this.commandName = plugin.commandManager.getConfig().getString("$identifier.name")
        this.message = plugin.commandManager.getConfig().getString("$identifier.message")
        this.link = plugin.commandManager.getConfig().getString("$identifier.link")
        this.hover = plugin.commandManager.getConfig().getString("$identifier.hover")

        setItems()
        return this
    }

    enum class Action {
        IDENTIFIER,
        NAME,
        MESSAGE,
        LINK,
        HOVER
    }
}