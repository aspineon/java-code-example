package translit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Translit {
    private static final String[] charTable = new String[81];

    private static final char START_CHAR = '�';

    static {
        charTable['�'- START_CHAR] = "A";
        charTable['�'- START_CHAR] = "B";
        charTable['�'- START_CHAR] = "V";
        charTable['�'- START_CHAR] = "G";
        charTable['�'- START_CHAR] = "D";
        charTable['�'- START_CHAR] = "E";
        charTable['�'- START_CHAR] = "E";
        charTable['�'- START_CHAR] = "ZH";
        charTable['�'- START_CHAR] = "Z";
        charTable['�'- START_CHAR] = "I";
        charTable['�'- START_CHAR] = "I";
        charTable['�'- START_CHAR] = "K";
        charTable['�'- START_CHAR] = "L";
        charTable['�'- START_CHAR] = "M";
        charTable['�'- START_CHAR] = "N";
        charTable['�'- START_CHAR] = "O";
        charTable['�'- START_CHAR] = "P";
        charTable['�'- START_CHAR] = "R";
        charTable['�'- START_CHAR] = "S";
        charTable['�'- START_CHAR] = "T";
        charTable['�'- START_CHAR] = "U";
        charTable['�'- START_CHAR] = "F";
        charTable['�'- START_CHAR] = "H";
        charTable['�'- START_CHAR] = "C";
        charTable['�'- START_CHAR] = "CH";
        charTable['�'- START_CHAR] = "SH";
        charTable['�'- START_CHAR] = "SCH";
        charTable['�'- START_CHAR] = "'";
        charTable['�'- START_CHAR] = "Y";
        charTable['�'- START_CHAR] = "'";
        charTable['�'- START_CHAR] = "E";
        charTable['�'- START_CHAR] = "YU"; // U
        charTable['�'- START_CHAR] = "YA"; // �

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char)((char)i + START_CHAR);
            char lower = new String(new char[]{idx}).toLowerCase().charAt(0);
            if (charTable[i] != null) {
                charTable[lower - START_CHAR] = charTable[i].toLowerCase();
            }
        }
    }


    /**
     * @param text �������� ����� � �������� ���������
     * @return ���������
     */
    public static String toTranslit(String text) {
        char charBuffer[] = text.toCharArray();
        StringBuilder sb = new StringBuilder(text.length());
        char symbol;
        char oldChar=' ';
        for(int counter=0;counter<charBuffer.length;counter++){
        	symbol=charBuffer[counter];
            int i = symbol - START_CHAR;
            if (i>=0 && i<charTable.length) {
            	if(oldChar==' '){
            		// word begin
                    String replace = charTable[i];
                    if(replace.equals("y")){
                    	replace="i";
                    }
                    if(replace.equals("Y")){
                    	replace="I";
                    }
                    if(replace.equals("YU")){
                    	replace="IU";
                    }
                    if(replace.equals("yu")){
                    	replace="iu";
                    }
                    if(replace.equals("YA")){
                    	replace="IA";
                    }
                    if(replace.equals("ya")){
                    	replace="ia";
                    }
                    sb.append(replace == null ? symbol : replace);
            	}else{
                    String replace = charTable[i];
                    sb.append(replace == null ? symbol : replace);
            	}
            }
            else {
                sb.append(symbol);
            }
            oldChar=symbol;
        }
        return sb.toString();
    }


    


    public static void main(String[] args) {
    	JFrame frame=new JFrame();
    	final JTextArea source=new JTextArea(3,20);
    	final JTextArea destination=new JTextArea(3,20);
    	JButton button=new JButton("translit");
    	button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				destination.setText(Translit.toTranslit(source.getText()));
			}
    	});
    	
    	JPanel panelMain=new JPanel();
    	panelMain.setLayout(new GridLayout(3,1));
    	
    	JPanel panelSource=new JPanel(new GridLayout(1,1));
    	panelSource.setBorder(javax.swing.BorderFactory.createTitledBorder("����� ��� �������������� "));
    	panelSource.add(source);
    	JPanel panelDestination=new JPanel(new GridLayout(1,1));
    	panelDestination.setBorder(javax.swing.BorderFactory.createTitledBorder("Translit text"));
    	panelDestination.add(destination);

    	panelMain.add(panelSource);
    	panelMain.add(button);
    	panelMain.add(panelDestination);
    	
    	frame.getContentPane().add(panelMain);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setVisible(true);
    	
        //final String test = "������ � ������� ��������� �������� ��������.";
        //System.out.println("toTranslit(test) = " + toTranslit(test));
        
    }


}
