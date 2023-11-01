package com.josejordan.chooseyourownadventure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

data class Page(
    val id: Int,
    val text: String,
    val choices: List<Choice>
)

data class Choice(
    val text: String,
    val nextPageId: Int
)


class MainActivity : AppCompatActivity() {

    private lateinit var storyText: TextView
    private lateinit var choice1Button: Button
    private lateinit var choice2Button: Button

    private val pages = listOf(
        Page(0, "Estás en una encrucijada. ¿Vas a la izquierda o a la derecha?", listOf(
            Choice("Izquierda", 1),
            Choice("Derecha", 2)
        )),
        Page(1, "Encuentras un cofre. ¿Lo abres o continúas tu camino?", listOf(
            Choice("Abrir", 3),
            Choice("Continuar", 4)
        )),
        Page(2, "Ves un puente. ¿Lo cruzas o tomas otro camino?", listOf(
            Choice("Cruzar", 5),
            Choice("Otro camino", 6)
        )),
        // Aquí puedes continuar con más páginas y finales
    )

    private var currentPage: Page? = pages[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storyText = findViewById(R.id.storyText)
        choice1Button = findViewById(R.id.choice1Button)
        choice2Button = findViewById(R.id.choice2Button)

        updatePage()

        choice1Button.setOnClickListener {
            val nextPageId = currentPage?.choices?.get(0)?.nextPageId
            nextPageId?.let { id -> currentPage = pages[id] }
            updatePage()
        }

        choice2Button.setOnClickListener {
            val nextPageId = currentPage?.choices?.get(1)?.nextPageId
            nextPageId?.let { id -> currentPage = pages[id] }
            updatePage()
        }
    }

    private fun updatePage() {
        storyText.text = currentPage?.text
        choice1Button.text = currentPage?.choices?.get(0)?.text
        choice2Button.text = currentPage?.choices?.get(1)?.text
    }
}
