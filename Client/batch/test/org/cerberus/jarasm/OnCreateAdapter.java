package test.org.cerberus.jarasm;

import java.util.Arrays;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import profiling.org.cerberus.jarasm.JarAsmTest;

public class OnCreateAdapter extends AdviceAdapter {

	protected OnCreateAdapter(int api, MethodVisitor mv, int access,
			String name, String desc) {
		super(api, mv, access, name, desc);

	}

//	@Override
//	protected void onMethodExit(int opcode) {
//		System.out.println("onMethodExit - " + opcode);
////		{
////			mv.visitLdcInsn("Log");
////			mv.visitLdcInsn("Test...");
////			mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "i",
////					"(Ljava/lang/String;Ljava/lang/String;)I");
////		}
////		{
//			
//			
////		}
//		
//		
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "android/app/Activity", "getWindow", "()Landroid/view/Window;");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/Window", "getDecorView", "()Landroid/view/View;");
//		mv.visitLdcInsn(new Integer(16908290));
//		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "findViewById", "(I)Landroid/view/View;");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getRootView", "()Landroid/view/View;");
//		mv.visitTypeInsn(CHECKCAST, "android/widget/FrameLayout");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitMethodInsn(INVOKESTATIC, "org/cerberus/scenario/RuntimeMotionInjector", "injectEvent", "(Landroid/view/View;Landroid/content/Context;)V");
//		
//		
//		mv.visitTypeInsn(NEW, "org/cerberus/index/CerberusAPI");
//		mv.visitInsn(DUP);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "com/example/testandroid/MainActivity", "getApplicationContext", "()Landroid/content/Context;");
//		mv.visitLdcInsn(JarAsmTest.apiKey);
//		mv.visitInsn(ICONST_0);
//		mv.visitMethodInsn(INVOKESPECIAL, "org/cerberus/index/CerberusAPI", "<init>", "(Landroid/content/Context;Ljava/lang/String;Z)V");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "org/cerberus/index/CerberusAPI", "start", "()V");
//		
//		
//		super.onMethodExit(opcode);
//	}

	@Override
	protected void onMethodExit(int opcode) {
		
		System.out.println("======================" + opcode);
		
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, "android/app/Activity", "getWindow", "()Landroid/view/Window;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/Window", "getDecorView", "()Landroid/view/View;");
		mv.visitLdcInsn(new Integer(16908290));
		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "findViewById", "(I)Landroid/view/View;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getRootView", "()Landroid/view/View;");
		mv.visitTypeInsn(CHECKCAST, "android/widget/FrameLayout");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESTATIC, "org/cerberus/scenario/RuntimeMotionInjector", "injectEvent", "(Landroid/view/View;Landroid/content/Context;)V");
		
		
//		mv.visitTypeInsn(NEW, "org/cerberus/index/CerberusAPI");
//		mv.visitInsn(DUP);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitLdcInsn(JarAsmTest.apiKey);
//		mv.visitMethodInsn(INVOKESPECIAL, "org/cerberus/index/CerberusAPI", "<init>", "(Landroid/content/Context;Ljava/lang/String;)V");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "org/cerberus/index/CerberusAPI", "start", "()V");
		
//		mv.visitTypeInsn(NEW, "org/cerberus/index/CerberusAPI");
//		mv.visitInsn(DUP);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "com/example/testandroid/MainActivity", "getApplicationContext", "()Landroid/content/Context;");
//		mv.visitLdcInsn(JarAsmTest.apiKey);
//		mv.visitInsn(ICONST_0);
//		mv.visitMethodInsn(INVOKESPECIAL, "org/cerberus/index/CerberusAPI", "<init>", "(Landroid/content/Context;Ljava/lang/String;Z)V");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "org/cerberus/index/CerberusAPI", "start", "()V");
//		
		
		mv.visitTypeInsn(NEW, "org/cerberus/index/CerberusAPI");
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEVIRTUAL, "android/content/Context", "getApplicationContext", "()Landroid/content/Context;");
		mv.visitLdcInsn(JarAsmTest.apiKey);
		mv.visitInsn(ICONST_0);
		mv.visitMethodInsn(INVOKESPECIAL, "org/cerberus/index/CerberusAPI", "<init>", "(Landroid/content/Context;Ljava/lang/String;Z)V");
		mv.visitMethodInsn(INVOKEVIRTUAL, "org/cerberus/index/CerberusAPI", "start", "()V");
		super.onMethodExit(opcode);
		
	}


}
