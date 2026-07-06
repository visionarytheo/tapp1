
import { signIn } from "@/auth"
import { Icons } from "../Icons"

export default function SignIn() {
    return (
        <form
            action={async () => {
                "use server"
                await signIn("google")
            }}
        >
            <button type="submit" className="flex justify-center items-center border-2 border-green-600 p-3 rounded-4xl"><span className="mx-2">Signin with Google</span><span className="h-5 w-5 mx-2"><Icons.google2 /></span></button>
        </form>
    )
} 