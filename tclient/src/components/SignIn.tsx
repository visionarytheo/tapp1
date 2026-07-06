
import { signIn } from "@/auth"
import { Icons } from "./Icons"

export default function SignIn() {
    return (
        <form
            action={async () => {
                "use server"
                await signIn("google")
            }}
        >
            <button type="submit" className="flex justify-center items-center border-2 border-green-600 p-3 rounded-4xl"><span>Signin with Google</span><Icons.google className="w-4 h-4 text-red-600 mx-2" /></button>
        </form>
    )
} 