package me.falsecode.chatlink.managers

import me.falsecode.chatlink.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

abstract class Manager(plugin: Main, name: String) {
    private val plugin:Main
    private val file: File
    private val configFile:FileConfiguration

    init {
        file = File("${plugin.dataFolder}/${name}.yml")
        configFile = YamlConfiguration.loadConfiguration(file)
        this.plugin = plugin
    }

    fun saveFile() {
        try {
            configFile.save(file)
        } catch(exc:IOException) {
            exc.printStackTrace()
        }
    }

    fun getPlugin():Main {
        return plugin
    }

    fun getFile():File {
        return file
    }

    fun getConfig():FileConfiguration {
        return configFile
    }
}