package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class IntegerNBTField(var data: Int) : AbstractNBTField<Int>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagInt")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getDeclaredConstructor(Int::class.javaPrimitiveType)
            NBT_CONSTRUCTOR.isAccessible = true
        }
    }

    override fun getAppliedClass(): Class<Int> = Int::class.java

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): IntegerNBTField {
        return IntegerNBTField(CONTENT_FIELD.getInt(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Int> = IntegerNBTField(t as Int)
    override fun get(): Int {
        return data
    }

}