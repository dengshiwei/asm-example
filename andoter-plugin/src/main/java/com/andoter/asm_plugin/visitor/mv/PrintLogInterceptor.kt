package com.andoter.asm_plugin.visitor.mv

import com.andoter.asm_plugin.visitor.BaseMethodInterceptor
import org.objectweb.asm.MethodVisitor

/**
 * 删除所有调用 Log 日志输出的地方
 */
internal class PrintLogInterceptor(api: Int,
                                   methodVisitor: MethodVisitor,
                                   access:Int,
                                   name: String,
                                   descriptor: String) :BaseMethodInterceptor(methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        super.onMethodEnter()
    }
}