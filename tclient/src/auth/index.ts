import { getUserRoleByEmail, syncUser } from "@/server/user.server";
import NextAuth from "next-auth";
import Google from "next-auth/providers/google";

const { AUTH_SECRET } = process.env;

export const { handlers, signIn, signOut, auth } = NextAuth({
  providers: [Google],
  session: {
    strategy: "jwt",
    maxAge: 15 * 60,
  },
  pages: {
    signIn: "/auth/signin",
  },
  trustHost: true,
  secret: AUTH_SECRET,

  callbacks: {
    async signIn({ account }) {
      if (account?.provider === "google" && account.id_token) {
        const res = await syncUser(account.id_token);

        return res;
      }
      return true;
    },

    async jwt({ token, account, user }) {
      if (account && user) {
        const idTokenString = account.id_token as string;
        const email = user.email as string;

        const dbRole = await getUserRoleByEmail(email, idTokenString);
        console.log({ dbRole });

        token.googleIdToken = idTokenString;
        token.role = dbRole;
      }
      return token;
    },

    async session({ session, token }) {
      session.googleIdToken = token.googleIdToken;
      session.user.role = token.role;

      return session;
    },
  },
});
