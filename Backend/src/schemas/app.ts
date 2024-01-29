import joi from 'joi'

export const appSchema = joi.object({
    name: joi.string().min(3).required(), 
    versionCode: joi.string().min(3).required(), 
    versionNumber: joi.string().min(0).required(), 
    tags: joi.string().min(0).default(""), 
    flag: joi.string().valid("production","feature", "bugfix").required(), 
    changelog: joi.string().min(3).required(), 
})