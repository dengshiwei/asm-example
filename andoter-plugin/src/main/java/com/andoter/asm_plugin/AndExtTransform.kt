package com.andoter.asm_plugin

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.TransformHelper
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
        return true
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        beforeTransform()
        internalTransform(transformInvocation)
        afterTransform()

    }

    private fun beforeTransform() {
        ADLog.info("Andoter Plugin Config {debug =  ${andExt.debug}, tryCatch = ${andExt.tryCatch}, " +
                "printLog = ${andExt.printLog}, methodTrace = ${andExt.methodTrace} }")
    }

    private fun internalTransform(transformInvocation: TransformInvocation?) {
        ADLog.info("internalTransform")
        if (transformInvocation != null) {
            transformInvocation.outputProvider.deleteAll()
            for (transformInput in transformInvocation.inputs) {
                for (jarInput in transformInput.jarInputs) {
                    TransformHelper.transformJars(jarInput, transformInvocation.outputProvider, transformInvocation.isIncremental)
                }

                for (directoryInput in transformInput.directoryInputs) {
                    TransformHelper.transformDirectory(directoryInput, transformInvocation.outputProvider, transformInvocation.isIncremental)
                }
            }
        }
    }

    private fun afterTransform() {

    }
}