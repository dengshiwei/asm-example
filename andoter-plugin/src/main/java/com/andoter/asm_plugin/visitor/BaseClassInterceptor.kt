package com.andoter.asm_plugin.visitor

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.AccessCodeUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor

open class BaseClassInterceptor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {
    private var className: String? = ""
    private var signature: String? = ""
    private var superName: String? = ""
    // Method 拦截处理
    private var methodInterceptors = mutableListOf<BaseMethodInterceptor>()

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        ADLog.info("开始访问【类】，name = $name, superName = $superName, version = $version, access = ${AccessCodeUtils.accessCode2String(access)}")
        this.className = name
        this.signature = signature
        this.superName = superName
    }


    override fun visitField(access: Int, name: String?, descriptor: String?, signature: String?, value: Any?): FieldVisitor {
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        super.visitEnd()
        ADLog.info("结束访问类")
    }
}