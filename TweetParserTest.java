package org.cis120;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/** Tests for TweetParser */
public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<String>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<String>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc . def.", TweetParser.removeURLs("abc http://www.cis.upenn.edu. def."));
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals("abc .", TweetParser.removeURLs("abc http://www.cis.upenn.edu."));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    /* **** ****** ***** **** EXTRACT COLUMN TESTS **** **** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(
                " This is a tweet.",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 3
                )
        );
    }

    @Test
    public void testExtractColumnRepeatedWords() {
        assertEquals(
                " that.",
                TweetParser.extractColumn(
                        "This, that , this, that.", 3
                )
        );
    }

    @Test
    public void testExtractColumnNull() {
        assertEquals(
                null ,
                TweetParser.extractColumn(
                        "This, that , this, that.", 4
                )
        );
    }

    @Test
    public void testExtractEmptyString() {
        assertEquals(null, TweetParser.extractColumn(" ", 1));

    }

    /* **** ****** ***** ***** CSV DATA TO TWEETS ***** **** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testCsvDataToTweetsSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 1);
        List<String> expected = new LinkedList<String>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsFourthColumnWithDuplicates() {
        StringReader sr = new StringReader(
            "There, were, dinosaurs, before, there, were, humans.\n" +
                    "This, comes, from, data, with, duplicate, words."
                    );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 4);
        List<String> expected = new LinkedList<String>();
        expected.add(" there");
        expected.add(" with");
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsSkipLines() {
        StringReader sr = new StringReader(
                "There, were, dinosaurs, before, there, were, humans, ok.\n" +
                        "This, comes, from, data, with, duplicate, words."
                         );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 7);
        List<String> expected = new LinkedList<String>();
        expected.add(" ok.");
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsSkipMultipleLines() {
        StringReader sr = new StringReader(
                "There, were, dinosaurs, before, there, were, humans, ok.\n" +
                        "This, comes, from, data, with, duplicate, words, to.\n" +
                        "This, is, great."
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 7);
        List<String> expected = new LinkedList<String>();
        expected.add(" ok.");
        expected.add(" to.");
        assertEquals(expected, tweets);
    }


    /* **** ****** ***** ** PARSE AND CLEAN SENTENCE ** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceCapitalSameString() {
        List<String> sentence = TweetParser.parseAndCleanSentence("ABC");
        List<String> expected = new LinkedList<String>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    /* **** ****** ***** **** PARSE AND CLEAN TWEET *** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweetRemovesSymbols() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc ##$");
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    /* **** ****** ***** ** CSV DATA TO TRAINING DATA ** ***** ****** **** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testCsvDataToTrainingDataSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTrainingDataWithSpecialSymbols() {
        StringReader sr = new StringReader(
                "There, % # were dinosaurs before there were humans.\n" +
                        "This, $ comes from data with duplicate words."
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<List<String>>();
        expected.add(listOfArray("were dinosaurs before there were humans".split(" ")));
        expected.add(listOfArray("comes from data with duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

}
