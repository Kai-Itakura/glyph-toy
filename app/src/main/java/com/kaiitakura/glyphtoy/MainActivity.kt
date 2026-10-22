package com.kaiitakura.glyphtoy

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

/** Toy の有効化手順を表示するだけの画面。 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (24 * resources.displayMetrics.density).toInt()
        setContentView(TextView(this).apply {
            setText(R.string.how_to_enable)
            textSize = 16f
            setPadding(pad, pad, pad, pad)
        })
    }
}
