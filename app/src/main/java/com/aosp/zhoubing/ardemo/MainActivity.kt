package com.aosp.zhoubing.ardemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    private var modelRenderable: ModelRenderable? = null
    private var arfragment: ArFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arfragment = supportFragmentManager.findFragmentById(R.id.ux) as ArFragment?
        ModelRenderable.builder().setSource(this, R.raw.andy).build()
            .thenAccept {modelRenderable = it}
            .exceptionally {
                Log.e("AR", "", it)
                return@exceptionally null
            }
        arfragment?.setOnTapArPlaneListener {
            hitResult, plane, motionEvent ->
            if (modelRenderable == null) {
                return@setOnTapArPlaneListener
            }
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arfragment?.arSceneView?.scene)
            val andy = TransformableNode(arfragment?.transformationSystem)
            andy.setParent(anchorNode)
            andy.renderable = modelRenderable
            andy.select()
        }
    }
}
