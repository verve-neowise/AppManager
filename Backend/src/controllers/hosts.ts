import { createHost, findHost, findHosts, removeHost, updateHost } from '@services/host.service';
import catchAsync from '@utils/catchAsync';

export const getHosts = catchAsync(async (req, res, next) => {

    const hosts = await findHosts()

    res.json({
        success: true,
        hosts
    })
})

export const postHost = catchAsync(async (req, res, next) => {
    const { hostName, description, baseUrl, filesUrl, active, accounts } = req.body

    const created = await createHost({
        hostName,
        description,
        baseUrl,
        filesUrl,
        accounts,
        active
    })

    res.json({
        success: true,
        host: created
    })
})

export const putHost = catchAsync(async (req, res, next) => {

    const id = Number(req.params.id)

    const { hostName, description, baseUrl, filesUrl, active, accounts } = req.body

    const host = await findHost(id)

    if (!host) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const updated = await updateHost(id, {
        hostName,
        description,
        baseUrl,
        filesUrl,
        accounts,
        active
    })

    res.json({
        success: true,
        host: updated
    })
})

export const deleteHost = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const host = await findHost(id)

    if (!host) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const deleted = await removeHost(id)

    res.json({
        success: true,
        host: deleted
    })
})