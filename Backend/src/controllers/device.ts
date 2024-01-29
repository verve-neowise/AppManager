import { createDevice, findDevice, findDeviceByIdAndModel, findDeviceByUserIdAndModel, findDevices, removeDevice, setLockDevice, setUnlockDevice, updateDevice, updateDeviceStatus, updateDeviceSync } from '@services/device.service';
import catchAsync from '@utils/catchAsync';

export const getDevices = catchAsync(async (req, res, next) => {

    const devices = await findDevices()

    res.json({
        success: true,
        devices
    })
})

export const registerDevice = catchAsync(async (req, res, next) => {

    const { userId, deviceId, name, phone, model, value } = req.body

    const [lockSignature, unlockSignature] = value.split(":")
    const device = await findDeviceByIdAndModel(deviceId, model)

    if (device) {
        await updateDeviceSync(device.id, {
            userId,
            name,
            phone: String(phone),
            lockSignature,
            unlockSignature
        })
        return res.send(device.status)
    }
    else {
        const newDevice = await createDevice({
            userId,
            name,
            model,
            deviceId,
            phoneNumber: phone,
            status: "unlocked",
            lockSignature,
            unlockSignature
        })
        return res.send(newDevice.status)
    }
})

export const putDeviceStatus = catchAsync(async (req, res, next) => {

        const { model, id, status } = req.query

        const device = await findDeviceByIdAndModel(String(id), String(model))

        if (device) {
            await updateDeviceStatus(device.id, String(status) as ("locked" | "unlocked"))
        }
        else {
            res.send("Not found")
        }
})

export const postSyncDevice = catchAsync(async (req, res, next) => {

    // const { model, id, phone } = req.query
    // const { value } = req.body

    // const device = await findDeviceByUserIdAndModel(Number(id), String(model))

    // const [lockSignature, unlockSignature] = value.split(":")

    // if (device) {
    //     await updateDeviceSync(device.id, {
    //         phone: String(phone),
    //         lockSignature,
    //         unlockSignature
    //     })
    //     res.send(device?.status)
    // }
    // else {
    // }
    res.send("Not found")
})

export const postDevice = catchAsync(async (req, res, next) => {
    const { name, deviceId, userId, model, phoneNumber, status } = req.body

    const created = await createDevice({
        name,
        userId,
        model,
        deviceId,
        phoneNumber,
        status
    })

    res.json({
        success: true,
        device: created
    })
})

export const putDevice = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)
    const { name, userId, model, phoneNumber, status } = req.body

    const device = await findDevice(id)

    if (!device) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const updated = await updateDevice(id, {
        name,
        userId,
        model,
        phoneNumber,
        status
    })

    res.json({
        success: true,
        device: updated
    })
})


export const deleteDevice = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const device = await findDevice(id)

    if (!device) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const deleted = await removeDevice(id)

    res.json({
        success: true,
        device: deleted
    })
})


export const lockDevice = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const device = await findDevice(id)

    if (!device) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }
    
    const updated = await setLockDevice(id)

    res.json({
        success: true,
        device: updated
    })
})

export const unlockDevice = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const device = await findDevice(id)

    if (!device) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const updated = await setUnlockDevice(id)

    res.json({
        success: true,
        device: updated
    })
})