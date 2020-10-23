public class CreateURL {
    private static String url = "https://api.stackexchange.com/2.2/search?";
    private static String tail = "site=stackoverflow";



    public String getUrl() {
        return url;
    }

    public void setUrl(String template, String url) {
        this.url = this.url + template + "=" + url + "&";
    }

    public void setUrl(String template) {
        this.url = this.url + tail;
    }

    @Override
    public String toString() {
        return "CreateURL{" +
                "url='" + url + '\'' +
                '}';
    }
}
