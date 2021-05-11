function calcola() {
   const msOra = Date.now()
   const data = new Date(document.getElementById("data").value)

   if (!data)
      return alert("Inserisci una data valida")

   const etaMs = msOra - data.getTime()
   // 568025136000 = 18 anni in ms
   if (etaMs >= 568025136000)
      alert("Sei maggiorenne!")
   else
      alert(`Ti mancano ancora ${parseInt((568025136000 - etaMs) / 86400000)} giorni per diventare maggiorenne.`)
}
