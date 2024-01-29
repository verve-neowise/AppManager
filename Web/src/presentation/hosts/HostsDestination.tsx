import { useHostsViewModel } from "./HostsViewModel";
import HostsScreen from "./HostsScreen";

export default function HostsDestination() {
    const viewModel = useHostsViewModel()
    return <HostsScreen viewModel={viewModel}/>
}