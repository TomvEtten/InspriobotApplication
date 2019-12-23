package hva.nl.inspiriobot.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.data.InspirobotQuote
import hva.nl.inspiriobot.ui.viewmodel.SaveActivityViewModel
import kotlinx.android.synthetic.main.activity_save.*
import java.io.File

const val PICTURE_EXTRA = "picture"

class SaveActivity : BaseActivity() {
    private lateinit var quote: InspirobotQuote
    private lateinit var viewModel: SaveActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)
        quote = intent.getParcelableExtra(PICTURE_EXTRA) ?: return
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SaveActivityViewModel::class.java)
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun initViews() {
        val imgFile = File(quote.uri)
        ivImage.setImageURI(Uri.parse(imgFile.absolutePath))
        etTitle.setText(quote.title)
        etDescription.setText(quote.description)
        btnSave.setOnClickListener { save() }
    }

    private fun save() {
        quote.title = etTitle.text.toString()
        quote.description = etDescription.text.toString()
        if (viewModel.insertQuote(quote)) {
            finish()
        }
    }

}
