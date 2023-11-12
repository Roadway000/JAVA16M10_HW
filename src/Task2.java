import java.io.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Task2 {
    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        String fileName = "task2.txt";
        String txtUsers = "name age isMarried\n" +
                          "alice 21 false\n" +
                          "ryan 30 true";

        /* записуемо текст у файл */
        writeExampleToFile(txtUsers, fileName);
        /* зчитуемо послідовно кожен рядок з текстового файлу як запис об'екту класа User в масив users */
        readFromTxtFile(fileName);

        int usersSize = users.size();
        int counter = 0;
        Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[\n");
        for (User u: users) {
            strBuilder.append(gson1.toJson(u));
            counter++;
            if (usersSize != counter)
                strBuilder.append(",\n");
        }
        strBuilder.append("\n]");
        System.out.println(strBuilder);
        writeExampleToFile(strBuilder.toString(), "user.json");
    }
    public static void writeExampleToFile(String data, String fileName) {
        try(FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(data);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readFromTxtFile(String fileName) {
        File file = new File(fileName);
        int row = 0;
        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                WriteToData(line, row);
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void WriteToData(String line, int rowNumber) throws IOException {
        if (rowNumber == 0) return;
        String[] stringData = line.split(" ");
        User newUser = new User(stringData[0],Integer.valueOf(stringData[1]),Boolean.valueOf(stringData[2]));
        users.add(newUser);
    }

}

class User {
    private String name;
    private int age;
    private boolean isMarried;
    private static int fieldCount = 3;
    public User(String name, int age, boolean isMarried) {
        this.name = name;
        this.age = age;
        this.isMarried = isMarried;
    }

    public User() {}
    public int getFieldCount() {
        return fieldCount;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public boolean isMarried() {
        return isMarried;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setMarried(boolean married) {
        isMarried = married;
    }
}
