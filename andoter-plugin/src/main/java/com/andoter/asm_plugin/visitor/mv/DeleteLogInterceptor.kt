package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.AccessCodeUtils
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class DeleteLogInterceptor(methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : BaseMethodInterceptor(methodVisitor, access, name, descriptor) {

    /**
     * 在方法内部调用的地方，检测 Log 的用法，然后进行删除
     */
    override fun visitMethodInsn(opcodeAndSource: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {
        ADLog.info("DeleteLogInterceptor, owner = $owner, name = $name， opcode = ${AccessCodeUtils.accessCode2String(opcodeAndSource)}, des = $descriptor")
        if (Opcodes.ACC_STATIC.and(opcodeAndSource) != 0 && owner == "android/util/Log" && (name == "d" || name == "i" || name == "e" || name == "w" || name == "v")
                && descriptor == "(Ljava/lang/String;Ljava/lang/String;)I"
        ) {
            /**
             * 直接 return 只是删除了 Log 指令的调用，但是对应的当前的操作数栈没有进行处理
             */
            return
        }

        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
    }
}