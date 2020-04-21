package springbootdemo.demo.models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbootdemo.demo.exception.DivdeByZeroException;

import java.util.*;

// comment for the project.

    /*
        1) More functions.
        2) All the logic is present in Controller. Controller should only have routing logic, and delegate heavy work to other classes.
        1) Use @Autowired, @Component for Singleton.
        4) Junit should do for Calculator.
     */


@Component
public class Calculator{

    private double first_number;
    private double second_number;
    private String operator;

    @Autowired
    private  Calculator calculator;
    private int index=0;
    public double calculateResult(double first_number,double second_number,String operator) throws DivdeByZeroException {

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
                result = 0;
        }

        return result;
    }

    public  double evaluateExpression(String expression) throws RuntimeException{
        index=0;
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
         final  Set<Character> SET_OF_CONSTANTS = Collections.unmodifiableSet(
                new HashSet<Character>(Arrays.asList(
                        '+',
                        '-',
                        '*',
                        '/'
              )));

        for(; index<expression.length();index++) {

            char c = expression.charAt(index);
            if(Character.isDigit(c) || (c=='.')){
                String num=slice_number(expression);
                index--;
                double ans = Double.parseDouble(num);
                numbers.push(ans);
            }
            else if(SET_OF_CONSTANTS.contains(c)){

                while(!operations.isEmpty() && precedence_of_operator(c)<=precedence_of_operator(operations.peek())){
                    double output = performOperation(numbers, operations);
                    numbers.push(output);
                }
                operations.push(c);
            }else{
                throw new NumberFormatException("Please don't enter the alphabet");
            }
        }

        while(!operations.isEmpty()){
            double output = performOperation(numbers, operations);
            numbers.push(output);
        }

        return numbers.pop();
    }

    private String slice_number(String expression) throws NumberFormatException{
        String num="";
        char c=expression.charAt(index);
        while (Character.isDigit(c) || (c=='.')) {
            num = num+c;
            index++;
            if(index < expression.length())
                c = expression.charAt(index);
            else
                break;
        }

        if (num.equals(".")){
            throw new NumberFormatException("please dont enter other than number");
        }

        return num;
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

    private   double performOperation(Stack<Double> numbers, Stack<Character> operations) {
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
