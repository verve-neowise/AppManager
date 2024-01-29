export default <T>(resultReceiver: (data: T) => void, errorHandler: (error: string) => void, beforeRequest?: () => void) => {
    return async (request: () => Promise<T>) => {
        
        beforeRequest?.call(null)

        try {
            const result = await request()
            resultReceiver(result)
        }
        catch(e: any) {
            errorHandler(e.message)
        }
    }
}