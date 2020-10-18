package com.andoter.asm_plugin

import com.andoter.asm_plugin.utils.ADLog
import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager

class AndExtTransform(val appExtension: AppExtension, val andExt: AndExt) : Transform() {

    override fun getName(): String {
        return "AndPlugin"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        ADLog.info("Andoter Plugin Config {debug =  ${andExt.debug}, tryCatch = ${andExt.tryCatch}, " +
                "printLog = ${andExt.printLog}, methodTrace = ${andExt.methodTrace} }")
    }
}