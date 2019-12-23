package hva.nl.inspiriobot.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import hva.nl.inspiriobot.R
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

@RuntimePermissions
open class BaseActivity : AppCompatActivity() {
     var slider: Boolean = false

    protected fun saveImageToStorage(drawable: Drawable): String {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val file = File(dataDir, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_saved_items -> {
                val intent = Intent(this, SavedItemsActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.nav_check -> {
                slider = !slider
                item.isChecked = slider
            }
            R.id.nav_mindfullness -> {
                val intent = Intent(this, MindfulnessActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun share(drawable: Drawable, title: String, description: String) {
        val bm = (drawable as BitmapDrawable).bitmap
        val path =
            MediaStore.Images.Media.insertImage(contentResolver, bm, title, description)
        val screenshotUri = Uri.parse(path)
        val intent = Intent(Intent.ACTION_SEND).apply {
            this.putExtra(Intent.EXTRA_STREAM, screenshotUri)
            this.type = "image/*"
            this.putExtra(Intent.EXTRA_TITLE, title)
            this.putExtra(Intent.EXTRA_TEXT, description)
        }
        startActivity(Intent.createChooser(intent, getString(R.string.share_text)))
    }

}