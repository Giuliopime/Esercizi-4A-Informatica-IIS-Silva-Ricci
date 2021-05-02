let selezioneCarteContainer, schermataVittoria, board, numCarte, elementiCarte = [], tentativiErrati = 0

const listaEmoji = [
   '🐒',
   '🐶',
   '🐕',
   '🐩',
   '🦊',
   '🦄',
   '🐷',
   '🦒',
   '🐸',
   '🐢',
   '🐍',
   '🐳',
   '🦋',
   '🐝',
   '🦂',
   '🌸',
   '🌹',
   '🌱',
   '🌵',
   '🍀',
   '🌙',
   '☀️',
   '⭐',
   '🌈',
   '⚡',
   '❄️',
   '🔥',
   '💧',
   '❤️',
   '🍍',
   '🍓',
   '🍒',
   '🍔',
   '🍟',
   '🍕',
   '🍫',
   '⚽',
   '⚾',
   '🏀',
   '🏈',
   '🎮',
   '🎭',
   '♟️',
   '🎧',
   '🎷',
   '🎸',
   '🎹',
   '🎬',
   '🏹',
   '💣',
   '🎁',
   '🧸',
   '💎',
   '📀',
   '💾',
   '💡',
   '📚',
   '🔑',
   '🔫',
   '🧬',
]

let emojiCarte = []
let emojiENumCartaPrecedente = null
const numeriCarteScoperte = []

let inElaborazione = false

window.onload = () => {
   selezioneCarteContainer = document.getElementById("selezione-carte-container")
   schermataVittoria = document.getElementById("schermata-vittoria")
   board = document.getElementById("board")

   board.style.display = "none"
   schermataVittoria.style.display = "none"

   document.getElementById("input-num-carte").focus()

   document.addEventListener("keyup", e => {
      if (e.keyCode === 13) {
         selezionaNumCarte()
      }
   });
}

function selezionaNumCarte() {
   numCarte = parseInt(document.getElementById("input-num-carte").value)

   if (isNaN(numCarte))
      return alert("Inserisci un numero di carte valido")

   if (numCarte <= 1)
      return alert("Il numero minimo di carte con cui giocare è 2")

   if (numCarte > 60)
      return alert("Il numero massimo di carte con cui giocare è 60")

   if (numCarte % 2 !== 0)
      return alert("Il numero di carte deve essere un numero pari")


   selezioneCarteContainer.style.display = "none"
   board.style.display = "flex"

   const timeCounter = document.getElementById("time-counter")
   let counter = 0
   setInterval(() => {
      timeCounter.innerText = formattaTempo(counter++)
   }, 1000)


   const cardsContainer = document.getElementById("cards-container")

   generaMappa()

   for (let i=0; i<numCarte; i++){
      const card = document.createElement("div")

      card.classList.add("card")
      card.classList.add("centrato")
      card.onclick = function() { scopri(i) }

      cardsContainer.appendChild(card)

      elementiCarte.push(card)
   }
}

function generaMappa() {
   for (let i=0; i<numCarte; i+=2) {
      let emojiIndex = Math.floor(Math.random() * listaEmoji.length)
      let emoji = listaEmoji[emojiIndex];

      listaEmoji.splice(emojiIndex, 1)

      emojiCarte.push(emoji, emoji)
   }

   emojiCarte = shuffleArray(emojiCarte)
}

async function scopri(numCarta) {
   if (inElaborazione)
      return

   inElaborazione = true

   if (numeriCarteScoperte.includes(numCarta))
      return

   const emojiCarta = emojiCarte[numCarta]

   elementiCarte[numCarta].classList.add("scoperta")
   elementiCarte[numCarta].innerText = emojiCarta

   if (emojiENumCartaPrecedente !== null) {
      const sbagliato = emojiENumCartaPrecedente.emoji !== emojiCarta

      await sleep(750)

      if (sbagliato) {
         numeriCarteScoperte.splice(numeriCarteScoperte.findIndex(num => num === emojiENumCartaPrecedente.numCarta), 1)
         copri(emojiENumCartaPrecedente.numCarta)
         copri(numCarta)
         document.getElementById("num-tentativi").innerText = `Tentativi errati: ${++tentativiErrati}`
      } else {
         numeriCarteScoperte.push(numCarta)
         if (numeriCarteScoperte.length === numCarte)
            vittoria()

      }

      emojiENumCartaPrecedente = null
   } else {
      numeriCarteScoperte.push(numCarta)
      emojiENumCartaPrecedente = {
         emoji: emojiCarta,
         numCarta: numCarta
      }
   }

   inElaborazione = false
}


function copri(numCarta) {
   elementiCarte[numCarta].classList.remove("scoperta")
   elementiCarte[numCarta].innerText = ""
}

function vittoria() {
   board.style.display = "none"
   schermataVittoria.style.display = "flex"
}

// Funzioni d'utilità
function formattaTempo(secondi) {
   let h = Math.floor(secondi / 3600),
      m = Math.floor(secondi / 60) % 60,
      s = secondi % 60
   if (h < 10) h = "0" + h
   if (m < 10) m = "0" + m
   if (s < 10) s = "0" + s
   return h + ":" + m + ":" + s
}

function shuffleArray(array) {
   for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      const temp = array[i];
      array[i] = array[j];
      array[j] = temp;
   }

   return array
}

function sleep (ms) {
   return new Promise(resolve => setTimeout(resolve, ms))
}
