import { FC, useEffect } from "react";
import { CircularProgress } from "@nextui-org/react";
import { AppVersionViewModel } from "./AppVersionViewModel";
import AppVersionList from "./AppVersionList";

interface Props {
    viewModel: AppVersionViewModel
}

const HostsScreen: FC<Props> = ( { viewModel } ) => {

    const state = viewModel.state

    useEffect(() => {
        viewModel.getApps()
    }, [])

    return ( 
        <div className="relative flex justify-center w-full">
            <AppVersionList 
                apps={state.apps}
                createApp={viewModel.createApp}
                updateApp={viewModel.updateApp}
                deleteApp={viewModel.deleteApp}
            />
            {
                state.isLoading && (
                    <div className="absolute w-full h-full grow top-0 left-0 bg-black/40 flex justify-center items-center ">
                        <CircularProgress size="sm" aria-label="Loading..."/>
                    </div>
                )
            }
        </div>
    )
}
 
export default HostsScreen