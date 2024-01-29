export interface Device {
    id: number
    name: string
    userId: string
    model: string
    phoneNumber: string
    lastSync: string
    status: DeviceStatus
    lockSignature: string | undefined
    unlockSignature: string | undefined
}

export enum DeviceStatus {
    Locked = "locked",
    Unlocked = "unlocked"
}

export interface DeviceResponse {
    success: boolean,
    devices: []
}