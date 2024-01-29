import { Device } from "../models/device"
import query from "../util/query"

export default class DevicesService {

    static async allDevices() {
        return query((axios) => axios.get('devices'))
    }

    static async createDevice(input: Device) {
        return query((axios) => axios.post('devices', {
            name: input.name,
            userId: input.userId,
            model: input.model,
            phoneNumber: input.phoneNumber,
            status: input.status
        }))
    }

    static async updateDevice(id: number, input: Device) {
        return query((axios) => axios.put('devices/' + id, {
            name: input.name,
            userId: input.userId,
            model: input.model,
            phoneNumber: input.phoneNumber,
            status: input.status
        }))
    }

    static async deleteDevice(id: number) {
        return query((axios) => axios.delete('devices/' + id))
    }

    static async lockDevice(id: number) {
        return query((axios) => axios.patch('devices/' + id + "/lock"))
    }

    static async unlockDevice(id: number) {
        return query((axios) => axios.patch('devices/' + id + "/unlock"))
    }
}