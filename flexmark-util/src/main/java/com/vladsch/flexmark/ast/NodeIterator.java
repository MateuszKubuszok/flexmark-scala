/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class NodeIterator implements ReversiblePeekingIterator<Node> {
    final Node firstNode;
    final Node lastNode;
    final boolean reversed;
    Node node;
    Node result;

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed
     */
    public NodeIterator(Node firstNode) {
        this(firstNode, null, false);
    }

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed
     * @param reversed  true/false if the nodes are to be traversed in reverse order. If true the nodes previous sibling will be used instead of next sibling
     */
    public NodeIterator(Node firstNode, boolean reversed) {
        this(firstNode, null, reversed);
    }

    /**
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed or lastNode has been traversed
     * @param lastNode  the last node to be traversed
     */
    public NodeIterator(Node firstNode, Node lastNode) {
        this(firstNode, lastNode, false);
    }

    /**
     * iterate nodes until null or last node is iterated over
     *
     * @param firstNode node from which to start the iteration and continue until all sibling nodes have been traversed or lastNode has been traversed
     * @param lastNode  the last node to be traversed
     * @param reversed true/false if the nodes are to be traversed in reverse order. If true the nodes previous sibling will be used instead of next sibling
     */
    public NodeIterator(Node firstNode, Node lastNode, boolean reversed) {
        Objects.requireNonNull(firstNode);

        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.reversed = reversed;
        this.node = reversed ? lastNode : firstNode;
    }

    /**
     * @return true if the iterator is a reversed iterator
     */
    @Override
    public boolean isReversed() {
        return reversed;
    }

    /**
     * @return true if there is a next node
     */
    @Override
    public boolean hasNext() {
        return node != null;
    }

    /**
     * @return the next node for the iterator. If the iterator is not reversed then the previous node's next sibling is returned. If it is reversed the the previous node's previous sibling is returned.
     */
    @Override
    public Node next() {
        result = null;

        if (node == null) {
            throw new NoSuchElementException();
        }

        result = node;
        node = reversed ? node.getPrevious() : node.getNext();
        if (node == null || result == (reversed ? firstNode : lastNode)) node = null;
        return result;
    }

    /**
     * @return the node which would be returned by a call to {@link #next()} or null if there is no next node.
     */
    public Node peek() {
        if (node != null) {
            return node;
        }
        return null;
    }

    /**
     * Remove the last node returned by {@link #next()}
     */
    @Override
    public void remove() {
        if (result == null) {
            throw new IllegalStateException("Either next() was not called yet or the node was removed");
        }
        result.unlink();
        result = null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Node> consumer) {
        Objects.requireNonNull(consumer);

        while (hasNext()) {
            consumer.accept(next());
        }
    }

    public static final ReversiblePeekingIterator<Node> EMPTY = new ReversiblePeekingIterator<Node>() {
        @Override
        public boolean isReversed() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            throw new NoSuchElementException();
        }

        @Override
        public Node peek() {
            return null;
        }
    };
}
