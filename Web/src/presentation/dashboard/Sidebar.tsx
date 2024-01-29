import { NavLink } from "react-router-dom";
import NavItem from "./NavItem";

interface Props {
    navItems: NavItem[]
}

function Sidebar({ navItems } : Props) {
    return (
        <ul className="p-0 gap-0 divide-y divide-default-300/50 dark:divide-default-100/80 bg-content1 overflow-visible shadow-small rounded-medium w-[250px]">
            {
                navItems.map(item => (
                    <NavLink 
                        key={item.route}
                        to={item.route}
                        className={({isActive}) => "flex items-center px-3 first:rounded-t-medium last:rounded-b-medium rounded-none gap-3 h-12 hover:bg-default-100/80 focus:bg-default-100/80 " + (isActive ? "bg-default-100/80" : "")}>
                         {item.icon}
                         <p className="w-full">
                         {item.title}
                         </p>
                    </NavLink>
                ))
            }
        </ul>
    );
}

export default Sidebar;