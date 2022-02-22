/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;

import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author omnic
 */
public class InstFrame_1 extends javax.swing.JFrame {

    /**
     * Creates new form InstFrame_1
     * 
     * 
     */
    Dictionary formatDix;
    
    public InstFrame_1() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        formatDix = new Dictionary("{\n" +
                            "	\"add\":\"add Ra, Rb, Rc\",\n" +
                            "	\"sub\":\"sub Ra, Rb, Rc\",\n" +
                            "	\"shr\":\"shr Ra, Rb, Rc\",\n" +
                            "	\"shl\":\"shl Ra, Rb, Rc\",\n" +
                            "	\"ror\":\"ror Ra, Rb, Rc\",\n" +
                            "	\"rol\":\"rol Ra, Rb, Rc\",\n" +
                            "	\"and\":\"and Ra, Rb, Rc\",\n" +
                            "	\"or\":\"or Ra, Rb, Rc\",\n" +
                            "	\"addi\":\"addi Ra, Rb, C\",\n" +
                            "	\"andi\":\"andi Ra, Rb, C\",\n" +
                            "	\"ori\":\"ori Ra, Rb, C\",\n" +
                            "	\"mul\":\"mul Ra, Rb\",\n" +
                            "	\"div\":\"div Ra, Rb\",\n" +
                            "	\"neg\":\"neg Ra, Rb\",\n" +
                            "	\"not\":\"not Ra, Rb\",\n" +
                            "	\"jr\":\"jr Ra\",\n" +
                            "	\"jal\":\"jal Ra\",\n" +
                            "	\"in\":\"in Ra\",\n" +
                            "	\"out\":\"out Ra\",\n" +
                            "	\"mfhi\":\"mfhi Ra\",\n" +
                            "	\"mflo\":\"mflo Ra\",\n" +
                            "	\"nop\":\"nop\",\n" +
                            "	\"halt\":\"halt\",\n" +
                            "	\"ld\":\"ld Ra, C     <OR>     ld Ra, C(Rb)\",\n" +
                            "	\"ldi\":\"ldi Ra, C     <OR>     ldi Ra, C(Rb)\",\n" +
                            "	\"st\":\"st C, Ra     <OR>     st C(Rb), Ra\",\n" +
                            "	\"brzr\":\"brzr Ra, C\",\n" +
                            "	\"brnz\":\"brnz Ra, C\",\n" +
                            "	\"brpl\":\"brpl Ra, C\",\n" +
                            "	\"brmi\":\"brmi Ra, C\"\n" +
                            "}");
        
    }
        
    public void encode( String instNameIn, String[] regsIn, String Cin){
        //retrieve the data from the fields
        String instname = instNameIn;
        
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
        
        
        
        if(instNameEmpty){
            showMessageDialog(this,"Empty Instruction!");
        } else{
            if(raEmpty&&rbEmpty&&rcEmpty && !instname.equals("nop") && !instname.equals("halt")){
                instOut.setText("");
                showMessageDialog(this,"This instruction has at least one register argument");
                
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
                //System.out.println("instame: "+instname);
                //for(String s : arr){
                  //  System.out.println("arr: "+s);
                //}
                //System.out.println("Cout: "+Cout);
                //System.out.println("baseout: "+baseout);

                String result;

                try {
                    result = InstEncoder.encode(instname,arr,Cout,baseout);
                    instOut.setText(result);
                } catch (InstException ex) {
                   showMessageDialog(this,"There was an error encoding instruction\nMessage: "+ex.getMessage());
                }
            }

            
        }
        
    }
    
    public void parseAndEncode(String textIn){
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
                    if(i == regOps) regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length()-1);
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
               // //System.out.println("regOps: "+regOps);
                
                for(int i=1; i<regOps+1 && i<splitBySpaces.length; i++ ){
                    //System.out.println(splitBySpaces[i]);
                    if(i == regOps) regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length());
                    else regArray[k] = splitBySpaces[i].substring(0, splitBySpaces[i].length()-1);
                    k++;
                }
                
                break;
        }
        
        //Ra = regArray[1]
//        instruction = (instruction.equals("")) ? "NULL" : instruction;
//        regArray[0] = (regArray[0].equals("")) ? "NULL" : regArray[0];
//        regArray[1] = (regArray[1].equals("")) ? "NULL" : regArray[1];
//        regArray[2] = (regArray[2].equals("")) ? "NULL" : regArray[2];
//        Cout = (Cout.equals("")) ? "NULL" : Cout;
        System.out.println("Instruction Name: "+instruction);
        System.out.println("Ra: "+regArray[0]);
        System.out.println("Rb: "+regArray[1]);
        System.out.println("Rc: "+regArray[2]);
        System.out.println("Cout: "+Cout);

        encode(instruction, regArray, Cout);
    }
    
    
    
    public void clear(){
        String empty = "";
        
        nameField.setText(empty);
        instOut.setText(empty);
    }
    
    public void setFormat(){
        Object val = formatDix.getValue(nameField.getText().split(" ")[0]);
        
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
        title = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        encodeBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        instOut = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        HBCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        formatField = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instruction Encoder");
        setResizable(false);

        title.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        title.setText("Instruction Encoder");

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

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Instruction:");

        encodeBtn.setText("Encode Instruction");
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

        instOut.setEditable(false);
        instOut.setColumns(20);
        instOut.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        instOut.setLineWrap(true);
        instOut.setRows(5);
        jScrollPane1.setViewportView(instOut);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Encoded Instruction:");
        jLabel6.setToolTipText("");

        HBCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "Binary" }));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Output Format:");
        jLabel7.setToolTipText("");

        formatField.setEditable(false);
        formatField.setColumns(20);
        formatField.setRows(5);
        jScrollPane3.setViewportView(formatField);

        jLabel9.setText("Instruction format:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(encodeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(43, 43, 43)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 31, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(encodeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encodeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encodeBtnMouseClicked
        // TODO add your handling code here:
         parseAndEncode(nameField.getText());
    }//GEN-LAST:event_encodeBtnMouseClicked

    private void clearBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearBtnMouseClicked
        // TODO add your handling code here:
        
        clear();
    }//GEN-LAST:event_clearBtnMouseClicked

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
        setFormat();
    }//GEN-LAST:event_nameFieldActionPerformed

    private void nameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameFieldKeyReleased
        // TODO add your handling code here:
        setFormat();
    }//GEN-LAST:event_nameFieldKeyReleased

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> HBCombo;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton encodeBtn;
    private javax.swing.JTextArea formatField;
    private javax.swing.JTextArea instOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
