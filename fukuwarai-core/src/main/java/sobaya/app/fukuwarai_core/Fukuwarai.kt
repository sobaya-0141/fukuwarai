package sobaya.app.fukuwarai_core

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import java.nio.ByteBuffer

class Fukuwarai {

    val options: FirebaseVisionFaceDetectorOptions
    val detector: FirebaseVisionFaceDetector
    val listener: Listener

    constructor(options: FirebaseVisionFaceDetectorOptions, listener: Listener) {
        this.options = options
        detector = FirebaseVision.getInstance()
            .getVisionFaceDetector(options)
        this.listener = listener
    }

    constructor(performanceMode: Int = FirebaseVisionFaceDetectorOptions.FAST,
                landmarkMode: Int = FirebaseVisionFaceDetectorOptions.NO_LANDMARKS,
                classificationMode: Int = FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS,
                contourMode: Int = FirebaseVisionFaceDetectorOptions.NO_CONTOURS,
                listener: Listener) {
        options = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(performanceMode)
            .setLandmarkMode(landmarkMode)
            .setClassificationMode(classificationMode)
            .setContourMode(contourMode)
            .build()
        detector = FirebaseVision.getInstance()
            .getVisionFaceDetector(options)
        this.listener = listener
    }

    fun execute(bitmap: Bitmap) =
        execute(FirebaseVisionImage.fromBitmap(bitmap))

    fun execute(context: Context, uri: Uri) =
        FirebaseVisionImage.fromFilePath(context, uri)

    fun execute(byteArray: ByteArray, metadata: FirebaseVisionImageMetadata) =
        FirebaseVisionImage.fromByteArray(byteArray, metadata)

    fun execute(byteBuffer: ByteBuffer, metadata: FirebaseVisionImageMetadata) =
        FirebaseVisionImage.fromByteBuffer(byteBuffer, metadata)

    fun execute(image: Image, rotation: Int) =
        FirebaseVisionImage.fromMediaImage(image, rotation)

    fun execute(image: FirebaseVisionImage) {
        detector.detectInImage(image)
            .addOnSuccessListener {
                listener.success(it)
            }
            .addOnFailureListener {
                listener.failure(it)
            }
            .addOnCanceledListener {
                listener.cancel()
            }
            .addOnCompleteListener {
                listener.complete(it)
            }
    }

    interface Listener {
        fun success(faces: List<FirebaseVisionFace>)
        fun failure(exception: Exception)
        fun complete(task: Task<List<FirebaseVisionFace>>)
        fun cancel()
    }
}