import { useAppVersionViewModel } from "./AppVersionViewModel";
import AppVersionScreen from "./AppVersionScreen";

export default function AppVersionDestination() {
    const viewModel = useAppVersionViewModel()
    return <AppVersionScreen viewModel={viewModel}/>
}