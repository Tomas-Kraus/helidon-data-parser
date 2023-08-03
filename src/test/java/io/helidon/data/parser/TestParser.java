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
    public void testMethodGet() throws IOException {
        System.out.println("\n*** Running testMethodGet ***");
        Token token = parse("get");
        assertThat(token.method(), is(Token.Method.GET));
    }

    // Method name contains return method "find" only
    @Test
    public void testMethodFind() throws IOException {
        System.out.println("\n*** Running testMethodFind ***");
        Token token = parse("find");
        assertThat(token.method(), is(Token.Method.FIND));
    }

    @Test
    public void testMethodGetProperty() throws IOException {
        System.out.println("\n*** Running testMethodGetProperty ***");
        Token token = parse("getProperty");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodFindProperty() throws IOException {
        System.out.println("\n*** Running testMethodFindProperty ***");
        Token token = parse("findProperty");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodGetMaxProperty() throws IOException {
        System.out.println("\n*** Running testMethodGetMaxProperty ***");
        Token token = parse("getMaxProperty");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projection(), is(Token.Projection.MAX));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodFindMinProperty() throws IOException {
        System.out.println("\n*** Running testMethodFindMinProperty ***");
        Token token = parse("findMinProperty");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projection(), is(Token.Projection.MIN));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodGetTop3Property() throws IOException {
        System.out.println("\n*** Running testMethodGetTop3Property ***");
        Token token = parse("getTop3Property");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projection(), is(Token.Projection.TOP));
        assertThat(token.topCount(), is(3));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodFindTop20Property() throws IOException {
        System.out.println("\n*** Running testMethodFindTop20Property ***");
        Token token = parse("findTop20Property");
        assertThat(token.method(), is(Token.Method.FIND));
        assertThat(token.projection(), is(Token.Projection.TOP));
        assertThat(token.topCount(), is(20));
        assertThat(token.projectionProperty(), is("Property"));
    }

    @Test
    public void testMethodGetPropertyBy() throws IOException {
        System.out.println("\n*** Running testMethodGetPropertyBy ***");
        Token token = parse("getPropertyBy");
        assertThat(token.method(), is(Token.Method.GET));
        assertThat(token.projectionProperty(), is("Property"));
    }

    private static Token parse(String str) throws IOException {
        StringReader sr = new StringReader(str);
        Lexer lexer = new Lexer(sr);
        return lexer.parse();
    }

}
