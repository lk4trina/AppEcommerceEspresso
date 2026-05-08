package be.testinfoprojet2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import be.testinfoprojet2.R

class DetailsProduit : AppCompatActivity() {

    lateinit var nomProduit: TextView
    lateinit var prix: TextView
    lateinit var boutonAddToPanier: Button
    lateinit var boutonPanier: Button
    lateinit var photoProduit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_produit)

        NavigationHelper.configurar(this, "Detalhes do produto")

        nomProduit = findViewById(R.id.nomProduit)
        prix = findViewById(R.id.priceDetails)
        photoProduit = findViewById(R.id.photoDetails)
        boutonAddToPanier = findViewById(R.id.buttonAddToCart)
        boutonPanier = findViewById(R.id.buttonDetailsToCart)

        val currentObject = intent.getParcelableExtra<Produit>("object")

        val name = currentObject?.name
        nomProduit.text = name

        val price = currentObject?.price
        prix.text = "$price €"

        val image = currentObject?.image
        if (image != null) {
            photoProduit.setImageResource(image)
        }

        boutonAddToPanier.setOnClickListener {
            addToPanier(currentObject)

            Toast.makeText(
                this,
                "Adicionado ao carrinho: ${currentObject?.name} - ${currentObject?.price} €",
                Toast.LENGTH_SHORT
            ).show()
        }

        boutonPanier.setOnClickListener {
            val intent = Intent(this, Panier::class.java)
            startActivity(intent)
        }
    }

    private fun addToPanier(currentObject: Produit?) {
        if (currentObject != null) {
            ListePanier.addItem(currentObject)
        }
    }
}