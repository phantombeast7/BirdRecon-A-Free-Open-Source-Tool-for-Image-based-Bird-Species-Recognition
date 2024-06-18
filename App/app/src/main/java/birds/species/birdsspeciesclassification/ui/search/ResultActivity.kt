package birds.species.birdsspeciesclassification.ui.search


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import birds.species.birdsspeciesclassification.databinding.ActivityResultBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent extras
        val birdName = intent.getStringExtra("bird_name")
        val summary = intent.getStringExtra("summary")
        val imageUrl = intent.getStringExtra("image_url")

        // Set data to views
        binding.birdNameTextView.text = birdName
        binding.summaryTextView.text = summary

        // Load image using Glide
        if (!imageUrl.isNullOrBlank()) {
            loadImage(imageUrl)
        } else {
            // Handle case when image URL is null or empty
            Log.e("Image Loading", "Image URL is null or empty")
        }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log the error and handle it appropriately
                    Log.e("Image Loading", "Error loading image: $e")
                    return false // Return false to allow Glide to call the error placeholder
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Log success message if needed
                    Log.d("Image Loading", "Image loaded successfully")
                    return false // Return false to allow Glide to handle resource set
                }
            })
            .into(binding.birdImageView)
    }
}
