/*
 * Copyright (c) 2023 Oracle and/or its affiliates.
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
package io.helidon.data.parser;

public class Token {

    /**
     * Query selection methods.
     */
    public enum Method {
        /**
         * Return single result.
         */
        GET("get"),
        /**
         * Return multiple results.
         */
        FIND("find");

        /**
         * Query selection methods enumeration length.
         */
        public static final int LENGTH = values().length;

        //  Supported method keyword.
        private final String keyword;

        Method(String keyword) {
            this.keyword = keyword;
        }

        /**
         * Supported method keyword.
         *
         * @return selection method keyword
         */
        public String keyword() {
            return keyword;
        }

    }

    /**
     * Projection methods.
     */
    public enum Projection {
        /**
         * Return count of the values.
         */
        COUNT("Count"),
        /**
         * Return count of the distinct values.
         */
        COUNT_DISTINCT("CountDistinct"),
        /**
         * Return the distinct values.
         */
        DISTINCT("Distinct"),
        /**
         * Return the maximum value.
         */
        MAX("Max"),
        /**
         * Return the minimum value.
         */
        MIN("Min"),
        /**
         * Return the summary of all values.
         */
        SUM("Sum"),
        /**
         * Return the average of all values.
         */
        AVG("Avg"),
        /**
         * Return first N of all values. N is integer value.
         */
        TOP("Top");

        //  Supported method keyword.
        public final String keyword;

        /**
         * Query projection methods enumeration length.
         */
        public static final int LENGTH = values().length;

        // Creates an instance of projection method.
        Projection(String keyword) {
            this.keyword = keyword;
        }

        /**
         * Supported method keyword.
         *
         * @return projection method keyword
         */
        public String keyword() {
            return keyword;
        }

    }


    private final Method method;
    private final Projection projection;
    private final String projectionProperty;
    private int topCount = 0;

    Token(Method method) {
        this.method = method;
        this.projection = null;
        this.topCount = 0;
        this.projectionProperty = null;
    }

    Token(Method method, Projection projection, int topCount, String projectionProperty) {
        this.method = method;
        this.projection = projection;
        this.topCount = topCount;
        this.projectionProperty = projectionProperty;
    }


    Method method() {
        return method;
    }

    Projection projection() {
        return projection;
    }

    int topCount() {
        return topCount;
    }

    String projectionProperty() {
        return projectionProperty;
    }


}
