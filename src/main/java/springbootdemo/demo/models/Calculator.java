package springbootdemo.demo.models;
import org.springframework.stereotype.Component;
import springbootdemo.demo.exception.DivdeByZeroException;
import springbootdemo.demo.exception.NumberFormatException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Calculator{
    private final static Set<Character> SET_OF_CONSTANTS = Collections.unmodifiableSet(
            new HashSet<Character>(Arrays.asList(
                    '+',
                    '-',
                    '*',
                    '/'
            )));

    private Stack<Double> numbers = new Stack<>();
    private Stack<String> operations = new Stack<>();

    public double calculateResult(double first_number,double second_number,String operator) throws DivdeByZeroException, NumberFormatException {
        double result= 0;
        switch (operator){
            case "+":
                result = first_number+second_number;
                break;
            case "-":
                result = first_number - second_number;
                break;
            case "*":
                result = first_number*second_number;
                break;
            case "/":
                if(second_number==0){
                    throw new DivdeByZeroException("Divide by zero error");
                }
                result = first_number/second_number;
                break;
            case "^":
                result=Math.pow(first_number,second_number);
                break;
            default:
                throw new NumberFormatException("Invalid Operator");
        }

        return result;
    }

    public  double evaluateExpression(String expression) throws NumberFormatException,DivdeByZeroException{
        boolean isvalid=isValidExpression(expression);

        if (!isvalid){
            throw new NumberFormatException("Invalid Expression");
        }

        List<Literal> literals=getArrayOfString(expression);
        for(int index =0; index<literals.size();index++) {
            if(literals.get(index).type== Type.DIGIT){
                    numbers.push( Double.parseDouble(literals.get(index).value));
            }
            else {
                performOperationOnStack(literals.get(index).value);
            }
        }

        double answer=finalResult();
        return answer;
    }

    private boolean isValidExpression(String expression){
        for (int i=0;i<expression.length();i++){
            char c=expression.charAt(i);
            if  (!(Character.isDigit(c) || SET_OF_CONSTANTS.contains(c))){
                return false;
            }
        }
        return true;
    }


    private double finalResult() throws NumberFormatException {
        while(!operations.isEmpty()){
            if (numbers.isEmpty()){
                throw new NumberFormatException("Invalid Expression");
            }
            double output = performOperation();
            numbers.push(output);
        }
        if (numbers.isEmpty()){
            throw new NumberFormatException("Invalid Expression");
        }
        return numbers.pop();
    }

    private void performOperationOnStack(String c) throws NumberFormatException{
        while(!operations.isEmpty() && precedence_of_operator(c)<=precedence_of_operator(operations.peek())){
            double output = performOperation();
            numbers.push(output);
        }
        operations.push(c);
    }

    private   int precedence_of_operator(String c){
        switch (c){
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
        }
        return -1;
    }

    private   double performOperation() {
        double a = numbers.pop();
        double b = numbers.pop();
        String operation = operations.pop();
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return b - a;
            case "*":
                return a * b;
            case "/":
                if (a == 0)
                    throw new DivdeByZeroException("can't be divided by zero");
        }
        return 0;
    }

    public static List<Literal> getArrayOfString(String a) {
        List<Literal> literals = getLiterals(a, 0);
        return literals;
    }

    private static List<Literal> getLiterals(String str, int index) {
        if (str.isEmpty())return Collections.EMPTY_LIST;
        String substr = str.substring(index);
        String digitRegex = "^(\\d+\\.?\\d*)";
        Pattern digitRegexPattern = Pattern.compile(digitRegex);
        Matcher match = digitRegexPattern.matcher(substr);
        String operatorRegex = "([\\+\\-\\/\\*]{1})";
        Pattern operatorRegexPattern = Pattern.compile(operatorRegex);
        Matcher operatorRegexMatch = operatorRegexPattern.matcher(substr);
        List<Literal> literals = new LinkedList<>();
        if (match.find()) {
            literals.add(new Literal(Type.DIGIT, match.group(1)));
            literals.addAll(getLiterals(substr, match.end()));
        } else if (operatorRegexMatch.find()) {
            literals.add(new Literal(Type.OPERATOR, operatorRegexMatch.group(1)));
            literals.addAll(getLiterals(substr, operatorRegexMatch.end()));
        }
        return literals;
    }

    static class Literal {
        private final Type type;
        private final String value;

        public Literal(Type type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Literal{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
    enum Type {
        DIGIT, OPERATOR
    }
}
