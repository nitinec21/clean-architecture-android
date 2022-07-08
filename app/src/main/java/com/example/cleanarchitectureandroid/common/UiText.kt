package com.example.cleanarchitectureandroid.common

import android.content.Context
import androidx.annotation.StringRes
import com.example.cleanarchitectureandroid.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return UiText.StringResource(R.string.error_unknown)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is UiText.DynamicString -> this.value
            is UiText.StringResource -> context.getString(this.id)
        }
    }
}
