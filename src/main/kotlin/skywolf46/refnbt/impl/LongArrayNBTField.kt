package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class LongArrayNBTField(var data: LongArray) : AbstractNBTField<LongArray>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field?
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagLongArray")
            CONTENT_FIELD = try {
                NBT_CLASS.getDeclaredField("data")
            } catch (e: Exception) {
                NBT_CLASS.getDeclaredField("b")
            }

            CONTENT_FIELD?.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(LongArray::class.java)
        }
    }

    override fun getAppliedClass(): Class<LongArray> = LongArray::class.java

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): LongArrayNBTField {
        return LongArrayNBTField(CONTENT_FIELD?.get(nbt) as LongArray)
    }

    override fun fromObject(t: Any): AbstractNBTField<LongArray> = LongArrayNBTField(t as LongArray)
    override fun get(): LongArray {
        return data
    }

}