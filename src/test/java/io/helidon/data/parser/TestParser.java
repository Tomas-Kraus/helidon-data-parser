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

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
public class TestParser {

    // Method name contains return method "get" only
    @Test
    public void testGet() throws IOException {
        System.out.println("\n*** Running testGet ***");
        Token token = parse("get");
        assertThat(token.method(), is(Token.Method.GET));
    }

    // Method name contains return method "find" only
    @Test
    public void testFind() throws IOException {
        System.out.println("\n*** Running testFind ***");
        Token token = parse("find");
        assertThat(token.method(), is(Token.Method.FIND));
    }

    @Test
    public void testGetProperty() throws IOException {
        System.out.println("\n*** Running testGetProperty ***");
        Token token = parse("getProperty");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testFindProperty() throws IOException {
        System.out.println("\n*** Running testFindProperty ***");
        Token token = parse("findProperty");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testGetMaxProperty() throws IOException {
        System.out.println("\n*** Running testGetMaxProperty ***");
        Token token = parse("getMaxProperty");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projection(), is(Token.Projection.MAX));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testFindMinProperty() throws IOException {
        System.out.println("\n*** Running testFindMinProperty ***");
        Token token = parse("findMinProperty");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projection(), is(Token.Projection.MIN));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testGetTop3Property() throws IOException {
        System.out.println("\n*** Running testGetTop3Property ***");
        Token token = parse("getTop3Property");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projection(), is(Token.Projection.TOP));
        assertThat(token.topCount(), is(3));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testFindTop20Property() throws IOException {
        System.out.println("\n*** Running testFindTop20Property ***");
        Token token = parse("findTop20Property");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projection(), is(Token.Projection.TOP));
        assertThat(token.topCount(), is(20));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testGetPropertyByValue() throws IOException {
        System.out.println("\n*** Running testGetPropertyByValue ***");
        Token token = parse("getPropertyByValue");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projectionProperty(), is("Property"));
        assertThat(token.criteria(), is(Token.Criteria.EQUALS));
        assertThat(token.criteriaProperty(), is("Value"));
    }

    @Test
    public void testGetPropertyByValueAfter() throws IOException {
        System.out.println("\n*** Running testGetPropertyByValueAfter ***");
        Token token = parse("getPropertyByValueAfter");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projectionProperty(), is("Property"));
        assertThat(token.criteria(), is(Token.Criteria.AFTER));
        assertThat(token.criteriaProperty(), is("Value"));
    }


    private static Token parse(String str) throws IOException {
        StringReader sr = new StringReader(str);
        Lexer lexer = new Lexer(sr);
        return lexer.parse();
    }

}
