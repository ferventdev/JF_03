package t03;

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

    private static Charset getHtmlEncodingCharset(String filename) throws IOException {
        Files.lines(Paths.get(filename), Charset.defaultCharset());
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
