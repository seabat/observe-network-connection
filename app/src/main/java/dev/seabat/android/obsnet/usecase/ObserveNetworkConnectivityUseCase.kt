package dev.seabat.android.obsnet.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ObserveNetworkConnectivityUseCase(
    private val context: Context,
    private val getCurrentNetworkConnectivityUseCase: GetCurrentNetworkConnectivityUseCaseContract
    ) : ObserveNetworkConnectivityUseCaseContract {

    override operator fun invoke() : Flow<NetworkConnectionState> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkConnectionState.Available)
            }
            override fun onUnavailable() {
                trySend(NetworkConnectionState.Unavailable)
            }
            override fun onLost(network: Network) {
                trySend(NetworkConnectionState.Unavailable)
            }
        }

        trySend(getCurrentNetworkConnectivityUseCase())

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}