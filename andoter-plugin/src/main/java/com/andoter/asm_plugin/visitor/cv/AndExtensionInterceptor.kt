package com.andoter.asm_plugin.visitor.cv

import com.andoter.asm_plugin.AndExt
import com.andoter.asm_plugin.visitor.BaseClassInterceptor
import com.andoter.asm_plugin.visitor.mv.PrintLogInterceptor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

internal class AndExtensionInterceptor(api: Int, classVisitor: ClassVisitor?, var andExt: AndExt) : BaseClassInterceptor(api, classVisitor) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return PrintLogInterceptor(className, methodVisitor, access, name, descriptor, andExt.printLog)
    }
}