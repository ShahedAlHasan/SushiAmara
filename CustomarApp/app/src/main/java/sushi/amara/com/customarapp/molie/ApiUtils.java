package sushi.amara.com.customarapp.molie;

/**ttp://builders.creativeitsoft.com/Webservices/login
 * Created by azeR on 9/20/2018.
 */

public class ApiUtils {
    private ApiUtils() {

    }

    public static final String BASE_URL = "https://api.mollie.com/v2/";

    public static final String token="Y3A9VWgTnMWdioz4evGPjuiOBPnfEwFRX2lV95sMSM3ukm0wTgfgWIssuTe9";

    public static ApiInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
