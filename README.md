# Glyph Toy

Nothing Phone (4a) Pro の Glyph Matrix（13×13・円形）用の自作 Glyph Toy。
現在はサンプルとして、時刻を表示する時計 Toy が入っています。

## 必要なもの

- Android Studio（または Android SDK + JDK 17）
- Nothing Phone (4a) Pro（USB デバッグを有効にしたもの）
- Glyph Matrix SDK（下記）

## セットアップ

1. **SDK を入手する**
   公式の [GlyphMatrix-Developer-Kit](https://github.com/Nothing-Developer-Programme/GlyphMatrix-Developer-Kit) から
   `glyph-matrix-sdk-2.0.aar` をダウンロードし、`app/libs/` に置きます。
   SDK はライセンス上再配布できないため、このリポジトリには含めていません（`.gitignore` で除外）。

2. **Android Studio でこのフォルダを開き、スマホに実行（Run）します。**

3. **Glyph 開発者モードを有効にする**（48時間で無効に戻ります）

   ```sh
   adb shell settings put global nt_glyph_interface_debug_enable 1
   ```

4. **スマホで Toy を選ぶ**
   設定 → Glyph Interface → Flip to Glyph → Always-on Glyph Toy で「Clock」を選択。

## 構成

| ファイル | 役割 |
|---|---|
| `ClockToyService.kt` | Toy 本体。Glyph サービスに接続し、約1分ごとの `EVENT_AOD` で再描画 |
| `MatrixCanvas.kt` | 13×13 のピクセル配列と 3×5 数字フォント。新しい表示はここに描画処理を足す |
| `MainActivity.kt` | 有効化手順を表示するだけの画面 |
| `AndroidManifest.xml` | Toy の登録（名前・アイコン・AOD 対応） |

## 4a Pro の制約

- マトリクスは 13×13（円形なので四隅は実在しない）
- AOD Toy のみ対応。Glyph Touch（背面ボタン操作）は非対応
- 描画はおおむね1分ごと（AOD イベントのタイミング）

## ライセンスについて

Glyph Matrix SDK は Nothing のライセンスに従います。SDK の商用利用は Nothing の事前の書面許可が必要です。
