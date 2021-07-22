package com.prep.newhbo

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.apply {
            // findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 0
            maxWidth = Integer.MAX_VALUE
            // remove any extra margins from edit layout
            val layoutView = findViewById<LinearLayout>(R.id.search_edit_frame)
            val params: ViewGroup.MarginLayoutParams =
                layoutView.layoutParams as ViewGroup.MarginLayoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.leftMargin = 0
            layoutView.layoutParams = params
            queryHint = "Search"
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        searchView.run {
            isIconified = false
            setIconifiedByDefault(true)
        }

        return true
    }
}