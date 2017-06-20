/**
 * Created by zhangchunyu on 2017/4/26.
 */


//医院地址
var address = function (ID,name) {
    this.addressID = ID; /** 地址ID */
    this.addressName = name; /** 地址名 */
}

var dataArr = [];


window.onload = function () {
    loadDataFromWeb();
}



/**
 * 请求网络数据
 */
function loadDataFromWeb() {
    var paramete  = {token : tokenx};
    CYAjax.post(paramete ,'manage/HospitalManage_getHospitalList' , function (res) {
        if(res.success){
            var msg = JSON.parse(res.msg);
            $(msg).each(function (index , value) {
                var obj = new address(value[0],value[1]);
                dataArr.push(obj);
            });
            updataUI();

        }else {
            prompt(res.msg);
        }
    } , function (error) {
        prompt('网络请求失败');
    });
}

/**
 * 更新UI
 */
function updataUI(){
    var body = $('body');
    $(dataArr).each(function (index , value) {
        var box = $('<div onclick="backData(this)"></div>').css({
            'background-color':'white',
            'padding':'15px'
        }).attr('index',index).appendTo(body);

        $('<p>'+value.addressName+'</p>').css({
            'font-size':'14px',
            'display':'inline-block'
        }).appendTo(box);

        $('<img src="./../Global/imgs/Enter.png">').css({
            'width':'10px',
            'float':'right',
            'display':'inline-block'
        }).appendTo(box);

        $('<hr>').css({
            'margin':'0px',
            'border':'0px',
            'border-bottom':"1px solid #dddddd"
        }).appendTo(body);

    });
}

/**
 * 点击Cell，保存数据并back页面
 */
function backData(box) {
    var index = $(box).attr('index');
    CYCookie.set('chooseHospitalData' ,dataArr[parseInt(index)]);
    window.history.back();
}