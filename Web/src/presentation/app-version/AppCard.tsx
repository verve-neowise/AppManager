import { Card, CardHeader, Chip, Divider, CardBody, CardFooter, Button } from "@nextui-org/react";
import { Download } from "lucide-react";
import { AppVersion, AppVersionFlag } from "../../models/appVersion";
import { useRef } from "react";
import useisoverflow from "../../util/useIsOverflow";
import fileDownload from "js-file-download";
import axios from "axios";

interface Props {
    app: AppVersion,
    onDetails: () => void,
    onEdit: () => void
}

function AppCard({ app, onEdit, onDetails }: Props) {

    const changelogRef = useRef<HTMLParagraphElement>(null)
    const isOverflow = useisoverflow(changelogRef)

    console.log(app.downloadPath);

    const download = () => {
        const url = "http://localhost:8081/api/v1/apps/" + app.id + "/download"

        axios.get(url, {
            responseType: 'blob'
        }).then(resp => {
            fileDownload(resp.data, app.downloadPath ?? "unknown.apk")
        })
    }

    return (<>
        <Card className="hover:bg-default-100/80 active:bg-default-100/90" isPressable onClick={() => onEdit() }>
            <CardHeader>
                <div className="flex justify-between w-full">
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
                </div>
            </CardHeader>
            <Divider />
            <CardBody>
                <p ref={changelogRef} className="text-xs overflow-y-hidden h-20"
                    dangerouslySetInnerHTML={
                        {
                            __html: app.changelog.replace(/\n/g, "<br />")
                        }
                } />
                {
                    isOverflow && (
                        <>  
                            <p>...</p>
                            <br />
                            <Button onClick={() => onDetails()} size="sm" color="default"> Read More </Button>
                        </>
                    )
                }
            </CardBody>
            <Divider />
            <CardFooter>
                <div className="flex justify-between w-full">
                    <div className="flex gap-2 items-center">
                        <div className="flex justify-center items-center text-xs w-8 h-8 rounded-full bg-orange-200 text-orange-950">
                            {app.owner[0]}
                        </div>
                        <p className="text-sm"> {app.owner} </p>
                    </div>
                    <Button onClick={download} size="sm" isIconOnly color="primary">
                        <Download size="16" />
                    </Button>
                </div>
            </CardFooter>
        </Card>
    </>);
}

export default AppCard