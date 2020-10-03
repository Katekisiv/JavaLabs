import java.io.*;

public class LengthCounter {
    String delimiter;
    String combiner;

    public LengthCounter(String delimiter, String combiner) {
        this.delimiter = delimiter;
        this.combiner = combiner;
    }

    public void countFromFile(String path1, String path2) throws IOException {
        BufferedReader file1 = new BufferedReader(new FileReader(path1));
        BufferedWriter file2 = new BufferedWriter(new FileWriter(path2));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = file1.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        String text = this.deleteComment(sb.toString());
        for (String tLine : text.split("\n")) {
            if (tLine.length() != 0) {
                file2.write(count(tLine) + "\n");
            }
        }

        file1.close();
        file2.close();
    }

    public String count(String s) {
        StringBuilder line = new StringBuilder();

        int delimiterIndex = findDelimiter(s);
        if (delimiterIndex == -1) {
            if(isInsideQuotes(s)){
                line.append(s.length() - 2);
            } else {
                line.append(s.length());
            }
        } else  {
            if (isInsideQuotes(s.substring(0, delimiterIndex))) {
                line.append(findDelimiter(s) - 2);
            } else {
                line.append(findDelimiter(s));
            }

            line.append(this.combiner);
            line.append(count(s.substring(delimiterIndex + 1)));
        }

        return line.toString();
    }

    private int findDelimiter(String s) {
        int firstDelimiter = s.indexOf(this.delimiter);
        if (firstDelimiter == -1) {
            return -1;
        }

        while (true) {
            int indexQuote = findClosingQuote(s);

            if(indexQuote == -1 || firstDelimiter == -1) {
                return firstDelimiter;
            }

            if(indexQuote == firstDelimiter - 1) {
                return firstDelimiter;
            } else if(indexQuote > firstDelimiter) {
                firstDelimiter = s.indexOf(this.delimiter, indexQuote);
            } else if(indexQuote < firstDelimiter) {
                if (s.substring(0, firstDelimiter).chars().filter(ch -> ch == '"').count() % 2 != 0) {
                    int prev = firstDelimiter;
                    firstDelimiter = s.indexOf(this.delimiter, prev+1);
                } else {
                    //if the amount of quotes is pair - they are closed, so delimiter isn't inside
                    return firstDelimiter;
                }
            }
        }
    }

    private String deleteComment(String text) {
        StringBuilder result = new StringBuilder();

        int firstComment = text.indexOf("/*");
        int secondComment = text.indexOf("*/", firstComment);
        if(firstComment == -1) {
            return text;
        }

        if (firstComment != 0) {
            result.append(text.substring(0, firstComment));
            result.append(text.substring(secondComment + 2));
        } else {
            result.append(text.substring(secondComment + 2));
        }

        return result.toString();
    }


    private int findClosingQuote(String s) {
        if (s.charAt(0) == '"') {
            return s.indexOf('"', 1);
        } else {
            return -1;
        }
    }

    private boolean isInsideQuotes(String s) {
        return s.length() != 0 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"';
    }
}
