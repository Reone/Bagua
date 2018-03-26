package com.reone.bagua.core.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.reone.bagua.core.App

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * 主要功能：用于存储缓存数据
 *
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil
 * @author: AbrahamCaiJin
 * @date: 2017年05月04日 14:13
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

object AppSharePreference {
    /**
     * 保存在手机里面的文件名
     */
    private val FILE_NAME = App.appContext.packageName+".share"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun put(context: Context, key: String, obj: Any?) {

        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()

        when (obj) {
            is String -> editor.putString(key, obj)
            is Int -> editor.putInt(key, obj)
            is Boolean -> editor.putBoolean(key, obj)
            is Float -> editor.putFloat(key, obj)
            is Long -> editor.putLong(key, obj)
            else -> editor.putString(key, obj.toString())
        }

        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 保存对象
     */
    fun putBean(context: Context,key: String,obj: Any?){
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, Gson().toJson(obj))
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 得到对象
     */
    fun <T> getBean(context: Context,key: String,default: T?,javaClass:Class<T>):T?{
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        return Gson().fromJson(sp.getString(key,""),javaClass)?:default
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    fun get(context: Context, key: String, default: Any?): Any? {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)

        return when (default) {
            is String -> sp.getString(key, "")
            is Int -> sp.getInt(key, default)
            is Boolean -> sp.getBoolean(key, default)
            is Float -> sp.getFloat(key, default)
            is Long -> sp.getLong(key, default)
            else -> null
        }

    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     */
    fun clear(context: Context) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        return sp.all
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        /**
         * 反射查找apply的方法
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }

            editor.commit()
        }
    }
}
