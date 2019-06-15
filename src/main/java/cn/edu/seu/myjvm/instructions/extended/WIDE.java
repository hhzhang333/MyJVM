package cn.edu.seu.myjvm.instructions.extended;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.instructions.loads.ILOAD;
import cn.edu.seu.myjvm.instructions.math.IINC;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class WIDE extends BranchInstruction{
    private Instruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        u1 opcode = reader.readU1();
        switch (opcode.getData()) {
            case 0x15:
                ILOAD iload = new ILOAD();
                iload.setIndex(reader.readU2toU1());
                this.modifiedInstruction = iload;
                break;
            case 0x16:
                break;
            case 0x17:
                break;
            case 0x18:
                break;
            case 0x19:
                break;
            case 0x36:
                break;
            case 0x37:
                break;
            case 0x38:
                break;
            case 0x39:
                break;
            case 0x3a:
                break;
            case 0x84:
                IINC iinc = new IINC();
                iinc.setIndex(reader.readU2toU1());
                iinc.setIncrement((int)reader.readShort());
                this.modifiedInstruction = iinc;
                break;
            case 0xa9:
                throw new Exception("unsupported opcode 0xa9");
        }
    }

    @Override
    public void execute(Frame frame) throws Exception {
        this.modifiedInstruction.execute(frame);
    }
}
