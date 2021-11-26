package sushi.amara.com.customarapp.Common;





import java.util.ArrayList;
import java.util.List;

import sushi.amara.com.customarapp.Remote.IGoogleAPI;
import sushi.amara.com.customarapp.Remote.RetrofitClient;
import sushi.amara.com.customarapp.pojo.AddFood;
import sushi.amara.com.customarapp.pojo.OrderChart;
import sushi.amara.com.customarapp.pojo.User;

public class Common {

    public static String token;

    public static AddFood currentFood = new AddFood();

    public static User currentUser;

    public static String location;
    public static String store;
    public static String system;

    public static String dateStart;
    public static String dateStartB;
    public static String dateEnd;
    public static String dateEndB;
    public static String holiday = "no";
    public static String holidayB = "no";

    public static int discount = 1;

    public static List<OrderChart> orderCharts = new ArrayList<OrderChart>();

    public static String myLocation ;
    public static String LangenfeldLocation = 51.1039604+","+7.011775;
    public static String LeichlingenLocation = 23.7561894+","+90.3753767;
//    public static String LeichlingenLocation = 51.1044433+","+6.9459513;



    public static final String googleAPIUrl = "https://maps.googleapis.com/";




    public static IGoogleAPI getGoogleService() {
        return RetrofitClient.getClient(googleAPIUrl).create(IGoogleAPI.class);
    }
}
