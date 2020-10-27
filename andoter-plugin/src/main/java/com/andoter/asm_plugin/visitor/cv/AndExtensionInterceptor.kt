package com.andoter.asm_plugin.visitor.cv

import com.andoter.asm_plugin.AndExt
import com.andoter.asm_plugin.visitor.BaseClassInterceptor
import com.andoter.asm_plugin.visitor.BaseMethodInterceptor
import com.andoter.asm_plugin.visitor.mv.PrintLogInterceptor
import org.objectweb.asm.ClassVisitor

internal class AndExtensionInterceptor(api: Int, classVisitor: ClassVisitor?, andExt: AndExt) : BaseClassInterceptor(api, classVisitor) {
    private val methodInterceptors = mutableListOf<BaseMethodInterceptor>(
            PrintLogInterceptor()

    )



}