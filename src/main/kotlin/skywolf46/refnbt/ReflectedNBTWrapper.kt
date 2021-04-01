package skywolf46.refnbt

import org.bukkit.plugin.java.JavaPlugin
import skywolf46.refnbt.abstraction.AbstractNBTField

class ReflectedNBTWrapper : JavaPlugin() {
    override fun onEnable() {
        AbstractNBTField.check()
    }
}