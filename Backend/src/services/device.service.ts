import { DeviceStatus } from '@prisma/client'
import prisma from './db'

interface CreateParams {
    name: string
    userId: number
    deviceId?: string
    model: string
    phoneNumber: string
    status: DeviceStatus
    lockSignature?: string
    unlockSignature?: string
}

interface SyncParams {
    name: string
    userId: number
    phone: string
    lockSignature: string
    unlockSignature: string
}

export const findDevices = () => {
    return prisma.device.findMany()
}

export const updateDeviceSync = (id: number, params: SyncParams) => {
    return prisma.device.update({
        where: {
            id
        },
        data: {
            userId: params.userId,
            name: params.name,
            phoneNumber: params.phone,
            lockSignature: params.lockSignature,
            unlockSignature: params.unlockSignature,
            lastSync: new Date()
        }
    })
}

export const findDevice = (id: number) => {
    return prisma.device.findUnique({
        where: {
            id
        }
    })
}

export const findDeviceByUserIdAndModel = (userId: number, model: string) => {
    return prisma.device.findFirst({
        where: {
            AND: [
                { model },
                { userId: userId }
            ]
        }
    })
}

export const findDeviceByIdAndModel = (deviceId: string, model: string) => {
    return prisma.device.findFirst({
        where: {
            AND: [
                { model },
                { deviceId }
            ]
        }
    })
}

export const createDevice = (params: CreateParams) => {
    return prisma.device.create({
        data: {
            name: params.name,
            userId: params.userId,
            model: params.model,
            deviceId: params.deviceId ?? "",
            phoneNumber: params.phoneNumber,
            status: params.status
        }
    })
}

export const updateDevice = async (id: number, params: CreateParams) => {

    return prisma.device.update({
        where: {
            id
        },
        data: {
            name: params.name,
            userId: params.userId,
            phoneNumber: params.phoneNumber,
            status: params.status
        }
    })
}


export const updateDeviceStatus = async (id: number, status: DeviceStatus) => {

    return prisma.device.update({
        where: {
            id
        },
        data: {
            status
        }
    })
}

export const removeDevice = (id: number) => {
    return prisma.device.delete({
        where: {
            id
        }
    })
}

export const setLockDevice = (id: number) => {
    return prisma.device.update({
        where: {
            id
        },
        data: {
            status: 'locked'
        }
    })
}

export const setUnlockDevice = (id: number) => {
    return prisma.device.update({
        where: {
            id
        },
        data: {
            status: 'unlocked'
        }
    })
}