export default function <T> (dispatch: React.Dispatch<React.SetStateAction<T>>) {
    return function (updater: (state: T) => T | void) {
        dispatch((prev) => {
            const newState = updater(prev)
            return newState ?? prev
        })
    }
}