import { AppVersion } from "../models/appVersion"
import query from "../util/query"

export default class AppVersionService {

    static async allApps() {
        return query((axios) => axios.get("apps"))
    }

    static async createApp(app: AppVersion) {

        const formData = new FormData();

        formData.append('name', app.name);
        formData.append('versionCode', app.versionCode);
        formData.append('versionNumber', app.versionNumber);
        formData.append('tags', app.tags.join(','));
        formData.append('flag', app.flag);
        formData.append('changelog', app.changelog);
        
        if (app.filePath && app.filePath instanceof File) {
            formData.append('file', app.filePath);
        }

        return query((axios) => axios.post("apps", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        }))
    }

    static async updateApp(id: number, app: AppVersion) {

        const formData = new FormData();

        formData.append('name', app.name);
        formData.append('versionCode', app.versionCode);
        formData.append('versionNumber', app.versionNumber);
        formData.append('tags', app.tags.join(','));
        formData.append('flag', app.flag);
        formData.append('changelog', app.changelog);

        console.log(app.filePath);

        if (app.filePath && app.filePath instanceof File) {
            formData.append('file', app.filePath);
        }

        return query((axios) => axios.put("apps/" + id, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        }))
    }

    static async deleteApp(id: number) {
        return query((axios) => axios.delete("apps/" + id))
    }
}