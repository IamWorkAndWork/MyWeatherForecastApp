package com.example.domain.usecase

import com.example.domain.model.SettingModel
import com.example.repository.DataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface GetAppSettingUseCase {
    fun execute(): Flow<SettingModel>
}

class GetAppSettingUseCaseImpl(
    private val dataStoreRepository: DataStoreRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetAppSettingUseCase {

    override fun execute(): Flow<SettingModel> {

        return dataStoreRepository.getSetting()
            .flowOn(dispatcher)

    }

}