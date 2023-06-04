# observe-network-connectivity

Kotlin Flow を使ってネットワーク接続状態を監視する


## ソフトウェアアーキテクチャ

* View - UseCase


## Dependencies

* Kotlin Flow  
    * [callbackFlow](https://developer.android.com/kotlin/flow?hl=ja#callback)  
        コールバックベースの API を Flow に変換する  

* Jetpack Compose
    * [State](https://developer.android.com/jetpack/compose/state?hl=ja#state-in-composables)
    * [remember](https://developer.android.com/jetpack/compose/state?hl=ja#state-in-composables)
    * [produceState](https://developer.android.com/jetpack/compose/side-effects?hl=ja#producestate)

* [ConnectivityManager](https://developer.android.com/training/basics/network-ops/reading-network-state?hl=ja)  
    システムの接続状態をアプリに通知


## 画面のスクリーンショット

<img src="images/top_screenshot.png" width="200">
