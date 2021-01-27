package com.example.montapalavras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
/**
 * A lógica do programa foi dividida em 5 fases, sendo elas:
 *  - Captura da entrada do usuário
 *  - Remoção de acentuação e espaços em branco
 *  - Formação das palavras
 *  - Captura da palavra formada de maior pontuação
 *  - Retorno dos caracteres que sobraram
 *  - Exibição dos resultados
 *
 *  A captura dos caracteres inseridos pelo usuário ocorre na Atividade principal pelo widget EditText com id "letras"
 *  e a string é então enviada para a Atividade MostrarResposta através de uma Intent quando o evento onClick() do botão "OK" for ativado.
 *
 *  Já na classe MostrarResposta é feito primeiro a captura da String inserida pelo usuário.
 *
 *  A remoção da acentuação e espaços em branco é feita quando ja temos armazenada em uma String, os caracteres inseridos pelo usuário.
 *  A remoção é feita utilizando a classe Normalize juntamente com o método replaceAll.
 *
 *  Quando já temos A string com a acentuação removida, é feita a formação das palavras.
 *  A formação é feita percorrendo a String de entrada uma vez para cada palavra do banco. Caso a String de entrada possua os caracteres
 *  necessário para formar a palavra comparada no banco, essa palavra é adicionada na lista de strings contendo as palavras formadas.
 *
 *  Com as palavras formadas, podemos então, calcular a pontuação de cada palavra da lista retornada.
 *  O calculo é feito percorrendo a lista e para cada caractere, seu valor é incrementado na variável que armazena a pontuação da palavra.
 *  Caso a pontuação da palavra atual seja maior que a pontuação obtida anteriormente, o valor e a palavra são substituidos.
 *  Caso o valor seja igual, a palavra de menor tamnha permanece.
 *  Ao final do percorrimento, um HashMap é retornado contendo a maior pontuação e sua respectiva palavra.
 *
 *  Resta agora obter os caracteres que não foram usados para formar a palavra.
 *  Isso é feito percorrendo a String inserida pelo usuário e comparando os caracteres, removendo da String os caracteres e da palavra formada
 *  os caracteres que sejam iguais, restando dessa forma na String inserida pelo usuario apenas os caracteres que não foram usados na formação da palavra.
 *
 *  E por último então, são feitas as alterações na Activity para exibição dos resultados.
 *
 *
 *
 * */
public class MainActivity extends AppCompatActivity {

    public static final String LETRAS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inicializar(View view){
        Intent intent = new Intent(this, MostrarResposta.class);
        EditText campoLetras = (EditText) findViewById(R.id.letras);
        String letras = campoLetras.getText().toString();

        intent.putExtra(LETRAS, letras);
        startActivity(intent);
    }



}