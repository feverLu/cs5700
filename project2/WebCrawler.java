import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebCrawler {
    public String website;
    private Socket socket;
    private Set<String> visitedPages = new HashSet<String>();
    private Queue<String> toBeVisitedPages = new LinkedList<String>();
    private Set<String> secretFlags = new HashSet<String>();
    // Use a PrintWriter to write to Server.
    private PrintWriter out;
    // Use a BufferedReader to get the response from Server.
    private BufferedReader in;

    private static final String COOKIE_PATTERN = "Set-Cookie: csrftoken=(\\w{32}+)";
    private static final String SESSIONID_PATTERN = "Set-Cookie: sessionid=(\\w{32}+)";
    private static final String A_HREF_PATTERN = "<a\\s.*?href=\"(/fakebook[^\"]+)\"[^>]*>(.*?)</a>";
    private static final String SECRET_FLAG_PATTERN = "<h2 class=\'secret_flag\' style=\"color:red\">FLAG: (\\w{64}+)</h2>";

    private String headerCookie;

    public WebCrawler(String website) {
        this.website = website;
        try {
            this.socket = new Socket(InetAddress.getByName(website), 80);
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e){
            System.out.println("The host is not known!");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // public void start(){
        // String responseHTML = goToRootPage();
        
        // toBeVisitedPages.add(getLinks(responseHTML));
        // while (toBeVisitedPages.size()){
        //     String nextPage = toBeVisitedPages.pop();
        //     if (!visitedPages.contains(nextPage)){
        //         responseHTML = visitPage(nextPage);
        //         toBeVisitedPages.add(getLinks(responseHTML));
        //     }
        // }
    // }
    // 
    public void start(String path){
        toBeVisitedPages.add(path);
        
        while (!toBeVisitedPages.isEmpty()){
            String visiting = toBeVisitedPages.poll();
            // System.out.println(visiting);
            request(website, visiting, headerCookie, null);
            visitedPages.add(visiting);
            String response = read();
            System.out.println("The page is: " + visiting + "\n" + response + "\n");
            List<String> flags = matchPattern(response, SECRET_FLAG_PATTERN);
            for (String flag: flags){
               System.out.println(flag);
               secretFlags.add(flag);
            }
            List<String> hrefs = matchPattern(response, A_HREF_PATTERN);
            for (String href : hrefs){
                if (!visitedPages.contains(href)){
                    toBeVisitedPages.add(href);
                    // System.out.println("To be visitied: " + href);
                }
            }
            System.out.println("To be visited queue size is: " + toBeVisitedPages.size());
        }
    }

    public String getCookie(){
        return headerCookie;
    }

    /**
     * The request is an abstract of Post and Get method. If data
     * is not null, use Post method, otherwise the Get method.
     * @param host The host of the website
     * @param path The path of the request
     * @param data the request body
     */
    public void request(String host, String path, String cookie, String data){
        // Use the Get method if data is null or empty.
        if (data == null || data.equals("")){
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: keep-alive");
            if (cookie != null){
                out.println("Cookie: " + cookie);
            }
            out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
            out.println("");
            out.flush();
        } else {
            out.println("POST " + path + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: keep-alive");
            out.println("Cookie: " + cookie);
            out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
            out.println("Content-Length: " + data.length());
            out.println("Content-Type: application/x-www-form-urlencoded; charset=utf-8");
            out.println("");
            out.println(data);
            out.flush();
        }

    }

    public void login(String path, String username, String password){
        // Initial GET request to /accounts/login/.
        request(website, path, null, null);
        String response = read();
        
        // POST request to /accounts/login/ to log in for cookie and sessionid.
        String cookie = matchPattern(response, COOKIE_PATTERN).get(0);
        String sessionid = matchPattern(response, SESSIONID_PATTERN).get(0);
        String data = "username=" + username + "&password=" + password + "&csrfmiddlewaretoken=" + cookie;
        String headerCookie = "csrftoken=" + cookie + "; sessionid=" + sessionid;
        request(website, path, headerCookie, data);
        response = read();
        
        // The server will return a new session_id if login successful.
        sessionid = matchPattern(response, SESSIONID_PATTERN).get(0);

        // This header cookie should be used
        this.headerCookie = "csrftoken=" + cookie + "; sessionid=" + sessionid;
    }

    public List<String> matchPattern(String response, String pattern){
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(response);
        List<String> results = new ArrayList<String>();
        while (m.find()) {
            String t = m.group(1);
            results.add(t);
        }
        return results;
    }

    public String read(){
        String t;
        StringBuilder sb = new StringBuilder();
        try {
            // The server would hang 5 seconds before closing the socket. To skip this, we 
            // add a in.ready() to check if server has more things to send.
            while (true){
                t = in.readLine();
                // System.out.println(t);
                sb.append(t);
                if (!in.ready())
                    break;
            }
        } catch (IOException e){
            System.out.println(e);
            System.out.println("There is an error in reading the response from server.");
        }
        return sb.toString();
    }

    public void finish() throws IOException{
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args)  throws IOException{
        if (args.length != 4) {
            System.err.println("Usage: java WebCrawler <host name> <path> <username> <passsword>");
            System.exit(1);
        }
        String website = args[0];
        String path = args[1];
        String username = args[2];
        String password = args[3];

        

        WebCrawler crawler = new WebCrawler(website);

        crawler.login("/accounts/login/", username, password);

        crawler.start(path);
        
        crawler.finish();

    }


}
