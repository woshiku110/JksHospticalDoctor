/**
 * Created by zhangchunyu on 2017/4/21.
 */



window.onload = function () {
    loadData('可爱的周明' , '18602650920' , '9999@qq.com' , 'test.jpg');
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
        }else {
            prompt('修改失败');
        }
    },function (error) {
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
    console.log('头像图片被点击');
}

/**
 * 设置头像图片
 * @param path  路径
 */
function setHeadImg(path) {
    $('#headImg').attr('src',path);
}