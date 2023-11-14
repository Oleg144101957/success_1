package tv.rasucc.ov.time.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.rasucc.ov.time.Constants
import tv.rasucc.ov.time.databinding.ActivitySavedGamesBinding
import tv.rasucc.ov.time.presentation.customUI.SavedGamesWebView

@AndroidEntryPoint
class SavedGamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedGamesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedGamesWebView = SavedGamesWebView(this)

        lifecycleScope.launch {
            for (i in Constants.loadingContentList){
                delay(1000)
                binding.loadingtextview.text = i
            }

            delay(1500)
            binding.progressbar.visibility = View.INVISIBLE
            binding.root.addView(savedGamesWebView)
        }
    }
}