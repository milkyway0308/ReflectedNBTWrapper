# **ReflectedNBTWrapper** [![](https://jitci.com/gh/milkyway0308/ReflectedNBTWrapper/svg)](https://jitci.com/gh/milkyway0308/ReflectedNBTWrapper)

ReflectedNBTWrapper is NBT class wrapper using reflection for plugin version support.


## Installation
  #### Without build tools
  1. Download last release of ReflectedNBTWrapper from git release history.
  2. Add to dependency. 
  Be careful, ReflectedNBTWrapper use **difference package** to evade version collision.
  3. Add to extract target.
  ReflectNBTWrapper must have to extract or included in your plugin.
  #### With Gradle
  > Preparing.
  #### With Maven
  > Preparing.
## Example
  ##### Before version 1.2(1.2 Release)
    ```java
          // Simple nbt extract sample.
          // Extract string from item nbt.
          public static String extractExample(ItemStack item){
              ReflectedNBTCompound base = ItemNBTExtrator.extractNBT(item);            
              if(base == null || base.getValue("ItemText") == null)
                  return null;
              return ((ReflectedNBTString)base.getValue("ItemText")).getValue();
          }
          
          // Simple nbt import sample.
          // Save some string to item nbt.
          public static ItemStack importSample(ItemStack item){
              ReflectedNBTCompound base = ItemNBTExtrator.extractOrCreateNBT(item);
              base.setValue("ItemText","Hello, World!");
              return ItemNBTImporter.importNBT(item,base);
          }
    ```
  ##### Before version 1.1 R2(1.1.2 Alpha)
  ```java
        // Simple nbt extract sample.
        // Extract string from item nbt.
        public static String extractExample(ItemStack item){
            ReflectedNBTCompound base = ItemNBTExtrator.extractNBT(item);            
            if(base == null || base.getValue("ItemText") == null)
                return null;
            return ((ReflectedNBTString)base.getValue("ItemText")).getValue();
        }
        
        // Simple nbt import sample.
        // Save some string to item nbt.
        public static ItemStack importSample(ItemStack item){
            ReflectedNBTCompound base = ItemNBTExtrator.extractOrCreateNBT(item);
            base.setValue("ItemText",new ReflectedNBTString("Hello, World!"));
            return ItemNBTImporter.importNBT(item,base);
        }
  ```