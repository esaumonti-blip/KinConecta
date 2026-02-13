(function () {
  const qs = (selector, parent = document) => parent.querySelector(selector);
  const qsa = (selector, parent = document) =>
    Array.from(parent.querySelectorAll(selector));

  const prefersReduced = window.matchMedia(
    "(prefers-reduced-motion: reduce)",
  ).matches;

  const menuBtn = qs(".header__menu-btn");
  const mobileMenu = qs("#mobileMenu");

  if (menuBtn && mobileMenu) {
    const setOpen = (open) => {
      menuBtn.setAttribute("aria-expanded", String(open));
      mobileMenu.hidden = !open;
      document.body.style.overflow = open ? "hidden" : "";
    };

    menuBtn.addEventListener("click", () => {
      const isOpen = menuBtn.getAttribute("aria-expanded") === "true";
      setOpen(!isOpen);
    });

    qsa(".mobile-menu__link", mobileMenu).forEach((link) => {
      link.addEventListener("click", () => setOpen(false));
    });

    document.addEventListener("keydown", (event) => {
      if (event.key === "Escape") setOpen(false);
    });
  }

  const rippleTargets = qsa(
    ".btn--ripple, .footer__social-btn, .toast__close",
  );

  rippleTargets.forEach((target) => {
    target.addEventListener("click", (event) => {
      const rect = target.getBoundingClientRect();
      const ripple = document.createElement("span");
      ripple.className = "ripple";

      const size = Math.max(rect.width, rect.height);
      ripple.style.width = ripple.style.height = `${size}px`;
      ripple.style.left = `${event.clientX - rect.left - size / 2}px`;
      ripple.style.top = `${event.clientY - rect.top - size / 2}px`;

      target.appendChild(ripple);
      window.setTimeout(() => ripple.remove(), 700);
    });
  });

  const revealItems = qsa(".reveal");
  if ("IntersectionObserver" in window && revealItems.length) {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("is-visible");
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.12 },
    );

    revealItems.forEach((item) => observer.observe(item));
  } else {
    revealItems.forEach((item) => item.classList.add("is-visible"));
  }

  const navLinks = qsa(".header__link");
  const navTargets = navLinks
    .map((link) => {
      const href = link.getAttribute("href") || "";
      const hashIndex = href.indexOf("#");
      const hasHash = hashIndex >= 0;
      const base = hasHash ? href.slice(0, hashIndex) : href;
      const id = hasHash ? href.slice(hashIndex + 1) : "inicio";

      if (base && !base.endsWith("index.html") && !base.endsWith("./index.html")) {
        return null;
      }

      return { link, id };
    })
    .filter(Boolean);

  const sections = navTargets
    .map((item) => qs(`#${item.id}`))
    .filter(Boolean);

  const setActive = (id) => {
    navLinks.forEach((link) => link.classList.remove("header__link--active"));
    const active = navTargets.find((item) => item.id === id);
    if (active) active.link.classList.add("header__link--active");
  };

  if (sections.length) {
    const spy = () => {
      const y = window.scrollY + 160;
      let current = sections[0].id;
      sections.forEach((section) => {
        if (section.offsetTop <= y) current = section.id;
      });
      setActive(current);
    };

    window.addEventListener("scroll", spy, { passive: true });
    spy();
  }

  const floatCards = qsa(".js-float");
  if (!prefersReduced && floatCards.length) {
    const setup = floatCards.map((card) => ({
      card,
      amplitude: 5 + Math.random() * 4,
      speed: 0.0011 + Math.random() * 0.0007,
      phase: Math.random() * Math.PI * 2,
      rotation: (Math.random() * 2 - 1) * 1.2,
    }));

    const animate = (time) => {
      setup.forEach((item) => {
        const y = Math.sin(time * item.speed + item.phase) * item.amplitude;
        const r = Math.sin(time * (item.speed * 0.9) + item.phase) * item.rotation;
        item.card.style.transform = `translateY(${y}px) rotate(${r}deg)`;
      });
      requestAnimationFrame(animate);
    };

    requestAnimationFrame(animate);
  }

  const experienceMap = qs(".js-experience-map");
  if (experienceMap) {
    const mapImage = qs(".experience-map__image", experienceMap);
    const mapStill = qs(".experience-map__still", experienceMap);

    if (mapImage && mapStill) {
      const ctx = mapStill.getContext("2d");

      const resizeStillCanvas = () => {
        const rect = mapImage.getBoundingClientRect();
        if (!rect.width || !rect.height) return null;

        const dpr = window.devicePixelRatio || 1;
        mapStill.width = Math.round(rect.width * dpr);
        mapStill.height = Math.round(rect.height * dpr);
        mapStill.style.width = `${rect.width}px`;
        mapStill.style.height = `${rect.height}px`;

        if (ctx) ctx.setTransform(dpr, 0, 0, dpr, 0, 0);

        return { width: rect.width, height: rect.height };
      };

      const drawFrameToCanvas = (frame) => {
        if (!ctx) return;
        const size = resizeStillCanvas();
        if (!size) return;

        ctx.clearRect(0, 0, size.width, size.height);
        ctx.drawImage(frame, 0, 0, size.width, size.height);
      };

      const setupFallbackFreeze = () => {
        let freezeTimer = null;

        const drawCurrentFrame = () => {
          if (!mapImage.complete || !mapImage.naturalWidth) return;
          drawFrameToCanvas(mapImage);
        };

        const freezeMap = () => {
          drawCurrentFrame();
          experienceMap.classList.add("experience-map--still");
        };

        const unfreezeMap = () => {
          experienceMap.classList.remove("experience-map--still");
        };

        const onScrollActivity = () => {
          if (prefersReduced) {
            freezeMap();
            return;
          }

          unfreezeMap();
          if (freezeTimer) window.clearTimeout(freezeTimer);
          freezeTimer = window.setTimeout(() => {
            freezeMap();
          }, 160);
        };

        const initMapState = () => {
          freezeMap();
        };

        if (mapImage.complete && mapImage.naturalWidth) {
          initMapState();
        } else {
          mapImage.addEventListener("load", initMapState, { once: true });
        }

        window.addEventListener("scroll", onScrollActivity, { passive: true });
        window.addEventListener("touchmove", onScrollActivity, { passive: true });
        window.addEventListener("wheel", onScrollActivity, { passive: true });
        window.addEventListener("resize", () => {
          if (experienceMap.classList.contains("experience-map--still")) {
            drawCurrentFrame();
          }
        });
      };

      const setupScrollScrub = async () => {
        if (prefersReduced || !("ImageDecoder" in window)) return false;

        try {
          const sourceUrl = mapImage.currentSrc || mapImage.src;
          if (!sourceUrl) return false;

          const response = await fetch(sourceUrl);
          if (!response.ok) return false;

          const data = await response.arrayBuffer();
          const decoder = new ImageDecoder({
            data,
            type: "image/webp",
            preferAnimation: true,
          });

          if (decoder.tracks && decoder.tracks.ready) {
            await decoder.tracks.ready;
          }

          const frameCount = decoder.tracks?.selectedTrack?.frameCount || 0;
          if (frameCount < 2) return false;

          let renderedIndex = -1;
          let renderToken = 0;
          let rafId = 0;

          const getScrollProgress = () => {
            const rect = experienceMap.getBoundingClientRect();
            const viewportHeight =
              window.innerHeight || document.documentElement.clientHeight;
            const start = viewportHeight * 0.9;
            const end = -rect.height;
            const raw = (start - rect.top) / (start - end);
            return Math.max(0, Math.min(1, raw));
          };

          const renderIndex = async (index) => {
            if (index === renderedIndex) return;

            const token = ++renderToken;
            const decoded = await decoder.decode({
              frameIndex: index,
              completeFramesOnly: true,
            });

            if (token !== renderToken) {
              decoded.image.close();
              return;
            }

            drawFrameToCanvas(decoded.image);
            decoded.image.close();
            renderedIndex = index;
          };

          const updateFromScroll = () => {
            const progress = getScrollProgress();
            const nextIndex = Math.round(progress * (frameCount - 1));
            renderIndex(nextIndex).catch(() => {});
          };

          const scheduleUpdate = () => {
            if (rafId) return;
            rafId = window.requestAnimationFrame(() => {
              rafId = 0;
              updateFromScroll();
            });
          };

          await renderIndex(0);
          experienceMap.classList.add("experience-map--scrub-ready");

          window.addEventListener("scroll", scheduleUpdate, { passive: true });
          window.addEventListener("touchmove", scheduleUpdate, { passive: true });
          window.addEventListener("wheel", scheduleUpdate, { passive: true });
          window.addEventListener("resize", scheduleUpdate);

          scheduleUpdate();
          return true;
        } catch (_error) {
          return false;
        }
      };

      setupScrollScrub().then((isEnabled) => {
        if (!isEnabled) {
          setupFallbackFreeze();
        }
      });
    }
  }

  const contactSection = qs("#contacto");
  const form = qs(".contact-form", contactSection || document);
  const toast = qs(".toast", contactSection || document);
  const toastClose = qs(".toast__close", contactSection || document);

  const hints = {
    name: qs('[data-hint="name"]', contactSection || document),
    email: qs('[data-hint="email"]', contactSection || document),
    message: qs('[data-hint="message"]', contactSection || document),
  };

  const showHint = (key, value) => {
    if (!hints[key]) return;
    hints[key].textContent = value || "";
  };

  const isEmail = (value) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(value).trim());

  const showToast = () => {
    if (!toast) return;
    toast.hidden = false;
    window.setTimeout(() => {
      toast.hidden = true;
    }, 4200);
  };

  if (toastClose && toast) {
    toastClose.addEventListener("click", () => {
      toast.hidden = true;
    });
  }

  if (form) {
    form.addEventListener("submit", (event) => {
      event.preventDefault();

      const name = qs("#name", form)?.value.trim() || "";
      const email = qs("#email", form)?.value.trim() || "";
      const message = qs("#message", form)?.value.trim() || "";

      let valid = true;
      showHint("name", "");
      showHint("email", "");
      showHint("message", "");

      if (name.length < 3) {
        valid = false;
        showHint("name", "Escribe tu nombre (minimo 3 caracteres).");
      }

      if (!isEmail(email)) {
        valid = false;
        showHint("email", "Revisa tu correo (ej. tu@email.com).");
      }

      if (message.length < 10) {
        valid = false;
        showHint("message", "Escribe al menos 10 caracteres.");
      }

      if (!valid) return;

      form.reset();
      showToast();
    });
  }
})();
