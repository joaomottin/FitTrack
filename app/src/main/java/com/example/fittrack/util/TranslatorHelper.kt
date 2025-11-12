package com.example.fittrack.util

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.Translation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object TranslatorHelper {
    suspend fun translateToPortuguese(text: String): String {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.PORTUGUESE)
            .build()
        val translator = Translation.getClient(options)
        return try {
            val conditions = DownloadConditions.Builder().requireWifi().build()
            suspendCancellableCoroutine { cont ->
                translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        translator.translate(text)
                            .addOnSuccessListener { translated -> cont.resume(translated) }
                            .addOnFailureListener { e -> cont.resumeWithException(e) }
                    }
                    .addOnFailureListener { e -> cont.resumeWithException(e) }
            }
        } finally {
            translator.close()
        }
    }
}
