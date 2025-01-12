barba.init({
    preventRunning: true,
    cacheIgnore: true,
    prefetchIgnore: true,
    transitions: [{
        name: 'main',
        sync: true,
        leave(data) {
            return gsap.to(data.current.container, {
                opacity: 0, duration: 0.25, ease: "power1.inout"
            });
        },
        enter(data) {
            gsap.set(data.next.container, {
                opacity: 0
            })
            gsap.to(data.next.container, {
                opacity: 1, duration: 0.25, ease: "power1.inout", delay: 0.25
            });
            return false;
        }
    }]
});


if (history.scrollRestoration) {
    history.scrollRestoration = 'manual';
}


barba.hooks.enter( (data) => {
    window.scrollY = 0;
    window.scrollTo(0,0);
})


barba.hooks.after((data) => {
    if ($("#edit-project-page") !== undefined) {
        editProjectInit();
    }
    if ($("#home-page") !== undefined) {
        homePageInit();
    }
});