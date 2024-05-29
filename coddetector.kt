package com.google.mediapipe.examples.facelandmarker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import kotlin.math.max
import kotlin.math.min

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: FaceLandmarkerResult? = null
    private var linePaint = Paint()
    private var pointPaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1

    init {
        initPaints()
    }

    fun clear() {
        results = null
        linePaint.reset()
        pointPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color = Color.GREEN
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.YELLOW
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (results == null || results!!.faceLandmarks().isEmpty()) {
            clear()
            return


        }

        results?.let { faceLandmarkerResult ->
            
            val lips = arrayOf(
                floatArrayOf(61f, 146f),
                floatArrayOf(91f, 181f),
                floatArrayOf(84f, 17f),
                floatArrayOf(314f, 405f),
                floatArrayOf(321f, 375f),
                floatArrayOf(291f, 308f),
                floatArrayOf(324f, 318f),
                floatArrayOf(402f, 317f),
                floatArrayOf(14f, 87f),
                floatArrayOf(178f, 88f),
                floatArrayOf(95f, 185f),
                floatArrayOf(40f, 39f),
                floatArrayOf(37f, 0f),
                floatArrayOf(267f, 269f),
                floatArrayOf(270f, 409f),
                floatArrayOf(415f, 310f),
                floatArrayOf(311f, 312f),
                floatArrayOf(13f, 82f),
                floatArrayOf(81f, 42f),
                floatArrayOf(183f, 78f)
            )

            val upperLowerLips = arrayOf(
                floatArrayOf(13f, 14f),
                floatArrayOf(39f, 27f),
                floatArrayOf(35f, 183f)
            )

            for (point in lips) {
                canvas.drawPoint(point[0] * imageWidth * scaleFactor, point[1] * imageHeight * scaleFactor, pointPaint)
            }

            for (point in upperLowerLips) {
                canvas.drawPoint(point[0] * imageWidth * scaleFactor, point[1] * imageHeight * scaleFactor, pointPaint)
            }

            for (point in upperLowerLips) {
                val x = point[0] * imageWidth * scaleFactor
                val y = point[1] * imageHeight * scaleFactor

                if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                    triggerAlarm()
                    println("Fadigou")
                    break
                }
            }

            FaceLandmarker.FACE_LANDMARKS_CONNECTORS.forEach {
                canvas.drawLine(
                    faceLandmarkerResult.faceLandmarks().get(0).get(it!!.start()).x() * imageWidth * scaleFactor,
                    faceLandmarkerResult.faceLandmarks().get(0).get(it.start()).y() * imageHeight * scaleFactor,
                    faceLandmarkerResult.faceLandmarks().get(0).get(it.end()).x() * imageWidth * scaleFactor,
                    faceLandmarkerResult.faceLandmarks().get(0).get(it.end()).y() * imageHeight * scaleFactor,
                    linePaint)
            }
        }
    }

    fun setResults(
        faceLandmarkerResults: FaceLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = faceLandmarkerResults
        println(results)
        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            }
            RunningMode.LIVE_STREAM -> {
                max(width * 1f / imageWidth, height * 1f / imageHeight)
            }
        }
        invalidate()
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 8F
        private const val TAG = "Face Landmarker Overlay"
        private const val minX = 20
        private const val maxX = 780
        private const val minY = 0
        private const val maxY = 600
    }

    private fun triggerAlarm() {
        // Coloque aqui a lógica para acionar o alarme
    }


}
