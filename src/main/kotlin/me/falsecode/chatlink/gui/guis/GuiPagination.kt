package me.falsecode.chatlink.gui.guis

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.gui.Gui
import me.falsecode.chatlink.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class GuiPagination(plugin: Main, player: Player, size: Int, title: String) : Gui(plugin, player, size, title) {
    private var page: Int = 0
        set(value) {
            val itemsPerPage = inventory.size-9

            if(value*itemsPerPage >= indices || value < 0) return

            field = value

            clear()

            initPagination()
            setItems()
            update()

        }

    protected abstract val indices: Int

    protected val startIndex: Int
        get() {
            val itemsPerPage = inventory.size-9

            return page * itemsPerPage
    }

    override fun init() {
        super.init()
        initPagination()
        setItems()
        update()
    }

    abstract fun setItems()

    private fun initPagination() {
        for(i in inventory.size-9 until inventory.size) {
            items[i] = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName("&0")
                .build()
        }

        if(page > 0)
            setButton(inventory.size-9, ItemBuilder(Material.ARROW, 1, "&fGo Back").build()) {
                page--
            }
        if(page+1 * inventory.size-9 < indices)
            setButton(inventory.size-1, ItemBuilder(Material.ARROW, 1, "&fNext Page").build()) {
                page++
            }

    }
}