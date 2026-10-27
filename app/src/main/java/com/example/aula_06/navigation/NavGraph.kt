package com.example.aula_06.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.aula_06.auth.SessaoUsuario
import com.example.aula_06.screens.*
import com.example.aula_06.ui.viewmodel.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val entradaAtual by navController.currentBackStackEntryAsState()
    val destino = entradaAtual?.destination
    val usuarioAtual = SessaoUsuario.usuarioId
    // Mesmo controle de acesso de AppNavigation na aula 14.
    LaunchedEffect(destino, usuarioAtual) {
        if (destino != null && !destino.hasRoute<Login>() && usuarioAtual == null) {
            navController.navigate(Login) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    Scaffold(topBar = {
        // As duas abas Entrar/Cadastrar pertencem a rota publica Login.
        if (usuarioAtual != null && destino != null && !destino.hasRoute<Login>()) {
            Menu(aoReceitas = {
                navController.navigate(Home) { popUpTo<Home>(); launchSingleTop = true }
            }, aoCompras = {
                navController.navigate(ListaCompras) { popUpTo<Home>(); launchSingleTop = true }
            }, aoSair = SessaoUsuario::encerrar)
        }
    }) { padding ->
        NavHost(navController, startDestination = Login, modifier = Modifier.padding(padding)) {
            composable<Login> {
                val vm: AuthViewModel = viewModel()
                LoginScreen(authViewModel = vm, aoEntrar = {
                    navController.navigate(Home) { popUpTo<Login> { inclusive = true } }
                })
            }
            composable<Home> { entrada ->
                if (usuarioAtual != null) {
                    val vm: ReceitaViewModel = viewModel()
                    DisposableEffect(entrada) {
                        val observador = LifecycleEventObserver { _, evento ->
                            if (evento == Lifecycle.Event.ON_RESUME) vm.listar()
                        }
                        entrada.lifecycle.addObserver(observador)
                        onDispose { entrada.lifecycle.removeObserver(observador) }
                    }
                    HomeScreen(viewModel = vm,
                        aoClicarReceita = { navController.navigate(DetalheReceita(it)) },
                        aoNovaReceita = { navController.navigate(CadastroReceita()) })
                }
            }
            composable<DetalheReceita> { entrada ->
                if (usuarioAtual != null) {
                    val rota = entrada.toRoute<DetalheReceita>()
                    val vm: ReceitaViewModel = viewModel()
                    DisposableEffect(entrada) {
                        val observador = LifecycleEventObserver { _, evento ->
                            if (evento == Lifecycle.Event.ON_RESUME) vm.carregar(rota.id)
                        }
                        entrada.lifecycle.addObserver(observador)
                        onDispose { entrada.lifecycle.removeObserver(observador) }
                    }
                    DetalheReceitaScreen(viewModel = vm,
                        aoEditar = { navController.navigate(CadastroReceita(it)) },
                        aoVoltar = { navController.popBackStack() })
                }
            }
            composable<CadastroReceita> { entrada ->
                if (usuarioAtual != null) {
                    val vm: ReceitaViewModel = viewModel()
                    CadastroReceitaScreen(viewModel = vm, receitaId = entrada.toRoute<CadastroReceita>().id,
                        aoSalvar = { navController.popBackStack() }, aoVoltar = { navController.popBackStack() })
                }
            }
            composable<ListaCompras> { entrada ->
                if (usuarioAtual != null) {
                    val vm: ListaComprasViewModel = viewModel()
                    DisposableEffect(entrada) {
                        val observador = LifecycleEventObserver { _, evento ->
                            if (evento == Lifecycle.Event.ON_RESUME) vm.listar()
                        }
                        entrada.lifecycle.addObserver(observador)
                        onDispose { entrada.lifecycle.removeObserver(observador) }
                    }
                    ListaComprasScreen(viewModel = vm,
                        aoNovo = { navController.navigate(CadastroItemCompra()) },
                        aoEditar = { navController.navigate(CadastroItemCompra(it)) })
                }
            }
            composable<CadastroItemCompra> { entrada ->
                if (usuarioAtual != null) {
                    val vm: ListaComprasViewModel = viewModel()
                    CadastroItemCompraScreen(viewModel = vm, itemId = entrada.toRoute<CadastroItemCompra>().id,
                        aoVoltar = { navController.popBackStack() })
                }
            }
        }
    }
}
