package me.falsecode.chatlink.utils

import me.falsecode.chatlink.Main
import me.falsecode.chatlink.managers.Manager
import net.md_5.bungee.api.ChatColor
import java.util.regex.Pattern

class MessageUtils(plugin: Main) : Manager(plugin, "messages") {
    fun getOrSetDefault(path:String, default:String):String {
        if(getConfig().contains(path)) return applyColor(getConfig().getString(path) ?: "&cNull values are not permitted")
        getConfig().set(path, default)
        return applyColor(default)
    }

    fun getOrSetDefault(path:String, default:List<String>):List<String> {
        if(getConfig().contains(path)) return applyColor(getConfig().getStringList(path))
        getConfig().set(path, default)
        return applyColor(default.toMutableList())
    }

    fun get(path:String):String? {
        return getConfig().getString(path)
    }

    companion object {
        private val hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>")

        fun applyColor(raw: String): String {
            var message = raw
            var matcher = hexPattern.matcher(message)

            while (matcher.find()) {
                val hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length - 1))
                val before = message.substring(0, matcher.start())
                val after = message.substring(matcher.end())
                message = before + hexColor + after
                matcher = hexPattern.matcher(message)
            }
            return ChatColor.translateAlternateColorCodes('&', message)
        }

        fun applyColor(raw: List<String>): List<String> {
            val mutable = raw.toMutableList()
            for (i in raw.indices) {
                mutable[i] = applyColor(mutable[i])
            }
            return mutable
        }
    }
}