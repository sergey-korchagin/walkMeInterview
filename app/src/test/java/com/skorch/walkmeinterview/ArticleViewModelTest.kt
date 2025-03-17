import com.skorch.walkmeinterview.domain.GetArticlesUseCase
import com.skorch.walkmeinterview.ui.listscreen.ArticleViewModel
import com.skorch.walkmeinterview.ui.listscreen.UiState
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleViewModelTest {

    private lateinit var viewModel: ArticleViewModel
    private val getArticlesUseCase: GetArticlesUseCase = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ArticleViewModel(getArticlesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchArticles should set state to Loading`() = runTest {
        assertTrue(viewModel.articles.value is UiState.Loading)
    }
}
