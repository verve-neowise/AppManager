import { deleteDevice, postSyncDevice, getDevices, lockDevice, postDevice, putDevice, unlockDevice, registerDevice, putDeviceStatus } from '@controllers/device'
import appProtected from '@middlewares/app-protected'
import authenticate from '@middlewares/authenticate'
import { idParam } from '@schemas/common'
import { deviceSchema, registerDeviceSchema, statusSchema, syncSchema } from '@schemas/device'
import { Router } from 'express'
import { createValidator } from 'express-joi-validation'

const validator = createValidator()

export default Router({ mergeParams: true })
    .get("/status", appProtected(), validator.query(statusSchema), putDeviceStatus)
    .post("/register", appProtected(), validator.body(registerDeviceSchema), registerDevice)
    .use(authenticate())
    .get("/", getDevices)
    .post("/", validator.body(deviceSchema), postDevice)
    .put("/:id", validator.params(idParam), validator.body(deviceSchema), putDevice)
    .delete("/:id", validator.params(idParam), deleteDevice)
    .patch("/:id/lock", validator.params(idParam), lockDevice)
    .patch("/:id/unlock", validator.params(idParam),  unlockDevice)