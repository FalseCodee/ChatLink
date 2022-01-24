package me.falsecode.chatlink.gui

import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class GuiManager {
    companion object {
        val guiHashMap:HashMap<UUID, Gui> = HashMap()

        fun getGui(player: Player):Gui? {
            return getGui(player.uniqueId)
        }

        private fun getGui(uuid: UUID):Gui? {
            return guiHashMap[uuid]
        }

        fun addGui(gui: Gui) {
            guiHashMap[gui.player.uniqueId] = gui
        }
    }
}