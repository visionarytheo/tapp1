const { SERVER_URL } = process.env;

export async function syncUser(token: string) {
  try {
    const res = await fetch(`${SERVER_URL}/api/v1/users/sync`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!res.ok) {
      const syncMsg = await res.json();
      console.error("Backend validation failed:", syncMsg.message);

      return false;
    }

    return true;
  } catch (error) {
    console.error("Network connection to Spring Boot failed:" + error);

    return false;
  }
}

export async function getUserRoleByEmail(email: string, token: string) {
  const res = await fetch(`${SERVER_URL}/api/v1/users/role/${email}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  const role = await res.json();

  if (!res.ok) {
    throw new Error(role.message);
  }

  return role.data;
}
