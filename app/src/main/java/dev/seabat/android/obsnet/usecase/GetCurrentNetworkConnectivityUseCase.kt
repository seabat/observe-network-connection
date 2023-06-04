package dev.seabat.android.obsnet.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class GetCurrentNetworkConnectivityUseCase(private val context: Context) : GetCurrentNetworkConnectivityUseCaseContract {
    override operator fun invoke(): NetworkConnectionState {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val connected = connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        return if (connected) {
            NetworkConnectionState.Available
        } else {
            NetworkConnectionState.Unavailable
        }
    }
}