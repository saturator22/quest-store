function switchMainMenu(user_id) {
    let buttonsList = document.querySelectorAll('*[id=buy-button]');
    for(index = 0; index < buttonsList.length; index++) {
        let button = buttonsList[index];
        if (user_id == 1) {
            button.innerHTML = "get artifact";
            // button.addEventListener("click", getArtifact);
        } else if (user_id == 2) {
            button.innerHTML = "edit artifact";
            // button.addEventListener("click", editArtifact);
        }
    }    
}