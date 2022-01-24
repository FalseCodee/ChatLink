package me.falsecode.chatlink.gui.guis

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.Gui
import me.falsecode.chatlink.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class GuiCommandListScreen(plugin: Main, player: Player) : GuiPagination(plugin, player, 18, "Find Command") {

    private val identifiers: List<String>
    override val indices: Int
    init {
        identifiers = plugin.commandManager.getAllIdentifiers()
        indices = identifiers.size

        init()
        openInventory()
    }


    override fun setItems() {
        for(i in 0 until inventory.size-9){
            if(i+startIndex >= identifiers.size) break

            setButton(i, ItemBuilder(Material.OAK_SIGN)
                .setName(identifiers[i+startIndex])
                .setLore(listOf(
                    "",
                    "&7Command:",
                    "&f/${plugin.commandManager.getConfig().getString("${identifiers[i+startIndex]}.name") ?: "None"}",
                    "&7Message:",
                    "&f${plugin.commandManager.getConfig().getString("${identifiers[i+startIndex]}.message") ?: "None"}",
                    "&7Link:",
                    "&f${plugin.commandManager.getConfig().getString("${identifiers[i+startIndex]}.link") ?: "None"}",
                )).build()){
                switchScreen(GuiCreateScreen(plugin, player, true).build(identifiers[i+startIndex]))
            }
        }
    }

    override fun onInventoryClick(event: InventoryClickEvent, gui: Gui) {
       event.isCancelled = true
    }


}