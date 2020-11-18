package com.andoter.asm_plugin.visitor

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.AccessCodeUtils
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

open class BaseMethodInterceptor(methodVisitor: MethodVisitor?, access: Int, name: String?, descriptor: String?) : AdviceAdapter(PluginConstant.ASM_VERSION, methodVisitor, access, name, descriptor) {

    private val descriptor = descriptor

    override fun visitCode() {
        super.visitCode()
        ADLog.info("开始访问方法： name = $name, access = ${AccessCodeUtils.accessCode2String(access)}, descriptor = $descriptor")
    }

    override fun visitEnd() {
        super.visitEnd()
        ADLog.info("方法访问结束")
    }
}