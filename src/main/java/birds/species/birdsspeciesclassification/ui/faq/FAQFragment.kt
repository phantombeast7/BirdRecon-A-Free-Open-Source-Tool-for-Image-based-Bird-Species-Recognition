package birds.species.birdsspeciesclassification.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import birds.species.birdsspeciesclassification.R


class FAQFragment : Fragment() {

    private lateinit var questionContainers: List<View>
    private lateinit var questionTexts: List<TextView>
    private lateinit var answerTexts: List<TextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize lists of views
        questionContainers = listOf(
            view.findViewById(R.id.question_container),
            view.findViewById(R.id.question_container1),
            view.findViewById(R.id.question_container2),
            view.findViewById(R.id.question_container3),
            view.findViewById(R.id.question_container4),
            // Add more if needed
        )
        questionTexts = listOf(
            view.findViewById(R.id.question_text),
            view.findViewById(R.id.question_text1),
            view.findViewById(R.id.question_text2),
            view.findViewById(R.id.question_text3),
            view.findViewById(R.id.question_text4)
            // Add more if needed
        )
        answerTexts = listOf(
            view.findViewById(R.id.answer_text),
            view.findViewById(R.id.answer_text1),
            view.findViewById(R.id.answer_text2),
            view.findViewById(R.id.answer_text3),
            view.findViewById(R.id.answer_text4)
            // Add more if needed
        )

        // Set click listeners for each question container
        questionContainers.forEachIndexed { index, container ->
            container.setOnClickListener {
                toggleAnswerVisibility(answerTexts[index])
            }
        }
    }

    private fun toggleAnswerVisibility(answerText: TextView) {
        val isExpanded = answerText.visibility == View.VISIBLE

        // Toggle the visibility of the answer text
        if (isExpanded) {
            answerText.visibility = View.GONE
        } else {
            answerText.visibility = View.VISIBLE
        }
    }
}
