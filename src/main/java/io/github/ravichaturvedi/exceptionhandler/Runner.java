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


/**
 * {@link Runner} specify some code block which returns nothing but can throw exceptionhandler.
 *
 * Intentionally kept the interface package so that outside code do not depend on this interface and use only lambda expressions.
 */
@FunctionalInterface
interface Runner {

    /**
     * Run the implementation
     *
     * @throws Exception If underlying implementation throws.
     */
    void run() throws Exception;
}
