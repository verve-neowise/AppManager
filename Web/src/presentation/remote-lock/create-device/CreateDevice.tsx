import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, Input, Switch } from "@nextui-org/react";
import { useState } from "react";
import { Device, DeviceStatus } from "../../../models/device";

interface Props {
    isOpen: boolean,
    onOpenChange: () => void,
    onSend: (host: Device) => void,
    onDelete: (host: Device) => void,
    device?: Device
}

interface State {
    name: string
    deviceId: string
    model: string
    phoneNumber: string
    status: DeviceStatus
}

function emptyDevice(): Device {
    return {
        id: 0,
        name: "",
        userId: "",
        model: "",
        phoneNumber: "",
        status: DeviceStatus.Unlocked,
        lastSync: "",
        lockSignature: undefined,
        unlockSignature: undefined
    }
}

function CreateDevice({ isOpen, onOpenChange, device = emptyDevice(), onSend, onDelete }: Props) {

    const isUpdate = device.id != 0

    const [state, setState] = useState<State>({
        name: device.name,
        deviceId: device.userId,
        model: device.model,
        status: device.status,
        phoneNumber: device.phoneNumber
    })

    return (
        <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
            <ModalContent>
                {(onClose) => (
                    <>
                        <ModalHeader className="flex flex-col gap-1">
                            {isUpdate ? "Update Device" : "Create Device"}
                        </ModalHeader>
                        <ModalBody>
                            <div className="flex flex-col gap-3">
                                <Input
                                    placeholder=""
                                    value={state.name}
                                    label="Device name"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, name: event.target.value }
                                    ))}
                                />

                                <Input
                                    placeholder=""
                                    value={state.model}
                                    label="Model"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, model: event.target.value }
                                    ))}
                                />

                                <Input
                                    placeholder=""
                                    value={state.deviceId}
                                    label="User ID"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, deviceId: event.target.value }
                                    ))}
                                />


                                <Input
                                    placeholder=""
                                    value={state.phoneNumber}
                                    label="Phone"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, phoneNumber: event.target.value }
                                    ))}
                                />

                                <Switch
                                    isSelected={state.status == DeviceStatus.Unlocked}
                                    onValueChange={(value) => setState(prev => ({ ...prev, status: value ? DeviceStatus.Unlocked : DeviceStatus.Locked }))}
                                    size="sm">
                                    Unlocked
                                </Switch>


                            </div>
                        </ModalBody>
                        <ModalFooter>

                            <Button color="default" variant="light" onPress={onClose}>
                                Close
                            </Button>

                            {
                                isUpdate && (
                                    <Button color="danger" variant="light" onPress={() => onDelete(device)}>
                                        Delete
                                    </Button>
                                )
                            }

                            <Button color="primary" onPress={() => onSend({
                                id: device.id,
                                name: state.name,
                                userId: state.deviceId,
                                phoneNumber: state.phoneNumber,
                                model: state.model,
                                lockSignature: undefined,
                                unlockSignature: undefined,
                                status: state.status,
                                lastSync: ""
                            })}>
                                {isUpdate ? "Update" : "Create"}
                            </Button>
                        </ModalFooter>
                    </>
                )}
            </ModalContent>
        </Modal>
    )
}

export default CreateDevice