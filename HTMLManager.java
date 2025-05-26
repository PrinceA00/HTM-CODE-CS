import java.util.*;

public class HTMLManager {
    // Queue holding the sequence of HTMLTag objects for this page
    private Queue<HTMLTag> tags;
    
    /**
     * Constructor: initialize this HTMLManager with an existing queue of tags.
     * Makes a defensive copy to avoid external modification.
     * @param html the input queue of HTMLTag objects (must not be null)
     * @throws IllegalArgumentException if html is null
     */
    public HTMLManager(Queue<HTMLTag> html) {
        if (html == null) {
            throw new IllegalArgumentException("Input tag queue cannot be null");
        } else {
            // Create a new LinkedList and copy over each tag
            tags = new LinkedList<>();
            for (HTMLTag tag : html) {
                tags.add(tag);
            }
        }
    }

    /**
     * Accessor for the internal queue of tags.
     * @return the queue of HTMLTag objects currently stored
     */
    public Queue<HTMLTag> getTags() {
        return tags;
    }

    /**
     * Build a single string representation of all tags in the queue.
     * Does not modify the state of the queue itself.
     * @return concatenated tags, trimmed of leading/trailing whitespace
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HTMLTag tag : tags) {
            sb.append(tag);
        }
        return sb.toString().trim();
    }

    /**
     * Walk through the current tags queue and build
     * a corrected version in-place, fixing any missing
     * or extra tags by using a local Stack<HTMLTag>.
     */
    public void fixHTML() {
        // Stack to keep track of unclosed opening tags
        Stack<HTMLTag> s = new Stack<>();
        // New queue to accumulate the fixed tags
        Queue<HTMLTag> corrected = new LinkedList<>();

        // 1) Process each tag from the original queue
        while (!tags.isEmpty()) {
            HTMLTag tag = tags.remove();

            if (tag.isSelfClosing()) {
                // Self-closing: carry forward unchanged
                corrected.add(tag);

            } else if (tag.isOpening()) {
                // Opening: enqueue and push onto stack for later matching
                corrected.add(tag);
                s.push(tag);

            } else {
                // Closing: attempt to match with top of stack
                if (s.isEmpty()) {
                    // No corresponding opener → discard this closer
                    continue;
                }
                HTMLTag openTop = s.peek();
                if (openTop.matches(tag)) {
                    // Perfect match  enqueue closer and pop opener
                    corrected.add(tag);
                    s.pop();
                } else {
                    // Mismatch close the opener first
                    HTMLTag neededClose = openTop.getMatching();
                    corrected.add(neededClose);
                    s.pop();
                    // Discard this incorrect closing tag
                }
            }
        }

        // 2) Close any open tags still on the stack
        while (!s.isEmpty()) {
            HTMLTag openTop = s.pop();
            corrected.add(openTop.getMatching());
        }

        // 3) Replace the old queue with the corrected one
        this.tags = corrected;
    }
    

}
