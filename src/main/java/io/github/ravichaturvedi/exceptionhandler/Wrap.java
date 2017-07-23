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
 * {@link Wrap} provides static methods to wrap the {@link Exception} thrown by the provided functionality into {@link RuntimeException}.
 */
public class Wrap {

    /**
     * Returns the same function as provided. Kept it so that we can make the source code looks more fluent.
     * @param exceptionFunction
     * @return
     */
    public static Function<Exception, RuntimeException> using(Function<Exception, RuntimeException> exceptionFunction) {
        return exceptionFunction;
    }

    /**
     * Wrap the {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException}, if checked {@link Exception}.
     *
     * @param runner
     */
    public static void wrap(Runner runner) {
        wrap(Callables.from(runner));
    }

    /**
     * Wrap the {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using exceptionFunction), if checked {@link Exception}.
     *
     * @param runner
     * @param exceptionFunction
     */
    public static void wrap(Runner runner, Function<Exception, RuntimeException> exceptionFunction) {
        wrap(from(runner), exceptionFunction);
    }


    /**
     * Wrap all the {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using exceptionFunction)
     * @param runner
     * @param exceptionFunction
     */
    public static void wrapAll(Runner runner, Function<Exception, RuntimeException> exceptionFunction) {
        wrapAll(from(runner), exceptionFunction);
    }


    /**
     * Wrap the {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException}, if checked {@link Exception}.
     *
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V wrap(Callable<V> callable) {
        return wrap(callable, RuntimeException::new);
    }

    /**
     * Wrap the {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using exceptionFunction), if checked {@link Exception}.
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
     * Wrap all the {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using exceptionFunction).
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
