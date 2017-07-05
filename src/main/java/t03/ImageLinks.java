package t03;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Aleksandr Shevkunenko on 04.07.2017.
 */
public class ImageLinks {

    private static String readFile(String filename, Charset cs) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), cs);
    }

    private static Charset getHtmlEncodingCharset(String filename) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String filename = "src\\main\\resources\\Handling_task_attachment.html";

        Charset charset = getHtmlEncodingCharset(filename);

        String text = null;
        try {
            text = readFile(filename, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(text);
    }
}
