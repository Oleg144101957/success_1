package tv.rasucc.ov.time.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tv.rasucc.ov.time.databinding.ActivityMenuBinding
import tv.rasucc.ov.time.domain.storage.PointsStorage
import javax.inject.Inject


@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    @Inject
    lateinit var pointsStorage: PointsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBtnListenners()
    }

    override fun onResume() {
        super.onResume()
        val bestPoints = pointsStorage.getBestPoints()
        binding.bestPoints.text = "Your best points is: $bestPoints"
    }

    private fun setBtnListenners() {

        binding.btnPlay.setOnClickListener {
            val intentToThePlayScreen = Intent(this, PlayActivity::class.java)
            startActivity(intentToThePlayScreen)
        }

        binding.btnSavedGames.setOnClickListener {
            val intentToTheSavedGamesScreen = Intent(this, SavedGamesActivity::class.java)
            startActivity(intentToTheSavedGamesScreen)
        }

        binding.btnOut.setOnClickListener {
            finishAffinity()
        }
    }
}