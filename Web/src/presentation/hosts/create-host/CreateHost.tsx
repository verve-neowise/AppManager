import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, Input, Switch, Textarea } from "@nextui-org/react";
import { useState } from "react";
import { Host } from "../../../models/host";


interface Props {
    isOpen: boolean,
    onOpenChange: () => void,
    onSend: (host: Host) => void,
    onDelete: (host: Host) => void,
    host?: Host
}

interface State {
    hostName: string,
    description: string,
    baseUrl: string,
    filesUrl: string,
    active: boolean
}

interface Account {
    id: number,
    email: string,
    password: string
}

function emptyHost(): Host {
    return {
        id: 0,
        hostName: "",
        description: "",
        active: true,
        baseUrl: "https://",
        filesUrl: "https://",
        accounts: []
    }
}

function CreateHost({ isOpen, onOpenChange, host = emptyHost(), onSend, onDelete }: Props) {

    const isUpdate = host.id != 0

    const [state, setState] = useState<State>({
        hostName: host.hostName,
        description: host.hostName,
        baseUrl: host.baseUrl,
        filesUrl: host.filesUrl,
        active: host.active
    })

    const [accounts, setAccounts] = useState<Account[]>(host.accounts)

    return (
        <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
            <ModalContent>
                {(onClose) => (
                    <>
                        <ModalHeader className="flex flex-col gap-1">
                            {isUpdate ? "Update Host" : "Create Host"}
                        </ModalHeader>
                        <ModalBody>
                            <div className="flex flex-col gap-2">
                                <Input
                                    placeholder=""
                                    value={state.hostName}
                                    label="Host name"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, hostName: event.target.value }
                                    ))}
                                />

                                <Textarea
                                    placeholder=""
                                    value={state.description}
                                    label="Description"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, description: event.target.value }
                                    ))}
                                />

                                <Input
                                    placeholder="http://"
                                    value={state.baseUrl}
                                    label="Base Url"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, baseUrl: event.target.value }
                                    ))}
                                />

                                <Input
                                    placeholder="http://"
                                    value={state.filesUrl}
                                    label="Files Url"
                                    onChange={(event) => setState(prev => (
                                        { ...prev, filesUrl: event.target.value }
                                    ))}
                                />

                                <Switch
                                    isSelected={state.active}
                                    onValueChange={(value) => setState(prev => ({ ...prev, active: value }))}
                                    size="sm">
                                    Active
                                </Switch>


                                <div className="flex justify-between items-center">
                                    <h2 className="text-1xl my-3"> Accounts </h2>
                                    <Button
                                        size="sm"
                                        onPress={() => setAccounts(prev => [
                                            ...prev,
                                            { id: new Date().getTime(), email: "", password: "" }]
                                        )}
                                    > + Account </Button>
                                </div>

                                <div className="flex flex-col gap-2">
                                    {
                                        accounts.map((account) => (
                                            <Account
                                                key={account.id}
                                                account={account}
                                                onDelete={deleteAccount =>
                                                    setAccounts(prev => prev.filter((value) => value.id != deleteAccount.id))
                                                }
                                                onUpdate={updatedAccount => {
                                                    console.log(updatedAccount)
                                                    setAccounts(prev => prev.map((value) => value.id == updatedAccount.id ? updatedAccount : value))
                                                }
                                                }
                                            />
                                        ))
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
                                    <Button color="danger" variant="light" onPress={() => onDelete(host)}>
                                        Delete
                                    </Button>
                                )
                            }

                            <Button color="primary" onPress={() => onSend({
                                id: host.id,
                                active: state.active,
                                description: state.description,
                                baseUrl: state.baseUrl,
                                filesUrl: state.filesUrl,
                                hostName: state.hostName,
                                accounts
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

function Account({ account, onDelete, onUpdate }: { account: Account, onDelete: (account: Account) => void, onUpdate: (account: Account) => void }) {

    return (
        <div className="flex gap-2 items-center">
            <Input placeholder="Email" value={account.email} onChange={(event) => onUpdate({ ...account, email: event.target.value })} />
            <Input placeholder="Password" value={account.password} onChange={(event) => onUpdate({ ...account, password: event.target.value })} />
            <Button size="sm" onPress={() => onDelete(account)}> x </Button>
        </div>
    )
}

export default CreateHost