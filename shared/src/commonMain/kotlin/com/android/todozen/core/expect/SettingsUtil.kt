package com.android.todozen.core.expect

import com.russhwolf.settings.Settings
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SettingsUtil {

    private val settings = Settings()

    var dayChallengeCounter by Setting("day", 5)
    var weekChallengeCounter by Setting("week", 25)
    var monthChallengeCounter by Setting("month", 100)

    private class Setting<T>(
        private val name: String,
        private val defaultValue: T
    ) : ReadWriteProperty<Any?, T> {

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return with(settings) {
                when (defaultValue) {
                    is Boolean -> getBoolean(name, defaultValue) as T
                    is Float -> getFloat(name, defaultValue) as T
                    is Int -> getInt(name, defaultValue) as T
                    is Long -> getLong(name, defaultValue) as T
                    is String -> getString(name, defaultValue) as T
                    else -> throw IllegalArgumentException("This type of data is not supported")
                }
            }
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            with(settings) {
                when (value) {
                    is Boolean -> putBoolean(name, value)
                    is Float -> putFloat(name, value)
                    is Int -> putInt(name, value)
                    is Long -> putLong(name, value)
                    is String -> putString(name, value)
                    else -> throw IllegalArgumentException("This type of data is not supported")
                }
            }
        }
    }
}