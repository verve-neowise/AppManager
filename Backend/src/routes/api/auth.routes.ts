import { login, register } from '@controllers/auth'
import { loginSchema, registerSchema } from '@schemas/auth'
import { Router } from 'express'
import { createValidator } from 'express-joi-validation'

const validator = createValidator()

export default Router({ mergeParams: true })
    .post('/login', validator.body(loginSchema), login)
    .post('/register', validator.body(registerSchema), register)