package com.depromeet.team5.core.logger.annotation


@JvmInline
value class BuildVariant(val type: String) {


    companion object {

        @JvmStatic
        val DEBUG = BuildVariant("debug")

        @JvmStatic
        val RELEASE = BuildVariant("release")
    }
}
