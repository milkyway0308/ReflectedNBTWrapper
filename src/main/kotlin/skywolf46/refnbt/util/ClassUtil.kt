package skywolf46.refnbt.util

import java.util.function.Consumer

object ClassUtil {
    private val OBJECT: Class<*> = Any::class.java
    fun iterateParentClass(c: Class<*>, runner: Consumer<Class<*>?>) {
        var c = c
        if (c == OBJECT) return
        do {
            runner.accept(c)
            for (x in c.interfaces) {
                runner.accept(x)
                iterateParentClass(x, runner)
            }
        } while (c.superclass.also { c = it } != null && c != OBJECT)
    }

    fun toObjectName(cx: Class<*>): String {
        return if (cx.isArray) "array of " + toObjectName(cx.componentType) else "class " + cx.name
    }
}
