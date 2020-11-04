package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.visitor.BaseMethodInterceptor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 删除所有调用 Log 日志输出的地方
 */
internal class PrintLogInterceptor(var className: String?, methodVisitor: MethodVisitor,
                                   access:Int,
                                   name: String?,
                                   descriptor: String?,private val log: Boolean) :BaseMethodInterceptor(methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        super.onMethodEnter()
        if (log) {
            ADLog.info("onMethodEnter")
            mv.visitLdcInsn(className)
            mv.visitLdcInsn(name)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I",false)
        }
    }


    override fun visitMethodInsn(opcodeAndSource: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        //todo 检测所有的 Log 并进行删除
    }
}