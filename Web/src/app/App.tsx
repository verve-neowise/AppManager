import { RouterProvider } from "react-router-dom"
import Routes from "./Routes"
import { Toaster } from "react-hot-toast"

function App() {
  return (
    <>
      <div>
        <Toaster
          position="top-right"
          reverseOrder={true}
        />
      </div>
      <RouterProvider router={Routes}/>
    </>
  )
}

export default App
