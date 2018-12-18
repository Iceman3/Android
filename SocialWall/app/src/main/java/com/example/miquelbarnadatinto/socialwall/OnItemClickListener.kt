package com.example.miquelbarnadatinto.socialwall

/**
 * Created by alex on 09/11/2018.
 */
interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}