import { useState } from "react"
import requester from "../../util/requester"
import { AppVersion } from "../../models/appVersion"
import AppService from "../../services/app.service"
import toast from "react-hot-toast"

export interface AppVersionState {
    isLoading: boolean,
    apps: AppVersion[],
    errorMessage: string | null,
}

export interface AppVersionViewModel {
    state: AppVersionState,
    getApps: () => void,
    createApp: (app: AppVersion) => void,
    updateApp: (app: AppVersion) => void,
    deleteApp: (app: AppVersion) => void,
}

export const useAppVersionViewModel: () => AppVersionViewModel = () => {

    const [state, setState] = useState<AppVersionState>({
        isLoading: false,
        apps: [],
        errorMessage: null
    })

    const setLoading = () => {
        setState({
            ...state,
            isLoading: true
        })
    }

    const setResult = (data: {}) => {
        setState({
            ...state,
            isLoading: false,
            ...data
        })
    }

    const setError = (error: string) => {
        setState({
            ...state,
            isLoading: false,
            errorMessage: error
        })

        toast.error(error, {
            className: 'bg-default-100/80 text-white rounded-md text-sm'
        })
    }

    const request = requester(setResult, setError, setLoading)

    const getApps = () => request(async () => {
        const result = await AppService.allApps()
        return {
            apps: result.apps
        }
    })

    const createApp = (app: AppVersion) => request(async () => {
        const result = await AppService.createApp(app)
        return {
            apps: [ result.app, ...state.apps]
        }
    })

    const updateApp = (app: AppVersion) => request(async () => {
        const result = await AppService.updateApp(app.id, app)
        return {
            apps: state.apps.map(app => app.id == result.app.id ? result.app : app)
        }
    })

    const deleteApp = (app: AppVersion) => request(async () => {
        const result = await AppService.deleteApp(app.id)
        return {
            apps: state.apps.filter(app => app.id != result.app.id)
        }
    })

    return {
        state,
        getApps,
        createApp,
        deleteApp,
        updateApp
    }
}