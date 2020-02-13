package com.requisicoes.cursoandroid.requisicoeshttp.requisicao;

import com.requisicoes.cursoandroid.requisicoeshttp.model.CepVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    @GET("{cep}/json/")
    Call<CepVO> recuperarCep(@Path("cep") String cep);

}
