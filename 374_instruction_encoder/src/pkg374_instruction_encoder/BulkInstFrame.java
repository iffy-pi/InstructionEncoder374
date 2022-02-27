/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author omnic
 */
public class BulkInstFrame extends javax.swing.JFrame {

    /**
     * Creates new form InstFrame_1
     * 
     * 
     */
    HashMap<String, String> instFormats = new HashMap<String, String>();
    public BulkInstFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        instFormats.put("add",      "add Ra, Rb, Rc");
        instFormats.put("sub",      "sub Ra, Rb, Rc");
        instFormats.put("shr",      "shr Ra, Rb, Rc");
        instFormats.put("shl",      "shl Ra, Rb, Rc");
        instFormats.put("ror",      "ror Ra, Rb, Rc");
        instFormats.put("rol",      "rol Ra, Rb, Rc");
        instFormats.put("and",      "and Ra, Rb, Rc");
        instFormats.put("or",       "or Ra, Rb, Rc");
        instFormats.put("addi",     "addi Ra, Rb, C");
        instFormats.put("andi",     "andi Ra, Rb, C");
        instFormats.put("ori",      "ori Ra, Rb, C");
        instFormats.put("mul",      "mul Ra, Rb");
        instFormats.put("div",      "div Ra, Rb");
        instFormats.put("neg",      "neg Ra, Rb");
        instFormats.put("not",      "not Ra, Rb");
        instFormats.put("jr",       "jr Ra");
        instFormats.put("jal",      "jal Ra");
        instFormats.put("in",       "in Ra");
        instFormats.put("out",      "out Ra");
        instFormats.put("mfhi",     "mfhi Ra");
        instFormats.put("mflo",     "mflo Ra");
        instFormats.put("nop",      "nop");
        instFormats.put("halt",     "halt");
        instFormats.put("ld",       "ld Ra, C     <OR>     ld Ra, C(Rb)");
        instFormats.put("st",       "st C, Ra     <OR>     st C(Rb), Ra");
        instFormats.put("brzr",     "brzr Ra, C");
        instFormats.put("brnz",     "brnz Ra, C");
        instFormats.put("brpl",     "brpl Ra, C");
        instFormats.put("brmi",     "brmi Ra, C");
        
        outputArea.setText(getInfo());
        
    }
    
    public String getInfo(){
        return "Type in instructions separated by newlines:\n"
                       +"ldi r8, $27(r15)\n"
                       +"add r6, r7, r1\n\n"
                       +"And they will be encoded and displayed\nin the order they appear:\n"
                       +"0C780027\n" +
                        "1B388000\n\n"+
                       "Use // for comments e.g.\n"+
                       "ldi r8, $27(r15)//for my code\n"+
                       "//ORG 0:\n\n"+
                       "Machine Encoding Translation:\n"+
                       "0C780027 //for my code\n"+
                       "//ORG 0:\n\n"+
                       "Checkbox option removes all\nappended comments\n"
                + "Not comment lines (ie beginning with //)";
                        
    }
        
    public String encode( String instNameIn, String[] regsIn, String Cin) throws Exception{
        //retrieve the data from the fields
        String instname = instNameIn;
        String result = "";
        int count = 0;
       
        
        String ratext = regsIn[0].toLowerCase();
        String rbtext = regsIn[1].toLowerCase();
        String rctext = regsIn[2].toLowerCase();
        
        
        String Cout = Cin;
        
        if(instname.equals("jal")){
            //this is the special case for jal
            //second register is r15
            //immediate value is 1
            rbtext = "r15";
            Cout = "1";
        }
        
        boolean instNameEmpty = instname.equals("");
        boolean raEmpty = ratext.equals("");
        boolean rbEmpty = rbtext.equals("");
        boolean rcEmpty = rctext.equals("");
        
        try{
            
            if(instNameEmpty){
                throw new InstException("Empty Instruction!");
            } else{
                if(raEmpty&&rbEmpty&&rcEmpty && !instname.equals("nop") && !instname.equals("halt")){
                    throw new InstException("This instruction has at least one register argument");

                } else{

                    if(!raEmpty) count++; //System.out.println("here");
                    if(!rbEmpty) count++; //System.out.println("here");
                    if(!rcEmpty) count++; //System.out.println("here");



                    if(ratext.length() >0 && ratext.charAt(0) == 'r'){
                        ratext = ratext.substring(1);
                        //showMessageDialog(this,"what gives: "+ratext);
                    }

                    if(rbtext.length() >0 && rbtext.charAt(0) == 'r') rbtext = rbtext.substring(1);
                    if(rctext.length() >0 && rctext.charAt(0) == 'r') rctext = rctext.substring(1);

                    String[] arr = new String[count];
                    if(count==1 || count==2 || count==3) arr[0] = ratext; 
                    if(count==2 || count == 3) arr[1] = rbtext;
                    if(count == 3) arr[2] = rctext;


                    //String Cout = Cin;

                    int baseout=2;
                    String choice = HBCombo.getSelectedItem().toString();
                    switch(choice){
                        case "Hex":
                            baseout = 16;
                            break;
                        case "Binary":
                            baseout = 2;
                            break;
                    }
                   
                    result = InstEncoder.encode(instname,arr,Cout,baseout);
                }     
            }
        } catch(Exception e){
            throw new Exception ("Encoder Error: "+e.getMessage());
        }
        
        return result;
    }
    
    public String parseAndEncode(String textIn) throws Exception{
        //how many register operands are in a given instruction
        HashMap<String, String> parsingInfo = new HashMap<String, String>();
        parsingInfo.put("add",     "3");
        parsingInfo.put("sub",     "3");
        parsingInfo.put("shr",     "3");
        parsingInfo.put("shl",     "3");
        parsingInfo.put("ror",     "3");
        parsingInfo.put("rol",     "3");
        parsingInfo.put("and",     "3");
        parsingInfo.put("or",      "3");
        parsingInfo.put("addi",    "*2");
        parsingInfo.put("andi",    "*2");
        parsingInfo.put("ori",     "*2");
        parsingInfo.put("mul",     "2");
        parsingInfo.put("div",     "2");
        parsingInfo.put("neg",     "2");
        parsingInfo.put("not",     "2");
        parsingInfo.put("jr",      "1");
        parsingInfo.put("jal",     "1");
        parsingInfo.put("in",      "1");
        parsingInfo.put("out",     "1");
        parsingInfo.put("mfhi",    "1");
        parsingInfo.put("mflo",    "1");
        parsingInfo.put("nop",     "0");
        parsingInfo.put("halt",    "0");
        parsingInfo.put("ld",      "!1");
        parsingInfo.put("ldi",     "!1");
        parsingInfo.put("st",      "&");
        parsingInfo.put("brzr",    "*1");
        parsingInfo.put("brnz",    "*1");
        parsingInfo.put("brpl",    "*1");
        parsingInfo.put("brmi",    "*1");
         
        String instruction = "", Ra ="", Rb ="", Rc="", Cout = "";

        //RegArray[0] is Ra, regArray[1] is Rb, regArray[2] is Rc
        String[] regArray = {"", "", ""};
        String result = "";
         
        try{
            //obtain instruction first
            String[] spaceSplitText = textIn.split(" ");

            //obtain instruction first
            String instName = spaceSplitText[0];
            instruction = instName;

            //use parsingInfo to tell us how much we are parsing from the
            String parseValue = parsingInfo.get(instName);
            String numImmediateOps;
            int k=0, j=0;
            int numOfRegOps = 0, imOps = 0;
            String[] brackSplit;
            switch(parseValue.charAt(0)){
                case '*':
                    //this is the case for immediate
                    //in all cases we have 2 register operands and the immediate at the end
                    numOfRegOps = Integer.parseInt(parseValue.substring(1));
                    k = 0;

                    //get the register operands
                    for(int i=0; i < numOfRegOps; i++ ){
                        //get the register operands without the commas
                        //instruction takes one space
                        regArray[i] = regArray[i] = spaceSplitText[i+1].substring(0, spaceSplitText[i+1].length()-1);
                    }

                    //get the immediate value
                    //then retrieve the Cout value
                    Cout = spaceSplitText[numOfRegOps+1];
                    break;

                case '&':
                    //st C, Ra     <OR>     st C(Rb), Ra
                    if(textIn.indexOf("(") == -1){
                        //then we have standard case

                        regArray[0] = spaceSplitText[2];

                        Cout = spaceSplitText[1].substring(0, spaceSplitText[1].length()-1);

                    } else{
                        //bracket case
                        regArray[0] = spaceSplitText[2];

                        brackSplit = spaceSplitText[1].split("\\(");

                        Cout= brackSplit[0];
                        regArray[1] = brackSplit[1].substring(0, brackSplit[1].length() -2);
                    }
                    break;

                case '!':
                    //ld Ra, C     <OR>     ld Ra, C(Rb)
                    if(textIn.indexOf("(") == -1){
                        //then we have standard case
                        regArray[0] = spaceSplitText[1].substring(0, spaceSplitText[1].length()-1);

                        Cout = spaceSplitText[2];

                    } else{
                        //bracket case
                        regArray[0] = spaceSplitText[1].substring(0, spaceSplitText[1].length()-1);
                        brackSplit = spaceSplitText[2].split("\\(");

                        Cout= brackSplit[0];
                        regArray[1] = brackSplit[1].substring(0, brackSplit[1].length() -1);
                    }
                    break;

                default:
                    //if this is the case, then we are only parsing reg ops
                    numOfRegOps = Integer.parseInt(parseValue);
                    k = 0;
                   // //System.out.println("numOfRegOps: "+numOfRegOps);

                    for(int i=1; i<numOfRegOps+1 && i<spaceSplitText.length; i++ ){
                        //System.out.println(spaceSplitText[i]);
                        if(i == numOfRegOps) regArray[k] = spaceSplitText[i].substring(0, spaceSplitText[i].length());
                        else regArray[k] = spaceSplitText[i].substring(0, spaceSplitText[i].length()-1);
                        k++;
                    }

                    break;
            }

            //Ra = regArray[1]
//            instruction = (instruction.equals("")) ? "NULL" : instruction;
//            regArray[0] = (regArray[0].equals("")) ? "NULL" : regArray[0];
//            regArray[1] = (regArray[1].equals("")) ? "NULL" : regArray[1];
//            regArray[2] = (regArray[2].equals("")) ? "NULL" : regArray[2];
//            Cout = (Cout.equals("")) ? "NULL" : Cout;
//            System.out.println("Instruction Name: "+instruction);
//            System.out.println("Ra: "+regArray[0]);
//            System.out.println("Rb: "+regArray[1]);
//            System.out.println("Rc: "+regArray[2]);
//            System.out.println("Cout: "+Cout);

            result = encode(instruction, regArray, Cout);
        } catch(Exception e){
            throw new Exception("Parser Error: "+e.getMessage());
        }
        
        return result;
    }
    
    public void bulkEncode(){
        
        //String men = "add r1, r2, r3\nldi r6, $29(r7)";
        int lineCnt = 0;
        if(inputArea.getText().equals("")){
            showMessageDialog(this, "No instruction to encode");
        } else{
            try{
                String[] input = inputArea.getText().split("\n");

                ArrayList<String> output = new ArrayList();

                for(String curline : input){
                    lineCnt++;
                    curline = curline.strip();
                    String curlineInst = "";
                    String curlineComment = "";
                    String outputComment = "";

                    if (curline.equals("")){
                        //empty line so just pass it through
                        output.add("");
                        continue;
                    }

                    //use regex to parse instruction and comments
                    Pattern r = Pattern.compile("(.*) *//(.*)");
                    Matcher m = r.matcher(curline);
                    boolean thereAreComments = m.find();

                    if (thereAreComments){
                        //there is instruction and comment or there is only comment
                        curlineInst = m.group(1).strip();
                        curlineComment = m.group(2).strip();
                    } else{
                        //there is only instruction
                        curlineInst = curline;
                    }

                    //if the line is only a comment we should pass it through to the output
                    if ( curlineInst.equals("") ){
                        if (thereAreComments){
                            output.add("//"+curlineComment);
                        }
                        continue;
                    }


                    //determine our output comment
                    //user wants both instruction and user comments
                    if(includeInst.isSelected() && includeComments.isSelected()) outputComment = curlineInst + ((curlineComment.equals("")) ? "" : ", "+curlineComment);

                    //user wants only instruction comments
                    else if(includeInst.isSelected() && !includeComments.isSelected()) outputComment = curlineInst;

                    //user wants only user comments
                    else if(!includeInst.isSelected() && includeComments.isSelected()) outputComment = curlineComment;

                    //now encode the instruction and add the output line
                    output.add( parseAndEncode(curlineInst) +  ((outputComment.equals("")) ? "" : " //" + outputComment ) );
                }

                setOutput(output);
            } catch(Exception e){
                String info = "Something went wrong (Line "+lineCnt+"):\n"
                        + e.getMessage()+"\n"
                        +"Are you sure your format is correct?";
                showMessageDialog(this, info);
            }
        }
        
    }
    
    public void setOutput(ArrayList<String> listIn){
        Iterator<String> myItr = listIn.iterator();
        String out = "";
        
        while(myItr.hasNext()){
           out = out + myItr.next()+"\n";
        }
        
        outputArea.setText(out);
    }
   
    
    
    public void clear(){
        String empty = "";
        
        inputArea.setText(empty);
        
       
       outputArea.setText(getInfo());
    }
    
    public void setFormat(){
        Object val = instFormats.get(nameField.getText().split(" ")[0]);
        
        if(val==null){
            formatField.setText("Unknown Instruction");
        }
        else{
            formatField.setText(val.toString());
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        encodeBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        HBCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        formatField = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        includeComments = new javax.swing.JCheckBox();
        includeInst = new javax.swing.JCheckBox();

        jTextField1.setText("jTextField1");

        jScrollPane1.setViewportView(jTextPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instruction Encoder");
        setResizable(false);

        title.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        title.setText("Instruction Encoder");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Instruction Name:");

        encodeBtn.setText("Encode Instructions");
        encodeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encodeBtnMouseClicked(evt);
            }
        });

        clearBtn.setText("Clear");
        clearBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearBtnMouseClicked(evt);
            }
        });
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        HBCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "Binary" }));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Output Format");
        jLabel7.setToolTipText("");

        inputArea.setColumns(20);
        inputArea.setRows(5);
        jScrollPane2.setViewportView(inputArea);

        outputArea.setEditable(false);
        outputArea.setColumns(20);
        outputArea.setLineWrap(true);
        outputArea.setRows(5);
        jScrollPane4.setViewportView(outputArea);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Machine Encoding:");

        formatField.setEditable(false);
        formatField.setColumns(20);
        formatField.setRows(5);
        jScrollPane3.setViewportView(formatField);

        jLabel9.setText("Instruction format:");

        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameFieldKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Instructions (Newline Separated):");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("See Acceptable Formats Through Search");

        includeComments.setText("Include Comments");
        includeComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeCommentsActionPerformed(evt);
            }
        });

        includeInst.setText("Include Instructions");
        includeInst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeInstActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(encodeBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(clearBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(includeComments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(includeInst, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(67, 67, 67))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(42, 42, 42)))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addGap(2, 2, 2)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(encodeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(includeComments)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(includeInst)))
                .addGap(15, 15, 15)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encodeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encodeBtnMouseClicked
        // TODO add your handling code here:
         bulkEncode();
    }//GEN-LAST:event_encodeBtnMouseClicked

    private void clearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBtnMouseClicked
        // TODO add your handling code here:
        
        clear();
    }//GEN-LAST:event_clearBtnMouseClicked

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearBtnActionPerformed

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void nameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameFieldKeyReleased
        // TODO add your handling code here:
        setFormat();
    }//GEN-LAST:event_nameFieldKeyReleased

    private void includeCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeCommentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_includeCommentsActionPerformed

    private void includeInstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeInstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_includeInstActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> HBCombo;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton encodeBtn;
    private javax.swing.JTextArea formatField;
    private javax.swing.JCheckBox includeComments;
    private javax.swing.JCheckBox includeInst;
    private javax.swing.JTextArea inputArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
