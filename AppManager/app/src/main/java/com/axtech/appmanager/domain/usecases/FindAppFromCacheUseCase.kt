package com.axtech.appmanager.domain.usecases

import com.axtech.appmanager.domain.repositories.ILocalCacheRepository

class FindAppFromCacheUseCase(
    private val localCacheRepository: ILocalCacheRepository
) {

    operator fun invoke(filename: String?): String? {
        return if (filename == null) {
            return null
        } else localCacheRepository.findApp(filename)
    }
}