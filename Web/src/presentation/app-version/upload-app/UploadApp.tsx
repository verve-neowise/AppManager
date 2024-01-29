import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, Input, Textarea, Chip } from "@nextui-org/react";
import { useState } from "react";
import { AppVersion, AppVersionFlag } from "../../../models/appVersion";
import { FileUploader } from "react-drag-drop-files";


interface Props {
    isOpen: boolean,
    onOpenChange: () => void,
    onSend: (app: AppVersion) => void,
    onDelete: (app: AppVersion) => void,
    app?: AppVersion
}

interface State {
    name: string,
    versionCode: string,
    versionNumber: string,
    tags: string,
    flag: AppVersionFlag,
    changelog: string,
    filePath: File | null,
}

function emptyAppVersion(): AppVersion {
    return {
        id: 0,
        name: "",
        versionCode: "",
        versionNumber: "",
        tags: "",
        flag: AppVersionFlag.feature,
        changelog: "",
        filePath: null,
        downloadPath: null,
        owner: ""
    }
}

const flagColors = new Map<string, any>([
    [AppVersionFlag.production, "success"],
    [AppVersionFlag.feature, "primary"],
    [AppVersionFlag.bugfix, "danger"],
])

function UploadApp({ isOpen, onOpenChange, app = emptyAppVersion(), onSend, onDelete }: Props) {

    const isUpdate = app.id != 0

    const [state, setState] = useState<State>({
        name: app.name,
        versionCode: app.versionCode,
        versionNumber: app.versionNumber,
        tags: app.tags,
        flag: app.flag,
        changelog: app.changelog,
        filePath: app.filePath,
    })

    return (
        <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
            <ModalContent>
                {(onClose) => (
                    <>
                        <ModalHeader className="flex flex-col gap-1">
                            {isUpdate ? "Update App" : "Create App"}
                        </ModalHeader>
                        <ModalBody>
                            <div className="flex flex-col gap-2">
                                <Input
                                    placeholder=""
                                    value={state.name}
                                    label="App name"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, name: event.target.value }
                                    ))}
                                />

                                <div className="flex gap-3">
                                    <Input
                                        className="grow"
                                        placeholder=""
                                        value={state.versionCode}
                                        label="Version code"
                                        onChange={(event) => setState(prev => (
                                            { ...prev, versionCode: event.target.value }
                                        ))}
                                    />

                                    <Input
                                        className="grow"
                                        placeholder=""
                                        value={state.versionNumber}
                                        label="Version number"
                                        onChange={(event) => setState(prev => (
                                            { ...prev, versionNumber: event.target.value }
                                        ))}
                                    />
                                </div>

                                <Input
                                    className="grow"
                                    placeholder=""
                                    value={state.tags}
                                    label="Tags"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, tags: event.target.value }
                                    ))}
                                />

                                <Textarea
                                    placeholder=""
                                    value={state.changelog}
                                    label="Changelog"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, changelog: event.target.value }
                                    ))}
                                />

                                <FileUploader 
                                    handleChange={(file: File) => {
                                        setState(prev => (
                                            { ...prev, filePath: file }
                                        ))
                                    }} 
                                    name="file" 
                                    fileTypes={["application/vnd.android.package-archive"]}
                                />

                                <div className="flex flex-wrap gap-4 text-white mt-4">
                                    {
                                        Object.keys(AppVersionFlag).map(flag => {
                                            return (
                                                <Chip
                                                    key={flag}
                                                    className={"text-white cursor-pointer " + (state.flag == flag ? "outline outline-offset-[3px] outline-" + flagColors.get(flag) : "")}
                                                    color={flagColors.get(flag)}
                                                    onClick={() => {
                                                        setState({
                                                            ...state,
                                                            flag: flag as AppVersionFlag
                                                        })
                                                    }}
                                                >
                                                    {flag}
                                                </Chip>
                                            )
                                        })
                                    }
                                </div>
                            </div>
                        </ModalBody>
                        <ModalFooter>

                            <Button color="default" variant="light" onPress={onClose}>
                                Close
                            </Button>

                            {
                                isUpdate && (
                                    <Button color="danger" variant="light" onPress={() => onDelete(app)}>
                                        Delete
                                    </Button>
                                )
                            }

                            <Button color="primary" onPress={() => onSend({
                                id: app.id,
                                name: state.name,
                                versionCode: state.versionCode,
                                versionNumber: state.versionNumber,
                                tags: state.tags.split(',').map(tag => tag.trim()).join(":"),
                                flag: state.flag,
                                downloadPath: null,
                                changelog: state.changelog,
                                filePath: state.filePath,
                                owner: ""
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

export default UploadApp