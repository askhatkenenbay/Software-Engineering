package kz.edu.nu.cs.se;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyToken {
    private final List<Object> tokenList = new ArrayList<>();
    private int currentIndex;

    public MyToken(String str) {
        StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(str));
        try {
            int currentToken = streamTokenizer.nextToken();
            while (currentToken != StreamTokenizer.TT_EOF) {
                if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) {
                    tokenList.add(streamTokenizer.sval);
                } else if (streamTokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                    tokenList.add(streamTokenizer.nval);
                } else {
                    tokenList.add((char) currentToken);
                }
                currentToken = streamTokenizer.nextToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> getTokenList() {
        return new LinkedList<>(tokenList);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void consume() {
        currentIndex++;
    }

    public boolean isEnd() {
        return (tokenList.size() == currentIndex);
    }

    public Object currentObject() {
        if (currentIndex >= tokenList.size()) {
            return null;
        }
        return tokenList.get(currentIndex);
    }

    public boolean terminal(Object obj) {
        if (obj != null && obj.equals(currentObject())) {
            currentIndex++;
            return true;
        }
        return false;
    }

}
