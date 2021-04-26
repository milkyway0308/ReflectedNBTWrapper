package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class DoubleNBTField(var data: Double) : AbstractNBTField<Double>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagDouble")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getDeclaredConstructor(Double::class.javaPrimitiveType)
            NBT_CONSTRUCTOR.isAccessible = true
        }
    }

    override fun getAppliedClass(): Class<Double> = Double::class.javaPrimitiveType!!

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): DoubleNBTField {
        return DoubleNBTField(CONTENT_FIELD.getDouble(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Double> = DoubleNBTField(t as Double)
    override fun get(): Double {
        return data
    }

}