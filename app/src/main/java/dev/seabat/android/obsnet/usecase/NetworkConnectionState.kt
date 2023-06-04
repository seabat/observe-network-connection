package dev.seabat.android.obsnet.usecase

sealed class NetworkConnectionState(val name: String) {

    object Available : NetworkConnectionState("Available")
    object Unavailable : NetworkConnectionState("Unavailable")
}
