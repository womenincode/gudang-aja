package id.amita.gudangaja.widget.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.LifecycleOwner

object WidgetUtils {

    val Context.lifecycleOwner: LifecycleOwner?
        get() {
            var context: Context? = this
            while (context != null && context !is LifecycleOwner) {
                val baseContext = (context as? ContextWrapper?)?.baseContext
                context = if (baseContext == context) null else baseContext
            }

            return if (context is LifecycleOwner) context else null
        }
}