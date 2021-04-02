package skywolf46.refnbt.abstraction

import skywolf46.refnbt.impl.structure.LocationStructure
import skywolf46.refnbt.impl.structure.UUIDStructure
import skywolf46.refnbt.impl.structure.VectorStructure
import kotlin.reflect.KClass

abstract class AbstractNBTStructure<T : Any, NBT : AbstractNBTField<Any>> constructor(val target: KClass<T>) {
    @Suppress("UNCHECKED_CAST")
    companion object {
        private val map =
            mutableMapOf<KClass<Any>, AbstractNBTStructure<Any, AbstractNBTField<Any>>>()

        private fun <T : Any> register(
            target: KClass<T>,
            struct: AbstractNBTStructure<Any, AbstractNBTField<Any>>,
        ) {
            map[target as KClass<Any>] = struct
        }

        @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
        fun <T : Any> getStructure(data: KClass<T>): AbstractNBTStructure<T, AbstractNBTField<Any>>? {
            return map[data] as AbstractNBTStructure<T, AbstractNBTField<Any>>?
        }

        init {
            LocationStructure.register()
            VectorStructure.register()
            UUIDStructure.register()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun register() {
        register(target, this as AbstractNBTStructure<Any, AbstractNBTField<Any>>)
    }

    abstract fun fromNBTData(nbt: NBT): T

    abstract fun toNBTData(obj: T): NBT

    // Yay EasterEgg
    @Suppress("UNCHECKED_CAST")
    internal fun iKnowWhatImDoingReallyICanSwearButKotlinIsBlockingMeThereWasNoWayWithoutThis(any: Any) {
        toNBTData(any as T)
    }
}