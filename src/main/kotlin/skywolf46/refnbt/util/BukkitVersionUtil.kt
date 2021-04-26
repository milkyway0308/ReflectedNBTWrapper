package skywolf46.refnbt.util

import org.bukkit.Bukkit
import org.bukkit.Server
import skywolf46.extrautility.util.log
import skywolf46.refnbt.util.item.ItemNBTUtil


object BukkitVersionUtil {
    fun getVersion(server: Server): String {
        val packageName = server.javaClass.getPackage().name
        return packageName.substring(packageName.lastIndexOf('.') + 1)
    }

    @Throws(ClassNotFoundException::class)
    fun getNMSClass(className: String): Class<*> {
        return try {
            Class.forName("net.minecraft.server." + getVersion(Bukkit.getServer()) + "." + className)
        } catch (ex: Exception) {
            Void.TYPE
        }
    }

    @Throws(ClassNotFoundException::class)
    fun getOBCClass(className: String): Class<*> {
        return Class.forName("org.bukkit.craftbukkit." + getVersion(Bukkit.getServer()) + "." + className)
    }

    fun checkLegacy() {
        // If lower than 1.8, convert to Legacy mode.
        if (BukkitVersion(1, 8, 9) > current()) {
            log("§e[ReflectedNBTWrapper] §cReflectedNBTWrapper is running as legacy mode")
            ItemNBTUtil.enableLegacy()
        }
    }

    fun current(): BukkitVersion {
        return getVersion(Bukkit.getServer()).replace(Regex("[vR]"), "").split("_").let {
            return@let BukkitVersion(it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
    }
}
