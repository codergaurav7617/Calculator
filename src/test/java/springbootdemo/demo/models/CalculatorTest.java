package springbootdemo.demo.models;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springbootdemo.demo.exception.DivdeByZeroException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    Calculator calculator =new Calculator();
    @Test
    void testCalculatedResult() throws DivdeByZeroException {

            assertEquals(3.0,calculator.calculateResult(1,2,"+"));
            assertEquals(3.0,calculator.calculateResult(1,0,"/"));
            assertEquals(18.0,calculator.calculateResult(9,2,"*"));
            assertEquals(3.0,calculator.calculateResult(1,2,"+"));
    }

    @Test
    void testEvaluateExpression() {
        assertEquals(6.0,calculator.evaluateExpression("1+2+3"));
        assertEquals(7, calculator.evaluateExpression("1/0"));
        assertEquals(8, calculator.evaluateExpression("a+1+2"));
    }
}