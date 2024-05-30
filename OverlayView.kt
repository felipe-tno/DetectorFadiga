package com.google.mediapipe.examples.facelandmarker

/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import kotlin.math.max
import kotlin.math.min
import android.media.AudioManager;
import android.media.MediaPlayer;
import kotlin.reflect.typeOf

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: FaceLandmarkerResult? = null
    private var linePaint = Paint()
    private var pointPaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1

    private var yp1: Float = 0f
    private var yp2: Float = 0f

    private var tempoOlhosFechados: Long = 0
    private val intervaloCansado = 800000 //1 segundo = 100000

    private var mediaPlayer: MediaPlayer? = null

    init {
        initPaints()
        initMediaPlayer(context)
    }

    private fun initMediaPlayer(context: Context?) {
        //
    }

    fun clear() {
        results = null
        linePaint.reset()
        pointPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_primary)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.YELLOW
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if(results == null || results!!.faceLandmarks().isEmpty()) {
            clear()
            return
        }

        results?.let { faceLandmarkerResult ->

            for(landmark in faceLandmarkerResult.faceLandmarks()) {
                for(normalizedLandmark in landmark) {
                    canvas.drawPoint(normalizedLandmark.x() * imageWidth * scaleFactor, normalizedLandmark.y() * imageHeight * scaleFactor, pointPaint)
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

    private fun playSound() {
        mediaPlayer?.start()
    }

    fun setResults(
        faceLandmarkerResults: FaceLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = faceLandmarkerResults

        println(results)
        // Printing the landmarks with their indices
        results?.let { faceLandmarkerResult ->
            for ((landmarkIndex, landmark) in faceLandmarkerResult.faceLandmarks().withIndex()) {
                for ((pointIndex, normalizedLandmark) in landmark.withIndex()) {
                    val x = normalizedLandmark.x()
                    val y = normalizedLandmark.y()
                    val z = normalizedLandmark.z()
                    val indexDesejado1 = 145
                    val indexDesejado2 = 159
                    val escolha = true

                    if(pointIndex == indexDesejado1 || pointIndex == indexDesejado2 || escolha) {

                        println("Point $pointIndex - x: $x, y: $y, z: $z")
                        if(pointIndex == indexDesejado1) {
                            yp1 = y;
                        } else if(pointIndex == indexDesejado2) {
                            yp2 = y;
                        }

                        val diff = yp1 - yp2;
                        if (diff < 0.009f) {
                            tempoOlhosFechados += 2000
                            println("Fechou o Olho!")
                            if (tempoOlhosFechados >= intervaloCansado) {
                                println("Você está fadigado")
                                playSound()
                                // Reseta o tempo
                                tempoOlhosFechados = 0
                            }

                        } else {
                            tempoOlhosFechados = 0
                            println("Abriu o Olho!")
                        }
                    }
                }
            }
        }


        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            }
            RunningMode.LIVE_STREAM -> {
                // PreviewView is in FILL_START mode. So we need to scale up the
                // landmarks to match with the size that the captured images will be
                // displayed.
                max(width * 1f / imageWidth, height * 1f / imageHeight)
            }
        }
        invalidate()

    }
    /*override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Liberar recursos do MediaPlayer quando a View é removida da janela
        mediaPlayer?.release()
        mediaPlayer = null
    }*/

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 8F
        private const val TAG = "Face Landmarker Overlay"
    }
}
