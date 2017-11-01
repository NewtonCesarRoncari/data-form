package br.redcode.dataform.lib.domain

import android.support.v7.widget.RecyclerView
import android.view.View
import br.redcode.dataform.lib.interfaces.OnItemClickListener

/**
 * Created by pedrofsn on 16/10/2017.
 */
abstract class ViewHolderGeneric<Objeto>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun popular(obj: Objeto)

    open fun popular(obj: Objeto, click: OnItemClickListener?) {
        if (obj != null) {
            popular(obj)

            if (click != null) {
                click(itemView, click)
            }
        }
    }

    fun click(view: View, clickListener: OnItemClickListener) {
        view.setOnClickListener { onClick(clickListener) }
    }

    open fun onClick(clickListener: OnItemClickListener?) {
        if (clickListener != null) clickListener.onItemClickListener(adapterPosition)
    }
}