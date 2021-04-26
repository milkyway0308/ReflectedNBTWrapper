package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class IntegerArrayNBTField(var data: IntArray) : AbstractNBTField<IntArray>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagIntArray")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getDeclaredConstructor(IntArray::class.java)
            NBT_CONSTRUCTOR.isAccessible = true
        }
    }

    override fun getAppliedClass(): Class<IntArray> = IntArray::class.java

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): IntegerArrayNBTField {
        return IntegerArrayNBTField(CONTENT_FIELD.get(nbt) as IntArray)
    }

    override fun fromObject(t: Any): AbstractNBTField<IntArray> = IntegerArrayNBTField(t as IntArray)
    override fun get(): IntArray {
        return data
    }

}