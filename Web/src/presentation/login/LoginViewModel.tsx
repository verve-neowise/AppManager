import { useState } from "react"
import requester from "../../util/requester"
import { Auth } from "../../models/auth"
import AuthService from "../../services/auth.service"
import toast from "react-hot-toast"

export interface AuthState {
    email: string,
    password: string,
    isLoading: boolean,
    auth: Auth | null,
    next: boolean,
    errorMessage: string | null,
}

export interface AuthViewModel {
    state: AuthState,
    login: () => void,
    emailChanged: (value: string) => void,
    passwordChanged: (value: string) => void,
}

export const useAuthViewModel: () => AuthViewModel = () => {

    const [state, setState] = useState<AuthState>({
        email: "ji@gmail.com",
        password: "12345678",
        isLoading: false,
        auth: null,
        next: false,
        errorMessage: null
    })

    const setLoading = () => {
        setState({
            ...state,
            isLoading: true
        })
    }

    const setResult = (data: {}) => {
        setState({
            ...state,
            isLoading: false,
            ...data
        })
    }

    const setError = (error: string) => {
        setState({
            ...state,
            isLoading: false,
            errorMessage: error
        })
        toast.error(error, {
            className: 'bg-default-100/80 text-white rounded-md text-sm'
        })
    }

    const request = requester(setResult, setError, setLoading)

    const login = () => request(async () => {
        const result = await AuthService.login(state.email, state.password)

        localStorage.setItem("token", result.token)

        return {
            auth: result,
            next: true
        }
    })

    const emailChanged = (value: string) => {
        setState({
            ...state,
            email: value
        })
    }

    const passwordChanged = (value: string) => {
        setState({
            ...state,
            password: value
        })
    }

    return {
        state,
        login,
        emailChanged,
        passwordChanged
    }
}