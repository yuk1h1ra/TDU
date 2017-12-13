import java.io.*;
import java.util.*;

abstract class Token implements Comparable<Token> {
    private String string;
    protected int kind;

    public static final int START = 0;
    public static final int SUM = 1;
    public static final int SUB = 2;
    public static final int MUL = 3;
    public static final int DIV = 4;
    public static final int LPAREN = 5;
    public static final int RPAREN = 6;
    public static final int OPERAND = 7;
    public static final int END = 8;

    private static final int order[][] = {
        // 始  +  -  *  /  (  ) 識別子 終 <-右  左
        {   9, 1, 1, 1, 1, 1, 9, 1,     0 }, // 始
        {   9,-1,-1, 1, 1, 1,-1, 1,    -1 }, // +
        {   9,-1,-1, 1, 1, 1,-1, 1,    -1 }, // -
        {   9,-1,-1,-1,-1, 1,-1, 1,    -1 }, // *
        {   9,-1,-1,-1,-1, 1,-1, 1,    -1 }, // /
        {   9, 1, 1, 1, 1, 1, 0, 1,     9 }, // (
        {   9, 9, 9, 9, 9, 9, 9, 9,     9 }, // )
        {   9,-1,-1,-1,-1, 9,-1, 9,    -1 }, // 識別子
        {   9, 9, 9, 9, 9, 9, 9, 9,     9 }, // 終
    };

    public Token(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }

    public int getKind() {
        return kind;
    }

    public int compareTo(Token anotherToken) {
        return order[this.kind][anotherToken.getKind()];
    }
}

class Calcurator {
    public Calcurator() {
        while(true) {
            System.out.println("何をしますか？(Ctrl-dで終了)");
            System.out.println("1: 関数を定義する");
            System.out.println("2: 式の値を求める");
            System.out.println("0: 終了");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            try {
                line = reader.readLine();
            }
            catch(IOException e) {
                break;
            }
            if(line == null) {
                break;
            }

            if(line.equals("1")) {
                
            }
            else if(line.equals("2")) {
                
            }
            else if(line.equals("0")) {
                System.exit(0);
            }
            else {
                System.out.println("1または2を代入してください");
            }
        }
    }
}

class MyCalcurator {
    public static void main(String[] args) {
        Calcurator calcurator = new Calcurator();
    }
}
