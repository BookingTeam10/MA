package com.example.shopapp.configuration;


import java.util.concurrent.TimeUnit;

import com.example.shopapp.services.interfaces.IAccommodationService;
import com.example.shopapp.services.interfaces.INotificationService;
import com.example.shopapp.services.interfaces.IReservationService;
import com.example.shopapp.services.interfaces.IReviewService;
import com.example.shopapp.services.interfaces.IUserReportService;
import com.example.shopapp.services.interfaces.users.IAdminService;
import com.example.shopapp.services.interfaces.users.IGuestService;
import com.example.shopapp.services.interfaces.users.IOwnerService;
import com.example.shopapp.services.interfaces.users.IReportUserService;
import com.example.shopapp.services.interfaces.users.IUnregisteredUserService;
import com.example.shopapp.services.interfaces.users.IUserService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

    //public static final String SERVICE_API_PATH = "http://"+ BuildConfig.IP_ADDR +":8090/api/";
    public static final String SERVICE_API_PATH = "http://192.168.0.6:8090/api/";

    //public static final String SERVICE_API_PATH = "http://192.168.115.187/api/"; telefon luka

//    public static final String SERVICE_API_PATH = "http://192.168.0.5:8090/api/";

   // public static final String SERVICE_API_PATH = "http://192.168.0.147:8090/api/";

    /*
     * Ovo ce nam sluziti za debug, da vidimo da li zahtevi i odgovori idu
     * odnosno dolaze i kako izgeldaju.
     * */
    public static OkHttpClient test(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static IAccommodationService accommodationService = retrofit.create(IAccommodationService.class);
    public static IUserService userService = retrofit.create(IUserService.class);
    public static IUnregisteredUserService unregisteredUserService = retrofit.create(IUnregisteredUserService.class);
    public static IReservationService reservationService = retrofit.create(IReservationService.class);
    public static IReviewService reviewService = retrofit.create(IReviewService.class);
    public static IReportUserService reportService = retrofit.create(IReportUserService.class);

    public static IGuestService guestService = retrofit.create(IGuestService.class);
    public static IOwnerService ownerService = retrofit.create(IOwnerService.class);
    public static INotificationService notificationService = retrofit.create(INotificationService.class);
    public static IAdminService adminService = retrofit.create(IAdminService.class);
    public static IUserReportService userReportService = retrofit.create(IUserReportService.class);
}
