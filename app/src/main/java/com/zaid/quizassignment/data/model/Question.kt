package com.zaid.quizassignment.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val question: String,
    val options: List<String>,
    val answer: String
): Parcelable