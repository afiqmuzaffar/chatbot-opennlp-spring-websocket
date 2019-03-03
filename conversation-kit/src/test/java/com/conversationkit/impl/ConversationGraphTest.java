/*
 * The MIT License
 *
 * Copyright 2016 Synclab Consulting LLC.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.conversationkit.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Formatter;
import java.util.logging.Logger;

import com.conversationkit.builder.JsonGraphBuilder;
import com.conversationkit.builder.TestCaseUserState;
import com.conversationkit.model.IConversationSnippet;
import com.conversationkit.model.IConversationSnippetButton;
import com.conversationkit.model.IConversationState;
import com.conversationkit.model.SnippetContentType;
import com.conversationkit.model.SnippetType;
import com.conversationkit.model.UnexpectedResponseException;
import com.conversationkit.model.UnmatchedResponseException;

import junit.framework.TestCase;

/**
 *
 * @author pdtyreus
 */
public class ConversationGraphTest extends TestCase {

    private static final Logger logger = Logger.getLogger(ConversationGraphTest.class.getName());

    public <S extends IConversationState<String, Object>> void testDirectedConversation() throws IOException {

        logger.info("** Initializing Templated Regex / JavaScript Conversation for testing");

        //In practice you would use a real template engine here, but we are making a simple one to minimize dependencies
        JsonGraphBuilder<TestCaseUserState> builder = new JsonGraphBuilder<>();
        Reader reader = new InputStreamReader(DialogTreeTest.class.getResourceAsStream("/directed_conversation.json"));
        DirectedConversationEngine<TestCaseUserState> tree = builder.readJsonGraph(reader);

        logger.info("** Testing conversation");

        TestCaseUserState state = new TestCaseUserState();
        state.setCurrentNodeId(1);

        Iterable<IConversationSnippet<TestCaseUserState>> nodes = tree.startConversationFromState(state);
        StringBuilder convo = new StringBuilder();
        Formatter formatter = new Formatter(convo);

        convo.append("\n");
        for (IConversationSnippet node : nodes) {
            OutputUtil.formatSnippet(formatter, node, state);
        }

        String response = "4";
        OutputUtil.formatResponse(formatter, response);
        try {
            tree.updateStateWithResponse(state, response);
        } catch (UnmatchedResponseException | UnexpectedResponseException e) {
            fail(e.toString());
        }
        nodes = tree.startConversationFromState(state);
        for (IConversationSnippet node : nodes) {
            OutputUtil.formatSnippet(formatter, node, state);
        }

        assertEquals(5, state.getCurrentNodeId());
        response = "yup";
        OutputUtil.formatResponse(formatter, response);

        try {
            tree.updateStateWithResponse(state, response);
        } catch (UnmatchedResponseException e) {
            OutputUtil.formatSnippet(formatter, new IConversationSnippet<TestCaseUserState>(){

                public String renderContent(TestCaseUserState state) {
                    return "I'm sorry, I didn't understand your response '"+state.getMostRecentResponse()+"'.";
                }

                public SnippetType getType() {
                    return SnippetType.STATEMENT;
                }

                public Iterable<String> getSuggestedResponses(TestCaseUserState state) {
                    return null;
                }

                public SnippetContentType getContentType() {
                    return SnippetContentType.TEXT;
                }

                @Override
                public Iterable<IConversationSnippetButton> getButtons() {
                    return null;
                }
                
            }, state);
        } catch (UnexpectedResponseException e) {
            fail(e.toString());
        }
        nodes = tree.startConversationFromState(state);
        for (IConversationSnippet node : nodes) {
            OutputUtil.formatSnippet(formatter, node, state);
        }
        
        response = "yes";
        OutputUtil.formatResponse(formatter, response);

        try {
            tree.updateStateWithResponse(state, response);
        } catch (UnmatchedResponseException | UnexpectedResponseException e) {
            fail(e.toString());
        }
        nodes = tree.startConversationFromState(state);
        for (IConversationSnippet node : nodes) {
            OutputUtil.formatSnippet(formatter, node, state);
        }

        response = "six";
        OutputUtil.formatResponse(formatter, response);

        try {
            tree.updateStateWithResponse(state, response);
        } catch (UnmatchedResponseException | UnexpectedResponseException e) {
            fail(e.toString());
        }
        nodes = tree.startConversationFromState(state);
        for (IConversationSnippet node : nodes) {
            OutputUtil.formatSnippet(formatter, node, state);
        }

        assertEquals(6, state.get("answer"));

        logger.info(convo.toString());
    }
}
