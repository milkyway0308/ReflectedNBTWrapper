package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class ByteNBTField(var data: Byte) : AbstractNBTField<Byte>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagByte")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getDeclaredConstructor(Byte::class.javaPrimitiveType)
            NBT_CONSTRUCTOR.isAccessible = true
        }
    }

    override fun getAppliedClass(): Class<Byte> = Byte::class.javaPrimitiveType!!

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): ByteNBTField {
        return ByteNBTField(CONTENT_FIELD.getByte(nbt))
    }

    override fun fromObject(t: Any): AbstractNBTField<Byte> = ByteNBTField(t as Byte)
    override fun get(): Byte {
        return data
    }

}