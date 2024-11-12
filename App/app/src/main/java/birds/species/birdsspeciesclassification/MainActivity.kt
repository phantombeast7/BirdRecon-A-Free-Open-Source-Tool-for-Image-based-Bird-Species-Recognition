package birds.species.birdsspeciesclassification

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import birds.species.birdsspeciesclassification.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the action bar
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_faq,
                R.id.navigation_feedback
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val languages = arrayOf("Choose a language", "English", "हिन्दी", "తెలుగు")
        val localeOption = mapOf(
            "English" to "en",
            "हिन्दी" to "hi",
            "తెలుగు" to "te"
        )

        val spinner = findViewById<Spinner>(R.id.spinner)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = aa

        // Set the default language from the saved preference
        val defaultLanguage = sharedPreferences.getString("language", "")
        val defaultLanguageIndex = if (defaultLanguage.isNullOrEmpty()) 0 else languages.indexOf(defaultLanguage)
        spinner.setSelection(defaultLanguageIndex)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    val selectedLanguage = languages[position]
                    val selectedLocale = localeOption[selectedLanguage]

                    // Save the selected language to sharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("language", selectedLocale)
                    editor.apply()

                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(selectedLocale)
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}
