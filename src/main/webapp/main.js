let stompClient = null;

const app = new Vue({
    el: '#app',
    methods: {
        kihiv: function (kartya) {
            if (this.stompClient && this.stompClient.connected) {
                console.log("sending " + JSON.stringify(kartya));
                this.stompClient.send("/app/kihiv", {}, JSON.stringify(kartya));
            }
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            let socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.debug = function(str) {};
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);

                stompClient.subscribe('/game/asztal', function (message) {
                    console.log(JSON.parse(message.body));
                });
                stompClient.subscribe('/game/log', function (message) {
                    console.log("LOG: " + message.body);
                });
            });
        });
    },
    data: {
        kovetkezo: "Kristóf",
        asztal: [
            "kor04",
        ],
        felso_kez: [
            "tarokk01",
            "tarokk02",
            "tarokk03",
            "tarokk04",
            "tarokk05",
            "tarokk06",
            "tarokk07",
            "tarokk08",
            "tarokk09",
        ],
        bal_kez: [
            "tarokk01",
            "tarokk02",
            "tarokk03",
            "tarokk04",
            "tarokk05",
            "tarokk06",
            "tarokk07",
            "tarokk08",
            "tarokk09",
        ],
        sajat_kez: [
            "tarokk11",
            "tarokk12",
            "tarokk13",
            "tarokk14",
            "tarokk15",
            "tarokk16",
            "tarokk17",
            "tarokk18",
            "tarokk21",
        ],
        jobb_kez: [
            "tarokk01",
            "tarokk02",
            "tarokk03",
            "tarokk04",
            "tarokk05",
            "tarokk06",
            "tarokk07",
            "tarokk08",
            "tarokk09",
        ]
    }
});
