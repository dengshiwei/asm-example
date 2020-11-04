package com.andoter.asm_plugin.visitor

import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.AccessCodeUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor

open class BaseClassInterceptor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {
    protected var className: String? = ""
    private var signature: String? = ""
    private var superName: String? = ""

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        ADLog.info("开始访问【类】，name = $name, superName = $superName, version = $version, access = ${AccessCodeUtils.accessCode2String(access)}")
        this.className = name
        this.signature = signature
        this.superName = superName
    }

    override fun visitEnd() {
        super.visitEnd()
        ADLog.info("结束访问类")
    }
}