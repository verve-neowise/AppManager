import { postHost, getHosts, putHost, deleteHost } from '@controllers/hosts'
import authenticate from '@middlewares/authenticate'
import { idParam } from '@schemas/common'
import { hostSchema } from '@schemas/host'
import { Router } from 'express'
import { createValidator } from 'express-joi-validation'

const validator = createValidator()

export default Router({ mergeParams: true })
    .use(authenticate())
    .get("/", getHosts)
    .post("/", validator.body(hostSchema), postHost)
    .put("/:id", validator.params(idParam), validator.body(hostSchema), putHost)
    .delete("/:id", validator.params(idParam), deleteHost)