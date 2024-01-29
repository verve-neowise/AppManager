import query from "../util/query"

export default class AuthService {
    
    static async login(email: string, password: string) {
        const url = 'auth/login'
        const data = { email, password }
        return query((axios) => axios.post(url, data))
    } 
}