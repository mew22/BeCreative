package io.github.mew22.becreative.feature.feed

import io.github.mew22.becreative.core.common.ValueObjectCreator

@JvmInline
value class ChainedNumber private constructor(val input: Int) {
    companion object : ValueObjectCreator<Int, ChainedNumber, IllegalArgumentException>() {
        const val MAX_CHAINS = 10
        const val MIN_CHAINS = 1

        override val construction: (Int) -> ChainedNumber = ::ChainedNumber

        override fun isValid(input: Int): Boolean = input in MIN_CHAINS..MAX_CHAINS

        fun getFirst(): ChainedNumber = construction(MIN_CHAINS)
    }
}
