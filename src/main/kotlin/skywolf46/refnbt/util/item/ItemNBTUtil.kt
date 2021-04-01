package skywolf46.refnbt.util.item

import org.bukkit.inventory.ItemStack
import skywolf46.extrautility.util.invoke
import skywolf46.refnbt.impl.collections.CompoundNBTField
import skywolf46.refnbt.util.BukkitVersionUtil

object ItemNBTUtil {
    private val ITEM_CLASS = BukkitVersionUtil.getOBCClass("inventory.CraftItemStack")
    private val HANDLE_FIELD = ITEM_CLASS.getDeclaredField("handle")
    private val SET_TAG_METHOD =
        BukkitVersionUtil.getNMSClass("ItemStack").getMethod("setTag", BukkitVersionUtil.getNMSClass("NBTTagCompound"))
    private val TAG_METHOD = BukkitVersionUtil.getNMSClass("ItemStack").getMethod("getTag")

    init {
        HANDLE_FIELD.isAccessible = true
    }

    fun getOrCreateNBT(item: ItemStack): CompoundNBTField {
        val handle = HANDLE_FIELD.get(item)
        var tag = TAG_METHOD.invoke(handle)
        (tag == null){
            val comp = CompoundNBTField()
            with(comp.toNBTBase()) {
                SET_TAG_METHOD.invoke(handle, this)
                tag = this
            }
        }
        return CompoundNBTField(tag)
    }
}

fun ItemStack.getTag(): CompoundNBTField {
    return ItemNBTUtil.getOrCreateNBT(this)
}