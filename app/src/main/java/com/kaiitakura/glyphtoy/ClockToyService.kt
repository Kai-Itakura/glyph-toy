package com.kaiitakura.glyphtoy

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Messenger
import android.util.Log
import com.nothing.ketchum.Glyph
import com.nothing.ketchum.GlyphMatrixManager
import com.nothing.ketchum.GlyphToy
import java.util.Calendar

/**
 * サンプルの Glyph Toy：13×13 のマトリクスに「時」と「分」を表示する時計。
 *
 * 4a Pro では AOD Toy として動き、選択中はシステムから
 * 約1分ごとに EVENT_AOD が届くので、そのたびに描き直す。
 */
class ClockToyService : Service() {

    private var gm: GlyphMatrixManager? = null
    private var connected = false

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == GlyphToy.MSG_GLYPH_TOY) {
            val event = msg.data.getString(GlyphToy.MSG_GLYPH_TOY_DATA)
            if (event == GlyphToy.EVENT_AOD || event == GlyphToy.EVENT_CHANGE) {
                draw()
            }
            true
        } else {
            false
        }
    }
    private val messenger = Messenger(handler)

    override fun onBind(intent: Intent?): IBinder {
        val manager = GlyphMatrixManager.getInstance(applicationContext)
        gm = manager
        manager.init(object : GlyphMatrixManager.Callback {
            override fun onServiceConnected(name: ComponentName?) {
                manager.register(Glyph.DEVICE_25111p)
                connected = true
                draw()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                connected = false
            }
        })
        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        try {
            gm?.turnOff()
            gm?.unInit()
        } catch (e: Exception) {
            Log.w(TAG, "unInit failed", e)
        }
        gm = null
        connected = false
        return false
    }

    private fun draw() {
        val manager = gm ?: return
        if (!connected) return

        val now = Calendar.getInstance()
        val canvas = MatrixCanvas()
        canvas.drawNumber(now.get(Calendar.HOUR_OF_DAY), y = 1)
        canvas.drawNumber(now.get(Calendar.MINUTE), y = 7)

        try {
            manager.setMatrixFrame(canvas.pixels)
        } catch (e: Exception) {
            Log.w(TAG, "setMatrixFrame failed", e)
        }
    }

    companion object {
        private const val TAG = "ClockToy"
    }
}
