package com.pr.paymentreminder

import org.junit.Assert

enum class VerificationMode {
    Exact,
    AtLeast,
    AtMost
}

class VerificationTimes(
    val times: Int,
    val mode: VerificationMode
)

fun times(times: Int) = VerificationTimes(
    times,
    VerificationMode.Exact
)

fun once() = times(1)

fun <T> TestObserver<T>.assertThat(
    times: VerificationTimes = once(),
    assertOn: (List<T>) -> List<T> = { it },
    block: T.() -> Boolean
) = assertOn(values).verify(times, block)

fun <T> TestObserver<T>.assertLast(
    block: T.() -> Boolean
) = assertThat(once(), { it.takeLast(1) }, block)

fun <T> TestObserver<T>.assertLastEquals(value: T) =
    assertThat(once(), { it.takeLast(1) }) { this == value }

inline fun <T> List<T>.verify(times: VerificationTimes, block: T.() -> Boolean) {
    val verificationMode = times.mode
    val conditionCountExpect = times.times
    /*Wrap evaluator into try/catch to avoid raising unwanted exceptions*/
    val conditionCount = count { runCatching { it.block() }.getOrNull() == true }
    val conditionMet = when (verificationMode) {
        VerificationMode.Exact -> conditionCount == conditionCountExpect
        VerificationMode.AtLeast -> conditionCount >= conditionCountExpect
        VerificationMode.AtMost -> conditionCount <= conditionCountExpect
    }
    Assert.assertTrue(
        buildConditionNotMetError(
            conditionCountExpect,
            conditionCount,
            verificationMode
        ),
        conditionMet
    )
}

fun buildConditionNotMetError(expected: Int, actual: Int, mode: VerificationMode): String {
    return "Verification mode was set to ${mode.name} $expected times but condition was met $actual times"
}
