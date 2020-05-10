let stompClient = null;

String.prototype.capitalize = function () {
    return this.charAt(0).toUpperCase() + this.slice(1)
}

const app = new Vue({
    el: '#app',
    methods: {
        kihiv: function (kartya) {
            if (app.asztal.kovetkezo.nev === app.whoami) {
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
            stompClient.debug = function (str) {
            };
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                app.whoami = frame.headers['user-name'].capitalize()

                stompClient.subscribe('/user/game/asztal', function (message) {
                    console.log(JSON.parse(message.body));
                    app.asztal = JSON.parse(message.body);
                });

                stompClient.subscribe('/user/game/sajatLapok', function (message) {
                    app.sajatLapok = JSON.parse(message.body);
                });

                stompClient.subscribe('/user/game/tobbiek', function (message) {
                    app.tobbiek = JSON.parse(message.body);
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
            sajatLapok: [],
            tobbiek: [],
            whoami: ""
        }
    }
});
