package skywolf46.refnbt.util.item


import skywolf46.refnbt.util.BukkitVersionUtil

private val NMS_ITEM_CONSTRUCTOR = BukkitVersionUtil.getNMSClass("ItemStack")
    .getConstructor(BukkitVersionUtil.getNMSClass("Item"),
        Int::class.javaPrimitiveType,
        Int::class.javaPrimitiveType,
        Boolean::class.javaPrimitiveType)
private val NMS_ITEM_CLONE = BukkitVersionUtil.getNMSClass("ItemStack").getMethod("cloneItemStack")
private val NMS_ITEM_LOAD =
    BukkitVersionUtil.getNMSClass("ItemStack").getMethod("load", BukkitVersionUtil.getNMSClass("NBTTagCompound"))

private val COPY_METHOD = BukkitVersionUtil.getOBCClass("inventory.CraftItemStack")
    .getMethod("asCraftMirror", BukkitVersionUtil.getNMSClass("ItemStack"))

private val MAGIC_NUMBER_GETTER =
    BukkitVersionUtil.getOBCClass("util.CraftMagicNumbers").getMethod("getItem", Int::class.javaPrimitiveType)

// Only works on CatServer
//fun ItemStack.asBukkitItem(): org.bukkit.inventory.ItemStack {
//    val item =
//        NMS_ITEM_CONSTRUCTOR.newInstance(MAGIC_NUMBER_GETTER.invoke(null, Item.REGISTRY.getIDForObject(this.item)),
//            count,
//            itemDamage,
//            false)
//    NMS_ITEM_LOAD.invoke(item, serializeNBT())
//    return COPY_METHOD.invoke(null, item) as org.bukkit.inventory.ItemStack
//}