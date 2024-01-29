import { Button, Listbox, ListboxItem, useDisclosure } from "@nextui-org/react";
import { useState } from "react";
import { Lock, Smartphone, Unlock } from "lucide-react";
import { Device, DeviceStatus } from "../../models/device";
import moment from "moment";
import SendMessageDialog from "./lock-dialog/SendMessageDialog";

interface Props {
    devices: Device[]
    createDevice: (device: Device) => void,
    updateDevice: (device: Device) => void,
    deleteDevice: (device: Device) => void,
    lockDevice: (device: Device) => void,
    unlockDevice: (device: Device) => void,
}

export default function DeviceList({ devices }: Props) {

    // const { isOpen, onOpen, } = useDisclosure();
    const lockDisclosure = useDisclosure();

    const [lockMode, setLockMode] = useState<DeviceStatus | null>(null)
    const [targetDevice, setTargetDevice] = useState<Device | undefined>()

    function openDialog(device?: Device) {
        setTargetDevice(device)
        // onOpen()
    }

    function openLockDialog(device: Device, mode: DeviceStatus) {
        setTargetDevice(device)
        setLockMode(mode)
        lockDisclosure.onOpen()
    }

    return (

        <div className="w-full flex relative">
            <div className="w-full flex flex-col items-end gap-10">

                <div className="flex w-full items-center justify-between">
                    <h1 className="font-bold text-lg"> Remote Lock </h1>
                    {/* <Button color="primary" onPress={() => openDialog()}> Add Device </Button> */}
                </div>
                <Listbox
                    aria-label="User Menu"
                    onAction={(key) => openDialog(devices.find(value => value.id == key))}
                    className="p-0 gap-0 grid divide-y divide-default-300/50 dark:divide-default-100/80 bg-content1 w-full overflow-visible shadow-small rounded-medium"
                    itemClasses={{
                        base: "p-4 flex gap-3 first:rounded-t-medium last:rounded-b-medium rounded-none gap-3 data-[hover=true]:bg-default-100/80",
                    }}
                >
                    {
                        devices.map(device => (
                            <ListboxItem
                                key={device.id}
                                className="flex gap-2"
                                startContent={
                                    <Smartphone className={device.status == DeviceStatus.Locked ? "text-danger" : "text-primary"} />
                                }
                                endContent={
                                    <div className="flex gap-2">
                                        <Button
                                            onClick={() => {
                                                // unlockDevice(device)
                                                openLockDialog(device, DeviceStatus.Unlocked)
                                            }}
                                            isIconOnly
                                            color="primary"
                                            aria-label="Unlock"
                                            size="sm">

                                            <Unlock size={16} />
                                        </Button>

                                        <Button
                                            onClick={() => {
                                                // lockDevice(device)
                                                openLockDialog(device, DeviceStatus.Locked)
                                            }}
                                            isIconOnly
                                            color="danger"
                                            aria-label="Lock"
                                            size="sm">
                                            <Lock size={16} />
                                        </Button>
                                    </div>
                                }
                            >
                                <div>
                                    <div className="flex gap-2 w-full items-center justify-between pr-4">
                                        <p className="font-bold"> {device.name}</p>
                                        <div>
                                            <span className="text-white/50"> {device.lastSync && moment(new Date(device.lastSync)).format("DD.MM.yyyy HH:mm")}</span>
                                            <span className="p-1 rounded-full inline-block bg-success ms-2"></span>
                                        </div>
                                    </div>
                                    <p className="text-white/70"> {device.model} {device.userId}</p>
                                </div>

                            </ListboxItem>
                        ))
                    }

                </Listbox>
                {/* 
                {
                    isOpen && (
                        <CreateDevice
                            isOpen={isOpen}
                            device={targetDevice}
                            onOpenChange={onOpenChange}
                            onSend={
                                (device) => {
                                    if (device.id == 0) {
                                        createDevice(device)
                                    }
                                    else {
                                        updateDevice(device)
                                    }
                                    onOpenChange()
                                }
                            }
                            onDelete={
                                (device) => {
                                    deleteDevice(device)
                                    onOpenChange()
                                }
                            }
                        />
                    )
                } */}

                {
                    lockDisclosure.isOpen && targetDevice && (
                        <SendMessageDialog
                            isOpen={lockDisclosure.isOpen}
                            onOpenChange={lockDisclosure.onOpenChange}
                            title={lockMode == DeviceStatus.Locked ? "Lock Device" : "Unlock Device"}
                            message={"AXTech Security\n" + (lockMode == DeviceStatus.Locked ? targetDevice.lockSignature : targetDevice.unlockSignature)}
                            phone={targetDevice?.phoneNumber ?? ""}
                        />
                    )
                }
            </div>
        </div>
    )
}
