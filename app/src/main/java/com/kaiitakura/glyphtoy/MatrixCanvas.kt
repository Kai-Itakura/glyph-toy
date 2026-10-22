package com.kaiitakura.glyphtoy

/**
 * 4a Pro の Glyph Matrix（13×13）に描くためのピクセル配列。
 * 値は明るさで 0（消灯）〜 255（最大）。
 *
 * 実機の Matrix は円形なので、四隅のピクセルは物理的に存在しない。
 * 端まで描くときは isVisible() で確認すること。
 */
class MatrixCanvas {

    val pixels = IntArray(SIZE * SIZE)

    fun set(x: Int, y: Int, brightness: Int = 255) {
        if (x in 0 until SIZE && y in 0 until SIZE) {
            pixels[y * SIZE + x] = brightness.coerceIn(0, 255)
        }
    }

    fun clear() = pixels.fill(0)

    /** 2桁の数字を横中央に描く（3px の数字 + 1px の隙間 + 3px の数字）。 */
    fun drawNumber(value: Int, y: Int, brightness: Int = 255) {
        drawDigit(value / 10 % 10, x = 3, y = y, brightness = brightness)
        drawDigit(value % 10, x = 7, y = y, brightness = brightness)
    }

    fun drawDigit(digit: Int, x: Int, y: Int, brightness: Int = 255) {
        val rows = FONT_3X5[digit]
        for (row in 0 until 5) {
            for (col in 0 until 3) {
                if ((rows[row] shr (2 - col)) and 1 == 1) {
                    set(x + col, y + row, brightness)
                }
            }
        }
    }

    companion object {
        const val SIZE = 13

        /** 円形マトリクス上に実在するピクセルかどうか（概算）。 */
        fun isVisible(x: Int, y: Int): Boolean {
            val c = (SIZE - 1) / 2.0
            val dx = x - c
            val dy = y - c
            return dx * dx + dy * dy <= (c + 0.5) * (c + 0.5)
        }

        /** 3×5 の数字フォント。各行は 3bit（左が上位ビット）。 */
        private val FONT_3X5 = arrayOf(
            intArrayOf(0b111, 0b101, 0b101, 0b101, 0b111), // 0
            intArrayOf(0b010, 0b110, 0b010, 0b010, 0b111), // 1
            intArrayOf(0b111, 0b001, 0b111, 0b100, 0b111), // 2
            intArrayOf(0b111, 0b001, 0b111, 0b001, 0b111), // 3
            intArrayOf(0b101, 0b101, 0b111, 0b001, 0b001), // 4
            intArrayOf(0b111, 0b100, 0b111, 0b001, 0b111), // 5
            intArrayOf(0b111, 0b100, 0b111, 0b101, 0b111), // 6
            intArrayOf(0b111, 0b001, 0b010, 0b010, 0b010), // 7
            intArrayOf(0b111, 0b101, 0b111, 0b101, 0b111), // 8
            intArrayOf(0b111, 0b101, 0b111, 0b001, 0b111), // 9
        )
    }
}
