package com.andoter.asm_plugin.utils

import com.android.build.api.transform.*
import com.android.utils.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

object TransformHelper {
    /**
     * 遍历处理 Jar
     */
    fun transformJars(jarInput: JarInput, outputProvider: TransformOutputProvider, isIncremental: Boolean) {
        val jarName = jarInput.name
        val status = jarInput.status
        val destFile = outputProvider.getContentLocation(jarName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        ADLog.info("TransformHelper[transformJars], jar = $jarName, status = $status, isIncremental = $isIncremental")
        if (isIncremental) {
            when (status) {
                Status.ADDED -> {
                    handleJarFile(jarInput, destFile)
                }
                Status.CHANGED -> {
                    handleJarFile(jarInput, destFile)
                }
                Status.REMOVED -> {
                    if (destFile.exists()) {
                        destFile.delete()
                    }
                }
                Status.NOTCHANGED -> {

                }
                else -> {
                }
            }
        } else {
            handleJarFile(jarInput, destFile)
        }
    }

    fun transformDirectory(directoryInput: DirectoryInput, outputProvider: TransformOutputProvider, isIncremental: Boolean) {
        val sourceFile = directoryInput.file
        val name = sourceFile.name
        val destDir = outputProvider.getContentLocation(name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
        ADLog.info("TransformHelper[transformDirectory], name = $name, sourceFile = ${sourceFile.absolutePath}, destFile = ${destDir.absolutePath}, isIncremental = $isIncremental")
        if (isIncremental) {
            val changeFiles = directoryInput.changedFiles
            for (changeFile in changeFiles) {
                val status = changeFile.value
                val inputFile = changeFile.key
                val destPath = inputFile.absolutePath.replace(sourceFile.absolutePath, destDir.absolutePath)
                val destFile = File(destPath)
                ADLog.info("目录：$destPath，状态：$status")
                when(status) {
                    Status.NOTCHANGED -> {

                    }
                    Status.REMOVED -> {
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                    }
                    Status.CHANGED, Status.ADDED -> {
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                        val modifyFile = modifyClassFile(inputFile, destFile)
                        if (modifyFile != null) {
                            FileUtils.copyFile(modifyFile, destFile)
                        } else {
                            FileUtils.copyFile(inputFile, destFile)
                        }
                    }
                    else -> {}
                }
            }
        } else {
            FileUtils.copyDirectory(sourceFile, destDir)
//            val files = sourceFile.listFiles()
//            for (file in files!!) {
//                FileUtils.copyFile(file, File(destDir, file.name))
//            }
        }
    }

    private fun handleJarFile(jarInput: JarInput,destFile: File) {
        // 空的 jar 包不进行处理
        if (jarInput.file == null || jarInput.file.length() == 0L) {
            ADLog.info("handleJarFile, ${jarInput.file.absolutePath} is null")
            return
        }
        // 构建 JarFile 文件
        val modifyJar = JarFile(jarInput.file, false)
        // 创建目标文件流
        val jarOutputStream = JarOutputStream(FileOutputStream(destFile))
        val enumerations = modifyJar.entries()
        // 遍历 Jar 文件进行处理
        for (jarEntry in enumerations) {
            val inputStream = modifyJar.getInputStream(jarEntry)
            val entryName = jarEntry.name
            if (entryName.startsWith(".DSA") || entryName.endsWith(".SF")) {
                return
            }
            val tempEntry = JarEntry(entryName)
            jarOutputStream.putNextEntry(tempEntry)
            var modifyClassBytes: ByteArray? = null
            val destClassBytes = IOUtils.readBytes(inputStream)
            if (!jarEntry.isDirectory && entryName.endsWith(".class")) {
                modifyClassBytes = destClassBytes?.let { modifyClass(it) }
            }

            if (modifyClassBytes != null) {
                jarOutputStream.write(modifyClassBytes)
            } else {
                jarOutputStream.write(destClassBytes)
            }
            jarOutputStream.flush()
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        modifyJar.close()
    }


    private fun modifyClass(sourceBytes: ByteArray): ByteArray? {
        try {

        } catch (exception: Exception) {
            ADLog.info("modify class exception = ${exception.message}")
        }
        return null
    }

    private fun modifyClassFile(inputFile: File, destDir: File): File? {
        return null
    }
}