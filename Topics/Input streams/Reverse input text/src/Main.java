import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String inputString = reader.readLine();

        for (int i = inputString.length() - 1; i >= 0; i--) {
            char c = inputString.charAt(i);
            System.out.print(c);
        }

        reader.close();
    }
}