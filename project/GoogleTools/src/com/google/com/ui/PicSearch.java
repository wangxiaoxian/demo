package com.google.com.ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class PicSearch extends JFrame {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1;
   JTextField inputPath,inputThreadNum; 
   JTextArea textShow;
   JLabel labelOne,labelTwo;
   JButton button;
//   OperatorListener operator;  
   ComputerListener computer ; 
   public PicSearch() {
      init();
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   void init() {
      setLayout(new FlowLayout());
      inputPath = new JTextField(20);
      inputThreadNum = new JTextField(5);
      labelOne=new JLabel("文件夹路径");
      labelTwo=new JLabel("线程数");
      button = new JButton("查找"); 
//      String [] a = {"+","-","*","/"};
//      for(int i=0;i<a.length;i++) {
//          choiceFuhao.addItem(a[i]);
//      }
      textShow = new JTextArea(9,30);
//      operator = new OperatorListener();
      computer = new ComputerListener();
//      operator.setJComboBox(choiceFuhao);
//      operator.setWorkTogether(computer);
      computer.setJTextFieldOne(inputPath);
      computer.setJTextFieldTwo(inputThreadNum );
      computer.setJTextArea(textShow);
//      choiceFuhao.addItemListener(operator);  
      button.addActionListener(computer);     
      add(inputPath);
      add(labelOne);
      add(labelTwo);
      add(inputThreadNum);
      add(button);
      add(new JScrollPane(textShow));
   }
}
