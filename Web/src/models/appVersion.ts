export interface AppVersion {
    id: number
    name: string
    versionCode: string
    versionNumber: string
    tags: string
    flag: AppVersionFlag
    changelog: string
    filePath: File | null
    downloadPath: string | null
    owner: string
}

export enum AppVersionFlag {
    production = "production",
    feature = "feature",
    bugfix = "bugfix"
}

export interface AppResponse {
    success: boolean,
    apps: AppVersion[]
}