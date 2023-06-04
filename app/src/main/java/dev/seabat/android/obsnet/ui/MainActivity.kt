package dev.seabat.android.obsnet.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.seabat.android.obsnet.ui.theme.ObserveNetworkConnectionStateTheme
import dev.seabat.android.obsnet.usecase.GetCurrentNetworkConnectivityUseCase
import dev.seabat.android.obsnet.usecase.GetCurrentNetworkConnectivityUseCaseContract
import dev.seabat.android.obsnet.usecase.NetworkConnectionState
import dev.seabat.android.obsnet.usecase.ObserveNetworkConnectivityUseCase
import dev.seabat.android.obsnet.usecase.ObserveNetworkConnectivityUseCaseContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainActivity : ComponentActivity() {

    private lateinit var getCurrentConnectivityUseCase: GetCurrentNetworkConnectivityUseCaseContract
    private lateinit var observeConnectivityUseCase: ObserveNetworkConnectivityUseCaseContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getCurrentConnectivityUseCase = GetCurrentNetworkConnectivityUseCase(applicationContext)
        observeConnectivityUseCase = ObserveNetworkConnectivityUseCase(applicationContext, getCurrentConnectivityUseCase)

        setContent {
            ObserveNetworkConnectionStateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NetworkScreen(getCurrentConnectivityUseCase, observeConnectivityUseCase)
                }
            }
        }
    }
}

@Composable
fun NetworkScreen(
    getCurrentConnectivityUseCase: GetCurrentNetworkConnectivityUseCaseContract,
    observeConnectivityUseCase: ObserveNetworkConnectivityUseCaseContract,
    modifier: Modifier = Modifier
) {
    val connectivityState = produceConnectivityState(getCurrentConnectivityUseCase, observeConnectivityUseCase)
    val rememberState by remember { connectivityState }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Network State: ${rememberState.name}")
    }
}

@Composable
fun produceConnectivityState(
    getCurrentConnectivityUseCase: GetCurrentNetworkConnectivityUseCaseContract,
    observeConnectivityUseCase: ObserveNetworkConnectivityUseCaseContract,
): State<NetworkConnectionState> {
    // produceState は Composition をスコープとするコルーチンを起動し、State を返す。
    // Composition が退場するとコルーチンはキャンセルされる。
    return produceState(initialValue = getCurrentConnectivityUseCase()) {
        observeConnectivityUseCase().collect{
            value = it
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val getCurrentConnectivityUseCase = object : GetCurrentNetworkConnectivityUseCaseContract {
        override fun invoke(): NetworkConnectionState {
            return NetworkConnectionState.Unavailable
        }
    }
    val observeConnectivityUseCase = object : ObserveNetworkConnectivityUseCaseContract {
        override fun invoke(): Flow<NetworkConnectionState> {
            return flow {
                emit(NetworkConnectionState.Available)
            }
        }
    }

    ObserveNetworkConnectionStateTheme {
        NetworkScreen(getCurrentConnectivityUseCase, observeConnectivityUseCase)
    }
}
