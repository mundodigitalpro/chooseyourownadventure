package com.josejordan.chooseyourownadventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        Page(
            0,
            "Estás en una encrucijada en un bosque oscuro. ¿Vas a la izquierda hacia una cabaña o a la derecha hacia un río?",
            listOf(
                Choice("Izquierda (Cabaña)", 1),
                Choice("Derecha (Río)", 2)
            )
        ),
        Page(
            1, "Llegas a una cabaña. ¿Decides entrar o seguir adelante?", listOf(
                Choice("Entrar", 3),
                Choice("Seguir", 4)
            )),
        Page(2, "Te encuentras con un río. ¿Decides cruzarlo o seguir su curso?", listOf(
            Choice("Cruzar", 5),
            Choice("Seguir curso", 6)
        )),
        Page(3, "Dentro de la cabaña, encuentras a un anciano que te ofrece una misión. ¿La aceptas?", listOf(
            Choice("Acepto", 7),
            Choice("Rechazar", 8)  // Lleva a la nueva página 8
        )),
        Page(4, "Llegas a un claro con un campamento abandonado. ¿Decides investigar o seguir tu camino?", listOf(
            Choice("Investigar", 9),
            Choice("Seguir camino", 10)
        )),
        Page(5, "Encuentras un castillo tras cruzar el río. ¿Deseas explorarlo o prefieres regresar?", listOf(
            Choice("Explorar", 11),
            Choice("Regresar", 12)
        )),
        Page(6, "Llegas a una cascada. ¿Te atreves a nadar o prefieres tomar un descanso?", listOf(
            Choice("Nadar", 13),
            Choice("Descansar", 14)
        )),
        Page(7, "El anciano te cuenta sobre un antiguo tesoro escondido en una cueva detrás de la cascada. ¿Decides buscarlo o ignorar la historia?", listOf(
            Choice("Buscar tesoro", 15),
            Choice("Ignorar", 16)
        )),
        Page(8, "Tras rechazar la oferta del anciano, te das cuenta de que no te sientes bien y decides descansar un rato en la cabaña. Al despertar, notas que algo ha cambiado en el bosque. ¿Quieres investigar o escapar de la cabaña?", listOf(
            Choice("Investigar", 9),
            Choice("Escapar", 10)
        )),
        Page(9, "Sigues las indicaciones del anciano y después de superar varios desafíos, finalmente encuentras el tesoro escondido. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(10, "Decides no involucrarte y abandonas la cabaña. Sigues tu camino sin mirar atrás. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(11, "Investigando el castillo, descubres indicios de otro tesoro pero no logras encontrarlo. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(12, "Sigues tu camino, el bosque se torna más denso y oscuro. Finalmente, encuentras una salida y regresas a casa. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(13, "Siguiendo las indicaciones del anciano, encuentras una cueva oculta detrás de la cascada. En su interior, descubres un cofre lleno de oro y joyas. ¡Has encontrado el tesoro perdido! ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(14, "Decides descansar y mientras lo haces, reflexionas sobre las aventuras que podrías haber vivido. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(15, "Siguiendo las indicaciones del anciano, llegas a la cueva detrás de la cascada. Después de una búsqueda exhaustiva, encuentras el tesoro escondido. ¡Te has vuelto rico! ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        )),
        Page(16, "Decides que el tesoro no vale el riesgo y decides regresar a tu aldea. La gente te recibe con alegría y te das cuenta de que el verdadero tesoro es estar en casa. ¡Fin!", listOf(
            Choice("Volver a empezar", 0)
        ))
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
            println("Current Page ID: ${currentPage?.id}, Selected choice1. Going to page ID: $nextPageId")
            nextPageId?.let { id -> currentPage = pages[id] }
            updatePage()
        }

        choice2Button.setOnClickListener {
            val nextPageId = currentPage?.choices?.get(1)?.nextPageId
            println("Current Page ID: ${currentPage?.id}, Selected choice2. Going to page ID: $nextPageId")
            nextPageId?.let { id -> currentPage = pages[id] }
            updatePage()
        }
    }

    private fun updatePage() {
        storyText.text = currentPage?.text
        when (currentPage?.choices?.size) {
            1 -> {
                choice1Button.text = currentPage?.choices?.get(0)?.text
                choice2Button.visibility = View.GONE // Esconde el segundo botón si no hay una segunda opción.
            }
            2 -> {
                choice1Button.text = currentPage?.choices?.get(0)?.text
                choice2Button.text = currentPage?.choices?.get(1)?.text
                choice2Button.visibility = View.VISIBLE // Asegúrate de que el botón sea visible.
            }
            else -> {
                choice1Button.visibility = View.GONE
                choice2Button.visibility = View.GONE
            }
        }
    }


    /*        private fun updatePage() {
                storyText.text = currentPage?.text
                choice1Button.text = currentPage?.choices?.get(0)?.text
                choice2Button.text = currentPage?.choices?.get(1)?.text
            }*/

/*    private fun updatePage() {
        storyText.text = currentPage?.text

        // Primera opción siempre presente
        choice1Button.text = currentPage?.choices?.getOrNull(0)?.text
        choice1Button.visibility =
            if (currentPage?.choices?.getOrNull(0) != null) View.VISIBLE else View.GONE

        // Segunda opción puede o no estar
        choice2Button.text = currentPage?.choices?.getOrNull(1)?.text
        choice2Button.visibility =
            if (currentPage?.choices?.getOrNull(1) != null) View.VISIBLE else View.GONE
    }*/

}
