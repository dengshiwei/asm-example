package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.visitor.PluginConstant
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter

class TryCatchInterceptor(methodVisitor: MethodVisitor, access: Int, name: String?, var descriptor: String?) :
        AdviceAdapter(PluginConstant.ASM_VERSION, methodVisitor, access, name, descriptor) {
    private val labelStart = Label()
    private val labelEnd = Label()
    private val labelTarget = Label()
    override fun onMethodEnter() {
        // 定义开始位置
        mv.visitLabel(labelStart)
        // 开始 try...catch 块
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
        //判断方法的返回类型
        mv.visitInsn(getReturnCode(descriptor = descriptor))
        super.visitMaxs(maxStack, maxLocals)
    }

    /**
     * 获取对应的返回值
     */
    private fun getReturnCode(descriptor: String?): Int {
        return when (descriptor!!.subSequence(descriptor.indexOf(")") + 1, descriptor.length)) {
            "V" -> Opcodes.RETURN
            "I", "Z", "B", "C", "S" -> {
                mv.visitInsn(Opcodes.ICONST_0)
                Opcodes.IRETURN
            }
            "D" -> {
                mv.visitInsn(Opcodes.DCONST_0)
                Opcodes.DRETURN
            }
            "J" -> {
                mv.visitInsn(Opcodes.LCONST_0)
                Opcodes.LRETURN
            }
            "F" -> {
                mv.visitInsn(Opcodes.FCONST_0)
                Opcodes.FRETURN
            }
            else -> {
                mv.visitInsn(Opcodes.ACONST_NULL)
                Opcodes.ARETURN
            }
        }
    }
}