package hva.nl.inspiriobot.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.SeasonEnum
import hva.nl.inspiriobot.data.InspirobotQuote
import hva.nl.inspiriobot.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var circularProgressDrawable: CircularProgressDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.quote.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .placeholder(circularProgressDrawable)
                .into(ivInspirioBot)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    private fun initViews() {
        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.start()
        setContentView(R.layout.activity_main)
        btnGenerate.setOnClickListener { generateQuote() }
        btnShare.setOnClickListener { shareWithPermissionCheck(ivInspirioBot.drawable, "" , "") }
        btnSave.setOnClickListener { saveImageWithPermissionCheck(ivInspirioBot.drawable) }
    }


    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage(drawable: Drawable?) {
        if (drawable == null) {
            viewModel.error.value = getString(R.string.error)
            return
        }
        val uri = saveImageToStorage(drawable)
        val quoteToPass = InspirobotQuote(uri, "", "")
        val intent = Intent(this, SaveActivity::class.java)
        intent.putExtra(PICTURE_EXTRA, quoteToPass)
        startActivity(intent)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val checkable = menu.findItem(R.id.nav_check)
        checkable.isChecked = slider
        return true
    }

    @NeedsPermission(android.Manifest.permission.INTERNET)
    fun generateQuote() {
        var string = ""
        if (slider) {
            string = SeasonEnum.CHRISTMAS.string
        }
        viewModel.getRandomQuote(string)
    }

}