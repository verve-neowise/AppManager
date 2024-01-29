import { Navigate } from "react-router-dom"

interface Props {
    children: React.ReactNode
}

function AuthProtect({ children } : Props) {
    const token = localStorage.getItem('token')

    if (!token) {
        return <Navigate to="/login"/>
    }

    return children
}

export default AuthProtect