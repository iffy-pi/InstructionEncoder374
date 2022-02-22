/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Arrays;
/**
 *
 * @author omnic
 */
public class InstructionEncoder {

    private static enum InstructionType {
        THREE_REGS,
        TWO_REGS,
        ONE_REGS,
        TWO_REGS_AND_IMMEDIATE,
        ONE_REGS_AND_IMMEDIATE,
        NO_OPS,
        LD_SPECIAL_CASE,
        ST_SPECIAL_CASE,
        UNKNOWN_TYPE
    }

    private HashMap<InstructionType, String> instRegexFormats;
    private HashMap<InstructionType, String[]> instructionGroups;

    private static String getGroupStr(InstructionType g){
        HashMap<InstructionType, String> groupStrings = new HashMap<InstructionType, String>();
        groupStrings.put(InstructionType.THREE_REGS, "THREE_REGS");
        groupStrings.put(InstructionType.TWO_REGS, "TWO_REGS");
        groupStrings.put(InstructionType.ONE_REGS, "ONE_REGS");
        groupStrings.put(InstructionType.TWO_REGS_AND_IMMEDIATE, "TWO_REGS_AND_IMMEDIATE");
        groupStrings.put(InstructionType.ONE_REGS_AND_IMMEDIATE, "ONE_REGS_AND_IMMEDIATE");
        groupStrings.put(InstructionType.NO_OPS, "NO_OPS");
        groupStrings.put(InstructionType.LD_SPECIAL_CASE, "LD_SPECIAL_CASE");
        groupStrings.put(InstructionType.ST_SPECIAL_CASE, "ST_SPECIAL_CASE");
        groupStrings.put(InstructionType.UNKNOWN_TYPE, "UNKNOWN_TYPE");
        return groupStrings.get(g);
    }


    private void initInstrRegexes(){
        instRegexFormats = new HashMap<InstructionType, String>();
        instRegexFormats.put(InstructionType.THREE_REGS, "<inst> *<reg> *, *<reg> *, *<reg>");
        instRegexFormats.put(InstructionType.TWO_REGS, "<inst> *<reg> *, *<reg>");
        instRegexFormats.put(InstructionType.ONE_REGS, "<inst> *<reg>");
        instRegexFormats.put(InstructionType.TWO_REGS_AND_IMMEDIATE, "<inst> *<reg> *, *<reg> *, *<imm>");
        instRegexFormats.put(InstructionType.ONE_REGS_AND_IMMEDIATE, "<inst> *<reg> *, *<imm>");
        instRegexFormats.put(InstructionType.NO_OPS, "<inst> *");
        instRegexFormats.put(InstructionType.LD_SPECIAL_CASE, "(ld) *<reg> *, *<imm> *(\\(<reg>\\))*");
        instRegexFormats.put(InstructionType.ST_SPECIAL_CASE, "(st) *<imm> *(\\(<reg>\\))* *, *<reg>");

        HashMap<String, String> regexReplacements = new HashMap<String, String>();
        regexReplacements.put("<inst>", "(inst)");
        regexReplacements.put("<reg>", "([rR][0-9]*)");
        regexReplacements.put("<imm>", "(\\$[0-9]*[a-f]*[A-F]*|[0-9]*)");


        for ( InstructionType k : instRegexFormats.keySet()) {
            String regvalue = instRegexFormats.get(k);
            for ( String symbol : regexReplacements.keySet() ) {
                regvalue = regvalue.replace(symbol, regexReplacements.get(symbol));
            }
            //print(getGroupStr(k)+ ": " + regvalue);
            instRegexFormats.put(k, regvalue);
        }
    }

    private void initInstructionGroups(){
        instructionGroups = new HashMap<InstructionType, String[]>();
        instructionGroups.put(InstructionType.THREE_REGS, new String[]{"add","sub","shr","shl","ror","rol","and","or"});
        instructionGroups.put(InstructionType.TWO_REGS, new String[]{"mul", "div", "neg", "not"});
        instructionGroups.put(InstructionType.ONE_REGS, new String[]{"jr", "jal", "in", "out", "mfhi", "mflo"});
        instructionGroups.put(InstructionType.TWO_REGS_AND_IMMEDIATE, new String[]{"andi", "addi", "ori"});
        instructionGroups.put(InstructionType.ONE_REGS_AND_IMMEDIATE, new String[]{"brzr", "brnz", "brpl", "brmi"});
        instructionGroups.put(InstructionType.NO_OPS, new String[]{"nop", "halt"});
        instructionGroups.put(InstructionType.LD_SPECIAL_CASE, new String[]{"ld"});
        instructionGroups.put(InstructionType.ST_SPECIAL_CASE, new String[]{"st"});
    }

    private static boolean isElementInArray(String elem, String[] arr){
      return Arrays.asList(arr).contains(elem);
    }

    private InstructionType getInstrType(String instName){
        //returns the instruction type if there is any, if not returns unknown 
        for (InstructionType t : instructionGroups.keySet()){
            if ( isElementInArray(instName, instructionGroups.get(t)) ) return t;
        }
        return InstructionType.UNKNOWN_TYPE;
    }

    private String makeInstructionFromParts(String[] instrParts, int outputBase){
        //takes the input instruction parts and makes the output in the specified base

        return "";
    }

    private String[] prepPartsForEncoding(String[] instrParts){
        //takes the parsed instruction string parts and applies any special instruction requirements
        //also strips registers to just raw values

        String[] somePart = { "", "" };

        return somePart;
    }


    private String[] parseInstruction(String instructionStr) throws InstException{
        //parses the 
        instructionStr = instructionStr.strip();

        if ( instructionStr.equals("") ) throw new InstException("Empty Instruction!");

        String instName = instructionStr.split(" ")[0];

        //get the group for the instruction to get the parsing regex
        InstructionType curType = getInstrType(instName);

        if ( curType == InstructionType.UNKNOWN_TYPE ) throw new InstException("Unknown Instruction: "+instName);

        String parseRegex = instRegexFormats.get(curType).replace("inst", instName);

        //use the regex for a pattern and matching
        Pattern r = Pattern.compile(parseRegex);
        Matcher m = r.matcher(instructionStr);

        if (!m.find()) throw new InstException("Could Not Parse Instruction: " + instructionStr+" for "+instName);

        //if we can parse it then handle the args based on the current type
        //values for          RA,  RB, RC
        String[] regArray = { "" , "", "" };
        String Cout = "";

        switch(curType){
        case THREE_REGS:
            //matched groups should be [ inst, ra, rb, rc ]
            //
            break;

        case TWO_REGS:
            break;

        case ONE_REGS:
            break;

        case TWO_REGS_AND_IMMEDIATE:
            //matcher groups are [ addi, ra, rb, c_out]
            //so we should have 4 groups
            if ( m.groupCount() != 4 ) throw new InstException("Could Not Parse Instruction: " + instructionStr);

            //check if we got the instruction right
            if ( !m.group(1).toLowerCase().equals(instName) )  throw new InstException("Could Not Parse Instruction: " + instructionStr);

            //check if any of our required operands are empty
            for (int i=2; i<5; i++){
            if ( m.group(i).equals("") )  throw new InstException("Could Not Parse Instruction: " + instructionStr);
            }

            //now we are good to parse the array
            regArray[0] = m.group(2);
            regArray[1] = m.group(3);
            Cout = m.group(4); 

            break;

        case ONE_REGS_AND_IMMEDIATE:
            break;

        case NO_OPS:
            break;

        case LD_SPECIAL_CASE:
            break;

        case ST_SPECIAL_CASE:
            break;
        }

        return new String[]{ instName, regArray[0], regArray[1], regArray[2], Cout };
    }

    private String _encodeInstruction(String instructionStr, int outputBase){
        //parse the instruction
        //returns array in format:
        //[ instruction name, ra, rb, rc, c_out]
        //if the registers do not exist then they will be empty string
        try {
            String[] instrParts = parseInstruction(instructionStr);
            String a_str = "";
            for (String part : instrParts) a_str = (a_str.equals("")) ? part : a_str+","+part;
            return a_str;
        } catch (InstException e){
            print("An error occured: "+e.getMessage());
            return "";
        }
    }

    public static void print(String in){
        System.out.println(in);
    }

    public String encodeInstruction(String instructionStr, int outputBase){
        //encodes the given instruction string to an ouput base
        return _encodeInstruction(instructionStr, outputBase);
    }

    public String encodeInstruction(String instructionStr){
        //encodes the given instruction string to an ouput base
        return _encodeInstruction(instructionStr, 2);
    }

    public InstructionEncoder(){
        initInstrRegexes();
        initInstructionGroups();
    }

    public static void my_main(){
        InstructionEncoder ne = new InstructionEncoder();
        String res = ne.encodeInstruction("addi     R2, R10, $27");
        print(res);
    }

}
