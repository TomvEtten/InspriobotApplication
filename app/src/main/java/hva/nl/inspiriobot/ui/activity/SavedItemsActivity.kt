package hva.nl.inspiriobot.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.data.InspirobotQuote
import hva.nl.inspiriobot.ui.adapter.QuoteAdapater
import hva.nl.inspiriobot.ui.viewmodel.SavedItemsViewModel
import kotlinx.android.synthetic.main.activity_saved_items.*
import kotlinx.android.synthetic.main.saved_quote.view.*

class SavedItemsActivity : BaseActivity() {

    private lateinit var viewModel: SavedItemsViewModel
    private val quoteList = arrayListOf<InspirobotQuote>()
    private val quoteAdapater = QuoteAdapater(quoteList) {inspirobotQuote -> editQuote(inspirobotQuote)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_items)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViewModel()
        initViews()
    }

    private fun editQuote(quoteToEdit: InspirobotQuote) {
        val intent = Intent(this, SaveActivity::class.java)
        intent.putExtra(PICTURE_EXTRA, quoteToEdit)
        startActivity(intent)
    }

    private fun initViews() {
        rvSavedItems.adapter = quoteAdapater
        rvSavedItems.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSavedItems.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        val touchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    viewModel.deleteQuote(quoteList[viewHolder.adapterPosition])
                    quoteAdapater.notifyItemRemoved(viewHolder.adapterPosition)
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    shareWithPermissionCheck(
                        viewHolder.itemView.ivSavedItem.drawable,
                        viewHolder.itemView.tvTitle.text.toString(),
                        viewHolder.itemView.tvDescription.text.toString()
                    )
                    quoteAdapater.notifyItemChanged(viewHolder.adapterPosition)
                }
            }
        })
        touchHelper.attachToRecyclerView(rvSavedItems)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SavedItemsViewModel::class.java)
        viewModel.quotes.observe(this, Observer {
            quoteList.clear()
            quoteList.addAll(it)
            quoteAdapater.notifyDataSetChanged()
        })
    }

}
