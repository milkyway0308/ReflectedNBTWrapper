package skywolf46.refnbt.util.item

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import skywolf46.refnbt.data.ItemMetaNBT
import skywolf46.refnbt.util.BukkitVersionUtil

object ItemNBTUtil {
    private var legacyMode = false
    private val ITEM_CLASS = BukkitVersionUtil.getOBCClass("inventory.CraftItemStack")
    private val ITEM_CLASS_CONSTRUCTOR = ITEM_CLASS.getDeclaredConstructor(ItemStack::class.java)

    private val HANDLE_FIELD = ITEM_CLASS.getDeclaredField("handle")

    //    private val SET_TAG_METHOD =
//        BukkitVersionUtil.getNMSClass("ItemStack").getMethod("setTag", BukkitVersionUtil.getNMSClass("NBTTagCompound"))
    private val TAG_METHOD = BukkitVersionUtil.getNMSClass("ItemStack").getDeclaredField("tag").apply {
        isAccessible = true
    }

    init {
        HANDLE_FIELD.isAccessible = true
        ITEM_CLASS_CONSTRUCTOR.isAccessible = true
    }

    fun getOrCreateNBT(item: ItemMeta): ItemMetaNBT {
        return ItemMetaNBT(item)
    }


    fun enableLegacy() {
        legacyMode = true
    }
}

fun ItemMeta.getTag(): ItemMetaNBT {
    return ItemNBTUtil.getOrCreateNBT(this)
}