import javax.swing.*;
import java.awt.*;
import java.rmi.Naming;

public class CalculatorGUI extends JFrame {
    private JTextField num1Field;
    private JTextField num2Field;
    private JLabel resultLabel;
    private Calculator calcStub;

    public CalculatorGUI() {
        // * Nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // ! nothing
        }

        setTitle("Calculator Client");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 245));

        // * call Server Func
        connectToServer();

        // * border
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // * Inputs
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setOpaque(false);

        JLabel lblNum1 = new JLabel("Number 1:");
        lblNum1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        num1Field = new JTextField();
        num1Field.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblNum2 = new JLabel("Number 2:");
        lblNum2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        num2Field = new JTextField();
        num2Field.setFont(new Font("SansSerif", Font.PLAIN, 14));

        inputPanel.add(lblNum1);
        inputPanel.add(num1Field);
        inputPanel.add(lblNum2);
        inputPanel.add(num2Field);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // * operations Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        buttonPanel.setOpaque(false);

        JButton btnAdd = createStyledButton("+");
        JButton btnSub = createStyledButton("-");
        JButton btnMul = createStyledButton("*");
        JButton btnDiv = createStyledButton("/");

        btnAdd.addActionListener(e -> calculate("+"));
        btnSub.addActionListener(e -> calculate("-"));
        btnMul.addActionListener(e -> calculate("*"));
        btnDiv.addActionListener(e -> calculate("/"));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSub);
        buttonPanel.add(btnMul);
        buttonPanel.add(btnDiv);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // * Result Section
        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false);
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultLabel.setForeground(new Color(50, 50, 50));
        resultPanel.add(resultLabel);

        mainPanel.add(resultPanel);

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    // * button style
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        return button;
    }

    // * connect to server
    private void connectToServer() {
        try {
            calcStub = (Calculator) Naming.lookup("rmi://localhost:2000/CalcService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Could not connect to Calculator Server.\nMake sure the server is running on port 2000.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculate(String operation) {
        if (calcStub == null) {
            // ! Try reconnecting
            connectToServer();
            if (calcStub == null)
                return;
        }

        try {
            // * numbers empty exception
            if (num1Field.getText().trim().isEmpty() || num2Field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both numbers.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int n1 = Integer.parseInt(num1Field.getText().trim());
            int n2 = Integer.parseInt(num2Field.getText().trim());
            double result = 0;
            boolean isDouble = false;

            switch (operation) {
                case "+":
                    result = calcStub.add(n1, n2);
                    break;
                case "-":
                    result = calcStub.subtract(n1, n2);
                    break;
                case "*":
                    result = calcStub.multiply(n1, n2);
                    break;
                case "/":
                    result = calcStub.divide(n1, n2);
                    isDouble = true;
                    break;
            }

            if (isDouble) {
                resultLabel.setText(String.format("Result: %.2f", result));
            } else {
                resultLabel.setText("Result: " + (int) result);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "Arithmetic Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Communication Error: " + e.getMessage(), "RMI Error",
                    JOptionPane.ERROR_MESSAGE);
            // Optionally reset stub to force reconnect next time
            calcStub = null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}
