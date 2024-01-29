import { FC, useEffect } from "react";
import { Button, Input } from "@nextui-org/react";
import { AuthViewModel } from "./LoginViewModel";
import { useNavigate } from "react-router-dom";
import { Smartphone } from "lucide-react";

interface Props {
    viewModel: AuthViewModel
}

const LoginScreen: FC<Props> = ({ viewModel }) => {

    const state = viewModel.state
    const navigate = useNavigate()
    
    console.log("Error", state.errorMessage);

    useEffect(() => {
        if (state.next) {
            navigate('/')
        }
    }, [state.next])

    return (
        <div className="relative flex justify-center w-full h-screen">
            <div className="flex flex-col gap-3 items-center justify-center h-full w-96">

                <h1 className="text-2xl font-bold text-sky-400 flex gap-2 items-center mb-8">
                    <Smartphone />
                    AX Mobile
                </h1>

                <Input
                    label="Email"
                    value={state.email}
                    onChange={(event) => viewModel.emailChanged(event.target.value)}
                />
                <Input
                    label="Password"
                    value={state.password}
                    onChange={(event) => viewModel.passwordChanged(event.target.value)}
                />

                <Button isLoading={state.isLoading} color="primary" onClick={() => viewModel.login()}> Login </Button>
            </div>
        </div>
    )
}

export default LoginScreen