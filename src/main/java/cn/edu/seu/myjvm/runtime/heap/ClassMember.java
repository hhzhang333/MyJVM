package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.MemberInfo;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by a on 2018/2/27.
 */
public class ClassMember {
    protected int accessFlags;
    protected String name;
    protected String descriptor;
    protected Class clazz;

    public void copyMemberInfo(MemberInfo memberInfo) throws Exception {
        this.accessFlags = memberInfo.accessFlags;
        this.name = memberInfo.name();
        this.descriptor = memberInfo.descriptor();
    }

    public boolean isAccessableTo(Class clazz) {
        if (this.isPublic())
            return true;
        Class c = this.clazz;
        if (this.isProtected())
            return clazz == c || clazz.isSubclassOf(c) || c.getPacketName().equals(clazz.getPacketName());
        if (!this.isPrivate()) {
            return c.getPacketName().equals(clazz.getPacketName());
        }
        return clazz == c;
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PRIVATE);
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isProtected() {
        return 0!= (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
