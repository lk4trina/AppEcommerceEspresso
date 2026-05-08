package be.testinfoprojet2

import be.testinfoprojet2.R
import kotlin.collections.ArrayList

class ListeProduits {

    private val listeProduits = arrayListOf<Produit>()

    init {
        // Lista pré-definida de produtos, cadastrada diretamente no app.
        // Em um projeto real, esses dados poderiam vir de uma API, JSON ou banco de dados.

        listeProduits.add(Produit("1", "Óculos de sol", "26.99", R.drawable.sunglasses_giorgio_trovato_unsplash))
        listeProduits.add(Produit("2", "Carrinho em miniatura", "11.50", R.drawable.car_giorgio_trovato_unsplash))
        listeProduits.add(Produit("3", "Livro", "14.99", R.drawable.book_brett_jordan_unsplash))
        listeProduits.add(Produit("4", "Câmera fotográfica", "485.50", R.drawable.camera_subtle_cinematics_unsplash))
        listeProduits.add(Produit("5", "Garrafa térmica para café", "12.00", R.drawable.coffeebottle_quokkabottles_unsplash))
        listeProduits.add(Produit("6", "Espremedor de limão", "7.99", R.drawable.lemonsqueezer_giorgio_trovato_unsplash))
        listeProduits.add(Produit("7", "Caneta", "4.99", R.drawable.pen_hope_house_press_unsplash))
        listeProduits.add(Produit("8", "Skate pequeno", "79.99", R.drawable.pennyboard_mike_meyers_unsplash))
        listeProduits.add(Produit("9", "Adesivo", "1.23", R.drawable.sticker_mathias_reding_unsplash))
        listeProduits.add(Produit("10", "Óculos de sol feminino", "27.99", R.drawable.sunglasseswomen_annie_theby_unsplash))
        listeProduits.add(Produit("11", "Carteira", "25.10", R.drawable.wallet_oliur_unsplash))
        listeProduits.add(Produit("12", "Relógio", "67.48", R.drawable.watch_krismawan_kadek_unsplash))
        listeProduits.add(Produit("13", "Camiseta branca", "8.99", R.drawable.whiteshirt_anomaly_unsplash))
        listeProduits.add(Produit("14", "Tênis masculino", "85.99", R.drawable.shoe_imani_bahati_unsplash))
        listeProduits.add(Produit("15", "Garrafa de vinho tinto", "25.00", R.drawable.winebottle_brett_jordan_unsplash))
    }

    fun getListeDesProduits(): ArrayList<Produit> {
        // Retorna a lista de produtos disponíveis no aplicativo.
        return listeProduits
    }
}