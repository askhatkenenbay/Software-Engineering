package kz.edu.nu.cs.se;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class MyLanguageParser {
    private MyToken myToken;
    private static final Character SEMICOLON = ';';
    private static final Character OPEN_BRACKET = '(';
    private static final Character CLOSE_BRACKET = ')';
    private static final Character COMMA = ',';
    private static final String FILE_NAME = "result.html";
    private static final String HTML_HEADER = "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head><body><div>";
    private static final String HTML_FOOTER = "<br></div></body></html>";
    private static final String ITALIC_OPEN = "<i>";
    private static final String ITALIC_CLOSE = "</i>";
    private static final String NEWLINE_BREAK = "\n<br>";
    private static final String SPAN_OPEN_1 = "<span class = 'lv";
    private static final String SPAN_OPEN_2 = "'>";
    private static final String SPAN_CLOSE = "</span>";

    public MyLanguageParser(String s) {
        myToken = new MyToken(s);
    }

    //Program -> Part*
    //Part -> Statement;
    //Statement -> [0-9]* | [a-zA-Z]* | Nested
    //Nested -> ( [Statement[,Statement]*] | empty )

    public boolean program() {
        if (part()) {
            while (part()) {
            }
            return myToken.isEnd();
        } else {
            return false;
        }
    }

    private boolean part() {
        int temp = myToken.getCurrentIndex();
        if (statement() && myToken.terminal(SEMICOLON)) {
            return true;
        }
        myToken.setCurrentIndex(temp);
        return false;
    }

    private boolean statement() {
        boolean isValue = myToken.currentObject() instanceof Number || myToken.currentObject() instanceof String;
        if (isValue) {
            myToken.consume();
            return true;
        }
        return nested();
    }

    private boolean nested() {
        int point = myToken.getCurrentIndex();
        if (myToken.terminal(OPEN_BRACKET)) {
            if (statement()) {
                while (commaStatement()) {
                }
            }
            if (myToken.terminal(CLOSE_BRACKET)) {
                return true;
            }
        }
        myToken.setCurrentIndex(point);
        return false;
    }

    private boolean commaStatement() {
        int point = myToken.getCurrentIndex();
        if (myToken.terminal(COMMA) && statement()) {
            return true;
        }
        myToken.setCurrentIndex(point);
        return false;
    }

    public void getHTML() {
        if (myToken == null || !program()) {
            throw new IllegalStateException();
        }
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            printWriter.println(HTML_HEADER);
            List<Object> myList = myToken.getTokenList();
            int levelInteger = -1;
            for (int i = 0; i < myList.size(); i++) {
                if (myList.get(i) instanceof String) {
                    printWriter.print(ITALIC_OPEN + myList.get(i) + ITALIC_CLOSE);
                } else if (myList.get(i).equals(OPEN_BRACKET)) {
                    levelInteger++;
                    printWriter.print(myList.get(i) + SPAN_OPEN_1 + levelInteger + SPAN_OPEN_2);
                } else if (myList.get(i).equals(CLOSE_BRACKET)) {
                    levelInteger--;
                    printWriter.print(SPAN_CLOSE + myList.get(i));
                } else if (myList.get(i).equals(SEMICOLON)) {
                    printWriter.print(myList.get(i) + NEWLINE_BREAK);
                } else {
                    printWriter.print(myList.get(i));
                }
            }
            printWriter.println(HTML_FOOTER);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyLanguageParser temp = new MyLanguageParser("a;b;c;d;e;\n" +
                "();\n" +
                "(());\n" +
                "((()));\n" +
                "((),(),());\n" +
                "(a,b,c,d,e,f,(g,h,i,j,(k,l,(m,n),o,p),q,r,s,t,u,v),w,x,y,z);\n" +
                "(a,b);\n" +
                "(a,b,(u,v));\n" +
                "(alice,(bob,(claire,dennis)));\n" +
                "(((alice,bob),claire),dennis);\n" +
                "((alice,2.0),(),(),(())); \n" +
                "((a,1),(b,2),(c,3),(d,4));\n" +
                "Alice;\n" +
                "Bob;\n" +
                "Claire;");
        temp.getHTML();
    }
}



