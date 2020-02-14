package com.requisicoes.cursoandroid.requisicoeshttp.requisicao;

import com.requisicoes.cursoandroid.requisicoeshttp.model.Foto;
import com.requisicoes.cursoandroid.requisicoeshttp.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {


        @GET("/photos")
        Call<List<Foto>> recuperarFotos();

        @GET("/posts")
        Call<List<Post>> recuperarPostagens();

        @POST("/posts")
        Call<Post> salvarPostagens(@Body Post post);

        @FormUrlEncoded
        @POST("/posts")
        Call<Post> salvarPostagem(
                @Field("userId") Integer userId,
                @Field("title") String title,
                @Field("body") String body
        );
}
