let currentProject;

function openPanel(elem) {
    const currentPanel = getCurrentPanel();
    if (elem === currentPanel) {
        return; //do nothing because we are already on this panel
    }

    //manage nav link backgrounds
    elem.classList.add("active");
    currentPanel.classList.remove("active");

    //show the target panel and hide the others.
    let panelContainer = document.getElementById("admin-console-panel-container");
    let targetPanel = elem.getAttribute("data-for");
    for (const child of panelContainer.children) { //hide the prior panel
        if (child.getAttribute("data-for") !== targetPanel) {
            child.classList.remove("panel-active"); //todo animations
            child.classList.add("panel-disabled");
        }
    }
    for (const child of panelContainer.children) { //show the target panel
        if (child.getAttribute("data-for") === targetPanel) {
            child.classList.add("panel-active"); //todo animations
            child.classList.remove("panel-disabled");
        }
    }
}



function getCurrentPanel() {
    for (const child of document.getElementById("admin-console-nav").children) {
        if (child.classList.contains("active")) {
            return child;
        }
    }

    return null;
}



function showProjectList() {
    const projectListContainer = document.getElementById("project-panel-list");
    const projectEditContainer = document.getElementById("project-edit-area");
    projectListContainer.classList.remove("hide-project-list");
    projectEditContainer.classList.add("hide-project-edit");
}



function setProject(title) {
    const projectListContainer = document.getElementById("project-panel-list");
    const projectEditContainer = document.getElementById("project-edit-area");

    //retrieve project data
    fetch("/admin/retrieveproject?target=" + title, {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(r => {
        r.json().then((data) => {
            if (data["error"] === "none") {
                currentProject = data["resource"];
                $("#project-edit-content").val(data["content"]);
                $("#project-title-input").val(data["title"]);
                $("#project-desc-input").val(data["shortdesc"]);
                $("#project-image-input").val(data["imageurl"]);
                let techToAdd = "";
                for (const tech of data["techlist"]) {
                    techToAdd += tech + "\n";
                }
                techToAdd = techToAdd.substring(0, techToAdd.length - 1);
                $("#project-tech-input").val(techToAdd);
                let linksToAdd = "";
                for (const link of data["linklist"]) {
                    linksToAdd += link + "\n";
                }
                linksToAdd = linksToAdd.substring(0, linksToAdd.length - 1);
                $("#project-links-input").val(linksToAdd);
            } else {
                console.log("error: " + data["error"]);
            }

            projectListContainer.classList.add("hide-project-list");
            projectEditContainer.classList.remove("hide-project-edit");
        })
    });
}


function openProjectCreatePanel() {
    const projectListContainer = document.getElementById("project-panel-list");
    const projectEditContainer = document.getElementById("project-edit-area");

    projectListContainer.classList.add("hide-project-list");
    projectEditContainer.classList.remove("hide-project-edit");
}



function projectSave() {
    let newDisplayTitle = $("#project-title-input");
    let newDesc = $("#project-desc-input");
    let newImage = $("#project-image-input");
    let newContent = $("#project-edit-content");
    let newTech = $("#project-tech-input");
    let newLinks = $("#project-links-input");
    if (newDisplayTitle.val() === "") {
        newDisplayTitle.focus();
        return;}
    if (newDesc.val() === "") {
        newDesc.focus();
        return;}
    if (newImage.val() === "") {
        newImage.focus();
        return;}
    if (newContent.val() === "") {
        newContent.focus();
        return;}
    if (newTech.val() === "") {
        newTech.focus();
        return;}
    if (newLinks.val() === "") {
        newLinks.focus();
        return;}

    if (currentProject === undefined) {
        currentProject = newDisplayTitle.val();
    }

    fetch("/admin/updateproject", {
        method: "POST",
        body: JSON.stringify({
            resource: currentProject,
            newDisplayTitle: newDisplayTitle.val(),
            newDesc: newDesc.val(),
            newImage: newImage.val(),
            newTech: newTech.val(),
            newContent: newContent.val(),
            newLink: newLinks.val()
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(r => {
        r.json().then((data) => {
            if (data["error"] === "none") {
                $("#save-edit-button").text("Success!");
            } else {
                console.log("error: " + data["error"]);
                $("#save-edit-button").text("Error");
            }
            setTimeout(() => {
                $("#save-edit-button").text("Save");
            }, 2500);
        })
    });
}