/**
 * Created by zhangchunyu on 2017/4/25.
 */


//地址
var address = function (arr) {
    this.addressID = arr[0]; /** 地址ID */
    this.seeingAddress = arr[1]; /** 就诊地址 */
    this.watingAddress = arr[2]; /** 候诊地址 */
    this.hospitalName = arr[3]; /** 医院名称 */
    this.defaultAddress = arr[4]; /** 是否为默认地址 */
    this.hospitalID = arr[5]; /** 医院ID */
}

var addressArr = [];

/*
*       README
*
*       1.需要在loadDataFromWeb前  给tokenx赋值   在Global.js中
*
*       2.需要原生实现弹出提示框，已在适当时候提示用户  在Global.js中 prompt这个方法已经给回调了
*
*       3.在点击删除时候，实现一下调用原生的确认框，得到用户授权后再调用  deleteAddress确定删除 来删除
*
*       4.新增地址  原生调用 addAddress（ ）这个方法即可
*
* */


window.onload = function () {
    loadDataFromWeb();
}


/**
 * 请求网络数据
 */
function loadDataFromWeb() {
    var paramete = {token:getToken()};
    CYAjax.post(paramete , 'yuyue/DocterPersonalCenter_getDoctorAddress' , function (res) {
        if(res.success){
            var msg = JSON.parse(res.msg);
            for (var i=0 ; i<msg.length; i++)
            {
                var obj = new address(msg[i]);
                addressArr.push(obj);
            }
            updataUI();
        }else {
            prompt('网络请求失败');
        }
    },function (error) {
        prompt('网络请求错误');
    });
}
/*
 *从客户端拿到token
 */
function getToken(){
    return window.control.getAndroidToken();
}

/**
 * 初始化UI
 */
function updataUI() {
    var body = $('body');
    for (var i=0;i<addressArr.length ; i++)
    {
        var box  = $('<div></div>').css({
            'background-color' : 'white'
        }).appendTo(body);
        creatAddress(addressArr[i].watingAddress , addressArr[i].seeingAddress , '15px' , addressArr[i].defaultAddress)
            .appendTo(box).attr('addressID',addressArr[i].addressID);
    }
}

/**
 * 创建地址
 * @param more1     候诊地址
 * @param more2     接诊地址
 * @param margins   外边距
 * @param isDefault 是否默认地址
 * @return          地址盒子
 */
function creatAddress(more1 , more2 ,margins ,isDefault) {
    var halfMargin = parseInt(margins)/2+'px';

    var box = $('<div></div>').css({
        'padding-top':halfMargin,
    });

    //box.attr('onclick','itemClick(this)');

    $(box).attr("isDefault",isDefault === '1' ? true : false);

    /*! 候诊 */
    var waitingBox =  $('<div></div>').css({
        'margin':margins ,
        'margin-top':'0px',
        'margin-bottom':halfMargin
    }).appendTo(box);

    $('<p>候诊地址</p>').css({
        'margin-bottom':halfMargin
    }).appendTo(waitingBox);
    $('<p>'+more1+'</p>').appendTo(waitingBox);

    $('<hr>').css({
        'border':'0',
        'border-bottom':'1px solid #dddddd'
    }).appendTo(box);


    /*!接诊 */
    var diagnosisBox =  $('<div></div>').css({
        'margin':margins ,
        'margin-top':halfMargin
    }).appendTo(box);

    $('<p>接诊地址</p>').css({
        'margin-bottom':halfMargin
    }).appendTo(diagnosisBox);
    $('<p>'+more2+'</p>').appendTo(diagnosisBox);

    $('<hr>').css({
        'border':'0',
        'border-bottom':'1px solid #dddddd',
        'margin-bottom':'0px'
    }).appendTo(box);



    /*! 按钮 */
    var bottomBtnBox = $('<div></div>').css({
        'margin':margins ,
        'padding-bottom':'15px'
    }).appendTo(box);

    var leftBtnBox = $('<div></div>').css({
        'display': 'inline-flex' ,
        'justify-content': 'flex-start'
    }).appendTo(bottomBtnBox);

    var imgPath = isDefault == '1' ? "./../Global/imgs/btn_s_pre.png" : "./../Global/imgs/btn_s.png";

    var defaultBtnx = creatButton('设为默认','setDefault(this)' ,imgPath).css({
        'width':'80px'
    }).attr('class','defaultSetting').appendTo(leftBtnBox);


    var rightBtnBox = $('<div></div>').css({
        'float':'right',
        'display': 'inline-flex' ,
        'justify-content': 'flex-end'
    }).appendTo(bottomBtnBox);

    creatButton('编辑','editBtnClick(this)' ,"./../Global/imgs/ico_edit.png").css({
        'width':'60px',
        'margin-right':'5px'
    }).appendTo(rightBtnBox);
    creatButton('删除','deleteBtnClick(this)' ,"./../Global/imgs/ico_delete.png").appendTo(rightBtnBox);


    return box;
}

function itemClick(more1,more2){
        alert('on click');
        window.control.addressReturn(more1,more2);
};
/**
 * 点击删除
 */
function deleteBtnClick(button) {
    var box = $(button.parentNode.parentNode.parentNode);
    if (box.attr('isDefault') == 'true'){
        prompt('默认地址不能删除');
        return;
    }
    deleteAddress(box.attr('addressID') , function (state) {
        switch (state){
            case 0:{
                prompt('删除成功');
                $(addressArr).each(function (index,value) {
                    if (value.addressID === box.attr('addressID'))
                    {
                        addressArr.pop(value);
                        return false;
                    }
                });
                box.remove();
                break;
            }
            case 1:{
                prompt('删除失败');
                break;
            }
            case 2:{
                prompt('请求失败');
                break;
            }
            default:{
                break;
            }
        }


    });
}

/**
 * 确定删除
 * @param dzidx 地址id
 * @param func  回调方法
 */
function deleteAddress(dzidx ,func) {
    $.ajax({
        url: IP + "yuyue/DocterPersonalCenter_deleteDoctorAddress",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            token:tokenx,
            dzid:dzidx
        },
        success: function(res) {
            if(res.success){
                func(0);
            }else {
                func(1);
            }
        },
        error:function(res){
            func(2);
        }
    });
}


/**
 * 点击编辑
 */
function editBtnClick(button) {
    var box = $(button.parentNode.parentNode.parentNode);
    $(addressArr).each(function (index , value) {
       if (value.addressID == box.attr('addressID')){
           initAddress(value);
           return false;
       }
    });
}

/**
 * 新增地址
 */
function addAddress() {
    initAddress();
}

/**
 * 初始化 并 跳转更新地址
 */
function initAddress(data) {
    CYCookie.clear('addressX');
    CYCookie.clear('chooseHospitalData');
    if (data == null || data == undefined){
        CYCookie.set('addressManageMentState','DocterPersonalCenter_addDoctorAddress');
        location.href = encodeURI(encodeURI('../updataAddress/updataAddress.html'));
    }else {
        CYCookie.set('addressManageMentState','DocterPersonalCenter_updateDoctorAddress');
        location.href = encodeURI(encodeURI('../updataAddress/updataAddress.html?data='+JSON.stringify(data)));
    }
}



/**
 * 设置默认
 */
function setDefault(button) {
    var box = $(button.parentNode.parentNode.parentNode);
    if (box.attr('isDefault') == 'false'){

        $(addressArr).each(function (index,value)
        {
            if (value.addressID === box.attr('addressID'))
            {
                value.defaultAddress = '1';
                updataAddress(value,function (str) {
                    console.log(str);
                   if (str === '更新成功')
                   {
                       //更新UI
                       var selectTag = $("div[isDefault = true]");
                       selectTag.find('.defaultSetting img').attr('src','./../Global/imgs/btn_s.png');
                       selectTag.attr("isDefault",false);
                       box.attr("isDefault",true);
                       box.find('.defaultSetting img').attr('src','./../Global/imgs/btn_s_pre.png');

                       //更新数据
                       $(addressArr).each(function (index,value) {
                           if (value.addressID != box.attr('addressID'))
                           {
                               value.defaultAddress = '0';
                           }
                       });
                   }
                });
                return false;
            }
        });

    }
}

/**
 * 更新地址
 * @param obj   地址对象
 * @param func  回调方法
 */
function updataAddress(obj,func) {
    $.ajax({
        url: IP + "yuyue/DocterPersonalCenter_updateDoctorAddress",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            token:tokenx ,
            dzid:obj.addressID,
            yyid:obj.hospitalID,
            sfmr:obj.defaultAddress,
            jzdz:obj.seeingAddress,
            hzdz:obj.watingAddress
        },
        success: function(res) {
            if(res.success){
                func('更新成功');
            }else {
                func(res.msg);
            }
        },
        error:function(res){
            func('网络请求失败');
        }
    });
}


/**
 * 生成按钮
 * @param title         标题
 * @param onclickfunc   点击方法
 * @param imgSrc        图像地址
 * @return              按钮
 */
function creatButton(title,onclickfunc,imgSrc) {
    var deleteBtnBox = $('<div onclick='+onclickfunc+'></div>').css({
        'width':'50px',
        'display': 'flex',
        'align-items': 'center'
    });

    $('<img src=' +imgSrc +'>').css({
        'width':'16px',
        'display':'inline-block'
    }).appendTo(deleteBtnBox);


    $('<p>'+title+'</p>').css({
        'font-size':'14px',
        'display':'inline-block',
        'margin-left':'4px'
    }).appendTo(deleteBtnBox);

    return deleteBtnBox;
}