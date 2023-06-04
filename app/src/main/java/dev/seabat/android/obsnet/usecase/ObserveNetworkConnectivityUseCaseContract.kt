package dev.seabat.android.obsnet.usecase

import kotlinx.coroutines.flow.Flow

interface ObserveNetworkConnectivityUseCaseContract {
    operator fun invoke() : Flow<NetworkConnectionState>
}