import { Navigate, createBrowserRouter } from "react-router-dom";
import DashboardScreen from "../presentation/dashboard/DashboardScreen";
import HostsDestination from "../presentation/hosts/HostsDestination";
import RemoteLockDestination from "../presentation/remote-lock/RemoteLockDestination";
import AppVersionDestination from "../presentation/app-version/AppVersionDestination";
import AuthDestination from "../presentation/login/LoginDestination";
import AuthProtect from "../components/AuthProtect";

export default createBrowserRouter([
    {
        path: "/login",
        element: <AuthDestination/>,
    },
    {
        path: "/",
        element: <AuthProtect>
            <DashboardScreen/>
        </AuthProtect>,
        children: [
            {
                index: true,
                element: <Navigate to="/remote-lock"/>
            },
            {
                path: "/app-version",
                element: <AppVersionDestination/>
            },
            {
                path: "/hosts",
                element: <HostsDestination/>
            },
            {
                path: "/remote-lock",
                element: <RemoteLockDestination/>
            }
        ]
    }
])