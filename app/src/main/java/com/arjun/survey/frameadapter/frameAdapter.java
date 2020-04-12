package com.arjun.survey.frameadapter;



public class frameAdapter {


    public static String frameid;
    public static String answersid;
    public static String doubtsid;
    public static String succ;



    public static String conversion_to_some_variables_indexes(String ss) {

        String letters = ss;
        String s = "";
        for (int index = 0; index < letters.length(); index += 9) {
            String temp = letters.substring(index, index + 8);
            int num = Integer.parseInt(temp, 2);
            char letter = (char) num;
            s = s + letter;
        }
        return s;

    }



}

