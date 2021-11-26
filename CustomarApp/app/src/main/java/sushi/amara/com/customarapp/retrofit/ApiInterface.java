package sushi.amara.com.customarapp.retrofit;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import sushi.amara.com.customarapp.retrofit.responce.MailResponce;



/**
 * Created by shafi.sbf on 7/21/2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("send_email.php")
    Call<MailResponce> sentMail(
            @Field("sender_email") String sender_email,
            @Field("sender_email_name") String sender_email_name,
            @Field("email_subject") String email_subject,
            @Field("email_body") String email_body);


}
