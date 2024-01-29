import { NextFunction, Request, Response, Router } from 'express'
import { errorHandler } from '@middlewares/index'
import authRoutes from './auth.routes'
import hostsRoutes from './hosts.routes'
import devicesRoutes from './devices.routes'
import appRoutes from './app.routes'

export default Router({ mergeParams: true })
    .use("/auth", authRoutes)
    .use("/hosts", hostsRoutes)
    .use("/devices", devicesRoutes)
    .use("/apps", appRoutes)
    .use(errorHandler)