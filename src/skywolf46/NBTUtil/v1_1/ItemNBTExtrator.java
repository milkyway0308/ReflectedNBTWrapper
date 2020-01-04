package skywolf46.NBTUtil.v1_1;

import org.bukkit.inventory.ItemStack;
import skywolf46.NBTUtil.v1_1.NBTData.ReflectedNBTCompound;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemNBTExtrator {
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

    public static ReflectedNBTCompound extractNBT(ItemStack item) {
        try {
            Object next = CRAFTITEM_COPY.invoke(null, item);
            Object extract = NET_ITEMSTACK_NBT_EXTRACT.invoke(next);
            if (extract == null)
                return null;
            return new ReflectedNBTCompound(extract);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ReflectedNBTCompound extractOrCreateNBT(ItemStack item) {
        ReflectedNBTCompound ex = extractNBT(item);
        if (ex == null)
            return new ReflectedNBTCompound();
        return ex;
    }

}
