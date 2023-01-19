package application.forms;

import application.controls.CalcultaionProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ApplicationForm extends JFrame {

    private JTextField inputField;
    private CalcultaionProcess calcultaionProcess;

    public ApplicationForm() throws HeadlessException {
        super.setTitle("Calculator v1.2");
        setBounds(300, 300, 350, 450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        calcultaionProcess = new CalcultaionProcess();
        setJMenuBar(createMenu());

        setLayout(new BorderLayout());

        add(createTop(), BorderLayout.NORTH);
        add(createBottom(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTop() {
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());

        inputField = new JTextField();
        inputField.setFont(new Font("TimesRoman", Font.BOLD, 14));

        inputField.setEditable(false);
        top.add(inputField);

        return top;
    }

    private JPanel createBottom() {
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());


        ActionListener buttonListener = e -> {
            JButton btn = (JButton) e.getSource();
            inputField.setText(inputField.getText() + btn.getText());
        };


        //DIGITS
        bottom.add(getDigitsPanel(buttonListener), BorderLayout.CENTER);

        //OPERATORS
        bottom.add(getAdvPanel(buttonListener), BorderLayout.EAST);

        return bottom;
    }

    private JPanel getDigitsPanel(ActionListener buttonListener) {
        JPanel digitsPanel = new JPanel();
        digitsPanel.setLayout(new GridLayout(4, 3));

        for (int i = 1; i <= 10; i++) {
            String buttonTitle = (i == 10) ? "0" : String.valueOf(i);
            JButton btn = getJButtonNums(buttonTitle);
            btn.addActionListener(buttonListener);
            digitsPanel.add(btn);
        }

        JButton calc = getJButtonOther("=");
        calc.addActionListener(e -> {
            Double result = calcultaionProcess.addOperand(getLastInputNum());
            showResult(result);
            calcultaionProcess.reset();
        });
        digitsPanel.add(calc);

        JButton clear = getJButtonOther("C");
        clear.addActionListener(e -> {
            inputField.setText("");
            calcultaionProcess.reset();
        });
        digitsPanel.add(clear);

        return digitsPanel;
    }



    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        menu.add(new JMenuItem("Clear"));

        JMenuItem exit = menu.add(new JMenuItem("Exit"));
        exit.addActionListener(e -> {
            System.out.println("Приложение завершает работу");
            System.exit(0);
        });

        return menuBar;
    }

    private JPanel getAdvPanel(ActionListener buttonListener) {
        JPanel advPanel = new JPanel();
        advPanel.setLayout(new GridLayout(4, 2));




        JButton plus = getJButtonOther("+");
        plus.addActionListener(e -> {
            if (inputField.getText().length() == 0) {
                return;
            }
            buttonOperatorHandling("+");
        });
        advPanel.add(plus);

        JButton minus = getJButtonOther("-");
        minus.addActionListener(e -> {
            if (inputField.getText().length() == 0) {
                return;
            }
            buttonOperatorHandling("-");
        });
        advPanel.add(minus);


        JButton multiply = getJButtonOther("*");
        multiply.addActionListener(e -> {
            if (inputField.getText().length() == 0) {
                return;
            }
            buttonOperatorHandling("*");
        });
        advPanel.add(multiply);

        JButton divide = getJButtonOther("/");
        divide.addActionListener(e -> {
            if (inputField.getText().length() == 0) {
                return;
            }
            buttonOperatorHandling("/");
        });
        advPanel.add(divide);



        return advPanel;
    }

    private JButton getJButtonOther(String title) {
        JButton btn = new JButton(title);
        return btn;
    }

    private void buttonOperatorHandling(String operator) {
        String inputStr = inputField.getText();

        if (String.valueOf(inputStr.charAt(inputStr.length() - 1)).matches("\\D")) {
            inputField.setText(inputStr.substring(0, inputStr.length() - 1) + operator);
            calcultaionProcess.setOperator(operator);
            return;
        }
        Double result = calcultaionProcess.addOperand(getLastInputNum());
        if (result != null) {
            showResult(result, operator);
        } else {
            inputField.setText(inputStr + operator);
        }

        calcultaionProcess.setOperator(operator);
    }

    private void showResult(double result) {
        showResult(result, null);
    }

    private void showResult(double result, String operator) {
        String resultStr = (result % 1 == 0) ? String.valueOf((int) result) : String.format("%.3f", result);
        if (operator != null) {
            resultStr += operator;
        }
        inputField.setText(resultStr);
    }

    private String getLastInputNum() {
        var arr = inputField.getText().split("[^0-9\\.]");
        return arr[arr.length - 1];
    }

    private JButton getJButtonNums(String title) {
        JButton btn = new JButton(title);
        btn.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        return btn;
    }



}
