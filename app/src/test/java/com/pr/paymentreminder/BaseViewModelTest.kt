package com.pr.paymentreminder

import kotlinx.coroutines.flow.Flow
import org.junit.After
import org.junit.Rule
import org.mockito.Mockito

abstract class BaseViewModelTest {

    @get:Rule
    var coroutinesTestRule = MainCoroutineTestRule()

    protected fun <T> Flow<T>.observe(): TestObserver<T> {
        return TestObserver<T>(coroutinesTestRule.testDispatcher).also { it.observe(this) }
    }

    @After
    fun validate() {
        /*Ensure misusing errors are showing in the correct tests and not at the end of the
        execution chain*/
        Mockito.validateMockitoUsage()
    }
}