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
     * {@link Handler} defines the handler for the fallback function.
     * @param <V>
     */
    @FunctionalInterface
    private interface Handler<V> {
        V handle(Exception e);
    }

    /**
     * Represents a function that accepts {@link Exception} and produces a result.
     * @param <V>
     */
    @FunctionalInterface
    interface Func<V> {
        V apply(Exception e) throws Exception;
    }

    /**
     * {@link Handler} defines the handler for the fallback function, which can throw {@link Exception}.
     * @param <V>
     */
    @FunctionalInterface
    private interface TryHandler<V> {
        V tryHandle(Exception e) throws Exception;
    }

    /**
     * Returns back the {@link Handler} using the provided value.
     *
     * @param value
     * @param <V>
     * @return
     */
    public static <V> Handler<V> to(V value) {
        return e -> value;
    }

    /**
     * Returns back the {@link Handler} using the provided value {@link Supplier}.
     * @param valueSupplier
     * @param <V>
     * @return
     */
    public static <V> Handler<V> to(Supplier<V> valueSupplier) {
        return e -> valueSupplier.get();
    }

    /**
     * Returns back the {@link Handler} using the given {@link Function}.
     * @param fallbackFunc
     * @param <V>
     * @return
     */
    public static <V> Handler<V> to(Function<Exception, V> fallbackFunc) {
        return fallbackFunc::apply;
    }

    /**
     * Returns back the {@link TryHandler} using the given {@link Func}.
     * @param func
     * @param <V>
     * @return
     */
    public static <V> TryHandler<V> toFunc(Func<V> func) {
        return func::apply;
    }

    /**
     * Fallback to the {@link Handler}, if provided {@link Callable} throws an {@link Exception}.
     * @param callable
     * @param handler
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, Handler<V> handler) {
        try {
            return callable.call();
        } catch (Exception e) {
            return handler.handle(e);
        }
    }

    /**
     * Fallback to the {@link TryHandler} which may throw {@link Exception}, if provided {@link Callable} throws an {@link Exception}.
     * @param callable
     * @param tryHandler
     * @param <V>
     * @return
     */
    public static <V> V fallback(Callable<V> callable, TryHandler<V> tryHandler) throws Exception {
        try {
            return callable.call();
        } catch (Exception e) {
            return tryHandler.tryHandle(e);
        }
    }

    /**
     * Fallback to the {@link Handler}, if provided {@link Callable} throws an {@link Exception}.
     * @param handler
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V fallback(Handler<V> handler, Callable<V> callable) {
        return fallback(callable, handler);
    }

    /**
     * Fallback to the {@link Handler}, if provided {@link Callable} throws an {@link Exception}.
     * @param tryHandler
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> V fallback(TryHandler<V> tryHandler, Callable<V> callable) throws Exception {
        return fallback(callable, tryHandler);
    }
}
