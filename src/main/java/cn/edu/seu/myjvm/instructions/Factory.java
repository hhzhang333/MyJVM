package cn.edu.seu.myjvm.instructions;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.instructions.constants.*;
import cn.edu.seu.myjvm.instructions.loads.*;
import cn.edu.seu.myjvm.runtime.Frame;

import java.lang.reflect.Method;

/**
 * Created by a on 2018/2/27.
 */
public class Factory {
    private static short constantsIndex = 0x14;
    private static short loadsIndex = 0x35;
    private static short storesIndex = 0x56;
    private static short stackIndex = 0x5f;
    private static short mathIndex = 0x84;
    private static short conversionsIndex = 0x93;
    private static short comparisonsIndex = 0xa6;
    private static short controlIndex = 0xb1;
    private static short referencesIndex = 0xc3;
    private static short extendedIndex = 0xc9;

    public static Instruction newInstruction(int opcode) throws Exception {
        String instructionName = InstructionTable.getInstruction(opcode);
        String packageName = null;
        if (opcode <= constantsIndex)
            packageName = "constants";
        if (opcode <= loadsIndex && opcode > constantsIndex)
            packageName = "loads";
        if (opcode <= storesIndex && opcode > loadsIndex)
            packageName = "stores";
        if (opcode <= stackIndex && opcode > storesIndex)
            packageName = "stack";
        if (opcode <= mathIndex && opcode > stackIndex)
            packageName = "math";
        if (opcode <= conversionsIndex && opcode > mathIndex)
            packageName = "conversions";
        if (opcode <= comparisonsIndex && opcode > conversionsIndex)
            packageName = "comparisons";
        if (opcode <= controlIndex && opcode > comparisonsIndex)
            packageName = "control";
        if (opcode <= referencesIndex && opcode > controlIndex)
            packageName = "references";
        if (opcode <= extendedIndex && opcode > referencesIndex)
            packageName = "extended";
        String fullPacketName = "cn.edu.seu.myjvm.instructions." + packageName + "." + instructionName.toUpperCase();

        System.out.println(fullPacketName);

        Class<?> instructionClass = Class.forName(fullPacketName);
        return (Instruction) instructionClass.newInstance();
//        switch (opcode) {
//            case 0x00:
//                return new NOP();
//            case 0x01:
//                return new ACONST_NULL();
//            case 0x02:
//                return new ICONST_M1();
//            case 0x03:
//                return new ICONST_0();
//            case 0x04:
//                return new ICONST_1();
//            case 0x05:
//                return new ICONST_2();
//            case 0x06:
//                return new ICONST_3();
//            case 0x07:
//                return new ICONST_4();
//            case 0x08:
//                return new ICONST_5();
//            case 0x09:
//                return new LCONST_0();
//            case 0x0a:
//                return new LCONST_1();
//            case 0x0b:
//                return new FCONST_0();
//            case 0x0c:
//                return new FCONST_1();
//            case 0x0d:
//                return new FCONST_2();
//            case 0x0e:
//                return new DCONST_0();
//            case 0x0f:
//                return new DCONST_1();
//            case 0x10:
//                return new BIPUSH();
//            case 0x11:
//                return new SIPUSH();
//            case 0x12:
//                return new LDC();
//            case 0x13:
//                return new LDC_W();
//            case 0x14:
//                return new LDC2_W();
//            case 0x15:
//                return new ILOAD();
//            case 0x16:
//                return new LLOAD();
//            case 0x17:
//                return new FLOAD();
//            case 0x18:
//                return new DLOAD();
//            case 0x19:
//                return new ALOAD();
//            case 0x1a:
//                return new ILOAD_0();
//            case 0x1b:
//                return new ILOAD_1();
//            case 0x1c:
//                return new ILOAD_2();
//            case 0x1d:
//                return new ILOAD_3();
//            case 0x1e:
//                return new LLOAD_0();
//            case 0x1f:
//                return new LLOAD_1();
//            case 0x20:
//                return new LLOAD_2();
//            case 0x21:
//                return new LLOAD_3();
//            case 0x22:
//                return new FLOAD_0();
//            case 0x23:
//                return new FLOAD_1();
//            case 0x24:
//                return new FLOAD_2();
//            case 0x25:
//                return new FLOAD_3();
//            case 0x26:
//                return new DLOAD_0();
//            case 0x27:
//                return new DLOAD_1();
//            case 0x28:
//                return new DLOAD_2();
//            case 0x29:
//                return new DLOAD_3();
//            case 0x2a:
//                return new ALOAD_0();
//            case 0x2b:
//                return new ALOAD_1();
//            case 0x2c:
//                return new ALOAD_2();
//            case 0x2d:
//                return new ALOAD_3();
//            case 0x2e:
//                return new IALOAD();
//            case 0x2f:
//                return new LALOAD();
//            case 0x30:
//                return new FALOAD();
//            case 0x31:
//                return new DALOAD();
//            case 0x32:
//                return new AALOAD();
//            case 0x33:
//                return new BALOAD();
//            case 0x34:
//                return new CALOAD();
//            case 0x35:
//                return new SALOAD();
//            case 0x36:
//                return new ISTORE();
//            case 0x37:
//                return new LSTORE();
//            case 0x38:
//                return new FSTORE();
//            case 0x39:
//                return new DSTORE();
//            case 0x3a:
//                return new ASTORE();
//            case 0x3b:
//                return new ISTORE_0();
//            case 0x3c:
//                return new ISTORE_1();
//            case 0x3d:
//                return new ISTORE_2();
//            case 0x3e:
//                return new ISTORE_3();
//            case 0x3f:
//                return new LSTORE_0();
//
//            default:
//                throw new Exception("unsupporter opcode: " + opcode);
//        }
    }

}
