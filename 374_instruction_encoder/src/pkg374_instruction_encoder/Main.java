/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;

/**
 *
 * @author omnic
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        //InstFrame_1 isf = new InstFrame_1();
        //BulkInstFrame bf = new BulkInstFrame();
        InstructionEncoder.my_main();
        
        //String test = "ldi R4, 5";
        //parseInstruction(test);
        
//        String test = "addi r3, r4, r5";
//        int comIndx = test.indexOf("//");
//        boolean isASpace = true;
//        
//        while(isASpace && comIndx != -1){
//            if(test.charAt(--comIndx) != ' ') isASpace = false;
//        }
//        if(comIndx==-1) comIndx = test.length()-1;
//        String rez = test.substring(0,comIndx+1);
//        System.out.println(rez+"...");
//        
        
    }
    
     public static void parseInstruction(String textIn){
         //how many register operands are in a given instruction
         Dictionary parseDix = new Dictionary("{\n" +
                            "	\"add\":\"3\",\n" +
                            "	\"sub\":\"3\",\n" +
                            "	\"shr\":\"3\",\n" +
                            "	\"shl\":\"3\",\n" +
                            "	\"ror\":\"3\",\n" +
                            "	\"rol\":\"3\",\n" +
                            "	\"and\":\"3\",\n" +
                            "	\"or\":\"3\",\n" +
                            "	\"addi\":\"*2\",\n" +
                            "	\"andi\":\"*2\",\n" +
                            "	\"ori\":\"*2\",\n" +
                            "	\"mul\":\"2\",\n" +
                            "	\"div\":\"2\",\n" +
                            "	\"neg\":\"2\",\n" +
                            "	\"not\":\"2\",\n" +
                            "	\"jr\":\"1\",\n" +
                            "	\"jal\":\"1\",\n" +
                            "	\"in\":\"1\",\n" +
                            "	\"out\":\"1\",\n" +
                            "	\"mfhi\":\"1\",\n" +
                            "	\"mflo\":\"1\",\n" +
                            "	\"nop\":\"0\",\n" +
                            "	\"halt\":\"0\",\n" +
                            "	\"ld\":\"!1\",\n" +
                            "	\"ldi\":\"!1\",\n" +
                            "	\"st\":\"&\",\n" +
                            "	\"brzr\":\"*1\",\n" +
                            "	\"brnz\":\"*1\",\n" +
                            "	\"brpl\":\"*1\",\n" +
                            "	\"brmi\":\"*1\"\n" +
                            "}");
         
         String instruction = "", Ra ="", Rb ="", Rc="", Cout = "";
         String[] regArray = {"", "", ""};
         
        //obtain instruction first
        String[] splitBySpaces = textIn.split(" ");
        for(String e: splitBySpaces){
            System.out.println(e+"...");
        }
        //obtain instruction first
        String instName = splitBySpaces[0];
        instruction = instName;
        
        //use parseDix to tell us how much we are parsing from the
        String parseValue = parseDix.getValue(instName).toString();
        String numRegOps;
        String numImmediateOps;
        int k=0, j=0;
        int regOps = 0, imOps = 0;
        String[] brackSplit;
        switch(parseValue.charAt(0)){
            case '*':
                //this is the case for immediate
                //in all cases we have 2 register operands and the immediate at the end
                regOps = Integer.parseInt(parseValue.substring(1));
                k = 0;
       
                
                for(int i=1; i<regOps+1 && i<splitBySpaces.length; i++ ){
                    //System.out.println(splitBySpaces[i]);
                    if(i == regOps) regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length());
                    else regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length()-1);
                    k++;
                    j = i;
                }
                
                //then retrieve the Cout value
                Cout = splitBySpaces[j+1];
                break;
            
            case '&':
                //st C, Ra     <OR>     st C(Rb), Ra
                if(textIn.indexOf("(") == -1){
                    //then we have standard case
                    
                    regArray[0] = splitBySpaces[2];
                    
                    Cout = splitBySpaces[1].substring(0, splitBySpaces[1].length()-1);
                    
                } else{
                    //bracket case
                    regArray[0] = splitBySpaces[2];
                    
                    brackSplit = splitBySpaces[1].split("\\(");
                    
                    Cout= brackSplit[0];
                    regArray[1] = brackSplit[1].substring(0, brackSplit[1].length() -2);
                }
                break;
                
            case '!':
                //ld Ra, C     <OR>     ld Ra, C(Rb)
                if(textIn.indexOf("(") == -1){
                    //then we have standard case
                    regArray[0] = splitBySpaces[1].substring(0, splitBySpaces[1].length()-1);
                    
                    Cout = splitBySpaces[2];
                    
                } else{
                    //bracket case
                    regArray[0] = splitBySpaces[1].substring(0, splitBySpaces[1].length()-1);
                    brackSplit = splitBySpaces[2].split("\\(");
                    
                    Cout= brackSplit[0];
                    regArray[1] = brackSplit[1].substring(0, brackSplit[1].length() -1);
                }
                break;
            
            default:
                //if this is the case, then we are only parsing reg ops
                regOps = Integer.parseInt(parseValue);
                k = 0;
               // System.out.println("regOps: "+regOps);
                
                for(int i=1; i<regOps+1 && i<splitBySpaces.length; i++ ){
                    System.out.println(splitBySpaces[i]);
                    if(i == regOps) regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length());
                    else regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length()-1);
                    k++;
                }
                
                break;
        }
        
        //Ra = regArray[1]
        instruction = (instruction.equals("")) ? "NULL" : instruction;
        regArray[0] = (regArray[0].equals("")) ? "NULL" : regArray[0];
        regArray[1] = (regArray[1].equals("")) ? "NULL" : regArray[1];
        regArray[2] = (regArray[2].equals("")) ? "NULL" : regArray[2];
        Cout = (Cout.equals("")) ? "NULL" : Cout;
        System.out.println("Instruction Name: "+instruction);
        System.out.println("Ra: "+regArray[0]);
        System.out.println("Rb: "+regArray[1]);
        System.out.println("Rc: "+regArray[2]);
        System.out.println("Cout: "+Cout);
    }
    
    //https://www.oracle.com/technical-resources/articles/javase/single-jar.html -- creating the jar file the right way
}
