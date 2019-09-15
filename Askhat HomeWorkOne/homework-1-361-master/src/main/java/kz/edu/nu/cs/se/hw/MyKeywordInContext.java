package kz.edu.nu.cs.se.hw;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyKeywordInContext implements KeywordInContext {
    private String name;
    private String pathstring;
    private PriorityQueue<ImplIndexable> priorityQueue;
    private HashSet<String> ignoreWords = new HashSet<>();

    public MyKeywordInContext(String name, String pathstring) {
        this.name = name;
        this.pathstring = pathstring;
        priorityQueue = new PriorityQueue<>(Comparator.comparing(ImplIndexable::getEntry));
        ignoreWords.addAll(Arrays.asList("the", "The", "a", "A", "an", "An", "on", "On", "and", "And", "was", "were", "Was", "Were", "of", "Of", "is",
                "Is", "as", "As", "in", "In", "at", "At", "to", "To"));
    }

    @Override
    public int find(String word) {
        int result = -1;
        ImplIndexable[] array = priorityQueue.toArray(new ImplIndexable[priorityQueue.size()]);
        for (int i = 0; i < array.length; i++) {
            if (array[i].getEntry().equals(word.toUpperCase())) {
                if (result == -1) {
                    result = i;
                } else {
                    if (result > array[i].getLineNumber()) {
                        result = i;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Indexable get(int i) {
        ImplIndexable[] array = priorityQueue.toArray(new ImplIndexable[priorityQueue.size()]);
        return array[i];
    }

    @Override
    public void txt2html() {
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        try {
            File file = new File(pathstring);
            bufferedWriter = new BufferedWriter(new FileWriter(name + "Example" + ".html"));
            bufferedReader = new BufferedReader(new FileReader(file));
            bufferedWriter.write("<!DOCTYPE html>");
            bufferedWriter.newLine();
            bufferedWriter.write("<html><head><meta charset=\"UTF-8\"></head><body>");
            bufferedWriter.newLine();
            bufferedWriter.write("<div>");
            bufferedWriter.newLine();
            String line;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                indexLines(line, i);
                bufferedWriter.write(line);
                bufferedWriter.write("<span id=\"line_");
                bufferedWriter.write(Integer.toString(i));
                bufferedWriter.write("\">&nbsp&nbsp[");
                bufferedWriter.write(Integer.toString(i));
                bufferedWriter.write("]</span><br>");
                bufferedWriter.newLine();
                i++;
            }
            bufferedWriter.write("</div></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void indexLines(String line, int lineNumber) {
        String[] words = line.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        String lineBefore = "";
        for (int i = 0; i < words.length; i++) {
            if (!ignoreWords.contains(words[i])) {
                String currentWord = words[i];
                for (int j = 0; j < words.length; j++) {
                    if (i == j) {
                        lineBefore = stringBuilder.toString();
                        stringBuilder.setLength(0);
                    } else {
                        stringBuilder.append(words[j]);
                        stringBuilder.append(" ");

                    }
                }
                if(!helper2(currentWord).toUpperCase().isBlank() && !helper2(currentWord).toUpperCase().isEmpty()){
                    priorityQueue.add(new ImplIndexable(lineBefore, stringBuilder.toString(), helper2(currentWord).toUpperCase(), lineNumber));
                }
                stringBuilder.setLength(0);
            }
        }
    }

    private String helper2(String str) {
        Pattern checkRegex = Pattern.compile("[a-zA-Z]*", Pattern.DOTALL);
        Matcher regexMatcher = checkRegex.matcher(str);
        regexMatcher.find();
        return regexMatcher.group();
    }

    @Override
    public void writeIndexToFile() {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("kwic-" + name + ".html"));
            bufferedWriter.write("<!DOCTYPE html>");
            bufferedWriter.newLine();
            bufferedWriter.write("<html><head><meta charset=\"UTF-8\"></head><body><div style=\"text-align:center;line-height:1.6\">");
            bufferedWriter.newLine();
            PriorityQueue<ImplIndexable> copy = new PriorityQueue<>(priorityQueue);
            while (copy.size() != 0) {
                ImplIndexable temp = copy.remove();
                bufferedWriter.write(temp.getLineBefore());
                bufferedWriter.write("<a href=\"");
                bufferedWriter.write(name + "Example" + ".html");
                bufferedWriter.write("#line_");
                bufferedWriter.write(Integer.toString(temp.getLineNumber()));
                bufferedWriter.write("\">");
                bufferedWriter.write(temp.getEntry());
                bufferedWriter.write("</a> ");
                bufferedWriter.write(temp.getLineAfter());
                bufferedWriter.write("<br>");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("</div></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
