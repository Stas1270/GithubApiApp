package com.stas1270.githubapi.ui.repolist

import com.stas1270.githubapi.data.local.model.Success
import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.ui.base.BaseViewModel
import com.stas1270.githubapi.ui.model.RepoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val reposRepository: ReposRepository
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(MainViewState())
    val viewState
        get() = _viewState.asStateFlow()

    private val _viewStateIsLoading = MutableStateFlow(false)
    val viewStateIsLoading
        get() = _viewStateIsLoading.asStateFlow()

    fun search(searchQuery: String) {
        _viewStateIsLoading.value = true
        launch(Dispatchers.IO) {
            reposRepository.getRepos(searchQuery)
                .collectLatest { result ->
                    _viewStateIsLoading.value = false
                    _viewState.update {
                        it.copy(
                            list = result.data,
                            isSuccess = result.status == Success
                        )
                    }
                }
        }
    }
}

data class MainViewState(
    val list: List<RepoModel> = emptyList(),
    val isSuccess: Boolean = true
)