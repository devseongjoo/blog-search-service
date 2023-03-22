package com.blogsearch.search.util;

public class Tokenizer {
    public static String getKeywordToken(String query) {
        try {
            String[] tokens = query.split(" ");
            return tokens[tokens.length - 1];
        } catch(NullPointerException e) {
            e.printStackTrace();
            //TODO
            return "";
        } catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            //TODO
            return "";
        }
    }
}
