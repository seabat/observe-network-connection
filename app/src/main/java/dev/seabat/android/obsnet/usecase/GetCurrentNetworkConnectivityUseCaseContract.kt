package dev.seabat.android.obsnet.usecase

interface GetCurrentNetworkConnectivityUseCaseContract {
    operator fun invoke(): NetworkConnectionState
}