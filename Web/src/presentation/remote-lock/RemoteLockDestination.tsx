import RemoteLockScreen from "./RemoteLockScreen";
import { useDeviceLockViewModel } from "./RemoteLockViewModel";

export default function RemoteLockDestination() {
    const viewModel = useDeviceLockViewModel()
    return <RemoteLockScreen viewModel={viewModel}/>
}