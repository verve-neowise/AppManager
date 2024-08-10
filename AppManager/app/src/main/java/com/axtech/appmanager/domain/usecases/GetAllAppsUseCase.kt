package com.axtech.appmanager.domain.usecases

import com.axtech.appmanager.domain.models.App
import com.axtech.appmanager.domain.repositories.IAppsRepository
import com.axtech.appmanager.util.NetworkFlow

class GetAllAppsUseCase(
    private val appsRepository: IAppsRepository
) {

    operator fun invoke(): NetworkFlow<List<App>> {
        return appsRepository.getAllApps()
    }
}