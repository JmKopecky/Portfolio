let currentProject;

document.addEventListener("DOMContentLoaded", () => {
    if ($("#edit-project-page") === undefined) {
        return;
    }
    editProjectInit();
});


function editProjectInit() {

}


function setProject(title) {
    fetch("/create", {
        method: "POST",
        body: JSON.stringify({
            mode: "retrieve",
            search: title
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(r => {
        r.json().then((data) => {
            if (data["error"] === "none") {
                console.log(data);
                currentProject = data["resource"];
                $("#project-browser").css("display", "none");
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
            } else {
                console.log("error: " + data["error"]);
            }
        })
    });
}


function save() {
    let newDisplayTitle = $("#project-title-input");
    let newDesc = $("#project-desc-input");
    let newImage = $("#project-image-input");
    let newContent = $("#project-edit-content");
    let newTech = $("#project-tech-input");
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

    fetch("/create", {
        method: "POST",
        body: JSON.stringify({
            mode: "update",
            resource: currentProject,
            newDisplayTitle: newDisplayTitle.val(),
            newDesc: newDesc.val(),
            newImage: newImage.val(),
            newTech: newTech.val(),
            newContent: newContent.val()
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(r => {
        r.json().then((data) => {
            if (data["error"] === "none") {
                console.log(data);
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

    console.log("save");
}