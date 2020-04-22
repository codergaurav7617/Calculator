package springbootdemo.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbootdemo.demo.exception.DivdeByZeroException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class evaluateExpressionTest {
    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    void testEvaluateExpressionOnlyOneKindOfOperator() {
        assertEquals(6.0,calculator.evaluateExpression("1+2+3"));
    }

    @Test
    void testEvaluateExpressionOnlyOneDifferentOfOperator() {
        assertEquals(6.0,calculator.evaluateExpression("1+2+3*2+3-6"));
    }


    @Test
    void testEvaluateExpressionContaingAlphabet() {
        try {
            calculator.evaluateExpression("a+1+2");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Please don't enter the alphabet", e.getMessage());;
        }
    }

    @Test
    public void testDivideByZero() {
        try {
            calculator.evaluateExpression("1/0");
            fail();
        } catch (DivdeByZeroException e) {
            assertEquals("can't be divided by zero", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingOperator(){
        try {
            calculator.evaluateExpression("+");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("please enter the operand first", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingOnlyDot(){
        try {
            calculator.evaluateExpression(".");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("please dont enter only the dot operator", e.getMessage());;
        }
    }

}
