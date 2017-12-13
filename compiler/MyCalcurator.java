import java.io.*;
import java.util.*;

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
