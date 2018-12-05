import net.tsystems.util.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionUtilsTest {
    @Test
    public void getFirstDoneRight() {
        Collection<Object> input = new LinkedList<>();
        input.add("First String");
        input.add("Second String");
        input.add("Third String");

        String expectedResult = "First String";
        String result = (String)CollectionUtils.getFirst(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getLastDoneRight() {
        Collection<Object> input = new LinkedList<>();
        input.add("First String");
        input.add("Second String");
        input.add("Third String");

        String expectedResult = "Third String";
        String result = (String)CollectionUtils.getLast(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getDoneRight() {
        Collection<Object> input = new LinkedList<>();
        input.add("First String");
        input.add("Second String");
        input.add("Third String");

        String expectedResult = "Second String";
        String result = (String)CollectionUtils.get(input, 2);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getFirstEmptyCollection() {
        Collection<Object> input = new LinkedList<>();

        String expectedResult = null;
        String result = (String)CollectionUtils.getFirst(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getFirstNullCollection() {
        Collection<Object> input = null;

        String expectedResult = null;
        String result = (String)CollectionUtils.getFirst(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getLastEmptyCollection() {
        Collection<Object> input = new LinkedList<>();

        String expectedResult = null;
        String result = (String)CollectionUtils.getLast(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void getLastNullCollection() {
        Collection<Object> input = null;

        String expectedResult = null;
        String result = (String)CollectionUtils.getLast(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void isEmptyDoneRight() {
        Collection<Object> input = new LinkedList<>();
        input.add("First String");
        input.add("Second String");
        input.add("Third String");

        boolean expectedResult = false;
        boolean result = CollectionUtils.isEmpty(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void isEmptyNullCollection() {
        Collection<Object> input = null;

        boolean expectedResult = true;
        boolean result = CollectionUtils.isEmpty(input);

        Assert.assertEquals(expectedResult, result);
    }
}
