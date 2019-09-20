load = function () {
    user.init();
};
var user = {
    getUserById: "user/getUserById",
    init: function () {
        var userId = url.substring(url.indexOf('=') + 1, url.length);
        user.initdata(userId);
    },

    initdata: function (userId) {
        $.ajax({
            type: "GET",
            url: user.getUserById,
            data: {"id": userId},
            dataType: "json",
            success: function (data) {
                if (!data.isError) {
                    alert(data.message)
                } else {
                    alert("数据异常");
                }
            },
            error: function () {
                alert("加载出错");
            }
        });
    },
}