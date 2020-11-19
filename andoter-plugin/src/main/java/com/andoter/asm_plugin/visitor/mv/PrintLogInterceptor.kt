package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.utils.StringUtils
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 增加所有调用方法的名称日志输出的地方
 */
internal class PrintLogInterceptor(var className: String?, methodVisitor: MethodVisitor,
                                   access:Int,
                                   name: String?,
                                   descriptor: String?, private val log: Boolean) : BaseMethodInterceptor(methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        super.onMethodEnter()
        if (log) {
            mv.visitLdcInsn(StringUtils.getFileName(className!!))
            mv.visitLdcInsn(name)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I",false)
        }
    }
}