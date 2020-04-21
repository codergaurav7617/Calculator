package springbootdemo.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springbootdemo.demo.exception.DivdeByZeroException;
import springbootdemo.demo.models.Calculator;

@RestController
@RequestMapping("/operation")
public class HomeController {
    @Autowired
    private Calculator calculator;

    @PostMapping("/airthmetic")
    public double airthmetic_operation(
            @RequestParam String fnumber,
            @RequestParam String operator,
            @RequestParam String snumber

    ) throws DivdeByZeroException {

        double first_number;
        double second_number;

        try {
            first_number = Double.parseDouble(fnumber);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Please don't enter the alphabet");
        }
        try {
            second_number = Double.parseDouble(snumber);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Please don't enter the alphabet");
        }

        double result = calculator.calculateResult(first_number,
                second_number,
                operator
        );
        return result;
    }

    @RequestMapping("/continous")
    public double evaluate_expression(
            @RequestParam String exp
    ) {
         double  answer = calculator.evaluateExpression(exp);
         return answer;
    }
  }
