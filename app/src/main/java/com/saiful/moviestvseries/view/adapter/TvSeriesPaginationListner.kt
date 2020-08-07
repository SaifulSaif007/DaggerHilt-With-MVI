package com.saiful.moviestvseries.view.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class TvSeriesPaginationListner
constructor(private val layoutManager: GridLayoutManager)
    : RecyclerView.OnScrollListener() {

    companion object {
        var PAGE_START = 1
        private val PAGE_SIZE = 12
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition : Int = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()){
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
            ){
                loadMoreItems()
            }
        }

    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

}