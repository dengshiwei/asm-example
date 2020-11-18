package com.andoter.asm_plugin.utils

object StringUtils {

    /**
     * 根据路径名，返回当前的文件名
     */
    fun getFileName(path: String): String {
        return path.substring(path.lastIndexOf("/") + 1)
    }
}