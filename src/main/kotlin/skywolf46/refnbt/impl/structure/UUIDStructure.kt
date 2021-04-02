package skywolf46.refnbt.impl.structure

import skywolf46.refnbt.abstraction.AbstractNBTStructure
import skywolf46.refnbt.impl.StringNBTField
import java.util.*

object UUIDStructure : AbstractNBTStructure<UUID, StringNBTField>(UUID::class) {
    override fun fromNBTData(nbt: StringNBTField): UUID {
        return UUID.fromString(nbt.get())
    }

    override fun toNBTData(obj: UUID): StringNBTField {
        return StringNBTField(obj.toString())
    }
}