package com.josejordan.chooseyourownadventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

data class Page(
    val id: Int,
    val text: String,
    val choices: List<Choice>,
    val image: String
)

data class Choice(
    val text: String,
    val nextPageId: Int
)


// MainActivity.kt
class MainActivity : AppCompatActivity() {

    private lateinit var storyText: TextView
    private lateinit var choice1Button: Button
    private lateinit var choice2Button: Button
    private lateinit var storyManager: StoryManager
    private lateinit var mainImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainImage = findViewById(R.id.mainImage)
        storyText = findViewById(R.id.storyText)
        choice1Button = findViewById(R.id.choice1Button)
        choice2Button = findViewById(R.id.choice2Button)

        // Intenta cargar la historia
        val pages = loadStory(this)

        // Verifica si la historia se cargó correctamente
        if (pages.isNullOrEmpty()) {
            // Si la lista de páginas es null o está vacía, muestra un diálogo de error
            showErrorDialog()
            return // Asegúrate de no ejecutar el código que sigue si no se cargó la historia
        }
        // Si la lista de páginas no está vacía, continúa con la inicialización
        storyManager = StoryManager(pages)

        if (!storyManager.isValidStory) {
            showInvalidStoryDialog()
        } else {
            updatePage()
        }


        choice1Button.setOnClickListener {
            onChoiceMade(0)
        }

        choice2Button.setOnClickListener {
            onChoiceMade(1)
        }
    }

    fun TextView.typeWriterText(text: String, delay: Long = 50L) {
        val handler = Handler(Looper.getMainLooper())
        this.text = ""
        text.forEachIndexed { index, c ->
            handler.postDelayed({
                this.text = text.substring(0, index + 1)
            }, index * delay)
        }
    }

    fun TextView.clearTypeWriter() {
        this.text = ""
    }


    private fun showInvalidStoryDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Invalid Story")
            setMessage("The story data is invalid. Please check the story configuration and try again.")
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            setOnDismissListener {
                // Close the activity or navigate the user away from the current activity
                finish()
            }
            create()
            show()
        }
    }
    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("There was an error loading the story. Please try again later.")
            .setPositiveButton("OK") { dialog, which ->
                finish() // Cierra la actividad cuando el usuario toca el botón
            }
            .setCancelable(false)
            .show()
    }

    private fun updatePage() {
        val currentPage = storyManager.currentPage
        //storyText.text = currentPage.text

        // Aplica el efecto de escritura al texto de la historia
        storyText.typeWriterText(currentPage.text)

        // Cargar la imagen para la página actual
        val imageResId = resources.getIdentifier(currentPage.image, "drawable", packageName)
        if (imageResId != 0) { // El recurso existe
            mainImage.setImageResource(imageResId)
        } else {
            // Opcional: Poner una imagen predeterminada si no se encuentra la imagen de la página
            mainImage.setImageResource(R.drawable.aventure_title) // Asegúrate de tener una imagen 'default_image' en tus recursos
        }

        // Configurar la visibilidad y el texto de los botones basado en las elecciones disponibles
        when (currentPage.choices.size) {
            1 -> {
                choice1Button.text = currentPage.choices[0].text
                choice1Button.visibility = View.VISIBLE
                choice2Button.visibility = View.GONE
            }

            2 -> {
                choice1Button.text = currentPage.choices[0].text
                choice2Button.text = currentPage.choices[1].text
                choice1Button.visibility = View.VISIBLE
                choice2Button.visibility = View.VISIBLE
            }

            else -> {
                choice1Button.visibility = View.GONE
                choice2Button.visibility = View.GONE
            }
        }
    }


    private fun onChoiceMade(choiceIndex: Int) {
        val nextPageId = storyManager.currentPage.choices.getOrNull(choiceIndex)?.nextPageId
        nextPageId?.let {
            if (!storyManager.goToPage(it)) {
                showPageNotFoundError()
            } else {
                updatePage()
            }
        }
    }

    private fun showPageNotFoundError() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("La página a la que intentas acceder no existe. Por favor, elige otra opción.")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Error de Navegación")
        alert.show()
    }
}