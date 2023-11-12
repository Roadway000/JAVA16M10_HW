import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Task1 {
    public static void main(String[] args) {
        String fileName = "task1.txt";
        String phones = "987-123-4567\n" +
                        "123 456 7890\n" +
                        "(123) 456-7890";
        /* записуемо номері телефонів у файл */
        writeExampleToFile(phones, fileName);
        /* зчитуемо послідовно номери телефонів з файлу з перевіркою за критеріем */
        readFromFile(fileName);

    }
    public static void writeExampleToFile(String data, String fileName) {
        try(FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(data);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readFromFile(String fileName) {
        File file = new File(fileName);
        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isPhoneCorrect(line))
                    System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isPhoneCorrect(String oneLine) {
        /* критерії перевірки номерів телефонів */
        String[] regexRules = {"[0-9]{3}-[0-9]{3}-[0-9]{4}",        /* кожен рядок мае три сегменти*/
                             "\\([0-9]{3}\\)\s[0-9]{3}-[0-9]{4}"};
        boolean result = false;
        for (int i=0; i < regexRules.length; i++) {
            Pattern pattern = Pattern.compile(regexRules[i]);
            Matcher matcher = pattern.matcher(oneLine);
            if (matcher.find()==true) {
                result = true;
            }
        }
        return result;
    }
}