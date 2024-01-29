"use client"
import { Button, Chip, Listbox, ListboxItem, useDisclosure } from "@nextui-org/react";
import { useState } from "react";
import { Host } from "../../models/host";
import CreateHost from "./create-host/CreateHost";
import { Globe } from "lucide-react";

interface Props {
    hosts: Host[],
    createHost: (host: Host) => void,
    updateHost: (host: Host) => void,
    deleteHost: (host: Host) => void,
}

export default function HostList({ hosts, deleteHost, createHost, updateHost }: Props) {

    const { isOpen, onOpen, onOpenChange } = useDisclosure();

    const [targetHost, setTargetHost] = useState<Host | undefined>()

    function openDialog(host?: Host) {
        setTargetHost(host)
        onOpen()
    }

    return (

        <div className="w-full flex relative">
            <div className="w-full flex flex-col items-end gap-10">
            <div className="flex w-full items-center justify-between">
                <h1 className="font-bold text-lg"> Hosts </h1>
                <Button color="primary" onPress={() => openDialog()}> Add Host </Button>
            </div>

                <Listbox
                    aria-label="User Menu"
                    onAction={(key) => openDialog(hosts.find(value => value.id == key))}
                    className="p-0 gap-0 grid divide-y divide-default-300/50 dark:divide-default-100/80 bg-content1 w-full overflow-visible shadow-small rounded-medium"
                    itemClasses={{
                        base: "p-4 flex gap-2 first:rounded-t-medium last:rounded-b-medium rounded-none gap-3 data-[hover=true]:bg-default-100/80",
                    }}
                >
                    {
                        hosts.map(host => (
                            <ListboxItem
                                key={host.id}
                                startContent = {
                                    <Globe/>
                                }
                                endContent={
                                    host.active ? <Chip size="sm" color="primary"> Active </Chip> : <Chip size="sm" color="danger"> Disabled </Chip>
                                }
                            >
                                <p className="font-bold"> {host.hostName}</p>
                                <p className="text-white/70"> {host.description}</p>
                            </ListboxItem>
                        ))
                    }

                </Listbox>

                {
                    isOpen && (
                        <CreateHost
                            isOpen={isOpen}
                            host={targetHost}
                            onOpenChange={onOpenChange}
                            onSend={
                                (host) => {
                                    if (host.id == 0) {
                                        createHost(host)
                                    }
                                    else {
                                        updateHost(host)
                                    }
                                    onOpenChange()
                                }
                            }
                            onDelete={
                                (host) => {
                                    deleteHost(host)
                                    onOpenChange()
                                }
                            }
                        />
                    )
                }
            </div>
        </div>
    )
}
