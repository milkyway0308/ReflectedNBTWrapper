package skywolf46.refnbt.impl.structure

import org.bukkit.util.Vector
import skywolf46.refnbt.abstraction.AbstractNBTStructure
import skywolf46.refnbt.impl.collections.CompoundNBTField

object VectorStructure : AbstractNBTStructure<Vector, CompoundNBTField>(Vector::class) {
    override fun fromNBTData(nbt: CompoundNBTField): Vector {
        return Vector(nbt.getDouble("x"), nbt.getDouble("y"), nbt.getDouble("z"))
    }

    override fun toNBTData(obj: Vector): CompoundNBTField {
        return CompoundNBTField.from(
            "x" to obj.x,
            "y" to obj.y,
            "z" to obj.z
        )
    }
}