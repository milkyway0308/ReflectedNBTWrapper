package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class FloatNBTField(var data: Float) : AbstractNBTField<Float>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagFloat")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(Float::class.javaPrimitiveType)
        }
    }

    override fun getAppliedClass(): Class<Float> = Float::class.javaPrimitiveType!!

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): FloatNBTField {
        return FloatNBTField(CONTENT_FIELD.getFloat(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Float> = FloatNBTField(t as Float)
    override fun get(): Float {
        return data
    }

}