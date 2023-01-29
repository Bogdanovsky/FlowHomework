package otus.homework.flowcats

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    override fun populate(fact: String) {
        findViewById<TextView>(R.id.fact_textView).text = fact
    }

    override fun showErrorMessage(s: String) {
        Toast.makeText(context, "Check the internet, asshole!", Toast.LENGTH_SHORT).show()
    }
}

interface ICatsView {

    fun populate(fact: String)
    abstract fun showErrorMessage(s: String)
}