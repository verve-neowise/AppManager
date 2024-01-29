import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, Input, Textarea, Tooltip } from "@nextui-org/react";
import { Copy } from "lucide-react";
import { copyTextToClipboard } from "../../../util/copyClipboard";
import { useState } from "react";

interface Props {
    isOpen: boolean,
    onOpenChange: () => void,
    title: string,
    message: string,
    phone: string
}

function SendMessageDialog({ isOpen, onOpenChange, title, message, phone }: Props) {

    const [copied, setCopied] = useState(false)

    return (
        <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
            <ModalContent>
                {() => (
                    <>
                        <ModalHeader className="flex flex-col gap-1">
                            {title}
                        </ModalHeader>
                        <ModalBody>
                            <div className="flex flex-col gap-3">
                                <Input
                                    placeholder=""
                                    value={phone}
                                    label="Phone number"
                                />

                                <Textarea
                                    placeholder=""
                                    value={message}
                                    label="Message"
                                    disabled
                                />

                                <Tooltip isOpen={copied} closeDelay={1000} placement="bottom" content="Copied" color="foreground" onOpenChange={(open) => open ? null : setCopied(false) } >
                                    <Button color="primary" onClick={() => {
                                            copyTextToClipboard(message)
                                            setCopied(true)
                                        }}>
                                        <Copy size={14} />
                                        Copy
                                    </Button>
                                </Tooltip>
                                <Button color="default" disabled>
                                    <Copy size={14} />
                                    Send SMS
                                </Button>
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="primary" onClick={onOpenChange}>
                                Okay
                            </Button>
                        </ModalFooter>
                    </>
                )}
            </ModalContent>
        </Modal>
    )
}

export default SendMessageDialog