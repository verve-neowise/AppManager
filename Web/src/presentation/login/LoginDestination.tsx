import LoginScreen from "./LoginScreen";
import { useAuthViewModel } from "./LoginViewModel";

export default function AuthDestination() {
    const viewModel = useAuthViewModel()
    return <LoginScreen viewModel={viewModel}/>
}