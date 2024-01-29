import { AppVersionFlag } from '@prisma/client'
import prisma from './db'

interface CreateParams {
    name: string
    versionCode: string
    versionNumber: string
    tags: string[]
    flag: AppVersionFlag
    changelog: string
    filePath?: string
    owner: string
}

export const findApps = () => {
    return prisma.app.findMany()
}

export const findApp = (id: number) => {
    return prisma.app.findUnique({
        where: {
            id
        }
    })
}

export const createApp = (params: CreateParams) => {
    return prisma.app.create({
        data: {
            name: params.name,
            changelog: params.changelog,
            flag: params.flag,
            owner: params.owner,
            versionCode: params.versionCode,
            versionNumber: params.versionNumber,
            downloadPath: params.filePath,
            tags: params.tags
        }
    })
}

export const updateApp = async (id: number, params: CreateParams) => {

    return prisma.app.update({
        where: {
            id
        },
        data: {
            name: params.name,
            changelog: params.changelog,
            flag: params.flag,
            owner: params.owner,
            versionCode: params.versionCode,
            versionNumber: params.versionNumber,
            downloadPath: params.filePath,
            tags: params.tags
        }
    })
}

export const removeApp = (id: number) => {
    return prisma.app.delete({
        where: {
            id
        }
    })
}