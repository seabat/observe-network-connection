package dev.seabat.android.obsnet.usecase

interface NetworkConnectionState {
    sealed interface NetworkConnectionState {
        object Available : NetworkConnectionState
        object Unavailable : NetworkConnectionState
    }
}