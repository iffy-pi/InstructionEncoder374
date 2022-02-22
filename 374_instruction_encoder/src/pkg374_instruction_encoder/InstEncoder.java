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
public class InstEncoder {
    //no constructor, will just be a house for functions
    
    public static String[] splitText(String input, String regex){
            return input.split(regex);
    }
    
    public static long pow(int base, int exp){
        int out = 1;
        for(int i=0;i<exp;i++){
            out = out * base;
        }
        
        return out;
    }
    
    public static String baseToDecimal(String value, int baseIn){
        //return the decimal version of the passed in string
        Dictionary invertedRemDix = new Dictionary("{\"A\":\"10\",\"2\":\"2\",\"B\":\"11\",\"3\":\"3\",\"C\":\"12\""
                                        + ",\"4\":\"4\",\"D\":\"13\",\"5\":\"5\",\"E\":\"14\",\"6\":\"6\""
                                        + ",\"F\":\"15\",\"7\":\"7\",\"G\":\"16\",\"0\":\"0\",\"8\":\"8\",\"1\":\"1\",\"9\":\"9\"}");
        
        String[] arr = value.split("");
        int lastPower = arr.length - 1;
        int index = lastPower;
        
        long sum = 0;
        String cur;
        long remainder;
        long multiplier;
        
        for(int i=0; i<arr.length;i++){
            cur = arr[i];
            
            if(cur != "0"){
                remainder = Long.parseLong(invertedRemDix.getValue(cur).toString());
                //ystem.out.println("current sum: "+sum);
                //System.out.println("index: "+index+"; base: "+baseIn+"; remainder: "+remainder);
                multiplier = pow(baseIn,index);
                if(multiplier<0) multiplier = multiplier * -1;
                
                //System.out.println("multiplier: "+multiplier);
                
                sum = (multiplier* remainder) + sum;
                
                index--;
            }
        }
        
        
        return new Long(sum).toString();
    }
    
    public static String binToHex(String value, int padding){
        String[] arr1 = value.split("");
        int toAdd =0;
        if(arr1.length%4 != 0) toAdd = (arr1.length/4 + 1)*4 - arr1.length;
        else toAdd = 0;
        
        for(int i=0;i<toAdd;i++){
            value = "0"+value;
        }
        
        String[] arr = value.split("");
        String output = "";
        String current = "";
        
       for(int i=0; i<arr.length;i=i+4){
          current = "";
          
          current = arr[i] + arr[i+1] + arr[i+2] + arr[i+3]; //concatenate them together
          current = baseToBase(current,2,16,0);
          output = output+current;
          
       }
        return output;
    }
    
    public static String decimalToBase(String value, int baseOut, int padding){
        //return the decimal version of the passed in string
        boolean noMath = false;
        int num = Integer.parseInt(value);
        int quotient = 0, remainder = 0, digitNum = 0;
        
        Dictionary remDix = new Dictionary("{\"10\":\"A\",\"2\":\"2\",\"15\":\"F\",\"3\":\"3\",\"11\":\"B\",\"4\":\"4\""
                                            + ",\"16\":\"G\",\"5\":\"5\",\"12\":\"C\",\"6\":\"6\",\"13\":\"D\",\"7\":\"7\""
                                            + ",\"0\":\"0\",\"8\":\"8\",\"14\":\"E\",\"1\":\"1\",\"9\":\"9\"}");
        
        String output = "";
        String remStr;
        
        if(num==0) noMath = true;
        
        if(noMath){
            digitNum = digitNum+1;
            output = "0";
        } else{
            while(num > 0){
                quotient = num / baseOut;
                remainder = num % baseOut;
                //System.out.println("remainder: "+remainder);
                remStr = remDix.getValue(new Integer(remainder).toString()).toString();
                output = remStr + output;
                num = quotient;
                digitNum = digitNum + 1;
            }
        }
        
        if(padding > 0){
            int result = padding - digitNum;
            if(result > 0){
                for(int i=0; i<result; i++){
                    output = "0"+output;
                }
            }
        }
        
        return output;
    }
    
    public static String decimalToBase(int value, int baseOut, int padding){
        return decimalToBase(new Integer(value).toString(), baseOut, padding);
    }
    
    public static String baseToBase(String numIn, int baseIn, int baseOut, int padding){
        
        String decOutput = "";
        
        if(baseIn==10){
            decOutput = numIn;
        } else
        {   
            //System.out.println("decout: "+decOutput);
            decOutput = baseToDecimal(numIn, baseIn);
           // System.out.println("decout: "+decOutput);
        }
        
        String output = "";
        
        if(baseOut==10){
            output = decOutput;
        } else{
            output = decimalToBase(decOutput, baseOut, padding);
        }
        
        
        return output;
    }
    
    public static String get2sComp(String binaryIn){
        //flip each bit and then add 1
        String[] bin = binaryIn.split("");
        String[] bin2 = new String[bin.length];
        String[] binu = new String[bin.length];
        String[] sumarr = new String[bin.length];
        int i =0;
       
        
        for(String s : bin){
           // System.out.println("s:"+s);
            
            
            switch(s){
                case "0":
                   // System.out.println("sIn:"+s);
                bin2[i] = "1";
                    break;
                case "1":
                    bin2[i] = "0";
                //System.out.println("sIn:"+s);
                    break;
            }
            
            if(i==bin.length-1) binu[i] = "1";
            else binu[i] = "0";
            i = i+1;
        }
       
        
        
        int opA=0, opB=0, res=0, cin = 0;
        //perform addition
        for(int k=bin2.length-1; k>=0;k--){
            opA = Integer.parseInt(bin2[k]);
            opB = Integer.parseInt(binu[k]);
            
            res = opA + opB+ cin;
            //System.out.println("k = "+k+" --------------------------");
            //System.out.println("opA: "+opA+"; opB: "+opB+"; Cin:"+cin);
            //System.out.println("res at "+k+" is: "+res);
            
            switch(res){
                case 0:
                    sumarr[k] = "0";
                    cin = 0;
                    break;
                    
                case 1:
                    sumarr[k] = "1";
                    cin = 0;
                    break;
                    
                case 2:
                    sumarr[k] = "0";
                    cin = 1;
                    break;
                    
                case 3:
                    sumarr[k] = "1";
                    cin = 1;
                    break;
                    
                default:
                    System.out.println("DEFAULT CASE??");
                    break;
            }
            
           // System.out.println("Sum text "+k+" is: "+sumarr[k]);
           
            
        }
        
        String sumtext = "";
        for(String k : sumarr){
            sumtext = sumtext+k;
        }
        
        //System.out.println("sumtext"+sumtext);
        
        return sumtext;
    }
    
    public static String get2sComp(long num, long padding){
        String result;
        if(num<0){
            num = num*-1; //to get positive version
        
            //convert to the binary with one extra
            long reqpad = (num%2 != 0) ? (num/2 +1) + 1 : (num/2)+1;
            padding = (reqpad>padding) ? reqpad : padding;

            return get2sComp(baseToBase(new Long(num).toString(), 10, 2, new Long(padding).intValue()));
        } else{
            return baseToBase(new Long(num).toString(), 10, 2, new Long(padding).intValue());
        }
    }
    
    public static String encode(String name, String[] usedRegs, String Cout, int baseOut)  throws InstException{
        name = name.toLowerCase();
        
        Dictionary opCodeDix = new Dictionary("{\n" +
                                                "	\"add\":\"00011\",\n" +
                                                "	\"sub\":\"00100\",\n" +
                                                "	\"shr\":\"00101\",\n" +
                                                "	\"shl\":\"00110\",\n" +
                                                "	\"ror\":\"00111\",\n" +
                                                "	\"rol\":\"01000\",\n" +
                                                "	\"and\":\"01001\",\n" +
                                                "	\"or\":\"01010\",\n" +
                                                "	\"addi\":\"01011\",\n" +
                                                "	\"andi\":\"01100\",\n" +
                                                "	\"ori\":\"01101\",\n" +
                                                "	\"mul\":\"01110\",\n" +
                                                "	\"div\":\"01111\",\n" +
                                                "	\"neg\":\"10000\",\n" +
                                                "	\"not\":\"10001\",\n" +
                                                "	\"jr\":\"10011\",\n" +
                                                "	\"jal\":\"10100\",\n" +
                                                "	\"in\":\"10101\",\n" +
                                                "	\"out\":\"10110\",\n" +
                                                "	\"mfhi\":\"10111\",\n" +
                                                "	\"mflo\":\"11000\",\n" +
                                                "	\"nop\":\"11001\",\n" +
                                                "	\"halt\":\"11010\",\n" +
                                                "	\"ld\":\"00000\",\n" +
                                                "	\"ldi\":\"00001\",\n" +
                                                "	\"st\":\"00010\",\n" +
                                                "	\"brzr\":\"10010\",\n" +
                                                "	\"brnz\":\"10010\",\n" +
                                                "	\"brpl\":\"10010\",\n" +
                                                "	\"brmi\":\"10010\"\n" +
                                                "}"); //load up opcode dix
        
        Dictionary branchDix  = new Dictionary("{\n" +
                                                "	\"brzr\":\"0000\",\n" +
                                                "	\"brnz\":\"0001\",\n" +
                                                "	\"brpl\":\"0010\",\n" +
                                                "	\"brmi\":\"0011\"\n" +
                                                "}"); //branch dix
        
        int unused = 32;
        
        String full; 
        if(opCodeDix.getValue(name) == null) throw new InstException("No opcode found for this instruction");
        else full = opCodeDix.getValue(name).toString();
        unused = unused - 5;
        
        String current;
        
        for(String reg : usedRegs){
            current = baseToBase(reg,10,2,4);
            full = full + current;
            
            unused = unused-4;
        }
        
        if(name.equals("ld") || name.equals("ldi") || name.equals("st")){
            if(unused == 23){
                full = full + "0000";
                unused = unused-4;
            }
        }
        
        if(name.equals("brzr") || name.equals("brnz") || name.equals("brpl") || name.equals("brmi")){
            current = branchDix.getValue(name).toString();
            //System.out.println(current);
            full = full + current;
            unused = unused - 4;
        }
       
        
        if(!Cout.equals("x") && !Cout.equals("")){
            int cnum = 0;
            if(Cout.charAt(0) == '$'){
                //this is a hex value
                Cout = Cout.substring(1);
                current = baseToBase(Cout, 16, 2, unused);
            } else{
                
                try{
                    cnum = Integer.parseInt(Cout);
                } catch (NumberFormatException e){
                    throw new InstException("Could not format immediate value");
                };

                if(cnum<0){
                    current = get2sComp(cnum,unused);

                } else{
                    current = baseToBase(Cout,10,2,unused);

                }
            }
        }
        else{
            //the remaining is just unused
            current = "";
            for(int i=0; i<unused;i++){
                current = 0 + current;
            }
        }
        
        full = full+current;
        if(full.length() != 32) throw new InstException("Parsing Instruction Error");
        
        if(baseOut == 16){
            full = binToHex(full,0);
        };
        return full;
    }
    
}
