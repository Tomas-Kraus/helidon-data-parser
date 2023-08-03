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

/**
 * Helidon data repository method name parser.
*/
%%

%class Lexer
%function parse
%type Token
%unicode
%line
%column

%state PROJECTION_TYPE
%state PROJECTION_TOP_COUNT
%state PROJECTION_IDENTIFIER
%state PROJECTION_AFTER_PROPERTY
%state CRITERIA
%state CRITERIA_PROPERTY
%state CRITERIA_NEXT

// FIXME: Remove when fully implemented and tested
%debug

%{
    Token.Method method = null;
    Token.Projection projection = null;
    int topCount = 0;
    String projectionProperty = null;
    Token.Criteria criteria = null;
    String criteriaProperty = null;
%}

UP_ALPHA=[A-Z]
ALPHA=[A-Za-z]
DIGIT=[0-9]
EXT_CHAR = [_$]

IDENT_CHAR = {ALPHA} | {DIGIT} | {EXT_CHAR}

Number = [0-9]+
Property = {UP_ALPHA} {IDENT_CHAR}+
BySuffix = By {IDENT_CHAR}+

%%

// Initial state of the parser
// Method name can start with "find" of "get" keywords to define query method
<YYINITIAL> {

    "get" {
        method = Token.Method.GET;
        yybegin(PROJECTION_TYPE);
    }

    "find" {
        method = Token.Method.FIND;
        yybegin(PROJECTION_TYPE);
    }

}

// Optional projection type
// This optional keyword follows query method keyword
<PROJECTION_TYPE> {

    "Count" {
        projection = Token.Projection.COUNT;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "CountDistinct" {
        projection = Token.Projection.COUNT_DISTINCT;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Distinct" {
        projection = Token.Projection.DISTINCT;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Max" {
        projection = Token.Projection.MAX;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Min" {
        projection = Token.Projection.MIN;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Sum" {
        projection = Token.Projection.SUM;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Avg" {
        projection = Token.Projection.AVG;
        yybegin(PROJECTION_IDENTIFIER);
    }

    "Top" {
        projection = Token.Projection.TOP;
        yybegin(PROJECTION_TOP_COUNT);
    }

    // Next state when no projection type was specified
    "" {
        yybegin(PROJECTION_IDENTIFIER);
    }

    // Allow finish parsing right after query method keyword
    <<EOF>> {
        return new Token(method);
    }

}

// Top projection type requires number as part of its name
<PROJECTION_TOP_COUNT> {

    {Number} {
        topCount = Integer.parseInt(yytext());
        yybegin(PROJECTION_IDENTIFIER);
    }

}

// Projection identifier
// May be followed by two additional keywords:
// "By" to define query criteria
// "OrderBy" to define ordering
<PROJECTION_IDENTIFIER> {

    // Lookahead for criteria part of the expression
    {Property} / {BySuffix} {
        projectionProperty = yytext();
        yybegin(CRITERIA);
    }

    // Property followed by no "By" or "OrderBy" keywords
    {Property} {
        projectionProperty = yytext();
        yybegin(PROJECTION_AFTER_PROPERTY);
    }

}

// No additional input is allowed after projection identifier when "By" or "OrderBy" keywords are missing
<PROJECTION_AFTER_PROPERTY> {

    <<EOF>> {
        return new Token(method, projection, topCount, projectionProperty);
    }

}

// Criteria - TBD
<CRITERIA> {

    // Just consume "By"
    "By" {
        yybegin(CRITERIA_PROPERTY);
    }

}
<CRITERIA_PROPERTY> {

    {Property}"After" {
        criteria = Token.Criteria.AFTER;
        criteriaProperty = yytext().substring(0, yytext().length() - 5);
        yybegin(CRITERIA_NEXT);
    }

    {Property}"Before" {
        criteria = Token.Criteria.BEFORE;
        criteriaProperty = yytext().substring(0, yytext().length() - 6);
        yybegin(CRITERIA_NEXT);
    }

    {Property} {
        criteria = Token.Criteria.EQUALS;
        criteriaProperty = yytext();
        return new Token(method, projection, topCount, projectionProperty, criteria, criteriaProperty);
    }

}

<CRITERIA_NEXT> {

    <<EOF>> {
        return new Token(method, projection, topCount, projectionProperty, criteria, criteriaProperty);
    }

}
