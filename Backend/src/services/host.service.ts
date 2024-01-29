import prisma from './db'

interface CreateParams {
    hostName: string,
	description: string,
	baseUrl: string,
	filesUrl: string,
	active: boolean,
	accounts: {
        email: string,
        password: string
    }[]
}

export const findHosts = () => {
    return prisma.host.findMany({
        include: {
            accounts: {
                select: {
                    id: true,
                    email: true,
                    password: true
                }
            }
        }
    })
}

export const findHost = (id: number) => {
    return prisma.host.findUnique({
        where: {
            id
        },
        include: {
            accounts: {
                select: {
                    id: true,
                    email: true,
                    password: true
                }
            }
        }
    })
}

export const createHost = (params: CreateParams) => {
    return prisma.host.create({
        data: {
            hostName: params.hostName,
            baseUrl: params.baseUrl,
            filesUrl: params.filesUrl,
            description: params.description,
            active: params.active,
            accounts: {
                create: params.accounts.map(account => ({
                    email: account.email,
                    password: account.password
                }))
            }
        },
        include: {
            accounts: {
                select: {
                    id: true,
                    email: true,
                    password: true
                }
            }
        }
    })
}

export const updateHost = async (id: number, params: CreateParams) => {

    await prisma.account.deleteMany({
        where: {
            hostId: id
        }
    })

    return prisma.host.update({
        where: {
            id
        },
        data: {
            hostName: params.hostName,
            baseUrl: params.baseUrl,
            filesUrl: params.filesUrl,
            description: params.description,
            active: params.active,
            accounts: {
                create: params.accounts.map(account => ({
                    email: account.email,
                    password: account.password
                }))
            }
        },
        include: {
            accounts: {
                select: {
                    id: true,
                    email: true,
                    password: true
                }
            }
        }
    })
}

export const removeHost = async (id: number) => {

    await prisma.account.deleteMany({
        where: {
            hostId: id
        }
    })

    return prisma.host.delete({
        where: {
            id
        }
    })
}