import { signOut } from "@/auth"

export default function SignOutBtn() {
    return (
        <form
            action={async () => {
                "use server"
                await signOut()
            }}
        >
            <button type="submit" className="flex justify-center items-center border-2 border-green-600 p-3 rounded-4xl"><span>Sign Out</span></button>
        </form>
    )
} 