import "next-auth/jwt"

declare module "next-auth" {
  interface User {
    role?:string
  }
  
  interface Session {
    user:{
        role?:string
    }
    googleIdToken?:string
  }
}

 declare module "next-auth/jwt" {
  interface JWT {
    role?: string
    googleIdToken?: string
  }
}
 