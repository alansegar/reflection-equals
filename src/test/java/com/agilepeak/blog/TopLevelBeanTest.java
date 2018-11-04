package com.agilepeak.blog;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TopLevelBeanTest {

    private LevelOneBean levelOneBeanExpected = new LevelOneBean("testLevelOne", 789, Arrays.asList("string1", "string2"));

    private TopLevelBean topLevelBean1Expected = new TopLevelBean("testId1", 1, "aTestValue1", 123, true, LocalDate.of(2018, 10, 27), levelOneBeanExpected);

    private LevelOneBean levelOneBeanActualCorrect = new LevelOneBean("testLevelOne", 789, Arrays.asList("string1", "string2"));

    private LevelOneBean levelOneBeanActualCorrectDifferentOrder = new LevelOneBean("testLevelOne", 789, Arrays.asList("string2", "string1"));

    private LevelOneBean levelOneBeanActualWrong = new LevelOneBean("wrongLevelOne", 111, Arrays.asList("wrong1", "string2"));

    private TopLevelBean topLevelBean1ActualCorrect = new TopLevelBean("testId1", 1, "aTestValue1", 123, true, LocalDate.of(2018, 10, 27), levelOneBeanActualCorrect);

    private TopLevelBean topLevelBean1ActualCorrectExceptListOrder = new TopLevelBean("testId1", 1, "aTestValue1", 123, true, LocalDate.of(2018, 10, 27), levelOneBeanActualCorrectDifferentOrder);

    private TopLevelBean actualWithOnlyTopLevelWrong = new TopLevelBean("testId1", 1, "aWrongValue", 456, false, LocalDate.of(2019, 1, 1), levelOneBeanActualCorrect);

    private TopLevelBean actualWithOnlyLevelOneWrong = new TopLevelBean("testId1", 1, "aTestValue1", 123, true, LocalDate.of(2018, 10, 27), levelOneBeanActualWrong);

    /**
     * Demonstrates that using the equals method ignores differences is all fields except id and version
     */
    @Test
    public void testEqualsDoesNotCompareAllFields() {
        assertThat(actualWithOnlyTopLevelWrong).isEqualTo(topLevelBean1Expected);
    }

    /**
     * Demonstrates AssertJ recursive comparision of all fields
     * http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#field-by-field-comparison
     */
    @Test
    public void testEqualToComparingFieldByFieldDoesCompareAllFields() {
        assertThat(topLevelBean1ActualCorrect).isEqualToComparingFieldByFieldRecursively(topLevelBean1Expected);
    }

    /**
     * Demonstrates limitation of AssertJ recursive comparision of all fields when level one class has a custom equals method.
     * Due to isEqualToComparingFieldByFieldRecursively using the custom equals method on classes which aren't at the
     * top level (unless they don't implement a custom equals method), this test passes despite fields other than
     * id and version being different on the levelOne class.
     * https://github.com/joel-costigliola/assertj-core/issues/646
     * https://github.com/joel-costigliola/assertj-core/issues/1336
     * <br>
     * This will probably be addressed soon in https://github.com/joel-costigliola/assertj-core/issues/1002
     */
    @Test
    public void testEqualToComparingFieldByFieldFailsWhenFieldsAreDifferent() {
        // Even though fields in the level one object are different, this does not fail
        assertThat(actualWithOnlyLevelOneWrong).isEqualToComparingFieldByFieldRecursively(topLevelBean1Expected);
    }

    /**
     * Demonstrates using Unitils as an alternative to AssertJ until https://github.com/joel-costigliola/assertj-core/issues/1002
     * is available.
     * http://www.unitils.org/tutorial-reflectionassert.html
     */
    @Test
    public void testAssertReflectionEqualsFailsWhenFieldsAreDifferent() {
        // This test will fail demonstrating that Unitils does check for differences in fields on the level one class
        // even though it defines a custom equals method.
        ReflectionAssert.assertReflectionEquals(topLevelBean1Expected, actualWithOnlyLevelOneWrong);
    }

    /**
     * Demonstrates ignoring the ordering of elements in a collection http://www.unitils.org/tutorial-reflectionassert.html
     */
    @Test
    public void testAssertReflectionEqualsLenientOrder() {
        ReflectionAssert.assertReflectionEquals(topLevelBean1Expected, topLevelBean1ActualCorrectExceptListOrder, ReflectionComparatorMode.LENIENT_ORDER);
    }

    /**
     * Demonstrates testing the ordering of elements in a collection http://www.unitils.org/tutorial-reflectionassert.html
     */
    @Test
    public void testAssertReflectionEqualsStrictOrder() {
        // This test will fail demonstrating how to test the ordering of elements in a collection
        ReflectionAssert.assertReflectionEquals(topLevelBean1Expected, topLevelBean1ActualCorrectExceptListOrder);
    }
}