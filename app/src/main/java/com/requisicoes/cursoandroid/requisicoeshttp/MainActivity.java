package com.requisicoes.cursoandroid.requisicoeshttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.requisicoes.cursoandroid.requisicoeshttp.model.CepVO;
import com.requisicoes.cursoandroid.requisicoeshttp.model.Foto;
import com.requisicoes.cursoandroid.requisicoeshttp.requisicao.CEPService;
import com.requisicoes.cursoandroid.requisicoeshttp.requisicao.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private Retrofit retrofit;

    private List<Foto> listaFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoResultado = findViewById(R.id.textResultado);

//        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyTask task = new MyTask();
//                String urlApi = "https://blockchain.info/ticker";
////                String cep = "01310100";
////                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
//                task.execute(urlApi);
//            }
//        });

//        String url = "https://viacep.com.br/ws/";
        String url = "https://jsonplaceholder.typicode.com";

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                recuperarDadosRetrofit();
                recuperarListaRetrofit();

            }
        });
    }

    public void recuperarListaRetrofit(){

        DataService dataService = retrofit.create(DataService.class);
        Call<List<Foto>> call = dataService.recuperarFotos();


        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {

                if (response.isSuccessful()){
                    listaFotos = response.body();

                    for (int i = 0; i < listaFotos.size(); i++){
                        Foto foto = listaFotos.get(i);
                        Log.d("resposta", "resultado: " + foto.getTitle() );
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });
    }


    public void recuperarDadosRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CepVO> call = cepService.recuperarCep("06843390");


        //cria tarefa asincrona
        call.enqueue(new Callback<CepVO>() {
            @Override
            public void onResponse(Call<CepVO> call, Response<CepVO> response) {

                if (response.isSuccessful()){
                    CepVO cep = response.body();
                    textoResultado.setText(cep.getBairro());
                }

            }

            @Override
            public void onFailure(Call<CepVO> call, Throwable t) {

            }
        });
    }



}
