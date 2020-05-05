var app = new Vue({
    el: '#app',
    mounted: function () {
        this.$nextTick(function () {
            let socket = new SockJS('/ws');
            let stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);

                stompClient.subscribe('/game/asztal', function (val) {
                    console.log(val);
                    console.log(JSON.parse(val.body));
                    vm.list1 = JSON.parse(val.body);
                });
                stompClient.subscribe('/game/tick', function (val) {
                    console.log(val);
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
})
