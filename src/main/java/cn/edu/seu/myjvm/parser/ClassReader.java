package cn.edu.seu.myjvm.parser;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.basictype.u4;
import cn.edu.seu.myjvm.instructions.InstructionTable;
import cn.edu.seu.myjvm.parser.attribute.CodeAttribute;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantClassInfo;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantUTF8Info;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ClassReader {

    public static ClassFile read(InputStream inputStream) throws Exception {
        ClassFile classFile = new ClassFile();
        classFile.magic = u4.init(inputStream).getData();
        classFile.minorVersion = u2.init(inputStream).getData();
        classFile.majorVersion = u2.init(inputStream).getData();

        //解析常量池
        u2 consantPoolCount = u2.init(inputStream);
        ConstantPool constantPool = new ConstantPool(consantPoolCount);
        constantPool.read(inputStream);
        constantPool.printConstanPoolInfo(constantPool);
        classFile.setConstantPool(constantPool);

        //解析类信息
        classFile.accessFlag = u2.init(inputStream).getData();
        u2 classIndex = u2.init(inputStream);
        ConstantClassInfo clazz = (ConstantClassInfo) constantPool.getCpInfo()[classIndex.getData()];  //获取类名，并将其转化为ContantClass类
        ConstantUTF8Info className = (ConstantUTF8Info) constantPool.getCpInfo()[clazz.nameIndex];
        classFile.className = className.value;

        //获取父类信息
        u2 superIndex = u2.init(inputStream);
        //java/lang/Object无超类
        if (superIndex.getData() != 0){
            ConstantClassInfo superClazz = (ConstantClassInfo) constantPool.getCpInfo()[superIndex.getData()];
            ConstantUTF8Info superclassName = (ConstantUTF8Info) constantPool.getCpInfo()[superClazz.nameIndex];
            classFile.superClass = superclassName.value;
        }

        //获取接口信息
        classFile.interfaceCount = u2.init(inputStream).getData();
        classFile.interfaces = new String[classFile.interfaceCount];
        for (int i = 0; i < classFile.interfaceCount; i++) {
            int interfaceIndex = u2.init(inputStream).getData();
            ConstantClassInfo interfaceClazz = (ConstantClassInfo) constantPool.getCpInfo()[interfaceIndex];
            ConstantUTF8Info interfaceName = (ConstantUTF8Info) constantPool.getCpInfo()[interfaceClazz.nameIndex];
            classFile.interfaces[i] = interfaceName.value;
        }
        System.out.print("\n");

        //获取字段信息
        classFile.fieldCount = u2.init(inputStream).getData();
        classFile.fields = new MemberInfo[classFile.fieldCount];
        for (int i = 0; i < classFile.fieldCount; i++) {
            MemberInfo fieldInfo = new MemberInfo(constantPool);
            fieldInfo.read(inputStream);
            System.out.print("field:" + ((ConstantUTF8Info) constantPool.getCpInfo()[fieldInfo.nameIndex]).value + ", ");
            System.out.print("desc:" + ((ConstantUTF8Info) constantPool.getCpInfo()[fieldInfo.descriptorIndex]).value + "\n");
            classFile.fields[i] = fieldInfo;
        }
        System.out.print("\n");

        //获取方法信息
        classFile.methodCount = u2.init(inputStream).getData();
        classFile.methods = new MemberInfo[classFile.methodCount];
        for (int i = 0; i < classFile.methodCount; i++) {
            MemberInfo methodInfo = new MemberInfo(constantPool);
            methodInfo.read(inputStream);
            System.out.print("method:" + ((ConstantUTF8Info) constantPool.getCpInfo()[methodInfo.nameIndex]).value + "(), ");
            System.out.print("desc:" + ((ConstantUTF8Info) constantPool.getCpInfo()[methodInfo.descriptorIndex]).value + "\n");
//            for (int j = 0; j < methodInfo.attributesCount; j++) {
//                if (methodInfo.attributes[j] instanceof CodeAttribute) {
//                    CodeAttribute codeAttribute = (CodeAttribute) methodInfo.attributes[j];
//                    for (int m = 0; m < codeAttribute.codeLength; m++) {
//                        short code = codeAttribute.code[m];
//                        System.out.print(InstructionTable.getInstruction(code) + "\n");
//                    }
//                }
//            }
            classFile.methods[i] = methodInfo;
        }
        return classFile;

    }

}
