package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class LongNBTField(var data: Long) : AbstractNBTField<Long>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagLong")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getDeclaredConstructor(Long::class.javaPrimitiveType)
            NBT_CONSTRUCTOR.isAccessible = true
        }
    }

    override fun getAppliedClass(): Class<Long> = Long::class.javaPrimitiveType!!

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): LongNBTField {
        return LongNBTField(CONTENT_FIELD.getLong(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Long> = LongNBTField(t as Long)
    override fun get(): Long {
        return data
    }

}