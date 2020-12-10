package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.visitor.PluginConstant
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class TryCatchInterceptor(methodVisitor: MethodVisitor, access: Int, name: String?, descriptor: String?) :
        AdviceAdapter(PluginConstant.ASM_VERSION, methodVisitor, access, name, descriptor) {
    private val labelStart = Label()
    private val labelEnd = Label()
    private val labelTarget = Label()
    override fun onMethodEnter() {
        mv.visitLabel(labelStart)
        // 定义开始位置
        mv.visitTryCatchBlock(labelStart, labelEnd, labelTarget, "java/lang/Exception")
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        // 定义正常代码结束的位置
        mv.visitLabel(labelEnd)
        // 定义 catch 块开始的位置
        mv.visitLabel(labelTarget)
        val local1 = newLocal(Type.getType("Ljava/lang/Exception"))
        mv.visitVarInsn(Opcodes.ASTORE, local1)
        mv.visitVarInsn(Opcodes.ALOAD, local1)
        // 输出 ex.printStackTrace
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false)
        super.visitMaxs(maxStack, maxLocals)
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }
}