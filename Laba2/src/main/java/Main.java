import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path to the input file:");
        String path1 = scanner.nextLine();

        System.out.println("Enter the path to the output file:");
        String path2 = scanner.nextLine();

        System.out.println("Enter the delimiter:");
        String delimiter = scanner.nextLine();

        System.out.println("Enter the combiner:");
        String combiner = scanner.nextLine();

        LengthCounter lengthCounter = new LengthCounter(delimiter, combiner);

        lengthCounter.countFromFile(path1, path2);

    }
}
