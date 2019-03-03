/*
 * The MIT License
 *
 * Copyright 2017 Synclab Consulting LLC.
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
package com.conversationkit.impl.edge;

import com.conversationkit.model.IConversationNode;
import com.conversationkit.model.IConversationState;

/**
 * Edge that matches no-like responses.
 * @author pdtyreus
 */
public class NegativeEdge <S extends IConversationState<String, Object>> extends RegexEdge<S> {

    private static final String NO = "\\bno\\b|\\bnope\\b|\\bnah\\b|\\bnone\\b|\\bnot really\\b";
    
    public NegativeEdge(String stateKey, Object stateValue, IConversationNode<S> endNode) {
        super(NO,stateKey,stateValue,endNode);
    }
    
    public NegativeEdge(IConversationNode<S> endNode) {
        super(NO,endNode);
    }
}
