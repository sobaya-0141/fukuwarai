# fukuwarai
Easy to introduce ML Kit face recognition

# Download
```implementation 'sobaya.app.fukuwarai:fukuwarai-core:0.1.0'```

# Introduction
```
val listener = object: Fukuwarai.Listener {
    override fun success(faces: List<com.google.firebase.ml.vision.face.FirebaseVisionFace>) {
        faces.forEach { face ->
            if (face.smilingProbability > 80) {
                Toast.makeText(this@MainActivity, "Smile", Toast.LENGTH_SHORT).show()
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
```

# License
```
 Copyright 2019 sobaya

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```
