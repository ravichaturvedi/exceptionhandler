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

import static io.github.ravichaturvedi.exceptionhandler.Swallow.*;


public class TestSwallow {

    @Test
    public void testHandle() {
        swallow(() -> TestHelper.foo(new Object()), usingLogger(System.out::println));
        swallow(usingLogger(System.out::println), () -> TestHelper.foo(new Object()));

        swallow(usingLogger(System.out::println), () -> TestHelper.foo(2));
        swallow(TestHelper::bar, usingLogger(System.out::println));
        swallow(usingLogger(System.out::println), TestHelper::bar);
    }
}
