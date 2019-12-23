package hva.nl.inspiriobot.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import hva.nl.inspiriobot.R
import hva.nl.inspiriobot.ui.viewmodel.MindfulnessActivityViewModel
import kotlinx.android.synthetic.main.activity_mindfulness.*
import com.aminography.redirectglide.RedirectGlideUrl



class MindfulnessActivity : BaseActivity() {
    private lateinit var viewModel: MindfulnessActivityViewModel
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mindfulness)
        initViews()
        initViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mediaPlayer?.stop()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnStart.setOnClickListener {
            startMindfulnessMode()
        }
    }

    private fun startMindfulnessMode() {
        viewModel.getMindfulnessMode()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MindfulnessActivityViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.text.observe(this, Observer {
           tvDisplayText.text = it
        })

        viewModel.stop.observe(this, Observer {
            btnStart.visibility = View.VISIBLE
            tvDisplayText.text = ""
            ivMindfulness.setImageDrawable(getDrawable(R.drawable.inspirobot_pink_dark))
        })

        viewModel.mindfullness.observe(this, Observer { mindfulnessResponse ->
            btnStart.visibility = View.GONE
            mediaPlayer = MediaPlayer().apply {
                setDataSource(mindfulnessResponse.mp3)
                prepare()
                start()
            }
            viewModel.processData(mindfulnessResponse.data)
        })

    }
}
