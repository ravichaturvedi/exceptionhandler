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
 * {@link Fallbacker} provides the static functions to deal with fallback values, in case provided caller throws an exceptionhandler.
 */
public class Fallbacker {

    /**
     * Fallback to a value if the provided {@link Callable} throws an exceptionhandler.
     * @param callable
     * @param value
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, V value) {
        try {
            return callable.call();
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * Fallback to a value {@link Supplier} if the provided {@link Callable} throws an exceptionhandler.
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
     * Fallback to the value {@link Function} (based on the exceptionhandler), if provided {@link Callable} throws an exceptionhandler.
     * @param callable
     * @param valueFunction
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, Function<Exception, V> valueFunction) {
        try {
            return callable.call();
        } catch (Exception e) {
            return valueFunction.apply(e);
        }
    }
}
