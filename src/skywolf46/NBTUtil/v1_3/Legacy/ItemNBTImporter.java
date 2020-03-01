package skywolf46.NBTUtil.v1_3.Legacy;

import org.bukkit.inventory.ItemStack;
import skywolf46.NBTUtil.v1_3.BukkitVersionUtil;
import skywolf46.NBTUtil.v1_3.NBTData.ReflectedNBTCompound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemNBTImporter {
    private static Method CRAFTITEM_COPY;
    private static Method BUKKIT_COPY;
    private static Method NET_ITEMSTACK_NBT_EXTRACT;
    private static Method NET_ITEMSTACK_NBT_IMPORT;

    static {
        try {
            Class c = BukkitVersionUtil.getOBCClass("inventory.CraftItemStack");
            CRAFTITEM_COPY = c.getMethod("asNMSCopy", Class.forName("org.bukkit.inventory.ItemStack"));
            BUKKIT_COPY = c.getMethod("asBukkitCopy", BukkitVersionUtil.getNMSClass("ItemStack"));
            c = BukkitVersionUtil.getNMSClass("ItemStack");
            NET_ITEMSTACK_NBT_EXTRACT = c.getMethod("getTag");
            NET_ITEMSTACK_NBT_IMPORT = c.getMethod("setTag",BukkitVersionUtil.getNMSClass("NBTTagCompound"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
    public static ItemStack importNBT(ItemStack item, ReflectedNBTCompound compound) {
        try {
            Object next = CRAFTITEM_COPY.invoke(null, item);
            NET_ITEMSTACK_NBT_IMPORT.invoke(next, compound.getNBTBase());
            return (ItemStack) BUKKIT_COPY.invoke(null, next);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
