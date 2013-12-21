package moodishsecurity.securityfilter.records.buffer;

import java.util.Iterator;

import moodishsecurity.securityfilter.records.models.SecurityFilterMood;

/**
 * This class implements an <i>Iterable</i> circular buffer for <i>SecurityFilterMood</i>. 
 * 
 * @author Default/Group 10
 * @version 1
 */
public class SecurityFilterMoodBuffer implements Iterable<SecurityFilterMood> {
	private SecurityFilterMood[] buffer; 
	private int first, next; 

	/**
	 * <i>SecurityFilterMoodBuffer</i> Class contructor.
	 * 
	 * @param <b>size</b> The buffer size.
	 */
    public SecurityFilterMoodBuffer(final int size) {
        buffer = new SecurityFilterMood[size]; 
        first = 0;
        next = 0;
    } 

    /**
     * Check if buffer is empty.
     * 
     * @return <b>true</b> if circular buffer is empty, otherwise <b>false</b>
     */
    public boolean isEmpty() { 
        return first == next; 
    }
    
    /**
     * Insert a mood into the buffer next position.
     * 
     * @param <b>mood</b> The mood to be inserted into the buffer.
     */
    public void insert(SecurityFilterMood mood) { 
        buffer[next] = mood;
        next = (next + 1) % buffer.length;
        if(next==first) first = (first+1) % buffer.length;
    } 
    
    /**
     * Jumps back <i>jumps</i> positions and retrieves mood. If <i>jumps</i> = 0 it will retrieve
     * last inserted <i>SecurityFilterMood</i>.
     *  
     * @param <b>jumps</b> Number of jumps in the circular buffer to get to the value.
     * @return <b>SecurityFilterMood</b> if found in buffer, otherwise <b>null</b>
     */
    public SecurityFilterMood getLast(int jumps) {
    	if(isEmpty() || jumps>(buffer.length-1)) return null;
    	int index = ((next+buffer.length-jumps-1) % buffer.length);
    	return buffer[index];
    }
    
    /**
     * Get last inserted SecurityFilterMood.
     * 
     * @return <b>SecurityFilterMood</b> if found in buffer, otherwise <b>null</b>
     */
    public SecurityFilterMood getLast() {
    	return getLast(0);
    }
    
	public Iterator<SecurityFilterMood> iterator() {
	   	return new BufferIterator(first,next);
	}

	/**
	 * The circular buffer Iterator private Class.
	 * 
	 * @author Default/Group 10
	 * @version 1
	 */
    private class BufferIterator implements Iterator<SecurityFilterMood> {
    	private int tail;
    	private int head;
    	
    	public BufferIterator(int head, int tail) {
    		this.head = head;
    		this.tail = tail;
    	}

    	public boolean hasNext() {
    		return tail != head;
    	}

    	public SecurityFilterMood previous() {
    		tail = ((tail-1)<0?(buffer.length-1):(tail-1));
			SecurityFilterMood mood = buffer[tail];
	    	return mood;
    	}
    	
		public SecurityFilterMood next() {
			return previous();
		}

		public void remove() {
		    throw new UnsupportedOperationException();
		}
	}
}
