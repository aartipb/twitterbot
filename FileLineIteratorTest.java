package org.cis120;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.BufferedReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for FileLineIterator */
public class FileLineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     */

    @Test
    public void testHasNextAndNext() {

        // Note we don't need to create a new file here in order to test out our
        // FileLineIterator if we do not want to. We can just create a
        // StringReader to make testing easy!
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }


    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void testDuplicate() {

        String words  = "There were dinosaurs before humans.\n"
                + "There were dinosaurs before humans.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("There were dinosaurs before humans.", li.next());
        assertTrue(li.hasNext());
        assertEquals("There were dinosaurs before humans.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testNextNoSuchElementException() {
        String words = "There were dinosaurs before humans.\n";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("There were dinosaurs before humans.", li.next());
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            li.next();
        });
    }

}
