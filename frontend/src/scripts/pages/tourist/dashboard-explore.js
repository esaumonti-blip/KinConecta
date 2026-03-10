const TouristExploreApp = (() => {
  const openUnderConstructionModal = () => {
    if (window.KCUnderConstruction?.open) {
      window.KCUnderConstruction.open();
      return;
    }
    window.alert("Aun estamos trabajando en esto.");
  };

  const state = {
    touristId: null,
    currentUser: null,
    activeFilter: "all",
    items: [],
  };

  const fallbackItems = [
    {
      id: "exp_1",
      title: "Sabores de Oaxaca",
      location: "Oaxaca, México",
      category: "gastronomia",
      tags: ["Gastronomía", "Cultura"],
      rating: 4.9,
      priceLabel: "$850 MXN / persona",
      image: "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: "exp_2",
      title: "Ruta de Cenotes",
      location: "Tulum, Quintana Roo",
      category: "aventura",
      tags: ["Aventura", "Naturaleza"],
      rating: 4.8,
      priceLabel: "$1,240 MXN / persona",
      image: "https://images.unsplash.com/photo-1519046904884-53103b34b206?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: "exp_3",
      title: "Centro Histórico Nocturno",
      location: "Ciudad de México",
      category: "historia",
      tags: ["Historia", "Arquitectura"],
      rating: 4.7,
      priceLabel: "$620 MXN / persona",
      image: "https://images.unsplash.com/photo-1518105779142-d975f22f1b0a?auto=format&fit=crop&w=1200&q=80",
    },
    {
      id: "exp_4",
      title: "Artesanías y Talleres",
      location: "San Miguel de Allende",
      category: "cultura",
      tags: ["Cultura", "Arte"],
      rating: 4.9,
      priceLabel: "$760 MXN / persona",
      image: "https://images.unsplash.com/photo-1545239351-1141bd82e8a6?auto=format&fit=crop&w=1200&q=80",
    },
  ];

  const dom = {
    pageTitle: null,
    pageSubtitle: null,
    sidebarUserName: null,
    searchInput: null,
    cards: null,
    filters: [],
    quickSearchBtn: null,
    btnChat: null,
    btnNewTrip: null,
  };

  const loadingMarkup = (label, compact = false) => `
    <div class="guide-loading ${compact ? "guide-loading--compact" : ""}" role="status" aria-live="polite" aria-busy="true">
      <span class="guide-loading__spinner" aria-hidden="true"></span>
      <span>${label}</span>
    </div>
  `;

  function renderLoadingState() {
    if (dom.pageTitle) dom.pageTitle.textContent = "Explorar";
    if (dom.pageSubtitle) dom.pageSubtitle.textContent = "Cargando información del usuario...";
    if (dom.cards) {
      dom.cards.innerHTML = loadingMarkup("Cargando experiencias...");
    }
  }

  function getCurrentTouristId() {
    const direct = window.localStorage.getItem("kc_tourist_id");
    const directDigits = String(direct || "").match(/\d+/g);
    const directParsed = Number(directDigits ? directDigits.join("") : direct);
    if (Number.isFinite(directParsed) && directParsed > 0) return directParsed;

    try {
      const rawSession = window.localStorage.getItem("kc_temp_auth_session_v1");
      const session = rawSession ? JSON.parse(rawSession) : null;
      const role = String(session?.role || "").trim().toLowerCase();
      const digits = String(session?.userId || "").match(/\d+/g);
      const parsed = Number(digits ? digits.join("") : session?.userId);
      if (role === "tourist" && Number.isFinite(parsed) && parsed > 0) {
        window.localStorage.setItem("kc_tourist_id", String(parsed));
        return parsed;
      }
    } catch (error) {
      console.warn("No se pudo resolver el touristId desde la sesion local.", error);
    }

    return null;
  }

  async function hydrateCurrentUser() {
    state.touristId = getCurrentTouristId();
    if (!window.KCTouristApi?.profile?.getMe || !state.touristId) return;

    try {
      const response = await window.KCTouristApi.profile.getMe(state.touristId);
      const profile = response?.data || {};
      const name = String(profile.name || profile.fullName || "").trim();
      if (!name) return;

      state.currentUser = {
        name,
        location: String(profile.location || "").trim(),
      };

      try {
        const rawSession = window.localStorage.getItem("kc_temp_auth_session_v1");
        const session = rawSession ? JSON.parse(rawSession) : {};
        window.localStorage.setItem(
          "kc_temp_auth_session_v1",
          JSON.stringify({
            ...session,
            role: "tourist",
            userId: String(state.touristId),
            fullName: name,
          }),
        );
      } catch (error) {
        console.warn("No se pudo actualizar la sesion local con el nombre del turista.", error);
      }
    } catch (error) {
      console.warn("No se pudo cargar el usuario actual en explorar desde API.", error);
    }
  }

  function renderCurrentUser() {
    const name = state.currentUser?.name || "Explorar";
    if (dom.sidebarUserName && state.currentUser?.name) {
      dom.sidebarUserName.textContent = state.currentUser.name;
    }
    if (dom.pageTitle) {
      dom.pageTitle.textContent = state.currentUser?.name
        ? `Explorar para ${name}`
        : "Explorar destinos y guías";
    }
    if (dom.pageSubtitle) {
      const location = state.currentUser?.location;
      dom.pageSubtitle.textContent = location
        ? `Descubre experiencias y guías pensadas para ${location}.`
        : "Encuentra la experiencia ideal para tu siguiente aventura.";
    }
  }

  function mapExploreItem(raw) {
    return {
      id: raw.id,
      title: raw.title || raw.name || "Experiencia",
      location: raw.location || "México",
      category: (raw.category || "all").toLowerCase(),
      tags: Array.isArray(raw.tags) ? raw.tags : [],
      rating: Number(raw.rating || 0),
      priceLabel: raw.priceLabel || raw.priceText || "$0 MXN",
      image:
        raw.imageUrl ||
        "https://images.unsplash.com/photo-1473093295043-cdd812d0e601?auto=format&fit=crop&w=1200&q=80",
    };
  }

  async function hydrateFromApi() {
    if (!window.KCTouristApi) {
      state.items = fallbackItems.slice();
      return;
    }

    try {
      const response = await window.KCTouristApi.explore.listExperiences({
        page: 0,
        size: 24,
      }, state.touristId);
      const data = response?.data?.items || response?.data || [];
      state.items = Array.isArray(data) && data.length
        ? data.map(mapExploreItem)
        : fallbackItems.slice();
    } catch (error) {
      console.warn("Explore API fallback enabled:", error);
      state.items = fallbackItems.slice();
    }
  }

  function filterItems() {
    const query = dom.searchInput?.value?.trim().toLowerCase() || "";
    return state.items.filter((item) => {
      const passFilter =
        state.activeFilter === "all" || item.category === state.activeFilter;
      if (!passFilter) return false;

      if (!query) return true;
      return (
        item.title.toLowerCase().includes(query) ||
        item.location.toLowerCase().includes(query) ||
        item.tags.some((tag) => tag.toLowerCase().includes(query))
      );
    });
  }

  function renderCards() {
    if (!dom.cards) return;
    const rows = filterItems();
    dom.cards.innerHTML = "";

    if (!rows.length) {
      dom.cards.innerHTML = `
        <div class="guide-loading guide-loading--compact" role="status" aria-live="polite" aria-busy="false">
          <span>No se encontraron experiencias con esos filtros.</span>
        </div>
      `;
      return;
    }

    rows.forEach((item) => {
      const card = document.createElement("article");
      card.className = "explore-card";
      card.innerHTML = `
        <div class="explore-card__media" style="background-image:url('${item.image}');">
          <button class="explore-card__favorite" type="button" data-toggle-favorite="${item.id}" aria-label="Agregar a favoritos">
            <span class="material-symbols-outlined">favorite</span>
          </button>
          <span class="explore-card__badge">Experiencia</span>
          <span class="explore-card__rating">
            <span class="material-symbols-outlined">star</span>
            ${item.rating.toFixed(1)}
          </span>
        </div>
        <div class="explore-card__body">
          <h3 class="explore-card__title">${item.title}</h3>
          <p class="explore-card__meta">
            <span class="material-symbols-outlined">location_on</span>
            ${item.location}
          </p>
          <div class="explore-card__tags">
            ${item.tags.map((tag) => `<span class="explore-card__tag">${tag}</span>`).join("")}
          </div>
          <div class="explore-card__footer">
            <span class="explore-card__price">${item.priceLabel}</span>
          </div>
        </div>
      `;

      card.querySelector("[data-toggle-favorite]")?.addEventListener("click", async (event) => {
        event.stopPropagation();
        const itemId = event.currentTarget.getAttribute("data-toggle-favorite");
        try {
          if (window.KCTouristApi) {
            await window.KCTouristApi.explore.toggleFavorite(itemId, state.touristId);
          }
        } catch (error) {
          console.warn("Toggle favorite pending backend implementation:", error);
        }
      });

      card.addEventListener("click", () => {
        openUnderConstructionModal();
      });

      dom.cards.appendChild(card);
    });
  }

  function setActiveFilter(nextFilter) {
    state.activeFilter = nextFilter;
    dom.filters.forEach((button) => {
      button.classList.toggle("is-active", button.dataset.filter === nextFilter);
    });
    renderCards();
  }

  function bind() {
    dom.pageTitle = document.querySelector(".tourist-section__title");
    dom.pageSubtitle = document.querySelector(".tourist-section__subtitle");
    dom.sidebarUserName = document.getElementById("userName");
    dom.searchInput = document.getElementById("exploreSearchInput");
    dom.cards = document.getElementById("exploreCards");
    dom.filters = [...document.querySelectorAll("[data-filter]")];
    dom.quickSearchBtn = document.getElementById("exploreQuickSearch");
    dom.btnChat = document.getElementById("btnChat");
    dom.btnNewTrip = document.getElementById("btnNewTrip");

    dom.searchInput?.addEventListener("input", renderCards);
    dom.quickSearchBtn?.addEventListener("click", renderCards);

    dom.filters.forEach((button) => {
      button.addEventListener("click", () => {
        setActiveFilter(button.dataset.filter || "all");
      });
    });

    dom.btnChat?.addEventListener("click", () => {
      window.dispatchEvent(new CustomEvent("tourist-chat:open"));
    });

    dom.btnNewTrip?.addEventListener("click", () => {
      window.location.href = "./trips.html";
    });
  }

  async function init() {
    bind();
    renderLoadingState();
    await hydrateCurrentUser();
    renderCurrentUser();
    await hydrateFromApi();
    setActiveFilter("all");
  }

  return { init };
})();

const bootstrapTouristExplore = () => {
  const run = () => TouristExploreApp.init();
  const sidebarReady = window.__touristSidebarReadyPromise;

  if (sidebarReady && typeof sidebarReady.finally === "function") {
    sidebarReady.finally(run);
    return;
  }

  run();
};

if (document.readyState === "loading") {
  document.addEventListener("DOMContentLoaded", bootstrapTouristExplore, { once: true });
} else {
  bootstrapTouristExplore();
}
