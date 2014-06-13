package scenario.org.cerberus.jarasm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import profiling.org.cerberus.jarasm.CpuDump$1Dump;
import scenario.org.cerberus.jarasm.CustomClassNode;


public class JarAsmTest {

	public static String apiKey = "";
	public static String RealPath = "";
	public static void main(String[] args) throws Exception {
//		if(args!=null) {
//			return;
//		}
		System.out.println("--------------- Start Instrumentation Byte Code ---------------");
		
		String rootPath = "/Users/RhoSunghyun/Documents/dev/ttttttttttttttt/temp/newclz";
		String packageName = args[3];
		String className = args[4];
		if(args.length!=0 && args[0] != null) {
			rootPath = args[0];
		}
		
		if(args.length!=0 && args[1] != null) {
			apiKey = args[1];
		}

		System.out.println("apkkey " + apiKey);
		
		if(args.length>=3 && args[2] != null) {
			packageName = args[2];
			while( packageName.indexOf(".") > 0 ) {
				packageName = packageName.replace(".", "/");
			}
			
		}
		
		File file = new File(rootPath + "/" + packageName);
		if(packageName.length() != 0) {
			file = file.getParentFile();
		}
		
		if(!file.isDirectory() ) {
			System.out.println(rootPath + "/" + packageName + " is not directory");
			return;
		}
		RealPath = rootPath;
		
		
		new File(rootPath + "/org/cerberus/scenario/").mkdirs();
		
		byte[] b = NetworkMotionStream$2Dump.dump(className,packageName);
		
//		new File(rootPath + "/org/cerberus/profile/cpu/CpuDump$1.class").delete();
		
		FileOutputStream fos = new FileOutputStream(new File(rootPath + "/org/cerberus/scenario/NetworkMotionStream$2.class"));
		fos.write(b);
		fos.close();
	
		
		scanDirectory(file.getAbsolutePath());

		
		
		System.out.println("--------------- finish Instrumentation Byte Code ---------------");
		System.out.println("root - " + file.getAbsolutePath() );
	}
	
	private static void scanDirectory(String path) throws Exception {

		
		File rootDirectory = new File(path);
		
		boolean isSingleName = false;
		String faildString = "";
		for(String list : rootDirectory.list() ) {
			
			File childFile = new File(rootDirectory.getAbsolutePath() + "/" + list);
			
			if(childFile.isDirectory()) {
			} else {
				if(childFile.getName().replaceAll(".class", "").length() < 3 && !childFile.getName().replace(".class", "").equals("R") ){
					isSingleName = true;
					faildString = childFile.getName().replaceAll(".class", "");
					break;
				}
			}
			
		}	
		
		if(isSingleName) {
			System.out.println("warning progard..." + " " + path + " " + faildString);
			return;
		}
		
		for(String list : rootDirectory.list() ) {
			
			
			
			File childFile = new File(rootDirectory.getAbsolutePath() + "/" + list);
			
			if(childFile.isDirectory()) {
//				System.out.println(childFile.getAbsolutePath());	
				scanDirectory(childFile.getAbsolutePath());
			} else {
				if(childFile.getName().replaceAll(".class", "").length() > 3 )
					scanFile(childFile.getAbsolutePath());
			}
			
		}
		
		
	}
	
	private static void scanFile(String path) throws Exception {
	
		try{
//		System.out.println("	" + path);
		//&& 0>path.indexOf("$")
//		if(path.endsWith(".class") && 0<path.indexOf("Activity")) {
			// is class file
			if(path.indexOf("android") > 0 && path.indexOf("support") > 0 && path.indexOf("v4") > 0) {
				return;
			}
			if(path.indexOf("android") > 0 && path.indexOf("support") > 0 && path.indexOf("v7") > 0) {
				return;
			}
			if(path.indexOf("com") > 0 && path.indexOf("actionbarsherlock") > 0 ) {
				return;
			}
			
			File classFile = new File(path);
			
			FileInputStream fis = new FileInputStream(classFile);
			
			int api = Opcodes.V1_6;
//			int api = ClassWriter.COMPUTE_MAXS;
			try{
			ClassReader cr = new ClassReader(fis);
			ClassWriter cw = new ClassWriter(cr,api);
			
			
			ClassNode cn = new CustomClassNode(classFile.getName());
			
			cr.accept(cn, ClassReader.SKIP_FRAMES);
//			cr.accept(cn, ClassReader.EXPAND_FRAMES);
			cn.accept(cw);
			
			byte[] b = cw.toByteArray();
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(b);
			fos.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
//		}
		}
		catch(Exception e){
			System.out.println(path);
			e.printStackTrace();
		}
	}
	
}
