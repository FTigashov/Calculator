package application.controls;

public class CalcultaionProcess {
    private Double operand1;
    private Double operand2;
    private String operator;

    public Double addOperand(String operand) {
        if (operand1 == null) {
            operand1 = Double.parseDouble(operand);
            return null;
        }
        Double result = getResult(operand);
        operand1 = result;
        return result;
    }

    public Double getResult(String operand) {
        operand2 = Double.parseDouble(operand);
        Double result = calc(operand1, operand2, operator);
        operand1 = operand2 = null;
        return result;
    }

    public Double getResult(String operand, String operator) {
        operand2 = Double.parseDouble(operand);
        Double result = calc(operand1, operand2, operator);
        reset();
        return result;
    }

    public void reset() {
        operand1 = operand2 = null;
        operator = null;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    private double calc(double num1, double num2, String operator) {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> 0;
        };
    }


}
