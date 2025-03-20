package io.github.mew22.becreative.core.monitoring

import android.util.Log

class LogServiceImpl : LogService {
    override fun logNonFatalCrash(throwable: Throwable) {
        Log.e("ERROR", throwable.message, throwable)
    }
}
