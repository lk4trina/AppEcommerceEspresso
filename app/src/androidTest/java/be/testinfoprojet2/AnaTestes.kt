package be.testinfoprojet2

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnaTestes {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        ListePanier.clearPanier()
    }


    private fun validarTelaInicial() {

        onView(withId(R.id.myListView))
            .check(matches(isDisplayed()))
    }

    private fun abrirProduto(posicao: Int) {

        onView(withId(R.id.myListView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    posicao
                )
            )

        onView(withId(R.id.myListView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    posicao,
                    click()
                )
            )

        onView(withId(R.id.buttonAddToCart))
            .check(matches(isDisplayed()))
    }

    private fun abrirCarrinho() {

        onView(
            allOf(
                withId(R.id.navCart),
                isDisplayed()
            )
        ).perform(click())

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(isDisplayed()))
    }


    @Test
    fun mob02_validarExibicaoDeProdutoNaLista() {

        validarTelaInicial()

        onView(withId(R.id.myListView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    0
                )
            )

        onView(withId(R.id.myListView))
            .check(
                matches(
                    hasDescendant(withId(R.id.text1))
                )
            )

        onView(withId(R.id.myListView))
            .check(
                matches(
                    hasDescendant(withId(R.id.price1))
                )
            )

        onView(withId(R.id.myListView))
            .check(
                matches(
                    hasDescendant(withId(R.id.image1))
                )
            )
    }


    @Test
    fun mob04_validarAdicaoDeProdutoAoCarrinho() {

        validarTelaInicial()

        abrirProduto(0)

        onView(withId(R.id.buttonAddToCart))
            .perform(click())

        abrirCarrinho()

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerViewPanier))
            .check(
                matches(
                    hasDescendant(withId(R.id.namePanier))
                )
            )
    }


    @Test
    fun mob06_validarFinalizacaoDoPedido() {

        validarTelaInicial()

        abrirProduto(1)

        onView(withId(R.id.buttonAddToCart))
            .perform(click())

        abrirCarrinho()

        onView(withId(R.id.boutonFinaliser))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.MerciCommande))
            .check(matches(isDisplayed()))

        onView(withId(R.id.MerciCommande))
            .check(
                matches(
                    withText(
                        containsString("Agradecemos pelo seu pedido de")
                    )
                )
            )

        onView(withId(R.id.ok))
            .perform(click())
    }
}