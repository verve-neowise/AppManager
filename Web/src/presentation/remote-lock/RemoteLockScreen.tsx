import { FC, useEffect } from "react";
import DeviceList from "./DeviceList";
import { CircularProgress } from "@nextui-org/react";
import { RemoteLockViewModel } from "./RemoteLockViewModel";

interface Props {
    viewModel: RemoteLockViewModel
}

const RemoteLockScreen: FC<Props> = ( { viewModel } ) => {

    const state = viewModel.state

    useEffect(() => {
        viewModel.getDevices()
    }, [])

    return ( 
        <div className="relative flex justify-center w-full">
            <DeviceList 
                devices={state.devices}
                createDevice={viewModel.createDevice}
                updateDevice={viewModel.updateDevice}
                deleteDevice={viewModel.deleteDevice}
                lockDevice={viewModel.lockDevice}
                unlockDevice={viewModel.unlockDevice}
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
 
export default RemoteLockScreen