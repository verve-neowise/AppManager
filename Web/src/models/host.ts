export type Host = {
	id: number,
	hostName: string,
	description: string,
	baseUrl: string,
	filesUrl: string,
	active: boolean,
	accounts: Account[]
}

export interface Account {
    id: number,
    email: string,
    password: string
}
