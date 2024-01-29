"use client"
import { Button, useDisclosure } from "@nextui-org/react";
import { useState } from "react";
import { AppVersion } from "../../models/appVersion";
import AppCard from "./AppCard";
import ChangelogDialog from "./ChangelogDialog";
import UploadApp from "./upload-app/UploadApp";

interface Props {
    apps: AppVersion[],
    createApp: (app: AppVersion) => void,
    updateApp: (app: AppVersion) => void,
    deleteApp: (app: AppVersion) => void,
}

export default function AppVersionList({ apps, createApp, updateApp, deleteApp }: Props) {

    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const { isOpen: isChangelogOpen, onOpen: onChangelogOpen, onOpenChange: onChangelogOpenChange } = useDisclosure();

    const [targetApp, setTargetApp] = useState<AppVersion | undefined>()

    function openDialog(app?: AppVersion) {
        setTargetApp(app)
        onOpen()
    }

    function openChangeDialog(app?: AppVersion) {
        setTargetApp(app)
        onChangelogOpen()
    }

    console.log(apps)

    return (
        <div className="w-full flex relative">
            <div className="w-full flex flex-col items-end gap-10">
                <div className="flex w-full items-center justify-between">
                    <h1 className="font-bold text-lg"> App Version </h1>
                    <Button color="primary" onPress={() => openDialog()}> Upload App Version </Button>
                </div>

                <div className="grid w-full grid-cols-3 gap-3">
                    {
                        apps.map(app => (
                            <AppCard 
                            key={app.id} 
                            app={app} 
                            onDetails={() => openChangeDialog(app)}
                            onEdit={() => {
                                openDialog(app)
                            }}
                            />
                        ))
                    }
                </div>

                {
                    isOpen && (
                    <UploadApp
                        isOpen={isOpen}
                        app={targetApp}
                        onOpenChange={onOpenChange}
                        onSend={
                            (app) => {
                                if (app.id == 0) {
                                    createApp(app)
                                }
                                else {
                                    updateApp(app)
                                }
                                onOpenChange()
                            }
                        }
                        onDelete={
                            (app) => {
                                deleteApp(app)
                                onOpenChange()
                            }
                        }
                    />
                    )
                }

                {
                    (isChangelogOpen && targetApp) && (
                        <ChangelogDialog app={targetApp} isOpen={isChangelogOpen} onOpenChange={onChangelogOpenChange}/>
                    )
                }
            </div>
        </div>
    )
}
