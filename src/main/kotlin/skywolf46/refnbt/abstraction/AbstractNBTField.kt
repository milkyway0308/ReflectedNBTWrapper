package skywolf46.refnbt.abstraction

import skywolf46.extrautility.util.log
import skywolf46.refnbt.impl.*
import skywolf46.refnbt.impl.collections.CompoundNBTField
import skywolf46.refnbt.impl.collections.ListNBTField
import skywolf46.refnbt.util.BukkitVersionUtil
import java.lang.reflect.Method

abstract class AbstractNBTField<out T : Any> {
    companion object {
        private val registry: MutableMap<Class<*>, AbstractNBTField<*>> = HashMap()
        private val registryNBT: MutableMap<Class<*>, AbstractNBTField<*>> = HashMap()
        private val registryNumber: MutableMap<Int, AbstractNBTField<*>> = HashMap()
        var NBT_TYPE_METHOD: Method = BukkitVersionUtil.getNMSClass("NBTBase").getMethod("getTypeId")
            private set

        fun getNBTBaseID(nbt: Any): Int {
            return (NBT_TYPE_METHOD.invoke(nbt) as Byte).toInt()
        }

        fun <T : Any> getByClass(cls: Class<*>, obj: Any): AbstractNBTField<T>? =
            registry[cls]?.fromObject(obj) as AbstractNBTField<T>?

        fun register(nbt: AbstractNBTField<*>) {
            registry[nbt.getAppliedClass()] = nbt
            registryNBT[nbt.getNBTClass()] = nbt
            registryNumber[getNBTBaseID(nbt.toNBTBase())] = nbt
        }

        fun fromType(type: Int) = registryNumber[type]

        fun fromNBT(any: Any) = registryNBT[any::class.java]?.fromNBTBase(any)

        fun check() {
//            log("§e[ReflectedNBTWrapper] Init..")
        }

        init {
            register(CompoundNBTField())
            register(ListNBTField())
            register(ByteArrayNBTField(byteArrayOf()))
            register(ByteNBTField(0))
            register(DoubleNBTField(0.0))
            register(FloatNBTField(0.0f))
            register(IntegerArrayNBTField(intArrayOf()))
            register(IntegerNBTField(0))
            register(LongArrayNBTField(longArrayOf()))
            register(LongNBTField(0L))
            register(ShortNBTField(0))
            register(StringNBTField(""))
            log("§e[ReflectedNBTWrapper] §aInit Complete!")
        }
    }

    abstract fun getAppliedClass(): Class<out T>
    abstract fun getNBTClass(): Class<*>
    abstract fun toNBTBase(): Any
    abstract fun fromNBTBase(nbt: Any): AbstractNBTField<T>
    abstract fun fromObject(t: Any): AbstractNBTField<T>?
    abstract fun get(): T
}

fun <T : Any> T.asNBT(): AbstractNBTField<T>? {
    val data = AbstractNBTField.getByClass<T>(this::class.javaPrimitiveType ?: this::class.java, this)
    if (data != null)
        return data.fromObject(this)
    return AbstractNBTStructure.getStructure(this::class)
        ?.iKnowWhatImDoingReallyICanSwearButKotlinIsBlockingMeThereWasNoWayWithoutThis(this) as AbstractNBTField<T>?
}

