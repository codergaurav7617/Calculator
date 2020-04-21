package springbootdemo.demo.models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbootdemo.demo.exception.DivdeByZeroException;
import java.util.Stack;

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

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();


        for(int i=0; i<expression.length();i++) {
            char c = expression.charAt(i);

            if(Character.isDigit(c) || (c=='.')){

                String num="";
                while (Character.isDigit(c) || (c=='.')) {
                    num = num+c;
                    i++;
                    if(i < expression.length())
                        c = expression.charAt(i);
                    else
                        break;
                }
                i--;

                if (num.equals(".")){
                    throw new NumberFormatException("please dont enter other than number");
                }
                double ans = Double.parseDouble(num);
                numbers.push(ans);
            }
            else if(isOperator(c)){

                if (i==0){
                    throw new NumberFormatException("please enter the operator");
                }

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

    public  int precedence_of_operator(char c){
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

    public  double performOperation(Stack<Double> numbers, Stack<Character> operations) {
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
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return b / a;
        }
        return 0;
    }

    public  boolean isOperator(char c){
        return (c=='+'||c=='-'||c=='/'||c=='*'||c=='^');
    }

}
