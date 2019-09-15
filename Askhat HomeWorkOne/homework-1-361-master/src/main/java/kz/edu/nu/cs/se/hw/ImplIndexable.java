package kz.edu.nu.cs.se.hw;

public class ImplIndexable implements Indexable {
    private String lineBefore;
    private String lineAfter;
    private String word;
    private int lineNumber;

    ImplIndexable(String lineBefore, String lineAfter, String word, int lineNumber) {
        this.lineAfter = lineAfter;
        this.lineBefore = lineBefore;
        this.word = word;
        this.lineNumber = lineNumber;
    }

    @Override
    public String getEntry() {
        return word;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getLineBefore() {
        return lineBefore;
    }

    @Override
    public String getLineAfter() {
        return lineAfter;
    }

    public String toString() {
        return lineBefore + " " + word + " " + lineAfter;
    }
}
