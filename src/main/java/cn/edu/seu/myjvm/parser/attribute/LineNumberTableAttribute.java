package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by a on 2018/3/20.
 */
public class LineNumberTableAttribute extends AttributeInfo {
    private LineNumberTableEntry[] lineNumberTable;

    public LineNumberTableAttribute(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) throws Exception {
        int lineNumberTableLength = u2.init(inputStream).getData();
        lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
        for (int i = 0; i< lineNumberTable.length; i++)
            lineNumberTable[i] = new LineNumberTableEntry(u2.init(inputStream).getData(), u2.init(inputStream).getData());
    }

    private class LineNumberTableEntry {
        private int startPC;
        private int lineNumber;

        public int getStartPC() {
            return startPC;
        }

        public void setStartPC(int startPC) {
            this.startPC = startPC;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public LineNumberTableEntry(int startPC, int lineNumber) {
            this.startPC = startPC;
            this.lineNumber = lineNumber;
        }
    }
}
