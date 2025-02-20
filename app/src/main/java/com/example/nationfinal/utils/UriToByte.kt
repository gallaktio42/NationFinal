package com.example.nationfinal.utils

import android.content.Context
import android.net.Uri
import kotlinx.io.IOException
import kotlin.jvm.Throws

@Throws(IOException::class)
fun Uri.uriToByteArray(context: Context) =
    context.contentResolver.openInputStream(this)?.use { it.buffered().readBytes() }