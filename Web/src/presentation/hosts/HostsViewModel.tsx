import { useState } from "react"
import { Host } from "../../models/host"
import HostService from "../../services/hosts.service"
import requester from "../../util/requester"
import toast from "react-hot-toast"

export interface HostsState {
    isLoading: boolean,
    hosts: Host[],
    errorMessage: string | null,
}

export interface CreateHostState {
    host?: Host,
    hostName: string,
    baseUrl: string,
    filesUrl: string,
    active: boolean,
}

export interface HostsViewModel {
    state: HostsState,
    getHosts: () => void,
    createHost: (host: Host) => void,
    updateHost: (host: Host) => void,
    deleteHost: (host: Host) => void,
}

export const useHostsViewModel: () => HostsViewModel = () => {

    const [state, setState] = useState<HostsState>({
        isLoading: false,
        hosts: [],
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

    const getHosts = () => request(async () => {
        const result = await HostService.allHost()
        return {
            hosts: result.hosts
        }
    })

    const createHost = (host: Host) => request(async () => {
        const hosts = [...state.hosts] // why i must copy initial array??? otherwise i receive duplicates
        const result = await HostService.createHost(host)
        return {
            hosts: [...hosts, result.host]
        }
    })

    const updateHost = (host: Host) => request(async () => {
        const result = await HostService.updateHost(host)
        return {
            hosts: state.hosts.map(host => host.id == result.host.id ? result.host : host)
        }
    })

    const deleteHost = (host: Host) => request(async () => {
        const result = await HostService.deleteHost(host.id)
        return {
            hosts: state.hosts.filter(host => host.id != result.host.id)
        }
    })

    return {
        state,
        getHosts,
        createHost,
        deleteHost,
        updateHost
    }
}