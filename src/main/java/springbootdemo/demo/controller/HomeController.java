package springbootdemo.demo.controller;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.web.bind.annotation.*;
import  springbootdemo.demo.exception.DivideByZeroException;
import  springbootdemo.demo.exception.NumberFormatException;
import  springbootdemo.demo.models.Calculator;

@RestController
@RequestMapping("/operation")
public class HomeController {
    @Autowired
    private Calculator calculator;

    @PostMapping("/arithmetic")
    public double arithmetic_operation(
            @RequestParam String fnumber,
            @RequestParam String operator,
            @RequestParam String snumber
    ) throws DivideByZeroException, NumberFormatException {

        double first_number;
        double second_number;

        first_number = verifyNumber(fnumber);
        second_number = verifyNumber(snumber);

        double result;
        result = calculator.calculateResult(first_number,
                second_number,
                operator
        );
        return result;
    }

    public double verifyNumber(String number) throws NumberFormatException {
         return Double.parseDouble(number);
    }

    @RequestMapping("/continuous")
    public double evaluate_expression(
            @RequestParam String exp
    ) throws NumberFormatException {
        double  answer;
        answer = calculator.evaluateExpression(exp);
        return answer;
    }
}
