package br.redcode.dataform.lib.domain

import android.widget.ImageView
import br.redcode.dataform.lib.interfaces.ImagemCapturavel
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import java.io.File

/**
 * Created by pedrofsn on 02/11/2017.
 */
class HandlerCapturaImagem(val callback: ImagemCapturavel) {

    var uiPerguntaImagemTemp: UIPerguntaImagem? = null

    fun capturarImagem(uiPerguntaImagem: UIPerguntaImagem, tipo: UIPerguntaImagem.Tipo) {
        uiPerguntaImagemTemp = uiPerguntaImagem
        callback.capturarImagem(tipo)
    }

    fun onImagensSelecionadas(imagesFiles: List<File>) {
        uiPerguntaImagemTemp?.let {
            imagesFiles.forEach(fun(file: File) { it.adicionarImagem(Imagem(file.absolutePath)) })
        }
    }

    fun visualizarImagem(imagem: Imagem) {
        callback.visualizarImagem(imagem)
    }

    fun carregarImagem(imagem: String, imageView: ImageView) {
        callback.carregarImagem(imagem, imageView)
    }

    fun hasPermissoes(): Boolean {
        return callback.hasPermissoes()
    }

}