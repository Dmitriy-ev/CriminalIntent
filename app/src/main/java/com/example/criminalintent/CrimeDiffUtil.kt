package com.example.criminalintent

import androidx.recyclerview.widget.DiffUtil

class CrimeDiffUtil(val oldList: List<Crime>, val newList: List<Crime>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new // так как дата класс то вызов == это equals, и идет сравнение по всем полям.
    }
}