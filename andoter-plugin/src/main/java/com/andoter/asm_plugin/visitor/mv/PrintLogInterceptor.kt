package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.utils.StringUtils
import com.andoter.asm_plugin.visitor.PluginConstant
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * 增加所有调用方法的名称日志输出的地方
 */
internal class PrintLogInterceptor(var className: String?, methodVisitor: MethodVisitor,
                                   access: Int,
                                   name: String?,
                                   descriptor: String?) : AdviceAdapter(PluginConstant.ASM_VERSION, methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        super.onMethodEnter()
        // 将当前类名添加到操作栈，作为 TAG
        mv.visitLdcInsn(StringUtils.getFileName(className!!))
        // 将当前方法名添加到操作栈，进行输出
        mv.visitLdcInsn(name)
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false)
    }
}