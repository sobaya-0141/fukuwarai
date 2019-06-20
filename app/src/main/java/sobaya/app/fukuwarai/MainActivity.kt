package sobaya.app.fukuwarai

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import sobaya.app.fukuwarai_core.Fukuwarai

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    fun test() {
        val listener = object: Fukuwarai.Listener {
            override fun success(faces: List<com.google.firebase.ml.vision.face.FirebaseVisionFace>) {
                faces.forEach { face ->
                    if (face.smilingProbability > 80) {
                        Toast.makeText(this@MainActivity, "笑ってる", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun failure(exception: Exception) {}
            override fun complete(task: com.google.android.gms.tasks.Task<List<com.google.firebase.ml.vision.face.FirebaseVisionFace>>) {}
            override fun cancel() {}
        }
        val fukuwarai = Fukuwarai(listener = listener)
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.smile)
        fukuwarai.execute(bmp)
    }
}
