package io.github.mew22.becreative.core.monitoring

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
