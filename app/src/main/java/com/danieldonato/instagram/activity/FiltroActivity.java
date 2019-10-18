package com.danieldonato.instagram.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.danieldonato.instagram.R;

public class FiltroActivity extends AppCompatActivity {

    private ImageView imageFotoEscolhida;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        imageFotoEscolhida = findViewById(R.id.imageFotoEscolhida);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
            imageFotoEscolhida.setImageBitmap(imagem);
        }

    }
}
