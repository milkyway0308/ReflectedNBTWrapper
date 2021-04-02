package skywolf46.refnbt.impl.collections

import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.abstraction.AbstractNBTStructure
import skywolf46.refnbt.abstraction.asNBT
import skywolf46.refnbt.util.BukkitVersionUtil

class CompoundNBTField : AbstractNBTField<MutableMap<*, *>> {
    companion object {
        val NBT_CLASS = BukkitVersionUtil.getNMSClass("NBTTagCompound")
        val CONTENT_FIELD = NBT_CLASS.getDeclaredField("map")
        val NBT_CONSTRUCTOR = NBT_CLASS.getConstructor()

        init {
            CONTENT_FIELD.isAccessible = true
        }

        fun from(vararg data: Pair<String, Any>): CompoundNBTField {
            val nbt = CompoundNBTField()
            for (x in data)
                nbt[x.first] = x.second
            return nbt
        }
    }


    lateinit var original: MutableMap<String, Any>
        private set
    internal var nbt: Any

    constructor() {
        this.nbt = NBT_CONSTRUCTOR.newInstance()
        original = CONTENT_FIELD.get(nbt) as MutableMap<String, Any>
    }

    constructor(nbt: Any) {
        this.nbt = nbt
        original = CONTENT_FIELD.get(nbt) as MutableMap<String, Any>
    }


    override fun getAppliedClass(): Class<MutableMap<*, *>> {
        val java = MutableMap::class.java
        return java
    }

    override fun getNBTClass(): Class<*> = NBT_CLASS

    override fun toNBTBase(): Any = nbt

    override fun fromNBTBase(nbt: Any): AbstractNBTField<MutableMap<*, *>> = CompoundNBTField(nbt)

    fun remove(key: String) {
        original.remove(key)
    }

    operator fun set(key: String, field: Any) {
        original[key] = field.asNBT()?.toNBTBase() as Any
    }

    fun setNBT(key: String, field: AbstractNBTField<*>) {
        original[key] = field.toNBTBase()
    }

    inline operator fun <reified T : Any> get(key: String): T? {
        if (key !in original)
            return null
        with(AbstractNBTStructure.getStructure(T::class)) {
            return this?.fromNBTData(fromNBT(original[key]!!)!!)
                ?: original[key]?.let { fromNBT(it) }?.get() as T?
        }
    }

    fun has(key: String): Boolean {
        return original.containsKey(key)
    }

    fun getString(key: String): String? {
        return get(key) ?: null
    }


    fun getInt(key: String): Int {
        return get(key) ?: 0
    }


    fun getDouble(key: String): Double {
        return get(key) ?: 0.0
    }


    fun getFloat(key: String): Float {
        return get(key) ?: 0f
    }


    fun getByte(key: String): Byte {
        return get(key) ?: 0
    }

    fun getShort(key: String): Short {
        return get(key) ?: 0
    }

    fun getLong(key: String): Long {
        return get(key) ?: 0L
    }

    fun getOrCreateMap(key: String): CompoundNBTField {
        return if (original.containsKey(key)) fromNBT(original[key]!!) else {
            val data = CompoundNBTField()
            original[key] = data.nbt
            return data
        } as CompoundNBTField
    }

    fun getMap(key: String): CompoundNBTField? {
        return original[key]?.let { fromNBT(it) } as CompoundNBTField?
    }

    fun getList(key: String): ListNBTField? {
        return original[key]?.let { fromNBT(it) } as ListNBTField?
    }

    fun getOrCreateList(key: String): ListNBTField {
        return if (original.containsKey(key)) fromNBT(original[key]!!) else {
            val data = ListNBTField()
            original[key] = data.nbt
            return data
        } as ListNBTField
    }

    fun keys(): ArrayList<String> {
        return ArrayList(original.keys)
    }


    override fun fromObject(t: Any): AbstractNBTField<MutableMap<*, *>> {
        val comp = CompoundNBTField()
        (t as MutableMap<*, *>).forEach { (x1, x2) ->
            comp.original[x1 as String] = x2?.asNBT()?.toNBTBase() as Any
        }
        return comp
    }

    override fun get(): MutableMap<*, *> {
        return original
    }
}