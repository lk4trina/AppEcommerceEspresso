package be.testinfoprojet2

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import be.testinfoprojet2.R

object NavigationHelper {

    fun configurar(
        activity: Activity,
        titulo: String,
        mostrarVoltar: Boolean = true
    ) {
        val headerTitle = activity.findViewById<TextView?>(R.id.headerTitle)
        val btnBack = activity.findViewById<TextView?>(R.id.btnHeaderBack)
        val navHome = activity.findViewById<TextView?>(R.id.navHome)
        val navCart = activity.findViewById<TextView?>(R.id.navCart)

        headerTitle?.text = titulo

        if (mostrarVoltar) {
            btnBack?.visibility = View.VISIBLE
            btnBack?.setOnClickListener {
                activity.finish()
            }
        } else {
            btnBack?.visibility = View.INVISIBLE
        }

        navHome?.setOnClickListener {
            if (activity !is MainActivity) {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                activity.startActivity(intent)
            }
        }

        navCart?.setOnClickListener {
            if (activity !is Panier) {
                val intent = Intent(activity, Panier::class.java)
                activity.startActivity(intent)
            }
        }
    }
}