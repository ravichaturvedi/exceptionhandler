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
package io.github.ravichaturvedi.exceptionhandler;


import org.junit.Test;

import static io.github.ravichaturvedi.exceptionhandler.Wrap.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestWrap {

    @Test
    public void testWrap() {
        try {
            wrap(TestHelper::bar);
        } catch (RuntimeException e) {
            assertThat(e.getCause().getMessage(), is("bla bla"));
        }

        try {
            wrap(() -> TestHelper.foo(new Object()));
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Not a number"));
        }

        try {
            wrap(TestHelper::bar, using(IllegalArgumentException::new));
        } catch (IllegalArgumentException e) {
            assertThat(e.getCause().getMessage(), is("bla bla"));
        }

        try {
            wrap(using(IllegalArgumentException::new), TestHelper::bar);
        } catch (IllegalArgumentException e) {
            assertThat(e.getCause().getMessage(), is("bla bla"));
        }

        try {
            wrapAll(TestHelper::bar, using(RuntimeException::new));
        } catch (RuntimeException e) {
            assertThat(e.getCause().getMessage(), is("bla bla"));
        }

        try {
            wrapAll(using(RuntimeException::new), TestHelper::bar);
        } catch (RuntimeException e) {
            assertThat(e.getCause().getMessage(), is("bla bla"));
        }
    }
}
