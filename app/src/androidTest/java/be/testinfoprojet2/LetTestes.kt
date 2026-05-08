package be.testinfoprojet2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.testinfoprojet2.R
import be.testinfoprojet2.ListePanier
import be.testinfoprojet2.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LetTestes {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun limparCarrinhoAntesDoTeste() {
        ListePanier.clearPanier()
    }

    private fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "Aguardar $millis ms"

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }

    private fun esperarAparecer(viewId: Int, timeout: Long = 6000): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "Esperar view $viewId"

            override fun perform(uiController: UiController, rootView: View) {
                val tempoFinal = System.currentTimeMillis() + timeout
                val matcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                        if (matcher.matches(child) && child.isShown) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(100)
                } while (System.currentTimeMillis() < tempoFinal)

                throw AssertionError("A view com id $viewId não apareceu dentro de $timeout ms.")
            }
        }
    }

    private fun pausar(millis: Long) {
        onView(isRoot()).perform(waitFor(millis))
    }

    private fun aguardarView(viewId: Int) {
        onView(isRoot()).perform(esperarAparecer(viewId))
    }

    private fun recyclerViewComQuantidadeDeItens(quantidadeEsperada: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("RecyclerView com $quantidadeEsperada itens")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return recyclerView.adapter?.itemCount == quantidadeEsperada
            }
        }
    }

    private fun validarTelaInicial() {
        aguardarView(R.id.myListView)

        onView(withId(R.id.headerTitle))
            .check(matches(isDisplayed()))

        onView(allOf(withText("Loja Mobile"), isDisplayed()))
            .check(matches(isDisplayed()))

        onView(withId(R.id.myListView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navHome))
            .check(matches(isDisplayed()))

        onView(withId(R.id.navCart))
            .check(matches(isDisplayed()))

        pausar(500)
    }

    private fun validarTelaDetalhes() {
        aguardarView(R.id.buttonAddToCart)

        onView(withId(R.id.buttonAddToCart))
            .check(matches(isDisplayed()))

        onView(withId(R.id.nomProduit))
            .check(matches(isDisplayed()))

        onView(withId(R.id.priceDetails))
            .check(matches(isDisplayed()))

        onView(withId(R.id.photoDetails))
            .check(matches(isDisplayed()))
    }

    private fun abrirProdutoNaPosicao(posicao: Int) {
        aguardarView(R.id.myListView)

        onView(withId(R.id.myListView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.myListView))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(posicao)
            )

        pausar(500)

        onView(withId(R.id.myListView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    posicao,
                    click()
                )
            )

        validarTelaDetalhes()

        pausar(700)
    }

    private fun adicionarProdutoAtualAoCarrinho() {
        aguardarView(R.id.buttonAddToCart)

        onView(withId(R.id.buttonAddToCart))
            .check(matches(isDisplayed()))
            .perform(click())

        pausar(700)
    }

    private fun voltarParaInicioPeloHeader() {
        aguardarView(R.id.btnHeaderBack)

        onView(withId(R.id.btnHeaderBack))
            .check(matches(isDisplayed()))
            .perform(click())

        aguardarView(R.id.myListView)

        pausar(700)
    }

    private fun voltarParaInicioPelaNavbar() {
        aguardarView(R.id.navHome)

        onView(withId(R.id.navHome))
            .check(matches(isDisplayed()))
            .perform(click())

        aguardarView(R.id.myListView)

        pausar(700)
    }

    private fun irParaCarrinhoPelaNavbar() {
        aguardarView(R.id.navCart)

        onView(withId(R.id.navCart))
            .check(matches(isDisplayed()))
            .perform(click())

        validarTelaCarrinho()

        pausar(700)
    }

    private fun validarTelaCarrinho() {
        aguardarView(R.id.recyclerViewPanier)

        onView(withId(R.id.headerTitle))
            .check(matches(isDisplayed()))

        onView(allOf(withText("Carrinho"), isDisplayed()))
            .check(matches(isDisplayed()))

        onView(withId(R.id.textPanier))
            .check(matches(isDisplayed()))

        onView(withId(R.id.totalPanier))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(isDisplayed()))

        onView(withId(R.id.boutonFinaliser))
            .check(matches(isDisplayed()))
    }

    private fun adicionarProdutoEIrParaCarrinho(posicao: Int) {
        abrirProdutoNaPosicao(posicao)
        adicionarProdutoAtualAoCarrinho()
        voltarParaInicioPeloHeader()
        irParaCarrinhoPelaNavbar()
    }

    @Test
    fun mob01_deveCarregarTelaInicial() {
        validarTelaInicial()
        pausar(700)
    }

    @Test
    fun mob03_deveAbrirDetalhesDeProdutoMaisAbaixoEVoltar() {
        validarTelaInicial()

        abrirProdutoNaPosicao(4)

        voltarParaInicioPeloHeader()
    }

    @Test
    fun mob05_deveVisualizarCarrinhoPelaNavbar() {
        validarTelaInicial()

        irParaCarrinhoPelaNavbar()

        voltarParaInicioPelaNavbar()
    }

    @Test
    fun mob09_deveAdicionarProdutoDiferenteERemoverDoCarrinho() {
        validarTelaInicial()

        adicionarProdutoEIrParaCarrinho(3)

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(recyclerViewComQuantidadeDeItens(1)))

        pausar(700)

        onView(withId(R.id.recyclerViewPanier))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        pausar(700)

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(recyclerViewComQuantidadeDeItens(0)))
    }

    @Test
    fun mob10_deveRecalcularTotalDoCarrinhoComProdutoDiferente() {
        validarTelaInicial()

        adicionarProdutoEIrParaCarrinho(5)

        onView(withId(R.id.buttonRefreshTotal))
            .check(matches(isDisplayed()))
            .perform(click())

        pausar(700)

        onView(withId(R.id.totalPanier))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("0 €"))))
    }

    @Test
    fun mob11_deveAdicionarDoisProdutosDiferentesERemoverUm() {
        validarTelaInicial()

        abrirProdutoNaPosicao(2)
        adicionarProdutoAtualAoCarrinho()
        voltarParaInicioPeloHeader()

        abrirProdutoNaPosicao(5)
        adicionarProdutoAtualAoCarrinho()
        voltarParaInicioPeloHeader()

        irParaCarrinhoPelaNavbar()

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(recyclerViewComQuantidadeDeItens(2)))

        pausar(700)

        onView(withId(R.id.recyclerViewPanier))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        pausar(700)

        onView(withId(R.id.recyclerViewPanier))
            .check(matches(recyclerViewComQuantidadeDeItens(1)))
    }
}