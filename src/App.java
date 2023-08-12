import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    static Integer min = 1;
    static Integer max = 10;
    static Integer myNumber = generateRandomInteger(min, max);

    //counter for the tries
    static Integer tries = 1;
    //Main Frame
    static JFrame frame;
    static JLabel header;
    static JLabel triesLabel;
    static JLabel tipsLabel;
    static JTextField textField;
    static JButton button;


    //for the textfield and the button
    static ActionListener action;

    public static void main(String[] args) {
        openUI();
    }

    public static int generateRandomInteger(Integer min, Integer max) {
        if (min > max) {
            throw new IllegalArgumentException("Min sollte kleiner oder gleich Max sein.");
        }
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static void guess(Integer number){
        if(number.equals(myNumber)){
            win();
        }else {
            lose(number);
            textField.requestFocusInWindow(); //focus the textfield
        }
    }

    public static void openUI(){
        setNimbusLookAndFeel();
        generateFrame();
        header = generateLabel("Gebe eine Zahl zwischen 1 und 10 ein: ", 20, 50, 400, 30);
        triesLabel = generateLabel("Versuch: " + tries, 20, 20, 400, 30); 
        tipsLabel = generateLabel("", 20, 200, 400, 30);
        textField = generateTextField(20, 100, 250, 30);
        button = generateButton("Raten!",20, 150, 250, 30);
        buttonClick(button);
        addAllToFrame(button);
        //die Positionierung der GUI-Komponenten im Fenster muss manuell gesteuert werden
        frame.setLayout(null);
        //der Aufruf macht das Fenster sichtbar.
        frame.setVisible(true);
    }

    public static void win(){
        tipsLabel.setText("Richtig geraten, du hast " + tries + " Versuche gebraucht");
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        timer.setRepeats(false); // Stellen Sie sicher, dass der Timer nur einmal ausgelöst wird
        timer.start();
        
        // Entfernen des ActionListeners
        textField.removeActionListener(action);
        for (ActionListener al : textField.getActionListeners()) {
            textField.removeActionListener(al);
        }
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }

        // Verhindern, dass der Benutzer weiterhin in das Textfeld schreibt
        textField.setEditable(false);
    }

    //if your number is not the wanted number the variable tries increases and the label changed
    public static void lose(Integer number){
        tries++; //counts the tries
        triesLabel.setText("Versuch: " + tries); // change the label for tries
        //if the number is smaller or bigger
        tipsLabel.setText(isSmaller(number) ? "Deine Zahl ist zu klein" : "Deine Zahl ist zu groß");
    }
    
    //returns true if the number is smaler than the wanted number
    public static boolean isSmaller(Integer number){
        return number < myNumber;
    }

    public static void setNimbusLookAndFeel(){
        try {
            // Set Nimbus Look and Feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateFrame(){
        frame = new JFrame("Rate die Zahl!");
        frame.setSize(400,400);
        frame.setLocation(500,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static JLabel generateLabel(String content, Integer x, Integer y, Integer width, Integer height){
        JLabel label = new JLabel(content);
        label.setBounds(x, y, width, height);
        return label;
    }
    
    public static JTextField generateTextField(Integer x, Integer y, Integer width, Integer height){
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }
    
    public static JButton generateButton(String content,Integer x, Integer y, Integer width, Integer height){
        JButton button = new JButton(content);
        button.setBounds(x, y, width, height);
        return button;
    }

    public static void buttonClick(JButton button){
        action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textFromTextField = textField.getText();// get the text
                    Integer number = Integer.parseInt(textFromTextField);// convert the text to an Integer
                    guess(number);
                } catch (Exception error) {
                    tipsLabel.setText("Gebe eine Zahl ein!");
                    System.out.println(error);
                }
                textField.setText("");
            }
        };
        button.addActionListener(action);
        textField.addActionListener(action);  // Fügt den ActionListener zum textField hinzu
    }

    public static void addAllToFrame(JButton button){
        frame.add(header);
        frame.add(triesLabel);
        frame.add(tipsLabel);
        frame.add(textField);
        frame.add(button);
    }
}
