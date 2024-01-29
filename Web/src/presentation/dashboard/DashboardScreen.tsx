import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";
import NavItem from "./NavItem";
import { Globe, LayoutGrid, Lock, Smartphone } from 'lucide-react'

const navItems: NavItem[] = [
    {
        route: "/hosts",
        icon: <Globe />,
        title: "Hosts"
    },
    {
        route: "/app-version",
        icon: <LayoutGrid />,
        title: "App Versions"
    },
    {
        route: "/remote-lock",
        icon: <Lock />,
        title: "Remote Lock"
    }
]

function DashboardScreen() {
    return (
        <div className="flex flex-col gap-10 p-10 h-screen ">
            <h1 className="text-2xl font-bold text-sky-400 flex gap-2 items-center"> 
                <Smartphone />
                AX Mobile 
            </h1>
            <div className="flex items-start overflow-y-auto gap-4">
                <Sidebar navItems={navItems}/>
                <div className="w-full h-full px-10">
                    <Outlet/>
                </div>
            </div>
        </div>
    );
}

export default DashboardScreen;