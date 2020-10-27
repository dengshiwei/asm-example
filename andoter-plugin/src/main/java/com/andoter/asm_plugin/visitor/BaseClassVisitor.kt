package com.andoter.asm_plugin.visitor

import com.andoter.asm_plugin.AndExt
import com.andoter.asm_plugin.visitor.cv.AndExtensionInterceptor
import org.objectweb.asm.*

internal class BaseClassVisitor(api: Int, classWriter: ClassWriter, andExt: AndExt?) : ClassVisitor(api, classWriter){
    private var classInterceptors = mutableListOf<BaseClassInterceptor>()

    init {
        classInterceptors[0] = AndExtensionInterceptor(api, classWriter, andExt!!)
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        for (interceptor in classInterceptors) {
            interceptor.visitMethod(access, name, descriptor, signature, exceptions)
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitModule(name: String?, access: Int, version: String?): ModuleVisitor {
        return super.visitModule(name, access, version)
    }

    override fun visitNestHost(nestHost: String?) {
        super.visitNestHost(nestHost)
    }

    override fun visitInnerClass(name: String?, outerName: String?, innerName: String?, access: Int) {
        super.visitInnerClass(name, outerName, innerName, access)
    }

    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    override fun visitOuterClass(owner: String?, name: String?, descriptor: String?) {
        super.visitOuterClass(owner, name, descriptor)
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        for (interceptor in classInterceptors) {
            interceptor.visit(version, access, name, signature, superName, interfaces)
        }
    }

    override fun visitNestMember(nestMember: String?) {
        super.visitNestMember(nestMember)
    }

    override fun visitRecordComponent(name: String?, descriptor: String?, signature: String?): RecordComponentVisitor {
        return super.visitRecordComponent(name, descriptor, signature)
    }

    override fun visitField(access: Int, name: String?, descriptor: String?, signature: String?, value: Any?): FieldVisitor {
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitEnd() {
        super.visitEnd()
        for (interceptor in classInterceptors) {
            interceptor.visitEnd()
        }
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        for (interceptor in classInterceptors) {
            interceptor.visitAnnotation(descriptor, visible)
        }
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitTypeAnnotation(typeRef: Int, typePath: TypePath?, descriptor: String?, visible: Boolean): AnnotationVisitor {
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible)
    }

    override fun visitAttribute(attribute: Attribute?) {
        super.visitAttribute(attribute)
    }
}