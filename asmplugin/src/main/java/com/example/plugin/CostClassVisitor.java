package com.example.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class CostClassVisitor extends ClassVisitor {

    public CostClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {

            private boolean inject = false;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//                if (Type.getDescriptor(Cost.class).equals(desc)) {
//                    inject = true;
//                }
                System.out.println("dddd " + desc + " " + CostClassVisitor.class);
                inject = true;
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                System.out.println("onMethodEnter " + name);
            }

            @Override
            protected void onMethodExit(int opcode) {
                System.out.println("onMethodExit " + name);
                if (inject) {
                    mv.visitLdcInsn(name);
                    mv.visitLdcInsn("chalilayang");
                    mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                }
            }
        };
        return mv;
    }
}
