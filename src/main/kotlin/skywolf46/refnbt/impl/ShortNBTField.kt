package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class ShortNBTField(var data: Short) : AbstractNBTField<Short>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagShort")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(Short::class.javaPrimitiveType)
        }
    }

    override fun getAppliedClass(): Class<Short> = Short::class.javaPrimitiveType!!

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): ShortNBTField {
        return ShortNBTField(CONTENT_FIELD.getShort(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Short> = ShortNBTField(t as Short)
    override fun get(): Short {
        return data
    }

}