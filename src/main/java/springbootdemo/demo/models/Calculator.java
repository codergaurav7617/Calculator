package springbootdemo.demo.models;
import org.springframework.stereotype.Component;
import springbootdemo.demo.exception.DivideByZeroException;
import springbootdemo.demo.exception.NumberFormatException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Calculator{
    private final static Set<Character> SET_OF_CONSTANTS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    '+',
                    '-',
                    '*',
                    '/'
            )));

    private static final String invalid_expression="Invalid Expression";

    private static final String divide_by_zero="Divide by zero error";

    private static final String invalid_operator="Invalid Operator";

    private static final String digitRegex = "^(\\d+\\.?\\d*)";

    private static final String operatorRegex = "([\\+\\-\\/\\*]{1})";

    private static final String spaceRegex = "[\\s]";

    private static final Pattern digitRegexPattern = Pattern.compile(digitRegex);

    private static final Pattern operatorRegexPattern = Pattern.compile(operatorRegex);

    private static final Pattern spaceRegexPattern = Pattern.compile(spaceRegex);

    private final Stack<Double> numbers = new Stack<>();

    private final Stack<String> operations = new Stack<>();

    // used for the performing the airthmetic operation
    public double calculateResult(double first_number,double second_number,String operator) throws DivideByZeroException, NumberFormatException {
        double result;
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
                    throw new DivideByZeroException(divide_by_zero);
                }
                result = first_number/second_number;
                break;
            case "^":
                result=Math.pow(first_number,second_number);
                break;
            default:
                throw new NumberFormatException(invalid_operator);
        }

        return result;
    }

    // used for the evaluating the expression
    public  double evaluateExpression(String expression) throws NumberFormatException, DivideByZeroException {
        // used for the removal of the white space from the expression
        expression=regexMatcher(expression, Type.WHITE_SPACE).replaceAll("");

        boolean isvalid=isValidExpression(expression);
        if (!isvalid){
            throw new NumberFormatException(invalid_expression);
        }
        // converting the String expression into the String array using the regex
        List<Literal> literals=getArrayOfString(expression);
        for (Literal literal : literals) {
            if (literal.type == Type.DIGIT) {
                Double num=(Double.parseDouble(literal.value));
                if (num.isInfinite()){
                      throw new NumberFormatException("Unable to parse the expression");
                }
                numbers.push(num);
            } else {
                performOperationOnStack(literal.value);
            }
        }
        return finalResult();
    }

   // For checking whether the given expression is valid or not
    private boolean isValidExpression(String expression){
        for (int i=0;i<expression.length();i++){
            char c=expression.charAt(i);
            if  (!(Character.isDigit(c) || SET_OF_CONSTANTS.contains(c) || (c=='.'))){
                return false;
            }
        }
        return true;
    }

    private double finalResult() throws NumberFormatException {
        while(!operations.isEmpty()){
            if (numbers.isEmpty()){
                throw new NumberFormatException(invalid_expression);
            }
            double output = performOperation();
            numbers.push(output);
        }
        if (numbers.isEmpty()){
            throw new NumberFormatException(invalid_expression);
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

    // for finding out the precedence of the operator
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

    // to perform the opeartion .
    private   double performOperation() throws NumberFormatException {
        if (numbers.size()==1 || numbers.isEmpty()){
            throw new NumberFormatException("Unable to parse the expression");
        }
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
                    throw new DivideByZeroException(divide_by_zero);
        }
        return 0;
    }

    public static List<Literal> getArrayOfString(String a) {
        return getLiterals(a, 0);
    }

    private static List<Literal> getLiterals(String expression, int index) {
        if (expression.isEmpty()) {
            return new LinkedList<>();
        }
        String remainingExpression = expression.substring(index);
        Matcher match = regexMatcher(remainingExpression,Type.DIGIT);
        Matcher operatorRegexMatch = regexMatcher(remainingExpression, Type.OPERATOR);
        List<Literal> literals = new LinkedList<>();
        if (match.find()) {
            literals.add(new Literal(Type.DIGIT, match.group(1)));
            literals.addAll(getLiterals(remainingExpression, match.end()));
        } else if (operatorRegexMatch.find()) {
            literals.add(new Literal(Type.OPERATOR, operatorRegexMatch.group(1)));
            literals.addAll(getLiterals(remainingExpression, operatorRegexMatch.end()));
        }
        return literals;
    }

    private static Matcher regexMatcher(String expression,Type type){

        Matcher match;
        if (type==Type.DIGIT){
            match=digitRegexPattern.matcher(expression);
        }else if (type==Type.OPERATOR){
            match=operatorRegexPattern.matcher(expression);
        }else{
            match=spaceRegexPattern.matcher(expression);
        }

        return match;
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
        DIGIT, OPERATOR,WHITE_SPACE
    }
}
