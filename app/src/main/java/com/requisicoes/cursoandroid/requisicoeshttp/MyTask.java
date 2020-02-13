package com.requisicoes.cursoandroid.requisicoeshttp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class MyTask extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        String stringUrl = strings[0];
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer stringBuffer = null;

        try {

            URL url = new URL(stringUrl);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            // Recupera os dados em Bytes
            inputStream = conexao.getInputStream();

            //inputStreamReader lê os dados em Bytes e decodifica para caracteres
            inputStreamReader = new InputStreamReader( inputStream );

            //Objeto utilizado para leitura dos caracteres do InpuStreamReader
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );
            stringBuffer = new StringBuffer();
            String linha = "";

            while((linha = bufferedReader.readLine()) != null){
                stringBuffer.append( linha );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(String resultado) {
        super.onPostExecute(resultado);

        String objetoValor = null;
        String valorMoeda = null;
        String simbolo = null;

        try{
            JSONObject jsonObject = new JSONObject();
            objetoValor = jsonObject.optString("BRL");
            valorMoeda = jsonObject.optString("last");
            simbolo = jsonObject.optString("symbol");
        }catch (Exception e){

        }


//        textoResultado.setText("objetoValor " + objetoValor + "valorMoeda " + valorMoeda + "simbolo " + simbolo);
    }
}