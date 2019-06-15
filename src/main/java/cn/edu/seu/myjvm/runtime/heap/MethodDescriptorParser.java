package cn.edu.seu.myjvm.runtime.heap;

/**
 * Created by a on 2018/3/6.
 */
public class MethodDescriptorParser {
    private String raw;
    private int offset = 0;
    private MethodDescriptor parsed;

    public static MethodDescriptor parseMethodDescriptor(String descriptor) throws Exception {
        MethodDescriptorParser parser = new MethodDescriptorParser();
        return parser.parse(descriptor);
    }

    public MethodDescriptor parse(String descriptor) throws Exception {
        this.raw = descriptor;
        this.parsed = new MethodDescriptor();
        this.startParams();
        this.parsedMultiParam();
        this.endParams();
        this.parseReturnType();
        this.finish();
        return this.parsed;
    }

    public void startParams() throws Exception {
        if (this.readUint8() != '(')
            this.causeException();
    }

    public void causeException() throws Exception {
        throw new Exception("Bad descriptor:" + this.raw);
    }

    public void parsedMultiParam() throws Exception {
        parseParamTypes();
        while (this.readUint8() != ')') {
            this.unreadUint8();
            parseParamTypes();
        }
        this.unreadUint8();
    }

    public void parseParamTypes() throws Exception {
        String t = this.parseFieldType();
        if (!t.isEmpty())
            this.parsed.addParameterType(t);
    }

    public void endParams() throws Exception {
        if (this.readUint8() != ')')
            this.causeException();
    }

    public void parseReturnType() throws Exception {
        if (this.readUint8() == 'V') {
            this.parsed.setReturnType("V");
            return;
        }
        this.unreadUint8();
        String t = this.parseFieldType();
        if (!t.isEmpty()) {
            this.parsed.setReturnType(t);
            return;
        }
        throw new Exception("error in parseReturnType");
    }

    public void finish() throws Exception {
        if (this.offset != this.raw.length())
            this.causeException();
    }

    public char readUint8() {
        char result = this.raw.charAt(this.offset);
        this.offset++;
        return result;
    }

    public void unreadUint8() {
        this.offset--;
    }

    public String parseFieldType() throws Exception {
        switch (this.readUint8()) {
            case 'B':
                return "B";
            case 'C':
                return "C";
            case 'D':
                return "D";
            case 'F':
                return "F";
            case 'I':
                return "I";
            case 'J':
                return "J";
            case 'S':
                return "S";
            case 'Z':
                return "Z";
            case 'L':
                return this.parseObjectType();
            case '[':
                return this.parseArrayType();
            default:
                this.unreadUint8();
                return "";
        }
    }

    public String parseObjectType() throws Exception {
        String unread = this.raw.substring(this.offset, this.raw.length());
        int semicolonIndex = unread.indexOf(";");
        if (semicolonIndex == -1)
            this.causeException();
        else {
            int objStart = this.offset - 1;
            int objEnd = this.offset + semicolonIndex + 1;
            this.offset = objEnd;
            return this.raw.substring(objStart, objEnd);
        }
        return "";
    }

    public String parseArrayType() throws Exception {
        int arrStart = this.offset - 1;
        this.parseFieldType();
        int arrEnd = this.offset;
        return this.raw.substring(arrStart, arrEnd);
    }
}
