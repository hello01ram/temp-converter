import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JTextField tempField;
    private JLabel tempFieldLabel;
    private JLabel tempTypeLabel;
    private ButtonGroup tempTypeGroup;
    private JRadioButton tempTypeRadioCel;
    private JRadioButton tempTypeRadioFah;
    private JLabel resultsLabel;
    private JLabel resultField;
    private JButton convertButton;

    public Main() {
        initComponents();
        addComponents();
        setComponentsProperties();
        addListenerToConvertButton();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();
        tempField = new JTextField(10);
        tempFieldLabel = new JLabel("Temperature");
        tempTypeLabel = new JLabel("Convert to: ");
        tempTypeGroup = new ButtonGroup();
        tempTypeRadioCel = new JRadioButton("Celsius", true);
        tempTypeRadioFah = new JRadioButton("Fahrenheit");
        convertButton = new JButton("Convert");
        resultsLabel = new JLabel("Results: ");
        resultField = new JLabel("");

        resultsLabel.setFont(new Font("", 12, 20));
        resultField.setFont(new Font("", 12, 20));
    }

    private void addComponents() {
        // Add components to first row (label, temperature text box and button)
        topPanel.add(tempFieldLabel);
        topPanel.add(tempField);
        topPanel.add(convertButton);

        // Add radio button components to same group (2 radio buttons)
        tempTypeGroup.add(tempTypeRadioCel);
        tempTypeGroup.add(tempTypeRadioFah);

        // Add components to middle row (label and 2 radio buttons to select type)
        middlePanel.add(tempTypeLabel);
        middlePanel.add(tempTypeRadioCel);
        middlePanel.add(tempTypeRadioFah);

        // Add components to bottom row (results label and text)
        bottomPanel.add(resultsLabel);
        bottomPanel.add(resultField);

        // Add panels (rows) to main wrapper panel
        mainPanel.add(topPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(bottomPanel);

        // Add space to have rows at the top
        Box.Filler glue = (Box.Filler) Box.createVerticalGlue();
        glue.changeShape(glue.getMinimumSize(),
                new Dimension(0, Short.MAX_VALUE), // make glue greedy
                glue.getMaximumSize());
        mainPanel.add(glue);

        // Add wrapper panel to the frame
        add(mainPanel);
    }

    private void setComponentsProperties() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 175);
        setTitle("Temperature Converter");
    }

    private void addListenerToConvertButton() {
        convertButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                convertTemp(e);
            }
        });
    }

    private void convertTemp(MouseEvent e) {
        boolean isValidInput = isValidTemp();
        resultField.setText("");
        if (!isValidInput) {
            resultField.setText("Invalid temperature input!");
            return;
        }
        double temp = getTemp();
        boolean convertToCel = isConvertToCel();

        double result = convertTemp(convertToCel, temp);
        resultField.setText(String.format("%.3f", result));
    }

    private Double getTemp() {
        return Double.parseDouble(tempField.getText());
    }

    private boolean isConvertToCel() {
        return tempTypeRadioCel.isSelected();
    }

    private boolean isValidTemp() {
        try {
            Double.parseDouble(tempField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    public static double convertTemp(boolean toCelsius, double temp) {
        return toCelsius ? ((temp - 32) * 5/9) : ((temp * 9/5) + 32);
    }
}
