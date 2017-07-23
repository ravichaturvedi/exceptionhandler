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
import java.util.function.Function;

import static io.github.ravichaturvedi.exceptionhandler.Callables.from;


/**
 * {@link Wrapper} provides static methods to wrap the exceptionhandler thrown by the provided functionality into runtime exceptionhandler.
 */
public class Wrapper {

    /**
     * Wrap the exceptionhandler thrown by the provided {@link Runner} into the runtime exceptionhandler, if checked exceptionhandler.
     *
     * @param runner
     */
    public static void wrap(Runner runner) {
        wrap(Callables.from(runner));
    }

    /**
     * Wrap the exceptionhandler thrown by the provided {@link Runner} into the runtime exceptionhandler (using exceptionFunction), if checked exceptionhandler.
     *
     * @param runner
     * @param exceptionFunction
     */
    public static void wrap(Runner runner, Function<Exception, RuntimeException> exceptionFunction) {
        wrap(from(runner), exceptionFunction);
    }


    /**
     * Wrap all the exceptionhandler thrown by the provided {@link Runner} into the runtime exceptionhandler (using exceptionFunction)
     * @param runner
     * @param exceptionFunction
     */
    public static void wrapAll(Runner runner, Function<Exception, RuntimeException> exceptionFunction) {
        wrapAll(from(runner), exceptionFunction);
    }


    /**
     * Wrap the exceptionhandler thrown by the provided {@link Callable} into the runtime exceptionhandler, if checked exceptionhandler.
     *
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V wrap(Callable<V> callable) {
        return wrap(callable, RuntimeException::new);
    }

    /**
     * Wrap the exceptionhandler thrown by the provided {@link Callable} into the runtime exceptionhandler (using exceptionFunction), if checked exceptionhandler.
     *
     * @param callable
     * @param exceptionFunction
     * @param <V>
     * @return
     */
    public static <V> V wrap(Callable<V> callable, Function<Exception, RuntimeException> exceptionFunction) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw exceptionFunction.apply(e);
        }
    }

    /**
     * Wrap all the exceptionhandler thrown by the provided {@link Callable} into the runtime exceptionhandler (using exceptionFunction).
     *
     * @param callable
     * @param exceptionFunction
     * @param <V>
     * @return
     */
    public static <V> V wrapAll(Callable<V> callable, Function<Exception, RuntimeException> exceptionFunction) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw exceptionFunction.apply(e);
        }
    }
}
