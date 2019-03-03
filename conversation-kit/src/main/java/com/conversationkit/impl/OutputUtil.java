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

import com.conversationkit.model.IConversationSnippet;
import com.conversationkit.model.IConversationState;
import com.conversationkit.model.SnippetType;
import java.util.Formatter;

/**
 *
 * @author pdtyreus
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class OutputUtil {
    
	
	public static void formatSnippet(Formatter formatter, IConversationSnippet node, IConversationState state) {
        formatter.format("  > %-100s <\n", node.renderContent(state));
        if ((node.getType() == SnippetType.QUESTION) && (node.getSuggestedResponses(state) != null)) {
            formatter.format("  >   %-98s <\n", "[ " + String.join(" | ", node.getSuggestedResponses(state)) + " ]");
        }
    }
	
	public static void formatSnippetPlain(Formatter formatter, IConversationSnippet node, IConversationState state) {
        formatter.format("%s", node.renderContent(state));
        if ((node.getType() == SnippetType.QUESTION) && (node.getSuggestedResponses(state) != null)) {
            formatter.format("%s", "[ " + String.join(" | ", node.getSuggestedResponses(state)) + " ]");
        }
    }

    public static void formatResponse(Formatter formatter, String response) {
        formatter.format("  > %100s <\n", response);
    }
}
