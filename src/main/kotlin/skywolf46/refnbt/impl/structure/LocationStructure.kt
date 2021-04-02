package skywolf46.refnbt.impl.structure

import org.bukkit.Bukkit
import org.bukkit.Location
import skywolf46.refnbt.abstraction.AbstractNBTStructure
import skywolf46.refnbt.impl.collections.CompoundNBTField

object LocationStructure : AbstractNBTStructure<Location, CompoundNBTField>(Location::class) {
    override fun fromNBTData(nbt: CompoundNBTField): Location {
        return Location(Bukkit.getWorld(nbt.getString("w")),
            nbt.getDouble("x"),
            nbt.getDouble("y"),
            nbt.getDouble("z"),
            nbt.getFloat("_y"),
            nbt.getFloat("_p"))
    }

    override fun toNBTData(obj: Location): CompoundNBTField {
        return CompoundNBTField.from(
            "w" to obj.world.name,
            "x" to obj.x,
            "y" to obj.y,
            "z" to obj.z,
            "_y" to obj.yaw,
            "_p" to obj.pitch,
        )
    }

}