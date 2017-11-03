package br.redcode.sample.domain

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.widget.ImageView
import android.widget.Toast
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ActivityCapturarImagem
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.ui.UIPerguntaImagem
import br.redcode.sample.activities.ActivityImagemComZoom
import br.redcode.sample.utils.Utils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.intentFor
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

/**
 * Created by pedrofsn on 03/11/2017.
 */
abstract class BaseActivity : ActivityCapturarImagem(), EasyImage.Callbacks {

    private val RESULT_CODE_EASY_IMAGE = 1992
    private val RESULT_CODE_PERMISSAO = 12

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, this)
    }

    override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
        e?.let {
            it.printStackTrace()
            Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
        handlerCapturaImagem.onImagensSelecionadas(imagesFiles)
    }

    override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RESULT_CODE_PERMISSAO -> {
                if ((grantResults.filter { idPermissao: Int -> idPermissao == PackageManager.PERMISSION_GRANTED }.size == permissoes.size).not()) {
                    forcarPermissoes()
                }
                return
            }
        }
    }

    private fun forcarPermissoes() {
        Toast.makeText(this@BaseActivity, getString(R.string.habilite_todas_as_permissoes), Toast.LENGTH_LONG).show()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun capturarImagem(tipo: UIPerguntaImagem.Tipo) {
        when (tipo) {
            UIPerguntaImagem.Tipo.CAMERA_OU_GALERIA -> EasyImage.openChooserWithGallery(this, getString(R.string.selecione), RESULT_CODE_EASY_IMAGE)
            UIPerguntaImagem.Tipo.CAMERA -> EasyImage.openCamera(this, RESULT_CODE_EASY_IMAGE)
            UIPerguntaImagem.Tipo.GALERIA -> EasyImage.openGallery(this, RESULT_CODE_EASY_IMAGE)
        }
    }

    override fun hasPermissoes(): Boolean {
        for (permissao in permissoes) {
            val isOk: Boolean = ContextCompat.checkSelfPermission(this@BaseActivity, permissao) == PermissionChecker.PERMISSION_GRANTED

            if (isOk.not()) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissao)) {
                    ActivityCompat.requestPermissions(this, permissoes, RESULT_CODE_PERMISSAO)
                } else {
                    forcarPermissoes()
                }
                return false
            }
        }

        return true
    }

    override fun visualizarImagem(imagem: Imagem) {
        startActivity(intentFor<ActivityImagemComZoom>("imagem" to imagem.imagem))
    }

    override fun carregarImagem(imagem: String, imageView: ImageView) {
        var temp = imagem

        if (imagem.startsWith("/")) {
            temp = "file://" + imagem
        }

        Utils.log("Carregando: " + temp)

        Picasso.with(this)
                .load(temp)
                .placeholder(android.R.drawable.stat_sys_download)
                .error(android.R.drawable.stat_notify_error)
                .resize(100, 100)
                .into(imageView, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError() {
                        Toast.makeText(this@BaseActivity, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show()
                    }
                })
    }

}