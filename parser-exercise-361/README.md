# 361 Fall 2019, Recursive Descent Parser Exercise

This is the starter project for the recursive descent parser assignment.  
You will have time during lab to complete this exercise.  

We discussed the concept of a recursive descent parser during lecture.  
Please refer to the lecture slides for more detail.  Briefly, recursive descent allows a programmer to translate a description of a formal language into a program for recognizing valid statements in the language.  In this context, the formal language is typically expressed as a set of production rules.  In recursive descent, the productions become a set of mutually recursive functions.  

For this assignment, you have to create a recursive descent parser for a language where "programs" consist of a list of statements.  The statements can either be values or nested lists of values.  Statements end with a semicolon.  A value is an a number, an identifier or an array.  And an array is a nested list of values.  Also, ignore extra whitespace.  

The file in this project `sampledefinition.txt` gives a valid program in this language with many examples of statements.  

#### Tasks for the Recursive Descent Problem
- Clone the starter project and review the language sample
- Define an appropriate set of production rules that capture the language
- Complete the `MyLanguageParser` class by implementing a recursive descent parser as a set of methods, make sure that all unit tests pass
- Create a method in the `MyLanguageParser` class called `getHTML` that will output an HTML file corresponding to the parsed text.  The HTML should highlight identifiers based on nesting and also italicize identifiers

#### Hints
- The [`StreamTokenizer`](https://docs.oracle.com/javase/7/docs/api/java/io/StreamTokenizer.html) class can be used to split a string or a file into a sequence of tokens
