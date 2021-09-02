package skywolf46.refnbt.data

import org.bukkit.inventory.meta.ItemMeta
import skywolf46.extrautility.util.extractField
import skywolf46.refnbt.abstraction.AbstractNBTField
import skywolf46.refnbt.abstraction.AbstractNBTStructure
import skywolf46.refnbt.abstraction.asNBT
import skywolf46.refnbt.impl.collections.CompoundNBTField
import skywolf46.refnbt.impl.collections.ListNBTField
import java.util.*

@Suppress("DEPRECATION")
class ItemMetaNBT// Force extract itemMeta
    (meta: ItemMeta) {
    // HashMap<String, NBTBase>
    @Deprecated("Only for internal tags")
    val original: TreeMap<String, Any>

    init {
        original = meta.extractField("unhandledTags")!!
    }

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
            return this?.fromNBTData(AbstractNBTField.fromNBT(original[key]!!)!!)
                ?: original[key]?.let { AbstractNBTField.fromNBT(it) }?.get() as T?
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
        return if (original.containsKey(key)) AbstractNBTField.fromNBT(original[key]!!) else {
            val data = CompoundNBTField()
            original[key] = data.nbt
            return data
        } as CompoundNBTField
    }

    fun getMap(key: String): CompoundNBTField? {
        return original[key]?.let { AbstractNBTField.fromNBT(it) } as CompoundNBTField?
    }

    fun getList(key: String): ListNBTField? {
        return original[key]?.let { AbstractNBTField.fromNBT(it) } as ListNBTField?
    }

    fun getOrCreateList(key: String): ListNBTField {
        return if (original.containsKey(key)) AbstractNBTField.fromNBT(original[key]!!) else {
            val data = ListNBTField()
            original[key] = data.nbt
            return data
        } as ListNBTField
    }

    fun keys(): ArrayList<String> {
        return ArrayList(original.keys)
    }

}