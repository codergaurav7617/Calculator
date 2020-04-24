package springbootdemo.demo.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbootdemo.demo.exception.DivideByZeroException;
import springbootdemo.demo.exception.NumberFormatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class calculateResultTest {
    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculatedResultofOnePlusThree() throws NumberFormatException {
        assertEquals(3.0,calculator.calculateResult(1,2,"+"));
    }

    @Test
    public void testCalculatedResultofThreeMinusOne() throws NumberFormatException {
        assertEquals(2.0,calculator.calculateResult(3,1,"-"));
    }

    @Test
    public void testCalculatedResultofNineMultiplyThree() throws NumberFormatException {
        assertEquals(27.0,calculator.calculateResult(9,3,"*"));
    }

    @Test
    public void testCalculatedResultofSixDivideByThree() throws NumberFormatException {
        assertEquals(2.0,calculator.calculateResult(6,3,"/"));
    }

    @Test
    public void testCalculatedResultofAdditionOfBigNumber() throws NumberFormatException {
        assertEquals(9444444.0,calculator.calculateResult(6111111.0,3333333.0,"+"));
    }

    @Test
    public void testCalculatedResultofSubstractionOfBigNumber() throws NumberFormatException {
        assertEquals(4222221.0,calculator.calculateResult(888888.0,3333333.0,"+"));
    }

    @Test
    public void testDivideByZero() {
        try {
            calculator.calculateResult(1.0, 0, "/");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Divide by zero error", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultContaingDotOperator() {
        try {
            calculator.calculateResult(1.0, 0, ".");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultContaingNumberAsAOperator() {
        try {
            calculator.calculateResult(1.0, 0, "7");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultContaingContainingSmallAlphabetAsOperator() {
        try {
            calculator.calculateResult(1.0, 0, "a");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }


    @Test
    public void testCalculatedResultContaingContainingCapitalAlphabetAsOperator() {
        try {
            calculator.calculateResult(1.0, 0, "A");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultofDivisionOfBigNumber() throws NumberFormatException {
        assertEquals(2.6664755862040885,calculator.calculateResult(88888444448.0,33335555333.0,"/"));
    }



    @Test
    public void testCalculatedResultContaingSpecialCharacterAsAOperator() {
        try {
            calculator.calculateResult(1.0, 0, "$");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultContaingEmptyStringAsAOperator() {
        try {
            calculator.calculateResult(1.0, 0, " ");
            fail();
        } catch (NumberFormatException | DivideByZeroException e) {
            assertEquals("Invalid Operator", e.getMessage());
        }
    }

    @Test
    public void testCalculatedResultofMultiplicationOfBigNumber() throws NumberFormatException {
        assertEquals(2.9631456583606007E21,calculator.calculateResult(88888444448.0,33335555333.0,"*"));
    }
}