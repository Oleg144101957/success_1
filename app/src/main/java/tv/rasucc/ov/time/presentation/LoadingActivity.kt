package tv.rasucc.ov.time.presentation

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.rasucc.ov.time.Constants
import tv.rasucc.ov.time.R
import tv.rasucc.ov.time.data.repository.ConnectionManagerImpl
import tv.rasucc.ov.time.databinding.ActivityLoadingBinding

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoadingDecoration()
        checkNetworkConnection()
    }

    private fun checkNetworkConnection() {
        val connectionChecker = ConnectionManagerImpl(this)
        if (!connectionChecker.isNetworkAvailable){
            navigateToTheNoInternetConnectionScreen()
        }
    }

    private fun startLoadingDecoration() {
        lifecycleScope.launch {
            colorfulLoading()
            introWords()
            navigateToTheMenuScreen()
        }
    }

    private suspend fun colorfulLoading(){
        for (color in Constants.colorsList) {
            delay(100)
            binding.loadingtv.setTextColor(
                ContextCompat.getColor(
                    this@LoadingActivity,
                    color
                )
            )
        }
    }

    private suspend fun introWords(){
        for (phrase in Constants.helloWordsList){
            delay(800)
            binding.hellotv.text = phrase
        }
    }

    private suspend fun navigateToTheMenuScreen() {
        val intentToTheMenu = Intent(this, MenuActivity::class.java)
        delay(800)
        startActivity(intentToTheMenu)
    }

    private fun navigateToTheNoInternetConnectionScreen() {
        val intentToTheNoInternetConnection = Intent(this, NoConnectionActivity::class.java)
        startActivity(intentToTheNoInternetConnection)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}