package com.andoter.asm_plugin.visitor.mv

import org.objectweb.asm.MethodVisitor

class DeleteLogInterceptor(methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : BaseMethodInterceptor(methodVisitor, access, name, descriptor) {


    /**
     * 在方法内部调用的地方，检测 Log 的用法，然后进行删除
     */
    override fun visitMethodInsn(opcodeAndSource: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
    }
}