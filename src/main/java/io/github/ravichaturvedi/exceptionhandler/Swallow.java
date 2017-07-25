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

import static io.github.ravichaturvedi.exceptionhandler.Callables.from;

/**
 * {@link Swallow} provides static functions to deal with swallowing the {@link Exception}, in case provided code-block throws an {@link Exception}.
 */
public class Swallow {

    /**
     * {@link ExceptionLogger} defines the exception logging mechanism
     */
    @FunctionalInterface
    interface ExceptionLogger {
        void log(Exception e);
    }

    /**
     * {@link Handler} defines the handler for the swallow function.
     */
    @FunctionalInterface
    private interface Handler {
        void handle(Exception e);
    }

    /**
     * Returns the {@link Handler} for the given {@link ExceptionLogger}.
     * @param exceptionLogger
     * @return
     */
    public static Handler usingLogger(ExceptionLogger exceptionLogger) {
        return exceptionLogger::log;
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Runner}, using provided {@link Handler}.
     *
     * @param runner
     * @param handler
     */
    public static void swallow(Runner runner, Handler handler) {
        swallow(from(runner), handler);
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Runner}, using provided {@link Handler}.
     *
     * @param handler
     * @param runner
     */
    public static void swallow(Handler handler, Runner runner) {
        swallow(runner, handler);
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Runner}.
     *
     * @param runner
     */
    public static void swallow(Runner runner) {
        swallow(from(runner));
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Callable}, using provided {@link Handler}.
     *
     * @param callable
     * @param handler
     */
    public static void swallow(Callable<?> callable, Handler handler) {
        try {
            callable.call();
        } catch (Exception e) {
            handler.handle(e);
        }
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Callable}, using provided {@link Handler}.
     *
     * @param handler
     * @param callable
     */
    public static void swallow(Handler handler, Callable<?> callable) {
        swallow(callable, handler);
    }

    /**
     * Swallow the {@link Exception} thrown by the provided {@link Callable}.
     *
     * @param callable
     */
    public static void swallow(Callable<?> callable) {
        swallow(callable, usingLogger(e -> {}));
    }
}
