package br.redcode.dataform.lib.extension

import android.os.Handler
import android.widget.Spinner
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterSpinnerSpinneable
import br.redcode.dataform.lib.interfaces.Spinnable
import br.redcode.dataform.lib.utils.Constantes
import java.util.*

/**
 * Created by pedrofsn on 31/10/2017.
 */

fun Spinner.setSpinnable(list: List<Spinnable>, hasDefault: Boolean = false, id: String? = Constantes.STRING_VAZIA): AdapterSpinnerSpinneable {
    val temp = ArrayList<Spinnable>()
    var adapterSpinner = AdapterSpinnerSpinneable(context, temp)

    if (list.isNotEmpty()) {
        if (hasDefault) {
            temp.add(object : Spinnable {
                override fun getId(): String {
                    return Constantes.STRING_VAZIA
                }

                override fun getTexto(): String {
                    return context.getString(R.string.selecione)
                }
            } as Spinnable)
        }

        temp.addAll(list)
        adapterSpinner = AdapterSpinnerSpinneable(context, temp)
        adapter = adapterSpinner

        // Marcar item pré-selecionado
        if (id?.isNotEmpty() == true) {
            for (i in list.indices) {
                val spinnable = list[i]

                if (id.equals(spinnable.getId(), ignoreCase = true)) {
                    Handler().postDelayed(Runnable {
                        val positionOfItem = if (hasDefault) {
                            i + 1
                        } else {
                            i
                        }

                        setSelection(positionOfItem, true)
                    }, 500)

                    break
                }
            }
        }
    }

    return adapterSpinner
}

fun Spinner.getSpinnableFromSpinner(spinnables: List<Spinnable>): Spinnable? {
    val textoSelecionado = selectedItem?.toString()?.trim() ?: Constantes.STRING_VAZIA
    return if (textoSelecionado.isNotEmpty()) spinnables.firstOrNull { spinnable -> textoSelecionado == spinnable.getTexto().trim() } else null
}