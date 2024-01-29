import { FC, useEffect } from "react";
import HostList from "./HostList";
import { CircularProgress } from "@nextui-org/react";
import { HostsViewModel } from "./HostsViewModel";

interface Props {
    viewModel: HostsViewModel
}

const HostsScreen: FC<Props> = ( { viewModel } ) => {

    const state = viewModel.state

    useEffect(() => {
        viewModel.getHosts()
    }, [])

    return ( 
        <div className="relative flex justify-center w-full">
            <HostList 
                hosts={state.hosts}
                createHost={viewModel.createHost}
                updateHost={viewModel.updateHost}
                deleteHost={viewModel.deleteHost}
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