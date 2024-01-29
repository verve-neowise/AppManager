export default (time: number = 500) => new Promise<void>((resolve, _) => {
    setTimeout(() => {
        resolve()
    }, time)
})