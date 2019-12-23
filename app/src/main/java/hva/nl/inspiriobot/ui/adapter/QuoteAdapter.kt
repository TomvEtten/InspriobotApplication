package hva.nl.inspiriobot.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.data.InspirobotQuote
import kotlinx.android.synthetic.main.saved_quote.view.*
import java.io.File

class QuoteAdapater(
    private val results: List<InspirobotQuote>,
    val adapterOnClick : (InspirobotQuote) -> Unit
) :
    RecyclerView.Adapter<QuoteAdapater.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder object, inflating saved_quote.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.saved_quote,
                parent,
                false
            )
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return results.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(inspirobotQuote: InspirobotQuote) {
            itemView.setOnClickListener { adapterOnClick(inspirobotQuote) }
            itemView.tvTitle.text = inspirobotQuote.title
            itemView.tvDescription.text = inspirobotQuote.description
            val imgFile = File(inspirobotQuote.uri)
            itemView.ivSavedItem.setImageURI(Uri.parse(imgFile.absolutePath))
        }
    }
}
