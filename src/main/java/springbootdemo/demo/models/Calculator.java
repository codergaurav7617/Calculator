package springbootdemo.demo.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbootdemo.demo.exception.DivdeByZeroException;
import springbootdemo.demo.exception.NumberFormatException;
import java.util.*;

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
    private Stack<Character> operations = new Stack<>();

    private int index=0;
    private int curr_index=0;

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
        String[] array_numbers=expression.split("[\\+\\-\\/\\*]{1}");
        index=0;
        for(; index<expression.length();index++) {
            char c = expression.charAt(index);
            if(Character.isDigit(c) || (c=='.')){
                slice_number(array_numbers);
                index--;
            }
            else if(SET_OF_CONSTANTS.contains(c)){
                 performOperationOnStack(c,expression);
            }else{
                throw new NumberFormatException("Please don't enter the alphabet");
            }
        }
        double answer=finalResult();
        return answer;
    }

    private double finalResult(){
        while(!operations.isEmpty()){
            double output = performOperation();
            numbers.push(output);
        }
        return numbers.pop();
    }

    private void performOperationOnStack(Character c,String expression) throws NumberFormatException{
        if (index==0){
            throw new NumberFormatException("please enter the operand first");
        }else if(SET_OF_CONSTANTS.contains(expression.charAt(index-1))){
            throw new NumberFormatException("Invalid Expression");
        }
        while(!operations.isEmpty() && precedence_of_operator(c)<=precedence_of_operator(operations.peek())){
            double output = performOperation();
            numbers.push(output);
        }
        operations.push(c);
    }

    private void slice_number(String [] array_numbers) throws NumberFormatException{
        String num=array_numbers[curr_index];
        index+=array_numbers[curr_index].length();
        curr_index++;
        try {
            numbers.push( Double.parseDouble(num));
        }catch (java.lang.NumberFormatException e){
            throw new NumberFormatException("please do not enter other than number and operator");
        }
    }

    private   int precedence_of_operator(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private   double performOperation() {
        double a = numbers.pop();
        double b = numbers.pop();
        char operation = operations.pop();
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return b - a;
            case '*':
                return a * b;
            case '/':
                if (a == 0)
                    throw new DivdeByZeroException("can't be divided by zero");
        }

        return 0;
    }
}
