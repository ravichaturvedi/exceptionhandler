/*
 * Copyright 2017 The ExceptionHandler AUTHORS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ravichaturvedi.exceptionhandler.test;


import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.github.ravichaturvedi.exceptionhandler.Fallback.fallback;
import static io.github.ravichaturvedi.exceptionhandler.Fallback.to;
import static io.github.ravichaturvedi.exceptionhandler.Fallback.toFunc;
import static io.github.ravichaturvedi.exceptionhandler.Wrap.wrap;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestFallback {

    @Test
    public void testFallback() {
        int value = fallback(() -> 3, to(2));
        assertThat(value, is(3));

        value = fallback(() -> TestHelper.foo(3), to(2));
        assertThat(value, is(3));

        List<Object> result = fallback(() -> TestHelper.foo(Collections.EMPTY_LIST), to(Arrays.asList(1)));
        assertThat(result, is(Arrays.asList(1)));

        String res = fallback(() -> TestHelper.foo(""), to(e -> "2"));
        assertThat(res, is("2"));

        value = fallback(to(e -> 2), () -> {throw new IllegalStateException("");});
        assertThat(value, is(2));

        value = wrap(() -> fallback(toFunc(e -> 2), () -> {throw new IllegalStateException("");}));
        assertThat(value, is(2));

        try {
            fallback(toFunc(e -> {throw new Exception(e.getMessage());}), () -> {throw new IllegalStateException("");});
        } catch (Exception e) {
            assertThat(e.getMessage(), is(""));
        }
    }
}
