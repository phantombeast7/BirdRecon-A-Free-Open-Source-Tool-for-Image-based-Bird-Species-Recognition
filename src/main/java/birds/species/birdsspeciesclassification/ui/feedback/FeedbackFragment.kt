package birds.species.birdsspeciesclassification.ui.feedback

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import birds.species.birdsspeciesclassification.R

class FeedbackFragment : Fragment() {

    private lateinit var submitButton: Button
    private lateinit var feedbackEditText: EditText
    private lateinit var radioButtonYes: RadioButton
    private lateinit var emoji1: ImageView
    private lateinit var emoji2: ImageView
    private lateinit var emoji3: ImageView
    private lateinit var emoji4: ImageView
    private lateinit var emoji5: ImageView
    private lateinit var emailLayout: RelativeLayout
    private lateinit var emailTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var imageUploadFeedbackButton: ImageView

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views
        submitButton = view.findViewById(R.id.submitButton)
        feedbackEditText = view.findViewById(R.id.feedbackEditText)
        radioButtonYes = view.findViewById(R.id.radioButtonYes)
        emoji1 = view.findViewById(R.id.emoji1)
        emoji2 = view.findViewById(R.id.emoji2)
        emoji3 = view.findViewById(R.id.emoji3)
        emoji4 = view.findViewById(R.id.emoji4)
        emoji5 = view.findViewById(R.id.emoji5)
        emailLayout = view.findViewById(R.id.emailLayout)
        emailTextView = view.findViewById(R.id.emailTextView)
        emailEditText = view.findViewById(R.id.emailEditText)
        imageUploadFeedbackButton = view.findViewById(R.id.image_upload_feedback_button)

        // Add onClickListeners
        emoji1.setOnClickListener { emojiClicked(emoji1) }
        emoji2.setOnClickListener { emojiClicked(emoji2) }
        emoji3.setOnClickListener { emojiClicked(emoji3) }
        emoji4.setOnClickListener { emojiClicked(emoji4) }
        emoji5.setOnClickListener { emojiClicked(emoji5) }

        radioButtonYes.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                emailLayout.visibility = View.VISIBLE
            } else {
                emailLayout.visibility = View.GONE
            }
        }

        submitButton.setOnClickListener {
            val feedbackText = feedbackEditText.text.toString()
            val followUp = if (radioButtonYes.isChecked) "Yes" else "No"
            val selectedEmojiRating = determineEmojiRating()

            val feedbackData = FeedbackData(feedbackText, selectedEmojiRating, followUp)
            processFeedback(feedbackData)
        }

        imageUploadFeedbackButton.setOnClickListener {
            showImageSourceDialog()
        }
    }

    private fun emojiClicked(clickedEmoji: ImageView) {
        resetAllEmojiColors()
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.orange)
        val redColor = ContextCompat.getColor(requireContext(), R.color.button_color)
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        val defaultColor = Color.TRANSPARENT

        when (clickedEmoji) {
            emoji1 -> {
                emoji1.setColorFilter(redColor)
                emoji2.setColorFilter(defaultColor)
                emoji3.setColorFilter(defaultColor)
                emoji4.setColorFilter(defaultColor)
                emoji5.setColorFilter(defaultColor)
            }
            emoji2 -> {
                emoji1.setColorFilter(defaultColor)
                emoji2.setColorFilter(redColor)
                emoji3.setColorFilter(defaultColor)
                emoji4.setColorFilter(defaultColor)
                emoji5.setColorFilter(defaultColor)
            }
            emoji3 -> {
                emoji1.setColorFilter(defaultColor)
                emoji2.setColorFilter(defaultColor)
                emoji3.setColorFilter(yellowColor)
                emoji4.setColorFilter(defaultColor)
                emoji5.setColorFilter(defaultColor)
            }
            emoji4 -> {
                emoji1.setColorFilter(defaultColor)
                emoji2.setColorFilter(defaultColor)
                emoji3.setColorFilter(defaultColor)
                emoji4.setColorFilter(greenColor)
                emoji5.setColorFilter(defaultColor)
            }
            emoji5 -> {
                emoji1.setColorFilter(defaultColor)
                emoji2.setColorFilter(defaultColor)
                emoji3.setColorFilter(defaultColor)
                emoji4.setColorFilter(defaultColor)
                emoji5.setColorFilter(greenColor)
            }
        }
    }

    private fun resetAllEmojiColors() {
        val emojis = listOf(emoji1, emoji2, emoji3, emoji4, emoji5)
        emojis.forEach { it.clearColorFilter() }
    }

    private fun determineEmojiRating(): Int {
        val emojis = listOf(emoji1, emoji2, emoji3, emoji4, emoji5)
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.orange)
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)

        for ((index, emoji) in emojis.withIndex()) {
            val drawable = emoji.drawable
            if (drawable != null && drawable is ColorDrawable) {
                val color = drawable.color
                if (color == yellowColor || color == greenColor) {
                    return index + 1 // Ratings from 1 to 5
                }
            }
        }
        return -1 // Default if no emoji is selected
    }

    private fun processFeedback(feedbackData: FeedbackData) {
        // Here you can handle the collected feedback, for example, send it to a server
    }

    private fun showImageSourceDialog() {
        val options = arrayOf<CharSequence>( // Make options CharSequence type
            SpannableString("Take Photo").apply {
                setSpan(ForegroundColorSpan(Color.WHITE), 0, length, 0)
            },
            SpannableString("Choose from Gallery").apply {
                setSpan(ForegroundColorSpan(Color.WHITE), 0, length, 0)
            }
        )

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(SpannableString("Choose an option").apply {
            setSpan(ForegroundColorSpan(Color.WHITE), 0, length, 0)
        })
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> dispatchTakePictureIntent()
                1 -> dispatchPickImageIntent()
            }
        }
        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_opaque_background)
        }

        dialog.show()
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun dispatchPickImageIntent() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { pickIntent ->
            pickIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(pickIntent, REQUEST_PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> handleImageCaptureResult(data)
                REQUEST_PICK_IMAGE -> handleImagePickResult(data)
            }
        }
    }

    private fun handleImageCaptureResult(data: Intent?) {
        val imageBitmap = data?.extras?.get("data") as? Bitmap
        if (imageBitmap != null) {
            // Handle captured image bitmap
        }
    }

    private fun handleImagePickResult(data: Intent?) {
        val imageUri = data?.data
        val imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
        // Handle picked image bitmap
    }

    data class FeedbackData(val text: String, val emojiRating: Int, val followUp: String)
}
