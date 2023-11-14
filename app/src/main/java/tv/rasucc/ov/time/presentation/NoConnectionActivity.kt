package tv.rasucc.ov.time.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tv.rasucc.ov.time.databinding.ActivityNoConnectionBinding


@AndroidEntryPoint
class NoConnectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackToTheLoadingScreen.setOnClickListener {
            val intentToTheLoadingScreen = Intent(this, LoadingActivity::class.java)
            startActivity(intentToTheLoadingScreen)
        }
    }
}