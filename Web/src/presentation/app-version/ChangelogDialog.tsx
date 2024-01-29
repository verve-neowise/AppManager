import { Chip, Divider, Button, Modal, ModalBody, ModalContent, ModalFooter, ModalHeader } from "@nextui-org/react";
import { Download } from "lucide-react";
import { AppVersion, AppVersionFlag } from "../../models/appVersion";

interface Props {
    app: AppVersion,
    isOpen: boolean,
    onOpenChange: () => void
}

function ChangelogDialog({ app, isOpen, onOpenChange }: Props) {
    return (
        <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
            <ModalContent>
                {
                    <>
                        <ModalHeader className="flex justify-between w-full">
                            <div className="text-xs flex flex-col">
                                <p className="font-bold text-lg"> {app.name} </p>
                                <span className="text-white/50"> Version code: {app.versionCode} </span>
                                <span className="text-white/50"> Version number: {app.versionNumber} </span>
                            </div>
                            <div className="text-xs">
                                {
                                    app.flag == AppVersionFlag.bugfix && (
                                        <Chip className="text-xs" size="sm" color="danger"> Bugfix </Chip>
                                    )
                                }
                                {
                                    app.flag == AppVersionFlag.feature && (
                                        <Chip className="text-xs" size="sm" color="primary"> Feature </Chip>
                                    )
                                }
                                {
                                    app.flag == AppVersionFlag.production && (
                                        <Chip className="text-xs" size="sm" color="success"> Production </Chip>
                                    )
                                }
                            </div>
                        </ModalHeader>
                        <Divider/>
                        <ModalBody>
                            <p className="text-xs overflow-y-hidden h-3/5 py-3"
                                dangerouslySetInnerHTML={
                                    {
                                        __html: app.changelog.replace(/\n/g, "<br />")
                                    }
                                } />
                        </ModalBody>
                        <Divider/>
                        <ModalFooter>
                            <div className="flex justify-between items-center w-full">
                                <div className="flex gap-2 items-center">
                                    <div className="flex justify-center items-center text-xs w-8 h-8 rounded-full bg-orange-200 text-orange-950">
                                        {app.owner[0]}
                                    </div>
                                    <p className="text-sm"> {app.owner} </p>
                                </div>
                                <div className="flex gap-2 items-center">
                                    <Button color="default" variant="light" onPress={onOpenChange}>
                                        Close
                                    </Button>
                                    <Button size="sm" isIconOnly color="primary">
                                        <Download size="16" />
                                    </Button>
                                </div>
                            </div>
                        </ModalFooter>
                    </>
                }
            </ModalContent>
        </Modal>
    );
}

export default ChangelogDialog;