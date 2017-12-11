package br.com.andrecouto.easymetro.Utils

import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat
import android.app.Activity
import android.content.Context
import android.util.Log

object PermissionUtils {

    private val TAG = PermissionUtils::class.java.name

    fun validate(activity: Activity, requestCode: Int, permissions: ArrayList<String>): Boolean {
        val list = ArrayList<String>()
        for (permission in permissions) {
            // Valida permissão
            val ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
            if (!ok) {
                list.add(permission)
            }
        }
        if (list.isEmpty()) {
            return true
        }

        // Lista de permissões que falta acesso.
        val newPermissions = arrayOfNulls<String>(list.size)
        list.toTypedArray()

        // Solicita permissão
        ActivityCompat.requestPermissions(activity, newPermissions, requestCode)

        return false
    }

    fun hasPermission(context: Context, permission: String): Boolean {

        val res = context.checkCallingOrSelfPermission(permission)

        Log.v(TAG, "permission: " + permission + " = \t\t" +
                if (res == PackageManager.PERMISSION_GRANTED) "GRANTED" else "DENIED")

        return res == PackageManager.PERMISSION_GRANTED

    }

    fun hasPermission(context: Context, permissions: ArrayList<String>): Boolean {

        var hasAllPermissions = true

        for (permission in permissions) {
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false
            }
        }
        return hasAllPermissions
    }
}