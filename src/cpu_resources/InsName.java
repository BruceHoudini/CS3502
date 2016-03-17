package cpu_resources;

//ENUM for each individual instruction. 
//27 total unique instructions.
public enum InsName {
	RD_INS, WR_INS, ST_INS, LW_INS, MOV_INS, ADD_INS, SUB_INS, MUL_INS, DIV_INS, AND_INS, OR_INS, MOVI_INS, ADDI_INS,
	MULI_INS, DIVI_INS, LDI_INS, SLT_INS, SLTI_INS, HLT_INS, NOP_INS, JMP_INS, BEQ_INS, BNE_INS, BEZ_INS, BNZ_INS,
	BGZ_INS, BLZ_INS
}
