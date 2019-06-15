package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by a on 2018/3/20.
 */
public class LocalVariableTableAttribute extends AttributeInfo {

    private LocalVariableTableEntry[] localVariableTable;

    public LocalVariableTableAttribute(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) throws Exception {
        u2 localVariableTableLength = u2.init(inputStream);
        localVariableTable = new LocalVariableTableEntry[localVariableTableLength.getData()];
        for (int i = 0; i < localVariableTable.length; i++)
            localVariableTable[i] = new LocalVariableTableEntry(u2.init(inputStream).getData(), u2.init(inputStream).getData(),
                    u2.init(inputStream).getData(), u2.init(inputStream).getData(), u2.init(inputStream).getData());
    }

    private class LocalVariableTableEntry {
        private int startPC;
        private int length;
        private int nameIndex;
        private int descriptorIndex;
        private int index;

        public LocalVariableTableEntry(int startPC, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPC = startPC;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }

        public int getStartPC() {
            return startPC;
        }

        public void setStartPC(int startPC) {
            this.startPC = startPC;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getNameIndex() {
            return nameIndex;
        }

        public void setNameIndex(int nameIndex) {
            this.nameIndex = nameIndex;
        }

        public int getDescriptorIndex() {
            return descriptorIndex;
        }

        public void setDescriptorIndex(int descriptorIndex) {
            this.descriptorIndex = descriptorIndex;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
