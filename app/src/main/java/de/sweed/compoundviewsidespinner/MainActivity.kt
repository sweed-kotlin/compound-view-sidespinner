package de.sweed.compoundviewsidespinner


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fruitsSpinner: Sidespinner
        fruitsSpinner = findViewById<View>(R.id.fruitsSidespinner) as Sidespinner

        val fruitList = arrayOf<CharSequence>(
            "Apple",
            "Orange",
            "Pear",
            "Grapes"
        )
        fruitsSpinner.setValues(fruitList)
//        fruitsSpinner.setSelectedIndex(1)
    }
}