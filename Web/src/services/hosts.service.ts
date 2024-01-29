import { Host } from "../models/host"
import query from "../util/query"

export default class HostService {

    static async allHost() {
        return query((axios) => axios.get("hosts"))
    }

    static async createHost(input: Host) {
        return query((axios) => axios.post("hosts", {
            hostName: input.hostName,
            description: input.description,
            baseUrl: input.baseUrl,
            filesUrl: input.filesUrl,
            active: input.active,
            accounts: input.accounts.map(account => ({
                email: account.email,
                password: account.password
            }))
        }))
    }

    static async updateHost(input: Host) {
        return query((axios) => axios.put("hosts/" + input.id, {
            hostName: input.hostName,
            description: input.description,
            baseUrl: input.baseUrl,
            filesUrl: input.filesUrl,
            active: input.active,
            accounts: input.accounts.map(account => ({
                email: account.email,
                password: account.password
            }))
        }))
    }

    static async deleteHost(id: number) {
        return query((axios) => axios.delete("hosts/" + id))
    }
}