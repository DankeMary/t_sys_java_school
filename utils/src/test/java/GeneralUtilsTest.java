import net.tsystems.util.GeneralUtils;
import org.junit.Assert;
import org.junit.Test;

public class GeneralUtilsTest {
    @Test
    public void parseIntForPageDoneRight() {
        String input = "3";
        int min = 1;
        int max = 10;

        int expectedResult = 3;
        int result = GeneralUtils.parseIntForPage(input, min, max);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void parseIntForPageWrongInputFormat() {
        String input = "fdsgreg";
        int min = 1;
        int max = 10;

        int expectedResult = 1;
        int result = GeneralUtils.parseIntForPage(input, min, max);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void parseIntForPageOutOfBounds() {
        String input = "146";
        int min = 1;
        int max = 10;

        int expectedResult = max;
        int result = GeneralUtils.parseIntForPage(input, min, max);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void parseIntForPageEmptyInput() {
        String input = "";
        int min = 1;
        int max = 10;

        int expectedResult = 1;
        int result = GeneralUtils.parseIntForPage(input, min, max);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void parseIntForPageNullInput() {
        String input = null;
        int min = 1;
        int max = 10;

        int expectedResult = 1;
        int result = GeneralUtils.parseIntForPage(input, min, max);

        Assert.assertEquals(expectedResult, result);
    }
}
