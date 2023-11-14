package tv.rasucc.ov.time.presentation

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tv.rasucc.ov.time.Constants
import tv.rasucc.ov.time.R
import tv.rasucc.ov.time.databinding.ActivityPlayBinding
import tv.rasucc.ov.time.presentation.model.Action
import tv.rasucc.ov.time.presentation.model.GameState
import tv.rasucc.ov.time.presentation.viewmodel.GameViewModel
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class PlayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayBinding

    @Inject
    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchingActivityAnimation()
        setClickListenners()
        addGameStateObservers()
    }

    private fun addGameStateObservers() {

        lifecycleScope.launch {
            gameViewModel.points.collect{
                binding.gamepoints.text = "Your points is $it"
            }
        }

        lifecycleScope.launch {
            gameViewModel.health.collect{
                setHealthAppearence(it)
            }
        }

        lifecycleScope.launch {
            gameViewModel.power.collect{
                setPowerAppearence(it)
            }
        }

        lifecycleScope.launch {
            gameViewModel.gameState.collect{
                if (it == GameState.GAME_OVER){
                    binding.gameOverLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setPowerAppearence(power: Long) {

        Log.d("123123", "The power is $power")

        val listOfPowerElements = listOf(
            binding.powerImg6,
            binding.powerImg5,
            binding.powerImg4,
            binding.powerImg3,
            binding.powerImg2,
            binding.powerImg1
        )

        when(power){

            1800L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_white)
            }

            1600L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_white)
            }

            1400L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_white)

            }

            1200L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_white)
            }

            1000L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_white)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_white)

            }

            800L -> {
                listOfPowerElements[5].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[4].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[3].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[2].setImageResource(R.drawable.baseline_rectangle_24_green)
                listOfPowerElements[1].setImageResource(R.drawable.baseline_rectangle_24_red)
                listOfPowerElements[0].setImageResource(R.drawable.baseline_rectangle_24_red)
            }
        }
    }

    private fun setHealthAppearence(health: Int) {

        val listOfHealthElements = listOf(
            binding.health6,
            binding.health5,
            binding.health4,
            binding.health3,
            binding.health2,
            binding.health1
        )

        when(health){
            in 0..1 -> {
                listOfHealthElements.forEach { healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
                gameViewModel.submitAction(Action.FinishGame)
            }

            in 2..9 -> {
                listOfHealthElements.take(5).forEach {healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
            }

            in 10..14 -> {
                listOfHealthElements.take(4).forEach {healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
            }
            in 15..19 -> {
                listOfHealthElements.take(3).forEach {healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
            }

            in 20..24 -> {
                listOfHealthElements.take(2).forEach {healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
            }

            in 25..30 -> {
                listOfHealthElements.take(1).forEach {healthElement ->
                    healthElement.alpha = Constants.weakAlpha
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setClickListenners() {

        binding.throwable.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    gameViewModel.submitAction(Action.StartIncreasingPower)
                }

                MotionEvent.ACTION_UP -> {
                    if (gameViewModel.gameState.value == GameState.PLAYING){
                        animateThrowableElement(binding.throwable)
                    }

                    gameViewModel.submitAction(Action.FinishIncreasingPower)
                }
            }
            true // Return false to indicate that you've not handled the event
        }

    }

    private fun animateThrowableElement(throwableElement: ImageView) {

        val currentPower = gameViewModel.power.value
        Log.d("123123", "Current power is $currentPower")

        throwableElement.animate().apply {
            duration = gameViewModel.power.value
            translationY(-binding.throwable.y)
            withEndAction{
                val aimRect = Rect()
                val throwableRect = Rect()

                binding.target.getGlobalVisibleRect(aimRect)
                binding.throwable.getGlobalVisibleRect(throwableRect)

                if (aimRect.intersect(throwableRect)){
                    //Success
                    gameViewModel.submitAction(Action.IncreasePoints)
                    showTheSuccessDecor()
                    throwableElement.translationY = 0F
                    animateAimDissapearing(binding.target)

                } else {
                    throwableElement.translationY = 0F
                    gameViewModel.submitAction(Action.DecreaseHealth)
                }
            }
        }
    }

    private fun animateAim(){
        // Get the screen width
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels
        binding.target.post {
            // Now we can get the width of the ImageView
            val imageViewWidth = binding.target.width

            // Calculate the distance to move
            val distance = screenWidth - imageViewWidth.toFloat()

            // Animate to the end of the screen
            binding.target.animate()
                .translationX(distance)
                .setDuration(1000) // Set the duration of the animation
                .withEndAction {
                    // Reverse the animation back to the start position
                    binding.target.animate()
                        .translationX(0f)
                        .setDuration(1000)
                        .withEndAction {
                            // Recursive call to create an infinite loop
                            animateAim()
                        }
                        .start()
                }
                .start()
        }
    }

    private fun showTheSuccessDecor() {
        lifecycleScope.launch {
            binding.lightOfWin.alpha = Constants.fullAlpha
            binding.complimenttv.text = getRandomCompliment()
            binding.complimenttv.visibility = View.VISIBLE
            delay(2000)
            binding.lightOfWin.alpha = Constants.zeroAlpha
            binding.complimenttv.visibility = View.INVISIBLE
        }
    }


    private fun animateAimDissapearing(aim: ImageView){
        aim.animate().apply {
            duration = 500
            alpha(0F)
            scaleX(4F)
            scaleY(4F)

            withEndAction {
                setNewAim()
            }
        }
    }

    private fun setNewAim() {
        binding.target.setImageResource(getRandomAimImage())
        binding.target.alpha = Constants.fullAlpha
        binding.target.scaleX = 1F
        binding.target.scaleY = 1F
    }

    private fun launchingActivityAnimation() {
        //health anim

        lifecycleScope.launch {
            delay(800)

            for (i in 5 downTo 0){
                delay(130)
                clearHealth(i)
            }

            delay(300)

            for (i in 0..5){
                delay(130)
                accumHealth(i)
            }

            gameViewModel.submitAction(Action.StartPlaying)
        }

        //power anim

        lifecycleScope.launch {
            delay(100)
            for (i in 0..5){
                delay(50)
                accumPower(i)
            }

            delay(100)

            animateAim()

            for (i in 5 downTo 0){
                delay(30)
                clearPower(i)
            }
        }
    }


    private fun accumPower(power: Int){
        when(power){
            0 -> { binding.powerImg1.setImageResource(R.drawable.baseline_rectangle_24_green) }
            1 -> { binding.powerImg2.setImageResource(R.drawable.baseline_rectangle_24_green) }
            2 -> { binding.powerImg3.setImageResource(R.drawable.baseline_rectangle_24_green) }
            3 -> { binding.powerImg4.setImageResource(R.drawable.baseline_rectangle_24_green) }
            4 -> { binding.powerImg5.setImageResource(R.drawable.baseline_rectangle_24_red) }
            5 -> { binding.powerImg6.setImageResource(R.drawable.baseline_rectangle_24_red) }
        }
    }

    private fun clearPower(power: Int){
        when(power){
            0 -> { binding.powerImg1.setImageResource(R.drawable.baseline_rectangle_24_white) }
            1 -> { binding.powerImg2.setImageResource(R.drawable.baseline_rectangle_24_white) }
            2 -> { binding.powerImg3.setImageResource(R.drawable.baseline_rectangle_24_white) }
            3 -> { binding.powerImg4.setImageResource(R.drawable.baseline_rectangle_24_white) }
            4 -> { binding.powerImg5.setImageResource(R.drawable.baseline_rectangle_24_white) }
            5 -> { binding.powerImg6.setImageResource(R.drawable.baseline_rectangle_24_white) }
        }
    }

    private fun accumHealth(health: Int){
        when(health){
            0 -> { binding.health1.alpha = Constants.fullAlpha }
            1 -> { binding.health2.alpha = Constants.fullAlpha }
            2 -> { binding.health3.alpha = Constants.fullAlpha }
            3 -> { binding.health4.alpha = Constants.fullAlpha }
            4 -> { binding.health5.alpha = Constants.fullAlpha }
            5 -> { binding.health6.alpha = Constants.fullAlpha }
        }
    }

    private fun clearHealth(health: Int){
        when(health){
            0 -> { binding.health1.alpha = Constants.weakAlpha }
            1 -> { binding.health2.alpha = Constants.weakAlpha }
            2 -> { binding.health3.alpha = Constants.weakAlpha }
            3 -> { binding.health4.alpha = Constants.weakAlpha }
            4 -> { binding.health5.alpha = Constants.weakAlpha }
            5 -> { binding.health6.alpha = Constants.weakAlpha }
        }
    }


    private fun getRandomAimImage() : Int {
        return Constants.listOfAimsImages[Random.nextInt(Constants.listOfAimsImages.size)]
    }

    private fun getRandomCompliment() : String {
        return Constants.listOfCompliments[Random.nextInt(Constants.listOfCompliments.size)]
    }
}