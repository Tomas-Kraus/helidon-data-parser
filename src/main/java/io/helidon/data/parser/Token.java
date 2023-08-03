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

    /**
     * Condition operators with supported keywords.
     * Numbers of parameters are used to assign method parameters to statement {@code setParameter} calls.
     * Keywords are used to initialize related part of dynamic finder query method name parser.
     */
    public enum Criteria {
        AFTER(1, "After"),
        BEFORE(1, "Before"),
        CONTAINS(1, "Contains"),
        STARTS(1, "StartsWith", "StartingWith"),
        ENDS(1, "EndsWith", "EndingWith"),
        EQUALS(1, "Equal", "Equals"),
        GREATER_THAN(1, "GreaterThan"),
        GREATER_THAN_EQUALS(1, "GreaterThanEqual", "GreaterThanEquals"),
        LESS_THAN(1, "LessThan"),
        LESS_THAN_EQUALS(1, "LessThanEqual", "LessThanEquals"),
        LIKE(1, "Like"),
        ILIKE(1, "Ilike"),
        IN(1, "In", "InList"),
        BETWEEN(2, "Between", "InRange"),
        NULL(0, "Null", "IsNull"),
        EMPTY(0, "Empty", "IsEmpty"),
        TRUE(0, "True", "IsTrue"),
        FALSE(0, "False", "IsFalse");

        /** Condition operators enumeration length. */
        static final int LENGTH = values().length;

        // Number of operator parameters
        private final int paramCount;
        // Supported operator keywords
        private final String[] keywords;

        // Creates an instance of condition operators
        Criteria(int paramCount, String... keywords) {
            if (keywords == null || keywords.length == 0) {
                throw new IllegalArgumentException("At least one keyword is required!");
            }
            this.paramCount = paramCount;
            this.keywords = keywords;
        }

        // TODO: Remove public access to keywords and paramCount methods
        /**
         * Supported keywords.
         *
         * @return keywords supported by the condition method.
         */
        public String[] keywords() {
            return keywords;
        }

        /**
         * Number of operator parameters.
         *
         * @return number of operator parameters required from dynamic finder query method
         */
        public int paramCount() {
            return paramCount;
        }

    }

    private final Method method;
    private final Projection projection;
    private final String projectionProperty;
    private int topCount = 0;
    private final Criteria criteria;
    private final String criteriaProperty;

    Token(Method method) {
        this.method = method;
        this.projection = null;
        this.topCount = 0;
        this.projectionProperty = null;
        this.criteria = null;
        this.criteriaProperty = null;
    }

    Token(Method method,
          Projection projection,
          int topCount,
          String projectionProperty) {
        this.method = method;
        this.projection = projection;
        this.topCount = topCount;
        this.projectionProperty = projectionProperty;
        this.criteria = null;
        this.criteriaProperty = null;
    }

    Token(Method method,
          Projection projection,
          int topCount,
          String projectionProperty,
          Criteria criteria,
          String criteriaProperty) {
        this.method = method;
        this.projection = projection;
        this.topCount = topCount;
        this.projectionProperty = projectionProperty;
        this.criteria = criteria;
        this.criteriaProperty = criteriaProperty;
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

    Criteria criteria() {
        return criteria;
    }

    String criteriaProperty() {
        return criteriaProperty;
    }

}
