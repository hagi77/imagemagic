package eu.hagisoft.imgmagic.features.modifyimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.hagisoft.imgmagic.R
import eu.hagisoft.imgmagic.features.modifyimage.ui.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}