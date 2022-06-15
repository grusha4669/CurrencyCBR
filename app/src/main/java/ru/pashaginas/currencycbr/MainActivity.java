package ru.pashaginas.currencycbr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
    }

    public void onClick(View view) {

        CbrApiService cbrApi = CbrApiService.retrofit.create(CbrApiService.class);

        Call<ValCurs> call = cbrApi.getDailyValutes();

        call.enqueue(new Callback<ValCurs>() {
            @Override
            public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {

                if (response.isSuccessful()) {

                    ValCurs valCurs = response.body();

                    List<Valute> valutes = valCurs.valuteList;

                    // выводим данные всего списка
                    for (Valute valute : valutes) {
                        mTextView.append(valute.getName() + ": " + valute.getValue() + "\n");
                    }
                } else {
                    ResponseBody errorBody = response.errorBody();
                    try {
                        mTextView.setText(errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ValCurs> call, Throwable throwable) {
                mTextView.setText(throwable.getLocalizedMessage());
            }
        });
    }
}
