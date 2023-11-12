import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Task3 {
    public static void main(String[] args) {
        String text = readFromFile("task3.txt");
        //String text = "    the    day    is sunny the the\n\n"+"the sunny is is";
        List<Word> listWord = new ArrayList<>();
        String oneSpace = " ";
        int lengthWTextBefore;
        // видаляемо слова довжиною менше 2х символів та деяку пунктуацію
        text = text.trim().toLowerCase().replaceAll("\\b\\w{1,1}\\b|[,'′!:]", "");
        // заміна декількох сполучень невидимих символів та множину пробілів на один пробіл
        text = text.replaceAll("(\\r|\\n|\\r\\n)+", oneSpace).replaceAll("\\s+",oneSpace);
        System.out.println(text);
        int posSpace;
        do {
            posSpace = text.indexOf(oneSpace);
            if (posSpace > -1) {
                String nextWord = text.substring(0, posSpace);
                // шукаемо кількість входжень цілого слова де "\b" початок та кінець слова
                Pattern pattern = Pattern.compile(".*?\\b"+nextWord+"\\b.*?");
                Matcher matcher = pattern.matcher(text);
                int counter = 0;
                while (matcher.find()) {
                    counter++;
                }
                // створюемо запис пошукового слова та його кількість входжень
                if (counter==0) counter = 1;
                listWord.add(new Word(nextWord, counter));
                lengthWTextBefore = text.length();
                // видаляемо пошукове слово та утворенні після видалення зайві пробіли
                text = text.trim().replaceAll("\\b"+nextWord+"\\b", "");
                // відаляемо зациклювання в разі неспроможності попереднього коду знайти та видалити слово
                if (lengthWTextBefore == text.length()) {
                    text = text.substring(posSpace);
                }
                text = text.trim().replaceAll("\\s{2,}", oneSpace);
            }
        } while (posSpace >-1);
        // сортуемо список слів в зворотньому порядку
        Collections.sort(listWord, new WordComparator());
        for (Word w: listWord) {
            System.out.println(w.toString());
        }
    }
    public static String readFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            if (fileName.length()==0) { fileName = "NoName"; }
            return "The file by name " + fileName + " does not exist, so the result is returned by the expression specified in the Task3 condition.:\n" +
                   "the day is sunny the the\n" +
                   "the sunny is is";
        }
        StringBuilder result = new StringBuilder("");
        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
                result.append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}

class Word {
    private String value;
    private int count;
    public Word(String value, int count) {
        this.count = count;
        this.value = value;
    }
    @Override
    public String toString() { return value + " " + count; }
    public int getCount() { return count; }
}

class WordComparator implements java.util.Comparator<Word> {
    @Override
    public int compare(Word a, Word b) {
        return -(a.getCount() - b.getCount());
    }
}
