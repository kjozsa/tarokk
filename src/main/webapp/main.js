let stompClient = null;
let asztal = null

const app = new Vue({
    el: '#app',
    methods: {
        kihiv: function (kartya) {
            if (app.asztal.kovetkezo.nev === app.asztal.jatekosok[0].nev) {
                if (stompClient && stompClient.connected) {
                    console.log("sending " + JSON.stringify(kartya));
                    stompClient.send("/app/kihiv", {}, JSON.stringify(kartya));
                }
            }
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            let socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.debug = function (str) {};
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                app.whoami = frame.headers['user-name']

                stompClient.subscribe('/game/asztal', function (message) {
                    console.log(JSON.parse(message.body));
                    app.asztal = JSON.parse(message.body);
                });

                stompClient.subscribe('/game/log', function (message) {
                    console.log("LOG: " + message.body);
                });

                stompClient.send("/app/asztal", {});


            });
        });
    },
    data: function () {
        return {
            asztal: {},
            whoami: ""
        }
    }
});
