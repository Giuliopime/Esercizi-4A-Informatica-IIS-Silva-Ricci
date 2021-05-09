let bottone
let clickMancatiDiv, clickGiustiDiv, tempoRimanenteDiv, header

let cliccato = false

let cicliEseguiti = 0
let clickGiusti = 0

window.onload = () => {
   const urlParams = new URLSearchParams(window.location.search)
   let time = 0
   switch (urlParams.get("d")) {
      case "1": {
         time = 1100
         break
      }
      case "2": {
         time = 900
         break
      }
      case "3": {
         time = 800
         break
      }
      default: {
         time = 900
      }
   }

   header = document.getElementById("header")
   bottone = document.getElementById("bottone")
   clickMancatiDiv = document.getElementById("count-mancati")
   clickGiustiDiv = document.getElementById("count-giusti")
   tempoRimanenteDiv = document.getElementById("tempo")

   let interval = setInterval(cambia, time)
   let i = 0
   let timeInterval = setInterval(() => {
      i++
      tempoRimanenteDiv.innerText = `Timer: ${60 - i}`
   }, 1000)

   setTimeout(() => {
      clearInterval(interval)
      clearInterval(timeInterval)
      alert(`Partita terminata\n\nPrecisione: ${(clickGiusti*100/cicliEseguiti).toFixed(2)}%\nClick corretti: ${clickGiusti}\nClick mancati: ${cicliEseguiti - clickGiusti}`)
   }, 60001)
}

function cambia() {
   cliccato = false
   cicliEseguiti++

   const distTop = Math.floor(Math.random() * ((document.body.clientHeight - bottone.getBoundingClientRect().height) - header.getBoundingClientRect().height) + header.getBoundingClientRect().height) 
   const distLeft = Math.floor(Math.random() * (document.body.clientWidth - bottone.getBoundingClientRect().width))
   bottone.style.top = `${distTop}px`
   bottone.style.left = `${distLeft}px`
   bottone.style.backgroundColor = `rgb(${rand255()}, ${rand255()}, ${rand255()})`

   clickMancatiDiv.innerText = `Click mancati: ${cicliEseguiti - clickGiusti}`
}

function rand255() {
   return Math.floor(Math.random() * (255 + 1))
}

function cliccaBottone() {
   if (!cliccato) {
      clickGiustiDiv.innerText = `Click corretti: ${++clickGiusti}`
      cliccato = true
   }
}
