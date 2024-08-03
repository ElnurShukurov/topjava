package ru.javawebinar.topjava;

import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Factory for creating test matchers.
 * <p>
 * Comparing actual and expected objects via AssertJ
 * Support converting json MvcResult to objects for comparation.
 */
public class MatcherFactory {
    public static <T> Matcher<T> usingIgnoringFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return new Matcher<>(clazz, (actual, expected) ->
                assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected));
    }

    public static <T> Matcher<T> usingEqualsComparator(Class<T> clazz) {
        return new Matcher<>(clazz, Assertions::assertEquals);
    }

    public static class Matcher<T> {
        private final Class<T> clazz;
        private final BiConsumer<T, T> comparator;

        private Matcher(Class<T> clazz, BiConsumer<T, T> comparator) {
            this.clazz = clazz;
            this.comparator = comparator;
        }

        public void assertMatch(T actual, T expected) {
            comparator.accept(actual, expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, List.of(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            List<T> actualList = List.copyOf((Collection<T>) actual);
            List<T> expectedList = List.copyOf((Collection<T>) expected);

            assertThat(actualList).hasSameSizeAs(expectedList);
            for (T e : expectedList) {
                assertThat(actualList).contains(e);
            }
        }

        public ResultMatcher contentJson(T expected) {
            return result -> assertMatch(JsonUtil.readValue(getContent(result), clazz), expected);
        }

        @SafeVarargs
        public final ResultMatcher contentJson(T... expected) {
            return contentJson(List.of(expected));
        }

        public ResultMatcher contentJson(Iterable<T> expected) {
            return result -> assertMatch(JsonUtil.readValues(getContent(result), clazz), expected);
        }

        public T readFromJson(ResultActions action) throws UnsupportedEncodingException {
            return JsonUtil.readValue(getContent(action.andReturn()), clazz);
        }

        private static String getContent(MvcResult result) throws UnsupportedEncodingException {
            return result.getResponse().getContentAsString();
        }
    }
}
