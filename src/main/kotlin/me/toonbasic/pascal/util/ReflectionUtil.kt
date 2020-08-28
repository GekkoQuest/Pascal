package me.toonbasic.pascal.util

import java.lang.reflect.Field

object ReflectionUtil {

    private fun getField(clazz: Class<*>, name: String, type: Class<*>): Field {
        return try {
            val field = clazz.getDeclaredField(name)
            field.isAccessible = true

            check(!(field.type !== type)) { "Invalid action for field '" + name + "' (expected " + type.name + ", got " + field.type.name + ")" }

            field
        } catch (e: Exception) {
            throw RuntimeException("Failed to get field '$name'")
        }
    }

    /**
     * Gets a field value and casts it to the class specified generic in {@param action}
     *
     * @param clazz - The class with the field to retrieve in
     * @param fieldName - The field name
     * @param type - The field action, int, double etc
     * @param instance - The instance to use to retrieve the specified field value
     * @param <T> - The action generic
     * @return The field value for {@param fieldName} in the {@param clazz} class
    </T> */
    open fun <T> getFieldValue(clazz: Class<*>, fieldName: String, type: Class<*>, instance: Any?): T {
        val field = getField(clazz, fieldName, type)
        field.isAccessible = true

        return try {
            field.get(instance) as T
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Failed to get value of field '" + field.name.toString() + "'")
        }
    }
}
