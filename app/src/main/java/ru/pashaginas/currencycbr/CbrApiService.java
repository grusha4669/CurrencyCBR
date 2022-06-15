package ru.pashaginas.currencycbr;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public interface CbrApiService {
 @GET("scripts/XML_daily.asp") // на сервере
 Call<ValCurs> getDailyValutes(); // в коде

 // Возможно эту часть кода следует перенести в активность
 OkHttpClient okClient = new OkHttpClient.Builder()
         .addInterceptor(new ResponseInterceptor())
         .build();

 Retrofit retrofit = new Retrofit.Builder()
         .baseUrl("http://www.cbr.ru/")
         .addConverterFactory(SimpleXmlConverterFactory.create())
         .client(okClient)
         .build();

 class ResponseInterceptor implements Interceptor {

  @Override
  public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
   Request originalRequest = chain.request();
   okhttp3.Response originalResponse = chain.proceed(originalRequest);
   MediaType mediaType = MediaType.parse("application/xml; charset=windows-1251");
   ResponseBody modifiedBody = ResponseBody
           .create(mediaType, originalResponse.body().bytes());

   // Возвращаем изменённый ответ
   return originalResponse.newBuilder()
           .addHeader("Accept", "application/xml")
           .body(modifiedBody)
           .build();
  }
 }
}
