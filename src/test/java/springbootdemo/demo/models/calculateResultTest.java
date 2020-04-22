package springbootdemo.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbootdemo.demo.exception.DivdeByZeroException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class calculateResultTest {

    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculatedResultofOnePlusThree() {
        assertEquals(3.0,calculator.calculateResult(1,2,"+"));
    }

    @Test
    public void testCalculatedResultofThreeMinusOne(){
        assertEquals(2.0,calculator.calculateResult(3,1,"-"));
    }

    @Test
    public void testCalculatedResultofNineMultiplyThree(){
        assertEquals(27.0,calculator.calculateResult(9,3,"*"));
    }

    @Test
    public void testCalculatedResultofSixDivideByThree(){
        assertEquals(2.0,calculator.calculateResult(6,3,"/"));
    }

    @Test
    public void testDivideByZero() {
        try {
            calculator.calculateResult(1.0, 0, "/");
            fail();
        } catch (DivdeByZeroException e) {
            assertEquals("Divide by zero error", e.getMessage());;
        }
    }

}


