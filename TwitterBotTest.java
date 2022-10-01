package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Tests for TwitterBot class */
public class TwitterBotTest {

    /*
     * This tests whether your TwitterBot class itself is written correctly
     *
     * This test operates very similarly to our MarkovChain tests in its use of
     * `fixDistribution`, so make sure you know how to test MarkovChain before
     * testing this!
     */
    @Test
    public void simpleTwitterBotTest() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "comes", "from", "data", "with", "no", "duplicate", "words", ".",
                        "the", "end", "should", "come", "."
                )
        );
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(12));
        //System.out.println(actual.split(" ").length);
        assertEquals(expected, actual);
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */
    @Test
    public void simpleTwitterBotTest2() {
        List<String> desiredTweet = new ArrayList<>(
                Arrays.asList(
                        "this", "comes", "from", "data", "with", "no",
                        "duplicate", "words", ".",
                        "the", "end", "should", "come", "here", ".", "the", "."
                )
        );
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        TwitterBot t = new TwitterBot(br, 1);
        t.fixDistribution(desiredTweet);

        String expected = "this comes from data with no duplicate words. the end should come here. the.";
        String actual = TweetParser.replacePunctuation(t.generateTweet(14));
        //System.out.println(actual.split(" ").length);
        assertEquals(expected, actual);
    }
}
