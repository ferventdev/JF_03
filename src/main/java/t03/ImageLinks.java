package t03;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksandr Shevkunenko on 04.07.2017.
 */
public class ImageLinks {

    private static String readFile(String filename, Charset cs) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), cs);
    }

    private static Charset getHtmlEncodingCharset(String filename) {
        try (InputStream in = Files.newInputStream(Paths.get(filename));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String regexp = "<meta\\s+[^<>]*(content=['\"][^<>'\"]*charset=([a-zA-Z_0-9\\-]+)['\"])[^<>]*>";
            Pattern p = Pattern.compile(regexp);
            String line = null;
            while ((line = reader.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.find()) return Charset.forName(m.group(2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Charset.defaultCharset();
    }

    private static List<Integer> getAllImageReferences(String text) {
        List<Integer> refs = new ArrayList<>();
        String regexp = "<img\\s+[^<>]*src=\"(([^\"]+)/([^/\\d\"\\.]+)(\\d+)\\.(jpg|png|bmp|gif))\"[^<>]*>";
        Matcher m = Pattern.compile(regexp).matcher(text);
        while (m.find()) {
            try {
                refs.add(Integer.parseUnsignedInt(m.group(4)));
            } catch(NumberFormatException e) {
                e.printStackTrace();
                break;
            }
        }
        return refs;
    }

    private static boolean checkImageRefsAreConsequent(List<Integer> numbers) {
        for (int i = 0, n = numbers.size() - 1; i < n; i++) {
            if (numbers.get(i + 1) - numbers.get(i) != 1) return false;
        }
        return true;
    }

    private static List<String> getAllSentencesWithImageRefs(String text) {
        List<String> refs = new ArrayList<>();
        String regexp = "((<div>\\s*)(<p>\\s*)|([!\\.\\?]\\s+))([A-ZА-Я][^\\.]*(([Рр]?ис.)|([Рр]?исун[а-я]{2,3}))\\s*\\d+[^!\\.\\?]*[!\\.\\?])\\s*((</p>)|(</div>)|(</span>)|([A-ZА-Я]))";
        Matcher m = Pattern.compile(regexp).matcher(text);
        while (m.find()) {
            try {
                refs.add(m.group(5));
            } catch(NumberFormatException e) {
                e.printStackTrace();
                break;
            }
        }
        return refs;
    }

    public static void main(String[] args) {
        String filename = "src\\main\\resources\\Handling_task_attachment.html";

        Charset charset = getHtmlEncodingCharset(filename);

        String text = null;
        try {
            text = readFile(filename, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String message = checkImageRefsAreConsequent(getAllImageReferences(text)) ?
            "The author reference images consequently." :
            "The author does not reference images consequently.";

        System.out.println(message + "\n");

        System.out.println("Here are sentences with image references:");
        List<String> lines = getAllSentencesWithImageRefs(text);

        String regex = ".*(([Рр]и[с].)|([Рр]исун[а-я]+))\\s*\\d+.*";
        Pattern p = Pattern.compile(regex);
        for (String line : lines) {
            System.out.println(line);
            if (!p.matcher(line).find()) {
                System.out.println("This line doesn't contain an image reference:");
                System.out.println(line);
                break;
            }
            System.out.println("--------------------------------------------------------------------------");
        }
    }
}
