package ui.anwesome.com.linkedbstepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.bstepview.BStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BStepView.create(this)
    }
}
