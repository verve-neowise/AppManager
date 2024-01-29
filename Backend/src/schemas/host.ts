import joi from 'joi'

export const hostSchema = joi.object({
    "hostName": joi.string().min(3).required(), 
    "description": joi.string().min(3).required(), 
    "baseUrl": joi.string().min(3).required(), 
    "filesUrl": joi.string().min(3).required(), 
    "active": joi.boolean().required(), 
    "accounts": joi.array().items(joi.object({
        "email": joi.string().email().required(),
        "password": joi.string().required(),
    })).required()
})