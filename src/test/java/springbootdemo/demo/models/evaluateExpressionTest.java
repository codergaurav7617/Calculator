package springbootdemo.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbootdemo.demo.exception.DivdeByZeroException;
import springbootdemo.demo.exception.NumberFormatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class evaluateExpressionTest {
    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    void testEvaluateExpressionOnlyOneKindOfOperator() throws NumberFormatException {
        assertEquals(6.0,calculator.evaluateExpression("1+2+3"));
    }

    @Test
    void testEvaluateExpressionOnlyOneDifferentOfOperator() throws NumberFormatException {
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
    public void testEvaluateExpressionContaingDivideByZero() {
        try {
            calculator.evaluateExpression("1+2+3*1/0");
            fail();
        } catch (NumberFormatException | DivdeByZeroException e) {
            assertEquals("can't be divided by zero", e.getMessage());;
        }
    }

    @Test
    public void testDivideByZero() {
        try {
            calculator.evaluateExpression("1/0");
            fail();
        } catch (DivdeByZeroException | NumberFormatException e) {
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
            assertEquals("please do not enter other than number an operator", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingTwoOperatorAdjacent(){
        try {
            calculator.evaluateExpression("1+2+3++");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingTwoOperatorAdjacentAndADot(){
        try {
            calculator.evaluateExpression("1+4*4+3-+.");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingAdjacentOperatorAndTheNumber(){
        try {
            calculator.evaluateExpression("1+4+++34");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingAlphabetAndTheNumber(){
        try {
            calculator.evaluateExpression("1+4+34aa");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("please do not enter other than number an operator", e.getMessage());;
        }
    }

}
