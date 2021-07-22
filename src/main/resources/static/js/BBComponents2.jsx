function BBServiceURL() {
    var host = window.location.host;
    var url = 'wss://' + (host) + '/bbService';
    console.log("URL Calculada: " + url);
    return url;
}


class WSBBChannel {
    constructor(URL, callback) {
        this.URL = URL;
        this.wsocket = new WebSocket(URL);
        this.wsocket.onopen = (evt) => this.onOpen(evt);
        this.wsocket.onmessage = (evt) => this.onMessage(evt);
        this.wsocket.onerror = (evt) => this.onError(evt);
        this.receivef = callback;
    }


    onOpen(evt) {
        console.log("In onOpen", evt);
    }
    onMessage(evt) {
        console.log("In onMessage", evt);
        // Este if permite que el primer mensaje del servidor no se tenga en cuenta.
        // El primer mensaje solo confirma que se estableció la conexión.
        // De ahí en adelante intercambiaremos solo puntos(x,y) con el servidor
        if (evt.data != "Connection established.") {
            this.receivef(evt.data);
        }
    }
    onError(evt) {
        console.error("In onError", evt);
    }

    send(x, y) {
        let msg = '{ "x": ' + (x) + ', "y": ' + (y) + "}";
        console.log("sending: ", msg);
        this.wsocket.send(msg);
    }


}

. . .
// Retorna la url del servicio. Es una función de configuración.
function BBServiceURL() {
 var url = WShostURL() + '/bbService';
 console.log("BBService URL Calculada: " + url);
 return url;
}
// Retorna la url del servicio. Es una función de configuración.
function ticketServiceURL() {
 var url = RESThostURL() + '/getticket';
 console.log("ticketService URL Calculada: " + url);
 return url;
}
// Retorna la url del servicio. Es una función de configuración.
function WShostURL() {
 var host = window.location.host;
 var url = 'ws://' + (host);
 console.log("host URL Calculada: " + url);
 return url;
}
// Retorna la url del servicio. Es una función de configuración.
function RESThostURL() {
 var host = window.location.host;
 var protocol = window.location.protocol;
 var url = protocol + '//' + (host);
 console.log("host URL Calculada: " + url);
 return url;
}
// Retorna la url del servicio. Es una función de configuración.
async function getTicket() {
 const response = await fetch(ticketServiceURL());
 console.log("ticket: " + response);
 return response;
}
. . .
async onOpen(evt) {
 console.log("In onOpen", evt);
 var response = await getTicket();
 var json;
 if (response.ok) {
 // // if HTTP-status is 200-299
 // get the response body (the method explained below)
 json = await response.json();
 } else {
 console.log("HTTP-Error: " + response.status);
 }
 this.wsocket.send(json.ticket);
 }