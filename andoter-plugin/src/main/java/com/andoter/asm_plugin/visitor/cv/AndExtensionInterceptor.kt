package com.andoter.asm_plugin.visitor.cv

import com.andoter.asm_plugin.AndExt
import com.andoter.asm_plugin.utils.ADLog
import com.andoter.asm_plugin.utils.AccessCodeUtils
import com.andoter.asm_plugin.visitor.BaseClassInterceptor
import com.andoter.asm_plugin.visitor.mv.DeleteLogInterceptor
import com.andoter.asm_plugin.visitor.mv.PrintLogInterceptor
import com.andoter.asm_plugin.visitor.mv.TryCatchInterceptor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

internal class AndExtensionInterceptor(api: Int, classVisitor: ClassVisitor?, var andExt: AndExt?) : BaseClassInterceptor(api, classVisitor) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        ADLog.info("开始访问方法： name = $name, access = ${AccessCodeUtils.accessCode2String(access)}, descriptor = $descriptor")
        var methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (andExt!!.printLog) {
            ADLog.error("PrintLogInterceptor")
            methodVisitor = PrintLogInterceptor(className, methodVisitor, access, name, descriptor)
        }
        if (andExt!!.deleteLog) {
            ADLog.error("DeleteLogInterceptor")
            methodVisitor = DeleteLogInterceptor(methodVisitor, access, name, descriptor)
        }

        if (andExt!!.tryCatch) {
            ADLog.error("TryCatchInterceptor")
            methodVisitor = TryCatchInterceptor(methodVisitor, access, name, descriptor)
        }
        return methodVisitor
    }
}