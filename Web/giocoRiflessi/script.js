let bottone
let clickTotaliDiv, clickGiustiDiv

window.onload = () => {
   bottone = document.getElementById("bottone")
   clickTotaliDiv = document.getElementById("count-tot")
   clickGiustiDiv = document.getElementById("count-giusti")

   window.onclick = () => {
      clickTotali++;
      clickTotaliDiv.innerText = `Click totali: ${clickTotali}`
   }


   setInterval(cambiaPosizione, 700)
}

let clickTotali = 0
let clickGiusti = 0

function cambiaPosizione() {
   const rootFontSize = bottone.style.fontSize * 5
   const distTop = Math.floor(Math.random() * (document.body.clientHeight + 1)) - rootFontSize
   const distLeft = Math.floor(Math.random() * (document.body.clientWidth + 1)) - rootFontSize
   bottone.style.top = `${distTop}px`
   bottone.style.left = `${distLeft}px`
}

function cliccaBottone() {
   clickGiusti++

   clickGiustiDiv.innerText = `Click giusti ${clickGiusti}`
}
