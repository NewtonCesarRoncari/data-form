package br.redcode.sample.activities

import android.os.Bundle
import android.widget.ImageView
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityGeneric
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_imagem_com_zoom.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityImagemComZoom(override var ativarBotaoVoltar: Boolean = true) : ActivityGeneric() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagem_com_zoom)

        val imagem: String = intent.getStringExtra("imagem")

        Picasso.with(this)
                .load(imagem)
                .into(photoView as ImageView)
    }

}