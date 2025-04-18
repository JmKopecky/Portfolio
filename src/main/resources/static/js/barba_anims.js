//fun console message on js load initially
console.log("JK001: Booting...");


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





document.addEventListener("DOMContentLoaded", () => {

    //fun console greeting on DOM load
    console.log("JK001: Boot complete")
    console.log("JK001: Welcome to console:jkopecky.dev")
    console.log("JK001: If all goes to plan, there shouldn't be anything worrying here")
    console.log("JK001: Which means there should be at least five errors, natura-")
    setTimeout(() => {
        console.log("  -- Session Hijacked --")
        setTimeout(() => {
            console.log("Ghost714: I see you, Reclaimer.")
        }, 500);
    }, 500);



    if (history.scrollRestoration) {
        history.scrollRestoration = 'manual';
    }
    hljs.highlightAll();


    barba.hooks.enter((data) => {
        lenis.stop();
        hljs.highlightAll();
        console.log(2);
    })

    barba.hooks.after((data) => {
        window.scrollTo(0,0);
        hljs.highlightAll();
        console.log(3);

        if ($("#edit-project-page") !== undefined) {
            editProjectInit();
        }
        if ($("#home-page") !== undefined) {
            homePageInit();
        }
    });
})