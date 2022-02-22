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
public class InstFrame extends javax.swing.JFrame {

    /**
     * Creates new form InstFrame
     * 
     * 
     */
    Dictionary formatDix;
    
    public InstFrame() {
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
                            "	\"ld\":\"ld Ra,C     <OR>     ld Ra, C(Rb)\",\n" +
                            "	\"ldi\":\"ldi Ra,C     <OR>     ldi Ra, C(Rb)\",\n" +
                            "	\"st\":\"st C,Ra     <OR>     st C(Rb), Ra\",\n" +
                            "	\"brzr\":\"brzr Ra, C\",\n" +
                            "	\"brnz\":\"brnz Ra, C\",\n" +
                            "	\"brpl\":\"brpl Ra, C\",\n" +
                            "	\"brmi\":\"brmi Ra, C\"\n" +
                            "}");
        
    }
    
    public void encode(){
        //retrieve the data from the fields
        String instname = nameField.getText();
        
        int count = 0;
        
        String ratext = ra.getText().toLowerCase();
        String rbtext = rb.getText().toLowerCase();
        String rctext = rc.getText().toLowerCase();
        String Cout = cout.getText();
        
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
    
    public void clear(){
        String empty = "";
        
        nameField.setText(empty);
        ra.setText(empty);
        rb.setText(empty);
        rc.setText(empty);
        cout.setText(empty);
        instOut.setText(empty);
    }
    
    public void setFormat(){
        Object val = formatDix.getValue(nameField.getText());
        
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
        jLabel2 = new javax.swing.JLabel();
        ra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cout = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rb = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rc = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        encodeBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        instOut = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        HBCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
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
        jLabel1.setText("instruction name:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Ra:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("C immediate value:");
        jLabel3.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Rb:");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("Rc:");

        rc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rcActionPerformed(evt);
            }
        });

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

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("If instruction does not make use of registers or immediate,\nleave field empty.\nExamples:\nadd r5, r2, r1: \nname = add, ra = 5, rb = 2, rc= 1, c is empty\n\naddi r5, r2, -2: \nname = addi, ra=5, rb=2, rc is empty, c = -2\n\nmfhi r7: \nname = mfhi, ra = 7, other fields are empty\n\nbrnz r3, 6: \nname = brnz, ra = 3, c = 6, other fields are empty");
        jScrollPane2.setViewportView(jTextArea1);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("How-To:");

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
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rb, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(7, 7, 7))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cout, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel8)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3)))
                                .addGap(0, 0, Short.MAX_VALUE)))
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
                .addGap(2, 2, 2)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(88, 88, 88))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(13, 13, 13)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(HBCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void rcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rcActionPerformed

    private void encodeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encodeBtnMouseClicked
        // TODO add your handling code here:
         encode();
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
    private javax.swing.JTextField cout;
    private javax.swing.JButton encodeBtn;
    private javax.swing.JTextArea formatField;
    private javax.swing.JTextArea instOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField ra;
    private javax.swing.JTextField rb;
    private javax.swing.JTextField rc;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
