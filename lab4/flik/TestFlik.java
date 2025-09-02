package flik;
import org.junit.Test;
import org.junit.Assert;

public class TestFlik {
    @Test
    public void testFlik() {
        int a = 57;
        int b = 128;
        int c = 88;
        int d = 128;

        boolean result1 = Flik.isSameNumber(a, b);

        System.out.println("result1:" + result1);
        Assert.assertEquals(false,result1);



        boolean result2 = Flik.isSameNumber(c, d);

        System.out.println("result2:" + result2);
        Assert.assertEquals(false,result2);

        boolean result3 = Flik.isSameNumber(b, d);

        System.out.println("result3:" + result3);
        Assert.assertEquals(true,result3);
    }


    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("flik.TestFlik");
    }
}
