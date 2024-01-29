import axios, { Axios, AxiosResponse } from "axios";

const _axios = axios.create({
    baseURL: 'http://localhost:53001/api/v1'
})

_axios.interceptors.request.use(config => {
    config.headers['Authorization'] = localStorage.getItem("token")
    return config
})

export default async <T = any>(request: (axios: Axios) => Promise<AxiosResponse>) => {

    let response: AxiosResponse

    try {
        response = await request(_axios)
    }
    catch(e: any) {

        if (e.response.status == 401) {
            localStorage.removeItem("token")
        }
        throw Error(e.response.data)
    }

    if (typeof response.data == 'object') {
        if (response.data.success) {
            return response.data as T
        }
        else {
            throw Error(response.data.message)
        }
    }
    else {
        throw Error(response.data)
    }
}