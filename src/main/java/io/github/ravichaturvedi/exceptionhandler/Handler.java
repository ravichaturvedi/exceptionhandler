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


import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static io.github.ravichaturvedi.exceptionhandler.Callables.from;

/**
 * {@link Handler} provides static functions to deal with exceptions.
 */
public class Handler {

    /**
     * Handle the exceptionhandler thrown by the provided {@link Runner}, using the provided exceptionhandler {@link Consumer}.
     *
     * @param runner
     * @param exceptionConsumer
     */
    public static void handle(Runner runner, Consumer<Exception> exceptionConsumer) {
        handle(from(runner), exceptionConsumer);
    }

    /**
     * Handle the exceptionhandler thrown by the provided {@link Callable}, using the provided exceptionhandler {@link Consumer}.
     *
     * @param callable
     * @param exceptionConsumer
     */
    public static void handle(Callable<?> callable, Consumer<Exception> exceptionConsumer) {
        try {
            callable.call();
        } catch (Exception e) {
            exceptionConsumer.accept(e);
        }
    }
}
