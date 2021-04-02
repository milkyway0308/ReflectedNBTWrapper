package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class ByteArrayNBTField(var data: ByteArray) : AbstractNBTField<ByteArray>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagByteArray")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(ByteArray::class.java)
        }
    }

    override fun getAppliedClass(): Class<ByteArray> = ByteArray::class.java

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): ByteArrayNBTField {
        return ByteArrayNBTField(CONTENT_FIELD.get(nbt) as ByteArray)
    }

    override fun fromObject(t: Any): AbstractNBTField<ByteArray> = ByteArrayNBTField(t as ByteArray)
    override fun get(): ByteArray {
        return data
    }

}