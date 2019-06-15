package cn.edu.seu.myjvm.parser;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantClassInfo;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantMemberRefInfo;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantNameAndTypeInfo;
import cn.edu.seu.myjvm.parser.constantinfo.ConstantUTF8Info;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantPool {
    private int constant_pool_count;
    private ConstantInfo[] cpInfo;

    public ConstantPool(u2 count) {
        constant_pool_count = count.getData();
        cpInfo = new ConstantInfo[constant_pool_count];
    }

    public void read(InputStream inputStream) {
        for (int i = 1; i < constant_pool_count; i++) {
            short tag = u1.init(inputStream).getData();
            ConstantInfo constantInfo = ConstantInfo.getConstantInfo(tag, this);
            constantInfo.read(inputStream);
            cpInfo[i] = constantInfo;
            if (tag == ConstantInfo.CONSTANT_Double || tag == ConstantInfo.CONSTANT_Long) {
                i++;
            }
        }
    }

    public ConstantInfo getConstantInfo(int index) throws Exception {
        ConstantInfo cpInfo = this.cpInfo[index];
        if (cpInfo != null)
            return cpInfo;
        else
            throw new Exception("Invalid constant pool index");
    }

    public String[] getNameAndType(int index) throws Exception {
        String[] strings = new String[2];
        ConstantNameAndTypeInfo constantNameAndTypeInfo = (ConstantNameAndTypeInfo) this.getConstantInfo(index);
        strings[0] = this.getUtf8(constantNameAndTypeInfo.nameIndex);
        strings[1] = this.getUtf8(constantNameAndTypeInfo.descriptorIndex);
        return strings;
    }

    public String getClassName(int index) throws Exception {
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) this.getConstantInfo(index);
        return this.getUtf8(constantClassInfo.nameIndex);
    }

    public String getUtf8(int index) throws Exception {
        ConstantUTF8Info utf8Info = (ConstantUTF8Info) this.getConstantInfo(index);
        return utf8Info.value;

    }

    public int getConstant_pool_count() {
        return constant_pool_count;
    }

    public void setConstant_pool_count(int constant_pool_count) {
        this.constant_pool_count = constant_pool_count;
    }

    public ConstantInfo[] getCpInfo() {
        return cpInfo;
    }

    public void setCpInfo(ConstantInfo[] cpInfo) {
        this.cpInfo = cpInfo;
    }

    public void printConstanPoolInfo(ConstantPool cp){
        if(cp!=null){
            System.out.println("ConstantPool:"+cp.constant_pool_count);

            for(int i=1;i<cp.constant_pool_count;i++){
                ConstantInfo constantInfo = cp.cpInfo[i];
                if(constantInfo instanceof ConstantMemberRefInfo){
                    ConstantMemberRefInfo memberRef=(ConstantMemberRefInfo) constantInfo;
                    short tag=memberRef.tag;
                    switch(tag){
                        case 9:
                            System.out.println("#"+i+" fieldref:" + ((ConstantUTF8Info) cp.cpInfo[memberRef.nameAndTypeIndex]).value );
                            continue;
                        case 10:
                            System.out.println("#"+i+" methodref:" + ((ConstantUTF8Info) cp.cpInfo[memberRef.nameAndTypeIndex]).value);
                            continue;
                        default :
                            continue;
                    }
                }else if(constantInfo instanceof ConstantNameAndTypeInfo){
                    ConstantNameAndTypeInfo nameAndType_=(ConstantNameAndTypeInfo)constantInfo;
                    System.out.println("#"+i+" NameAndType        "+((ConstantUTF8Info) cp.cpInfo[nameAndType_.nameIndex]).value );
                }else if(constantInfo instanceof ConstantClassInfo){
                    ConstantClassInfo clazz=(ConstantClassInfo)constantInfo;
                    System.out.println("#"+i+" class        "+((ConstantUTF8Info) cp.cpInfo[clazz.nameIndex]).value);
                }else if(constantInfo instanceof ConstantUTF8Info){
                    ConstantUTF8Info utf_1=(ConstantUTF8Info) constantInfo;
                    System.out.println("#"+i+" UTF-8        "+utf_1.value);
                }
            }
            System.out.println("\n");
        }
    }
}
