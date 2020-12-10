package com.andoter.asm_plugin.utils

import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream

object ClassOutputUtil {

    fun byte2File(outputPath: String, sourceByte: ByteArray) {
        val file = File(outputPath)
        if (file.exists()) {
            file.delete()
        }

        val inputStream = ByteArrayInputStream(sourceByte)
        val outputStream = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).apply { len = this } != -1) {
            outputStream.write(buffer, 0, len)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
    }
}