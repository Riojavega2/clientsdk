package test.org.cerberus.jarasm;

import java.util.Arrays;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class CustomClassNode extends ClassNode {

	
	private String[] interfaces ;
	
	
	@Override
	public void visitSource(String file, String debug) {
//		System.out.println("visitSource  " + file + " " + debug);
		
		super.visitSource(file, debug);
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
//		System.out.println("visitOuterClass  "   + owner + " " + name + " " + desc);
		super.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
//		System.out.println("visitInnerClass  " + name + " " + outerName + " " + innerName + " " + access);
		super.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		
		
		System.out.println("visitMethod - " + access + " " +  name + " " + desc + " " + signature + " " + Arrays.toString(exceptions) + " " + Arrays.toString(this.interfaces) );
		
		if(name.startsWith("<") || (0<name.indexOf("$"))) {
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
//		
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

		if( 0<desc.indexOf("Landroid/view/View" ) && name.equals("onClick") && interfaces != null) {
			for(int i = 0 ; i < this.interfaces.length; i++) {
				if(interfaces[i].equals("android/view/View$OnClickListener")) {
					System.out.println(".....");
					MotionEventCollectAdviceAdapter mecaa = new MotionEventCollectAdviceAdapter(Opcodes.ASM4, mv, access, name, desc);
					return mecaa;		
				}
			}
			
		}
		
		if(name.equals("onCreate")) {
			ActivityAdviceAdapter caa = new ActivityAdviceAdapter(Opcodes.ASM4, mv, access, name, desc);
			return caa;
		} else {
			return mv;
		}
		
//		return mv;
		
//		
//		CustomMethodVisitor  customMV = new CustomMethodVisitor( access,  name, desc, signature, exceptions,mv);
//		customMV.visitMethodInsn( access,  name,  desc,  signature);
//		return customMV;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
//		System.out.println("visit  " + version + " " +  access + " " + name +  access + " " + signature
//				 +  access + " " + superName +  access + " " + Arrays.toString(interfaces));
		
		for(int interfaceIndex = 0; interfaceIndex < interfaces.length ; interfaceIndex++) {
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
		this.interfaces = interfaces;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
//		System.out.println("visitField  " + version + " " +  access + " " + name +  access + " " + signature
//				 +  access + " " + superName +  access + " " + value);
		
		return super.visitField(access, name, desc, signature, value);
	}

	
	
}
