package skywolf46.refnbt.util

import org.bukkit.Bukkit
import org.bukkit.Server


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
}
