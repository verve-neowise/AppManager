import { deleteApp, downloadApp, getApps, postApp, putApp } from '@controllers/apps'
import authenticate from '@middlewares/authenticate'
import { upload } from '@middlewares/fileupload'
import { appSchema } from '@schemas/app'
import { idParam } from '@schemas/common'
import { Router } from 'express'
import { createValidator } from 'express-joi-validation'

const validator = createValidator()

export default Router({ mergeParams: true })
    .get("/:id/download", validator.params(idParam), downloadApp)
    
    .use(authenticate())
    .get("/", getApps)
    .post("/", upload.single("file"), validator.body(appSchema), postApp)
    .put("/:id", upload.single("file"), validator.params(idParam), validator.body(appSchema), putApp)
    .delete("/:id", validator.params(idParam), deleteApp)