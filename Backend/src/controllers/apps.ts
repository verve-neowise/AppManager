import pathConfig from '@configs/path.config';
import { createApp, findApp, findApps, removeApp, updateApp } from '@services/app.service';
import catchAsync from '@utils/catchAsync';
import { rmSync, renameSync, createReadStream, statSync } from 'fs'
import path from 'path'

export const getApps = catchAsync(async (req, res, next) => {

    const apps = await findApps()

    res.json({
        success: true,
        apps
    })
})

export const postApp = catchAsync(async (req, res, next) => {
    const { name, versionCode, versionNumber, tags, flag, changelog  } = req.body

    const owner = res.locals.payload.name

    if (!req.file?.path) {
        return res.json({
            success: false,
            message: "File must be uploaded"
        })
    }

    const filePath = `${name.toLowerCase().replace(" ", "-").replace("\n", "-")}_v${versionCode}_b${versionNumber}.apk`

    try {
        renameSync(req.file.path, path.join(pathConfig.appsPath, filePath))
    }
    catch(e: any) {
    }

    const created = await createApp({
       name,
       versionCode,
       versionNumber,
       changelog,
       filePath: filePath,
       flag,
       owner,
       tags: tags.split(',').filter((tag: string) => tag.trim() != "")
    })

    res.json({
        success: true,
        app: created
    })
})

export const putApp = catchAsync(async (req, res, next) => {

    const id = Number(req.params.id)

    const { name, versionCode, versionNumber, tags, flag, changelog  } = req.body

    console.log("Update", req.body);

    const owner = res.locals.payload.name

    const app = await findApp(id)

    if (!app) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    let filePath: string | undefined = undefined

    if (req.file) {
        filePath = `${name.toLowerCase().replace(" ", "-").replace("\n", "-")}_v${versionCode}_b${versionNumber}.apk`

        if (filePath && app.downloadPath) {
            try {
                rmSync(path.join(pathConfig.appsPath, filePath))
                renameSync(filePath, path.join(pathConfig.appsPath, filePath))
            } 
            catch(e: any) {
            }
        }
    }

    const updated = await updateApp(id, {
        name,
       versionCode,
       versionNumber,
       changelog,
       filePath,
       flag,
       owner,
       tags: tags.split(',').filter((tag: string) => tag.trim() != "")
    })

    res.json({
        success: true,
        app: updated
    })
})

export const deleteApp = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const app = await findApp(id)

    if (!app) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    const deleted = await removeApp(id)

    if (deleted.downloadPath) {
        try {
            rmSync(path.join(pathConfig.appsPath, deleted.downloadPath))
        } 
        catch(e: any) { 
        }
    }

    res.json({
        success: true,
        app: deleted
    })
})

export const downloadApp = catchAsync(async (req, res, next) => {
    const id = Number(req.params.id)

    const app = await findApp(id)

    if (!app) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }

    if (!app.downloadPath) {
        return res.json({
            success: false,
            message: "Not found"
        })
    }
    var stats = statSync('./files/apps/' + app.downloadPath)
    var fileSizeInBytes = stats.size
    res.setHeader("content-length", fileSizeInBytes)
    
    createReadStream('./files/apps/' + app.downloadPath).pipe(res)
})