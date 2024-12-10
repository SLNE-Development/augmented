package dev.slne.augmented.common.base.core.extensions

import kotlin.streams.asSequence

private val STACK_WALK_INSTANCE: StackWalker =
    StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)

fun getCallerClass() = getCallerClass(0)
fun getCallerClass(depth: Int) =
    STACK_WALK_INSTANCE.walk { it.asSequence().drop(3 + depth).firstOrNull()?.declaringClass }

fun checkCallerClass(expected: Class<*>) {
    if (getCallerClass(1) != expected) {
        throw IllegalStateException("Caller class is not $expected")
    }
}

fun checkInstantiationByServiceLoader() {
    check(getCallerClass(1)?.name?.startsWith("java.util.ServiceLoader") == true) { "Cannot instantiate instance directly" }
}