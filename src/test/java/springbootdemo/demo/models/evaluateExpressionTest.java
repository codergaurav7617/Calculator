package springbootdemo.demo.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbootdemo.demo.exception.DivideByZeroException;
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
    void testEvaluateExpressionHavingWhiteSpace() throws NumberFormatException {
        assertEquals(6.0,calculator.evaluateExpression("1 + 2 + 3"));
    }


    @Test
    void testEvaluateExpressionHavingWhiteSpaceAtTheBeginigOfTheExpression() throws NumberFormatException {
        assertEquals(6.0,calculator.evaluateExpression("   1 + 2 + 3"));
    }


    @Test
    void testEvaluateExpressionHavingWhiteSpaceAtTheEndOfTheExpression() throws NumberFormatException {
        assertEquals(267903.0,calculator.evaluateExpression("   1 + 267899 + 3            "));
    }



    @Test
    void testEvaluateExpressionHavingWhiteSpaceInTheMiddleOfTheExpression() throws NumberFormatException {
        assertEquals(6.0,calculator.evaluateExpression("1        + 2 + 3"));
    }



    @Test
    void testEvaluateExpressionOnlyOneDifferentOfOperator() throws NumberFormatException {
        assertEquals(6.0,calculator.evaluateExpression("1+2+3*2+3-6"));
    }

    @Test
    void testEvaluateExpressionOfBigNumber() throws NumberFormatException {
        assertEquals(2.3111113E7,calculator.evaluateExpression("888888+22222222+3*2+3-6"));
    }

    @Test
    void testEvaluateExpressionContaingAlphabet() {
        try {
            calculator.evaluateExpression("a+1+2");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    void testEvaluateExpressionAlphabetDivedByNumberAndOperator() {
        try {
            calculator.evaluateExpression("a/1+");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    void testEvaluateExpressionAlphabetDivedByNumber() {
        try {
            calculator.evaluateExpression("a/1");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    void testExpression() {
        try {
            calculator.evaluateExpression("a/1.0");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    void testEvaluateExpressionOHavingBigNumber() throws NumberFormatException {
        assertEquals(2.4444445286404378E20,calculator.evaluateExpression("1+244444444444444444444+344444444*24444+3444-644444"));
    }

    @Test
    void testEvaluateExpressionOnlyOneDifferentOfOperatorAddition() throws NumberFormatException {
        assertEquals(2.5717064978275E13,calculator.evaluateExpression("1444444444+25555555555555+6*26677866544+6666666-8887654"));
    }


    @Test
    void testEvaluateExpressionHavingDecimal() throws NumberFormatException {
        assertEquals(2.5717064978275254E13,calculator.evaluateExpression("1444444444.25364775757+25555555555555+6*26677866544+6666666-8887654"));
    }


    @Test
    public void testEvaluateExpressionContaingDivideByZero() {
        try {
            calculator.evaluateExpression("1+2+3*1/0");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Divide by zero error", e.getMessage());;
        }
    }

    @Test
    public void testDivideByZero() {
        try {
            calculator.evaluateExpression("1/0");
            fail();
        } catch (DivideByZeroException | NumberFormatException e) {
            assertEquals("Divide by zero error", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingOperator(){
        try {
            calculator.evaluateExpression("+");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingOnlyDot(){
        try {
            calculator.evaluateExpression(".");
            fail();
        } catch (NumberFormatException e) {
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }

    @Test
    public void testEvaluateExpressionContainingAdjacentOperatorAndTheNumber(){
        try {
            calculator.evaluateExpression("1+4.134+3+4ff+34");
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
            assertEquals("Invalid Expression", e.getMessage());;
        }
    }
}
