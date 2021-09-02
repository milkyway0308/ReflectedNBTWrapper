package skywolf46.refnbt.impl.collections

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.abstraction.asNBT
import skywolf46.refnbt.util.BukkitVersionUtil

class ListNBTField : AbstractNBTField<List<*>> {
    companion object {
        val NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagList")
        val CONTENT_FIELD = NBT_CLASS.getDeclaredField("list")
        val NBT_CONSTRUCTOR = NBT_CLASS.getConstructor()

        init {
            CONTENT_FIELD.isAccessible = true
        }
    }

    private var original: MutableList<Any>
    internal var nbt: Any
    var targetClass: Class<*>? = null

    constructor() {
        nbt = NBT_CONSTRUCTOR.newInstance()
        original = CONTENT_FIELD.get(nbt) as MutableList<Any>
    }

    constructor(any: Any) {
        nbt = any
        original = CONTENT_FIELD.get(nbt) as MutableList<Any>
    }

    constructor(list: List<Any>) {
        nbt = NBT_CONSTRUCTOR.newInstance()
        original = CONTENT_FIELD.get(nbt) as MutableList<Any>
        if (list.isNotEmpty()) {
            val map: MutableMap<Class<*>, Int> = HashMap()
            list.forEach { any ->
                any.asNBT()?.let {
                    map[it.getNBTClass()] = (map[it.getNBTClass()] ?: 0) + 1
                    original.add(it.toNBTBase())
                } ?: run {
                    throw IllegalStateException("Cannot convert " + any::class.java + " to NBT")
                }
            }

            for (x: Map.Entry<Class<*>, Int> in map.entries) {
                if (x.value == list.size) {
                    targetClass = x.key
                    return
                }
            }
            throw IllegalStateException("Object classes not equals")
        }
    }

    override fun getAppliedClass(): Class<List<*>> = List::class.java

    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun toNBTBase(): Any = nbt

    override fun fromNBTBase(nbt: Any): AbstractNBTField<List<*>> = ListNBTField(nbt)

    override fun fromObject(t: Any): AbstractNBTField<List<*>> = ListNBTField(t as List<Any>)

    override fun get(): List<*> = original


    fun size() = original.size

    fun get(index: Int) = fromNBT(original[index])

    fun setNBT(index: Int, nbt: AbstractNBTField<Any>) {
        if (targetClass == null)
            targetClass = nbt.getNBTClass()
        if (targetClass != nbt.getNBTClass())
            throw IllegalStateException("NBTList add failed : This list only accepts ${targetClass!!.simpleName}")
        original[index] = nbt.toNBTBase()
    }

    fun set(index: Int, nbt: Any) {
        setNBT(index, nbt.asNBT() ?: throw IllegalStateException("Class ${nbt.javaClass.name} is not NBT class"))
    }

    fun addNBT(nbt: AbstractNBTField<Any>) {
        if (targetClass == null)
            targetClass = nbt.getNBTClass()
        if (targetClass != nbt.getNBTClass())
            throw IllegalStateException("NBTList add failed : This list only accepts ${targetClass!!.simpleName}")
        original.add(nbt.toNBTBase())
    }

    fun add(item: Any) {
        addNBT(item.asNBT() ?: throw IllegalStateException("Class ${item.javaClass.name} is not NBT class"))
    }

    fun remove(index: Int) {
        original.removeAt(index = index)
    }

    // Hashcode first check
    fun indexOf(any: Any): Int {
        val cacheHash = any.hashCode()
        for (data in 0 until original.size) {
            val orig = fromNBT(original[data])
            if (orig.hashCode() == cacheHash && orig == any) {
                return data
            }
        }
        return -1
    }

    fun contains(any: Any): Boolean {
        return indexOf(any) != -1
    }

    fun remove(item: Any) {
        for (x in 0 until size()) {
            if (get(x)?.get()?.equals(item) == true) {
                remove(x)
                return
            }
        }
    }

    fun getString(positional: Int) = get(positional)?.get() as String

    fun getDouble(positional: Int) = get(positional)?.get() as Double

    fun getLong(positional: Int) = get(positional)?.get() as Long

    fun getFloat(positional: Int) = get(positional)?.get() as Float

    fun getInt(positional: Int) = get(positional)?.get() as Int

    fun getShort(positional: Int) = get(positional)?.get() as Short

    fun getByte(positional: Int) = get(positional)?.get() as Byte

    fun asStringIterator() = TypedListIterator<String>(this)

    fun asIntegerIterator() = TypedListIterator<Int>(this)

    fun asLongIterator() = TypedListIterator<Long>(this)

    fun asListIterator() = TypedListIterator<ListNBTField>(this)

    fun asMapIterator() = TypedListIterator<CompoundNBTField>(this)


    class TypedListIterator<T : Any>(val nbt: ListNBTField) : Iterable<T>, Iterator<T> {
        private var pointer: Int = 0
        override fun hasNext(): Boolean = pointer >= nbt.size()

        override fun next(): T {
            return nbt.get(pointer++)?.get()!! as T
        }

        override fun iterator(): Iterator<T> {
            return this
        }

    }
}