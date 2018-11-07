public class Decoder {
    public Decoder(){

    }

    public String DecodeInstruction(byte b1, byte b2){
        //combine both bytes as 2 byte instruction
        short ins = (short) (b1 << 8 | b2 & 0xff);
		
		//System.out.println("b1: " + Integer.toBinaryString(b1 & 0xFF));
		//System.out.println("b2: " + Integer.toBinaryString(b2 & 0xFF));
		
        // read upper nibble of b1 to identify instruction type
		byte ins_header = (byte)(b1 >>> 4 & 0xf);
        switch (ins_header){
            case 0x0:
                return executeTypeZeroInstruction(ins);
            case 0x1:
                return executeTypeOneInstruction(ins);
            case 0x2:
                return executeTypeTwoInstruction(ins);
            case 0x3:
                return executeTypeThreeInstruction(ins);
            case 0x4:
                return executeTypeFourInstruction(ins); 
            case 0x5:
                return executeTypeFiveInstruction(ins);
            case 0x6:
                return executeTypeSixInstruction(ins);
            case 0x7:
                return executeTypeSevenInstruction(ins);
            case 0x8:
                return executeTypeEightInstruction(ins);
            case 0x9:
                return executeTypeNineInstruction(ins);
            case 0xa:
                return executeTypeAInstruction(ins);
            case 0xb:
                return executeTypeBInstruction(ins);
            case 0xc:
                return executeTypeCInstruction(ins);
            case 0xd:
                return executeTypeDInstruction(ins);
            case 0xe:
                return executeTypeEInstruction(ins);
            case 0xf:
                return executeTypeFInstruction(ins);
            default:
                return "Unknown instruction detected";
        }
        //return null;
    }
	
	private String executeTypeZeroInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		switch(lowerNible){
			case "0E0":
				return "Clear the Screen";
			case "FEE":
				return "Returns from a subroutine.";
			case "000":
				return "null";
			default:
				return "Execute machine language subroutine at address " + lowerNible; 
		}
	}

    private String executeTypeOneInstruction(short ins){
        short address = (short) (ins & 0xfff);
        return "Jump to 0x" + String.format("%03X", address);
    }
	
	private String executeTypeTwoInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Execute subroutine starting at address " + lowerNible; 
	}
	
	private String executeTypeThreeInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);		
		return "Skip the following instruction if the value of register V" + lowerNible.charAt(0) + " equals 0x" + lowerNible.substring(1);
	}
	
	private String executeTypeFourInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);	
		return "Skip the following instruction if the value of register V" + lowerNible.charAt(0) + " is not equal to 0x" + lowerNible.substring(1);
	}
	
	private String executeTypeFiveInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Skip the following instruction if the value of register V" + lowerNible.charAt(0) + " is equal to the value of register V" + lowerNible.charAt(1);
	}
	
	private String executeTypeSixInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Store number 0x"+ lowerNible.substring(1) + " in register V" + lowerNible.charAt(0);
	}
	
	private String executeTypeSevenInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Add the value 0x" + lowerNible.substring(1) + " to register V" + lowerNible.charAt(0);
	}
	
	private String executeTypeEightInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		switch(lowerNible.charAt(2)){
			case '0':
				return "Store the value of register V" + lowerNible.charAt(1) + " in register V" + lowerNible.charAt(0);
			case '1':
				return "Set V" + lowerNible.charAt(0) + " to V" + lowerNible.charAt(0) + " OR V" + lowerNible.charAt(1);
			case '2':
				return "Set V" + lowerNible.charAt(0) + " to V" + lowerNible.charAt(0) + " AND V" + lowerNible.charAt(1);
			case '3':
				return "Set V" + lowerNible.charAt(0) + " to V" + lowerNible.charAt(0) + " XOR V" + lowerNible.charAt(1);
			case '4':
				return "Add the value of register V" + lowerNible.charAt(1) + " to register V" + lowerNible.charAt(0) + ". Set VF to 01 if a carry occurs. Set VF to 00 if a carry does not occur";
			case '5':
				return "Subtract the value of register V" + lowerNible.charAt(1) + " from register V" + lowerNible.charAt(0) + ". Set VF to 00 if a borrow occurs. Set VF to 01 if a borrow does not occur";
			case '6':
				return "Store the value of register V" + lowerNible.charAt(1) + " shifted right one bit in register V" + lowerNible.charAt(0) + ". Set register VF to the least significant bit prior to the shift";
			case '7':
				return "Set register V" + lowerNible.charAt(0) + " to the value of V" + lowerNible.charAt(1) + " minus V" + lowerNible.charAt(0) + ". Set VF to 00 if a borrow occurs. Set VF to 01 if a borrow does not occur";
			case 'E':
				return "Store the value of register V" + lowerNible.charAt(1) + " shifted left one bit in register V" + lowerNible.charAt(0) + " Set register V" + lowerNible.charAt(1) + " to the most significant bit prior to the shift";
			default:
				return "Unknown type 8 command"; 
		}
	}
	
	private String executeTypeNineInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Skip the following instruction if the value of register V" + lowerNible.charAt(0) + " is not equal to the value of register  V" + lowerNible.charAt(1);
	}
	
	private String executeTypeAInstruction(short ins){
		short address = (short) (ins & 0xfff);
		short address2 = (short) (ins & 0xff);
		String lowerNible = String.format("%03X", address);
		return "Store memory address " + lowerNible + " in register I";
	}
	
	private String executeTypeBInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Jump to address " + lowerNible + " + V0";
	}
	
	private String executeTypeCInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Set V" + lowerNible.charAt(0) + " to a random number with a mask of 0x" + lowerNible.substring(1);
	}	
	
	private String executeTypeDInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		return "Draw a sprite at position V" + lowerNible.charAt(0) + ", V" + lowerNible.charAt(1) + " with " + lowerNible.charAt(2) + " bytes of sprite data starting at the address stored in I Set VF to 01 if any set pixels are changed to unset, and 00 otherwise";
	}
	
	private String executeTypeEInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		switch(lowerNible.substring(1)){
			case "9E":
				return "Skip the following instruction if the key corresponding to the hex value currently stored in register V" + lowerNible.charAt(0) + " is pressed";
			case "A1":
				return "Skip the following instruction if the key corresponding to the hex value currently stored in register V" + lowerNible.charAt(0) + " is not pressed";
			default:
				return "Unknown type E command";
		}
	}
	
	private String executeTypeFInstruction(short ins){
		short address = (short) (ins & 0xfff);
		String lowerNible = String.format("%03X", address);
		switch(lowerNible.substring(1)){
			case "07":
				return "Store the current value of the delay timer in register V" + lowerNible.charAt(0);
			case "0A":
				return "Wait for a keypress and store the result in register V" + lowerNible.charAt(0);
			case "15":
				return "Set the delay timer to the value of register V" + lowerNible.charAt(0);
			case "18":
				return "Set the sound timer to the value of register V" + lowerNible.charAt(0);
			case "1E":
				return "Add the value stored in register VX to register I";
			case "29":
				return "Set I to the memory address of the sprite data corresponding to the hexadecimal digit stored in register V" + lowerNible.charAt(0);
			case "33":
				return "Store the binary-coded decimal equivalent of the value stored in register V" + lowerNible.charAt(0) + " at addresses I , I + 1 , and I + 2";
			case "55":
				return "Store the values of registers V0 to V" + lowerNible.charAt(0) + " inclusive in memory starting at address I. I is set to I + " + lowerNible.charAt(0) + " + 1 after operation";
			case "65":
				return "Fill registers V0 to V" + lowerNible.charAt(0) + " inclusive with the values stored in memory starting at address I. I is set to I + " + lowerNible.charAt(0) + " + 1 after operation";
			default:
				return "Unknown type F command";
		}
	}
}
