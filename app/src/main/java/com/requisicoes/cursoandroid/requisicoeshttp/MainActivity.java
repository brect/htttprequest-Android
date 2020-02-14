package com.requisicoes.cursoandroid.requisicoeshttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.requisicoes.cursoandroid.requisicoeshttp.model.CepVO;
import com.requisicoes.cursoandroid.requisicoeshttp.model.Foto;
import com.requisicoes.cursoandroid.requisicoeshttp.model.Post;
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
    private List<Post> listaPostagens = new ArrayList<>();

    DataService dataService;

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
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);


        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                recuperarDadosRetrofit();
//                recuperarListaRetrofit();
//                salvarPostagem();
                atualizarPostagem();
//                atualizarPostagemPath();

//                deletarPostagens();
            }
        });
    }

    private void deletarPostagens() {

        Call<Void> call = dataService.removerPostagem(2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if( response.isSuccessful() ){
                    textoResultado.setText( "Status: " + response.code() );
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    private void atualizarPostagemPath() {
    }

    private void atualizarPostagem() {

        //Configura objeto post
        //Postagem post = new Postagem("1234", null, "Corpo post");
        Post post = new Post();
        post.setBody("Corpo da post alterado");

        Call<Post> call = dataService.atualizarPostagemPath(2, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if( response.isSuccessful() ){
                    Post postagemResposta = response.body();
                    textoResultado.setText(
                            " Status: " + response.code() +
                                    " id: " + postagemResposta.getId() +
                                    " userId: " + postagemResposta.getUserId() +
                                    " titulo: " + postagemResposta.getTitle() +
                                    " body: " + postagemResposta.getBody()
                    );
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


    private void salvarPostagem(){
        //Configura objeto postagem
        //Postagem postagem = new Postagem(132, "Título postagem!", "Corpo postagem");

        //recupera o serviço e salva postagem
        Call<Post> call = dataService.salvarPostagem( 132, "Título postagem!", "Corpo postagem" );

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if( response.isSuccessful() ){
                    Post postagemResposta = response.body();
                    textoResultado.setText(
                            "Código: " + response.code() +
                                    " id: " + postagemResposta.getId() +
                                    " titulo: " + postagemResposta.getTitle()
                    );
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });



    }

    public void recuperarListaRetrofit(){

        DataService service = retrofit.create(DataService.class);
        //Call<List<Foto>> call = service.recuperarFotos();
        Call<List<Post>> call = service.recuperarPostagens();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if( response.isSuccessful() ){
                    listaPostagens = response.body();

                    for (int i=0; i<listaPostagens.size(); i++ ){
                        Post postagem = listaPostagens.get( i );
                        Log.d("resultado", "resultado: " + postagem.getId() + " / " + postagem.getTitle() );
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

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
