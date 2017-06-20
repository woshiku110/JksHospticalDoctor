/**
 * Created by zhangchunyu on 2017/4/21.
 */



window.onload = function () {
    //loadData('可爱的周明' , '18602650920' , '9999@qq.com' , 'test.jpg');
    var datas = getInfos();
    setToken(datas[4]);
    loadData(datas[0] , datas[1] , datas[2] , datas[3]);
}

function getInfos(){
    var data = [];
    data[0] = window.control.getName();
    data[1] = window.control.getPhone();
    data[2] = window.control.getQQ();
    data[3] = window.control.getPic();
    data[4] = window.control.getToken();
    return data;
}


/**
 * 保存信息
 * @param imgname  图片名
 */
function save(imgname) {
    var parametes = {phone : $('#phonex').val() , email : $('#emailx').val() , value : imgname , token:tokenx};
    CYAjax.post(parametes , 'yuyue/DocterPersonalCenter_updateDoctorInfo' , function (res) {
        if(res.success){
            prompt('修改成功');
            window.control.reviseResult(true,"修改成功",parametes.phone,parametes.email,parametes.value);
        }else {
            window.control.reviseResult(false,"修改失败",parametes.phone,parametes.email,parametes.value);
            prompt('修改失败');
        }
    },function (error) {
        window.control.reviseResult(false,"网络请求失败",parametes.phone,parametes.email,parametes.value);
        prompt('网络请求失败');
    });
}



/**
 * 设置数据
 * @param userName  用户名
 * @param phone     手机号码
 * @param email     邮箱
 * @param headImg   图片名
 */
function loadData(userName , phone , email , headImg) {
    $('#headImg').attr('src',IMGPATH + headImg);
    $('#username').val(userName);
    $('#phonex').val(phone);
    $('#emailx').val(email);
}


/**
 * 点进进入 跳转到修改密码页
 */
function enterClick() {
    location.href= encodeURI(encodeURI("../updataPassword/updataPassword.html?usernamet=" + $('#username').val()));
}

/**
 * 头像图片被点击
 */
function headImgClick() {
    window.control.iconClicked();
    console.log('头像图片被点击');
}

/**
 * 设置头像图片
 * @param path  路径
 */
function setHeadImg(path) {
    $('#headImg').attr('src',path);
}