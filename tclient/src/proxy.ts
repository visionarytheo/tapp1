import { auth } from "@/auth";

export const proxy = auth((req) => {
  const { pathname, origin } = req.nextUrl;

  const user = req.auth?.user;
  const isLoggedIn = !!user;
  const userRole = user?.role;

  const isOnAuth = pathname.startsWith("/auth");
  const isOnDashboard = pathname.startsWith("/dashboard");
  const isOnAdmin = pathname.startsWith("/dashboard/admin");

  if (isOnDashboard && !isLoggedIn) {
    return Response.redirect(new URL("/auth/signin", origin));
  }

  if (isLoggedIn && isOnAuth) {
    return Response.redirect(new URL("/dashboard", origin));
  }

  if (isOnAdmin && userRole !== "ADMIN") {
    return Response.redirect(new URL("/dashboard/user", origin));
  }
});

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
