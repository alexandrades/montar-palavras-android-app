package com.example.montapalavras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostrarResposta extends AppCompatActivity {

    private List<String> banco_palavras = Arrays.asList("Abacaxi", "Manada", "mandar", "porta", "mesa", "Dado",
            "Mangas", "Já", "coisas", "radiografia", "matemática", "Drogas", "prédios", "implementação",
            "computador", "balão", "Xícara", "Tédio", "faixa", "Livro", "deixar", "superior", "Profissão",
            "Reunião", "Prédios", "Montanha", "Botânica", "Banheiro", "Caixas", "Xingamento", "Infestação",
            "Cupim", "Premiada", "empanada", "Ratos", "Ruído", "Antecedente", "Empresa", "Emissário",
            "Folga", "Fratura", "Goiaba", "Gratuito", "Hídrico", "Homem", "Jantar", "Jogos", "Montagem",
            "Manual", "Nuvem", "Neve", "Operação", "Ontem", "Pato", "Pé", "viagem", "Queijo", "Quarto",
            "Quintal", "Solto", "rota", "Selva", "Tatuagem", "Tigre", "Uva", "Último", "Vitupério", "Voltagem",
            "Zangado", "Zombaria", "Dor");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_resposta);

        //Captura a intenção criada na atividade anterior e a String com a entrada do usuário
        Intent intent = getIntent();
        String letras = intent.getStringExtra(MainActivity.LETRAS);

        //Remove acentos e espaços da String a ser usuada na jogada e a deixa com todas as letras maiúsculas
        letras = removerAcentos(letras);
        letras = letras.replaceAll("\\s", "");

        //Obtem uma lista com todas as palavras possíveis de serem formadas com a entrada e que existam no banco de palavras
        ArrayList<String> palavras_formadas = palavrasFormadas(letras);

        //Um hashMap recebe a palavra da lista de palavras possíveis que possui a maior pontuação respeitando as resgras de desempate
        Map<String, String> resultado = maiorPontuacao(palavras_formadas);

        //Espaço adicionado entre todos os caracteres da palavra obtida, com objetivo de melhorar a visualização para o usuário
        String palavra = resultado.get("palavra");
        palavra = palavra.replaceAll("(.)", "$1 ");

        //Captura dos caracteres que sobraram na formação da palavra
        String sobra = sobra(letras, palavra);
        sobra = sobra.replaceAll("(.)", "$1, ");

        //Alteração do TextView com id "textView_pontos" para a pontuação obtida
        TextView textViewPontuacao = (TextView) findViewById(R.id.textView_pontos);
        textViewPontuacao.setText("Palavra de " + resultado.get("pontuacao") + " pontos.");

        //Alteração do TextView com id "textView_palavra" para a palavra com maior pontuação obtida
        TextView textViewPalavra = (TextView) findViewById(R.id.textView_palavra);
        textViewPalavra.setText(palavra);

        //Alteração do TextView com id "textView_sobra" para os caracteres que sobraram
        TextView textViewSobra = (TextView) findViewById(R.id.textView_sobra);
        textViewSobra.setText(sobra);
    }


    //Função para dividir a String em uma lista de caracteres
    private ArrayList<Character> separarCaracteres(String conjunto_caracteres){

        ArrayList<Character> caracteres = new ArrayList<Character>();

        for(int i = 0; i < conjunto_caracteres.length(); i++){
            caracteres.add(conjunto_caracteres.charAt(i));
        }

        return caracteres;
    }

    //Função para remoção dos sinais de acentuação
    public String removerAcentos(String letras) {

        if(letras == null){
            return null;
        }
        else{
            return Normalizer.normalize(letras, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "").toUpperCase();
        }
    }

    //Função para formar as palavras e armazenar em uma lista para retorna-la
    public ArrayList<String> palavrasFormadas(String letras){
        ArrayList<Character> input_letras = separarCaracteres(letras);
        ArrayList<String> palavras_formadas = new ArrayList<String>();

        for(String palavra : banco_palavras){

            palavra = removerAcentos(palavra);
            ArrayList<Character> caracteres = separarCaracteres(palavra);

            for( int i = 0; i < input_letras.size(); i++){

                for( int j = 0; j < caracteres.size(); j++){

                    if(input_letras.get(i).equals(caracteres.get(j))) {
                        caracteres.remove(j);
                        break;
                    }
                }
            }

            if(caracteres.size() == 0){
                palavras_formadas.add(palavra);
            }
        }
        return palavras_formadas;
    }

    //Função para calcular a maior pontuação
    public Map maiorPontuacao(ArrayList<String> palavras){
        int pontuacao_final = 0, pontos = 0;
        String palavra_final = new String();

        for( String palavra : palavras){
            for(int i = 0; i < palavra.length(); i++){

                if(palavra.charAt(i) == 'A' || palavra.charAt(i) == 'E' || palavra.charAt(i) == 'I' ||
                        palavra.charAt(i) == 'O' || palavra.charAt(i) == 'U' || palavra.charAt(i) == 'U' || palavra.charAt(i) == 'N' || palavra.charAt(i) == 'R' || palavra.charAt(i) == 'L' || palavra.charAt(i) == 'S'){
                    pontos++;
                }
                else if( palavra.charAt(i) == 'W' || palavra.charAt(i) == 'D' || palavra.charAt(i) == 'G'){
                    pontos = pontos + 2;
                }
                else if( palavra.charAt(i) == 'B' || palavra.charAt(i) == 'C' || palavra.charAt(i) == 'M' || palavra.charAt(i) == 'P'){
                    pontos = pontos + 3;
                }
                else if( palavra.charAt(i) == 'F' || palavra.charAt(i) == 'H' || palavra.charAt(i) == 'V'){
                    pontos = pontos + 4;
                }
                else if( palavra.charAt(i) == 'J' || palavra.charAt(i) == 'X'){
                    pontos = pontos + 8;
                }
                else if( palavra.charAt(i) == 'Q' || palavra.charAt(i) == 'Z'){
                    pontos = pontos + 10;
                }
                else{

                }
            }

            if(pontos > pontuacao_final){
                pontuacao_final = pontos;
                palavra_final = palavra;
            }
            else if(pontos == pontuacao_final){
                if(palavra.length() < palavra_final.length()){
                    palavra_final = palavra;
                }
            }
            else{

            }
            pontos = 0;
        }

        Map<String, String> resultado = new HashMap<>();
        resultado.put("palavra", palavra_final);
        resultado.put("pontuacao", Integer.toString(pontuacao_final));
        return resultado;
    }

    //Função para obter os caracteres que não foram usados na formação da palavra
    public String sobra(String letras, String palavra){

        char arr_letras[] = letras.toCharArray();
        char arr_palavra[] = palavra.toCharArray();
        for(int i = 0; i < letras.length(); i ++){
            for(int j = 0; j < palavra.length(); j++){
                if(arr_letras[i] == arr_palavra[j]){
                    arr_letras[i] = ' ';
                    arr_palavra[j] = ' ';
                    break;
                }
            }
        }

        palavra = new String(arr_letras);

        return palavra.replaceAll("\\s", "");
    }

}