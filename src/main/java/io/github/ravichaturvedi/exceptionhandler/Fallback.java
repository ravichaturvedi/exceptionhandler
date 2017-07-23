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
import java.util.function.Supplier;


/**
 * {@link Fallback} provides the static functions to deal with fallback handling, in case provided {@link Callable} throws an {@link Exception}.
 */
public class Fallback {

    /**
     * Returns back the supplier function using the provided value.
     *
     * @param value
     * @param <V>
     * @return
     */
    public static <V> Supplier<V> to(V value) {
        return () -> value;
    }

    /**
     * Returns back the supplier function using the provided value supplier.
     * @param valueSupplier
     * @param <V>
     * @return
     */
    public static <V> Supplier<V> to(Supplier<V> valueSupplier) {
        return valueSupplier;
    }

    /**
     * Returns back the fallback function using the given function.
     * @param fallbackFunc
     * @param <V>
     * @return
     */
    public static <V> Function<Exception, V> to(Function fallbackFunc) {
        return fallbackFunc;
    }

    /**
     * Fallback to a value {@link Supplier<V>} if the provided {@link Callable<V>} throws an {@link Exception}.
     * @param callable
     * @param valueSupplier
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, Supplier<V> valueSupplier) {
        try {
            return callable.call();
        } catch (Exception e) {
            return valueSupplier.get();
        }
    }

    /**
     * Fallback to the {@link Function<Exception,V>}, if provided {@link Callable<V>} throws an {@link Exception}.
     * @param callable
     * @param fallbackFunc
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, Function<Exception, V> fallbackFunc) {
        try {
            return callable.call();
        } catch (Exception e) {
            return fallbackFunc.apply(e);
        }
    }
}
