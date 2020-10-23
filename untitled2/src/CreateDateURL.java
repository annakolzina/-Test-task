import java.util.Arrays;

public class CreateDateURL {

    private static int[] count_of_days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static long urlDate = 1577836800;//the starting point of the date is 01.01.2020
    private static int day = 1;
    private static int year = 2020;
    private static int month = 1;
    private static int count_days = 0;


    public CreateDateURL(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }


    public Long getUrlDate() {
        return urlDate;
    }


    public void setUrlDate(Long urlDate) {
        this.urlDate = urlDate;
    }


    public void Create(){
        count_days = ((year - 2020)*365) + ((month - 1) * count_of_days[month - 1]) + (day - 1);
        urlDate = urlDate + (count_days * 86400);
    }

    @Override
    public String toString() {
        return "CreateDateURL{" +
                "count_of_days=" + Arrays.toString(count_of_days) +
                ", urlDate=" + urlDate +
                ", day=" + day +
                ", year=" + year +
                ", month=" + month +
                ", count_days=" + count_days +
                '}';
    }
}
