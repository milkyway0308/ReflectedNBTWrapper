package skywolf46.refnbt.impl

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Constructor
import java.lang.reflect.Field

class StringNBTField(var data: String) : AbstractNBTField<String>() {
    companion object {
        var NBT_CLASS: Class<*>
            private set
        var NBT_CONSTRUCTOR: Constructor<*>
            private set
        var CONTENT_FIELD: Field
            private set

        init {
            NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagString")
            CONTENT_FIELD = NBT_CLASS.getDeclaredField("data")
            CONTENT_FIELD.isAccessible = true
            NBT_CONSTRUCTOR = NBT_CLASS.getConstructor(String::class.java)
        }
    }

    override fun getAppliedClass(): Class<String> = String::class.java

    override fun toNBTBase(): Any = NBT_CONSTRUCTOR.newInstance(data)
    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun fromNBTBase(nbt: Any): StringNBTField {
        return StringNBTField(CONTENT_FIELD.get(nbt) as String)
    }

    override fun fromObject(t: Any): AbstractNBTField<String> = StringNBTField(t as String)
    override fun get(): String {
        return data
    }

}