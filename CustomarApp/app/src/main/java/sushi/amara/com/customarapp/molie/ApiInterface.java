package sushi.amara.com.customarapp.molie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by azeR on 9/20/2018.
 */

public interface ApiInterface {

    @Headers({
            "Authorization: Bearer live_ANudMW9tawfdtEg5DvkPnprBBs8PeE",
            "Accept: */*"
    })
    @FormUrlEncoded
    @POST("payments")
    Call<Payment> getPayments(
            @FieldMap Map<String, String> parms,
            @Field("description") String descrip,
            @Field("redirectUrl") String redirectUrl,
            @Field("webhookUrl") String webhookUrl,
            @Field("metadata") String metadata
    );

}