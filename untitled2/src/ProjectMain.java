import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



class ProjectMain {

    private static String[] choose = {"exit", "page", "pagesize", "fromdate","todate", "order", "min", "max", "sort", "tagged", "nottagged", "intitle"};
    private static String[] choose_order ={"desc", "asc"};
    private static String[] choose_sort ={"activity", "votes", "creation", "relevance"};

    public static void SetAll(String[] array){
        int k = 0;
        for(String item:array){
            System.out.println(k + ") " + item);
            k++;
        }
    }

    public static InputStream getDecodeInputStream(
            HttpResponse<InputStream> httpResponse){
        String encoding = determineContentEncoding(httpResponse);
        try{
            switch (encoding){
                case " ":
                    return httpResponse.body();
                case "gzip":
                    return new GZIPInputStream(httpResponse.body());
                default:
                    throw new UnsupportedOperationException("Unexpected Content-Encoding: " + encoding);
            }
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    public static String determineContentEncoding(
            HttpResponse<?> httpResponse){
        return httpResponse.headers().firstValue("Content-Encoding").orElse(" ");
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" +
                "Enter the operation number ,if you want to finish press 0");
        SetAll(choose);

        CreateURL url = new CreateURL();
        int number = -1;
        while(number!=0){

            number = scanner.nextInt();
            switch (number){
                case 1:
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 2:
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 3:
                    System.out.println("Enter day month and year");
                    CreateDateURL dateObject = new CreateDateURL(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    dateObject.Create();
                    String dateUrl = Long.toString(dateObject.getUrlDate());
                    url.setUrl(choose[number], dateUrl);
                    break;

                case 4:
                    System.out.println("Enter day month and year");
                    CreateDateURL dateObj = new CreateDateURL(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    dateObj.Create();
                    String dtUrl = Long.toString(dateObj.getUrlDate());
                    url.setUrl(choose[number], dtUrl);
                    break;

                case 5:
                    System.out.println("Print the operation");
                    SetAll(choose_order);
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 6:
                    System.out.println("Enter day month and year");
                    CreateDateURL dateObjMin = new CreateDateURL(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    dateObjMin.Create();
                    String dtUrlMin = Long.toString(dateObjMin.getUrlDate());
                    url.setUrl(choose[number], dtUrlMin);

                case 7:
                    System.out.println("Enter day month and year");
                    CreateDateURL dateObjMax = new CreateDateURL(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    dateObjMax.Create();
                    String dtUrlMax = Long.toString(dateObjMax.getUrlDate());
                    url.setUrl(choose[number], dtUrlMax);

                case 8:
                    System.out.println("Print the operation");
                    SetAll(choose_sort);
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 9:
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 10:
                    url.setUrl(choose[number], scanner.next());
                    break;

                case 11:
                    System.out.println("Print the operation ,if you want to select *beautiful* print b");
                    if(scanner.next().equals("b")){
                        url.setUrl(choose[number], "beautiful");
                    }
                    else
                        url.setUrl(choose[number], scanner.next());
                    break;

                default:
                    url.setUrl(choose[number]);
            }
        }



        System.out.println(url.toString());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url.getUrl()))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        var stream = getDecodeInputStream(response);
        var reader = new InputStreamReader(stream);
        var in = new BufferedReader(reader);

        String jsonResult = in.readLine();
        Object obj = new JSONParser().parse(jsonResult);
        JSONObject jo = (JSONObject) obj;

        JSONArray UsersArr = (JSONArray) jo.get("items");
        Iterator phonesItr = UsersArr.iterator();
        System.out.println("check the document");

        //parsing json
        try(FileWriter writer = new FileWriter("text.txt", false))
        {
            while (phonesItr.hasNext()) {
                JSONObject test = (JSONObject) phonesItr.next();
                String owner = test.get("owner").toString();
                String title = test.get("title").toString();
                String is_answered = test.get("is_answered").toString();
                Object ob = new JSONParser().parse(owner);
                JSONObject joOwner = (JSONObject) ob;
                if(joOwner.get("user_id")!=null&&joOwner.get("display_name")!=null&&joOwner.get("link")!=null){
                    String user_id = joOwner.get("user_id").toString();
                    String display_name = joOwner.get("display_name").toString();
                    String link = joOwner.get("link").toString();
                    String result1 = "user_id: " + user_id + "     title:" + title + "    is_answered:" + is_answered + "  link: " + link;
                    String result2 = "     display_name:  " + display_name + "      ";
                    writer.write(result1);
                    writer.write(result2);}
                }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

}

