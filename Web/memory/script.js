let selezioneCarteContainer, board

window.onload = () => {
   selezioneCarteContainer = document.getElementById("selezione-carte-container")
   board = document.getElementById("board")

   board.style.display = "none"
}

function selezionaNumCarte() {
   selezioneCarteContainer.style.display = "none"
   board.style.display = "none"
}
