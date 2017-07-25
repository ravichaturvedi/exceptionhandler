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
 * {@link Wrap} provides static methods to wrap the {@link Exception} thrown by the provided code-block into {@link RuntimeException}.
 */
public class Wrap {

    /**
     * {@link Handler} defines the handler for the wrap function.
     */
    @FunctionalInterface
    private interface Handler {
        RuntimeException handle(Exception e);
    }

    /**
     * Returns the handler out of the provided exception {@link Function}.
     * @param exceptionFunction
     * @return
     */
    public static Handler using(Function<Exception, RuntimeException> exceptionFunction) {
        return exceptionFunction::apply;
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException}.
     *
     * @param runner
     */
    public static void wrap(Runner runner) {
        wrap(from(runner));
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param runner
     * @param handler
     */
    public static void wrap(Runner runner, Handler handler) {
        wrap(from(runner), handler);
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param runner
     * @param handler
     */
    public static void wrap(Handler handler, Runner runner) {
        wrap(runner, handler);
    }

    /**
     * Wrap all the {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using {@link Handler}).
     * @param runner
     * @param handler
     */
    public static void wrapAll(Runner runner, Handler handler) {
        wrapAll(from(runner), handler);
    }

    /**
     * Wrap all the {@link Exception} thrown by the provided {@link Runner} into the {@link RuntimeException} (using {@link Handler}).
     * @param handler
     * @param runner
     */
    public static void wrapAll(Handler handler, Runner runner) {
        wrapAll(runner, handler);
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException}.
     *
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V wrap(Callable<V> callable) {
        return wrap(callable, using(RuntimeException::new));
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param callable
     * @param handler
     * @param <V>
     * @return
     */
    public static <V> V wrap(Callable<V> callable, Handler handler) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw handler.handle(e);
        }
    }

    /**
     * Wrap the checked {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param handler
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V wrap(Handler handler, Callable<V> callable) {
       return wrap(callable, handler);
    }

    /**
     * Wrap all the {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param callable
     * @param handler
     * @param <V>
     * @return
     */
    public static <V> V wrapAll(Callable<V> callable, Handler handler) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw handler.handle(e);
        }
    }

    /**
     * Wrap all the {@link Exception} thrown by the provided {@link Callable} into the {@link RuntimeException} (using {@link Handler}).
     *
     * @param handler
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V wrapAll(Handler handler, Callable<V> callable) {
        return wrapAll(callable, handler);
    }
}
