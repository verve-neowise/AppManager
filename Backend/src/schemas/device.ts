import joi from 'joi'

export const deviceSchema = joi.object({
    "name": joi.string().min(3).required(),
    "userId": joi.number().min(3).required(),
    "model": joi.string().min(3).required(),
    "phoneNumber": joi.string().min(3).required(),
    "status": joi.string().valid('locked', "unlocked")
})

export const statusSchema = joi.object({
    "id": joi.number().min(3).required(),
    "model": joi.string().min(3).required(),
    "status": joi.string().valid("locked", "unlocked").required(),
})

export const syncSchema = joi.object({
    value: joi.string().min(3).required()
})

export const registerDeviceSchema = joi.object({
    userId: joi.number().required(),
    name: joi.string().min(3).required(),
    phone: joi.string().min(3).required(),
    model: joi.string().min(3).required(),
    deviceId: joi.string().required(),
    value: joi.string().min(3).required()
})