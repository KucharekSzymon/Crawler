import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Create object that download and crawl on whole domain
 */
public class Downloader extends Thread {
    /**
     * @param unique
     */
    public static Set<String> uniqueURL = new HashSet<String>();
    public static String my_site;
    public String allLinks;
    public int found =0;

    public void get_links(String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Safari").get();
            Elements links = doc.select("a");

            if (links.isEmpty()) return;

            links.stream().map(
                    (link) -> link.attr("abs:href")).forEachOrdered((this_url) ->
                        {
                            boolean add = uniqueURL.add(this_url);
                            if (add && this_url.contains(my_site)) {
                                System.out.println(this_url);
                                allLinks+=this_url+"\n";
                                get_links(this_url);
                                found++;
                            }
                        });

        } catch (IOException ex) {}
        catch (StackOverflowError err){ return; }
    }

}