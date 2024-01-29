import { useState } from "react"
import { Device } from "../../models/device"
import DevicesService from "../../services/devices.service"
import requester from "../../util/requester"
import toast from "react-hot-toast"

export interface RemoteLockState {
    isLoading: boolean,
    devices: Device[],
    errorMessage: string | null,
}

export interface RemoteLockViewModel {
    state: RemoteLockState,
    getDevices: () => void,
    createDevice: (device: Device) => void,
    lockDevice: (device: Device) => void,
    unlockDevice: (device: Device) => void,
    deleteDevice: (device: Device) => void,
    updateDevice: (device: Device) => void,
}

export const useDeviceLockViewModel : () => RemoteLockViewModel = () => {
    

    const [state, setState] = useState<RemoteLockState>({
        isLoading: false,
        devices: [],
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

    const getDevices = () => {
        request(async () => {
            const result = await DevicesService.allDevices()
            return { devices: result.devices }
        })
    }

    const createDevice = async (device: Device) => {

        request(async () => {
            const created = await DevicesService.createDevice(device)
            return {
                devices: [...state.devices, created.device]
            }
        })
    }

    const lockDevice = async (device: Device) => {
        request(async () => {
            const locked = await DevicesService.lockDevice(device.id)
            return {
                devices: state.devices.map(item => item.id == locked.device.id ? locked.device : item)
            }
        })
    }

    const unlockDevice = async (device: Device) => {
        request(async () => {
            const unlocked = await DevicesService.unlockDevice(device.id)
            return {
                devices: state.devices.map(device => device.id == unlocked.device.id ? unlocked.device : device)
            }
        })
    }

    const deleteDevice = async (device: Device) => {

        request(async () => {
            const deleted = await DevicesService.deleteDevice(device.id)
            return {
                devices: state.devices.filter(device => device.id != deleted.device.id)
            }
        })
    }

    const updateDevice = async (device: Device) => {
        request(async () => {
            const updated = await DevicesService.updateDevice(device.id, device)
            return {
                devices: state.devices.map(device => device.id == updated.device.id ? updated.device : device)
            }
        })
    }

    return {
        state,
        getDevices,
        createDevice,
        updateDevice,
        deleteDevice,
        lockDevice,
        unlockDevice
    }
}