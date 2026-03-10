(function () {
  const api = window.KCApiClient;

  if (!api) {
    console.warn("KCApiClient no esta disponible. Carga http-client.js antes de auth-api-services.js");
    return;
  }

  function unwrapItems(response) {
    const data = response?.data?.items || response?.data || [];
    return Array.isArray(data) ? data : [];
  }

  function coerceId(value, fallback) {
    const digits = String(value ?? "").match(/\d+/g);
    const parsed = Number(digits ? digits.join("") : value);
    return Number.isFinite(parsed) && parsed > 0 ? parsed : fallback;
  }

  function normalizeRole(value) {
    const raw = String(value || "").trim().toUpperCase();
    if (raw === "GUIDE") return "GUIDE";
    return "TOURIST";
  }

  function buildToken(user) {
    return `kc-user-${user.userId}-${Date.now()}`;
  }

  async function login(payload) {
    const usersResponse = await api.get("/users");
    const users = unwrapItems(usersResponse);
    const email = String(payload?.email || "").trim().toLowerCase();
    const password = String(payload?.password || "");

    const user = users.find((item) => {
      const itemEmail = String(item?.email || "").trim().toLowerCase();
      const storedPassword = String(item?.passwordHash || "");
      return itemEmail === email && storedPassword === password;
    });

    if (!user) {
      const error = new Error("Credenciales invalidas");
      error.status = 401;
      throw error;
    }

    const token = buildToken(user);
    const now = new Date();
    const expiresAt = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000).toISOString();

    let authSession = null;
    try {
      const response = await api.post("/auth_sessions", {
        userId: user.userId,
        tokenHash: token,
        expiresAt,
        ip: "127.0.0.1",
        userAgent: navigator.userAgent,
        createdAt: now.toISOString(),
      });
      authSession = response?.data || null;
    } catch (error) {
      console.warn("No se pudo crear la sesion en backend.", error);
    }

    return {
      ok: true,
      status: 200,
      data: {
        token,
        user,
        authSession,
      },
    };
  }

  async function register(payload) {
    const role = normalizeRole(payload?.role);
    const phoneDigits = String(payload?.phoneNumber || "").replace(/\D/g, "");
    const countryCode = String(payload?.countryCode || "").trim();
    const phoneE164 = `${countryCode}${phoneDigits}`;

    const response = await api.post("/users", {
      role,
      fullName: payload?.fullName,
      dateOfBirth: payload?.dateOfBirth,
      email: String(payload?.email || "").trim().toLowerCase(),
      passwordHash: payload?.password,
      countryCode,
      phoneNumber: phoneDigits,
      phoneE164,
      accountStatus: "ACTIVE",
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    });

    const user = response?.data || {};
    const numericUserId = coerceId(user?.userId, null);

    if (numericUserId && role === "GUIDE") {
      try {
        await api.post("/guide_profiles", {
          userId: numericUserId,
          summary: "",
          currency: "MXN",
          updatedAt: new Date().toISOString(),
        });
      } catch (error) {
        console.warn("No se pudo crear el perfil de guia inicial.", error);
      }
    }

    if (numericUserId && role === "TOURIST") {
      try {
        await api.post("/tourist_profiles", {
          userId: numericUserId,
          location: "",
          bio: "",
          updatedAt: new Date().toISOString(),
        });
      } catch (error) {
        console.warn("No se pudo crear el perfil de turista inicial.", error);
      }
    }

    return response;
  }

  function logout() {
    return Promise.resolve({ ok: true, status: 204, data: null });
  }

  window.KCAuthApi = {
    endpoints: {
      users: "/users",
      authSessions: "/auth_sessions",
    },
    auth: {
      login,
      register,
      logout,
    },
  };
})();
