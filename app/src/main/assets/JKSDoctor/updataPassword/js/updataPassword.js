/**
 * Created by zhangchunyu on 2017/4/21.
 */



window.onload = function () {
    tokenx = window.control.getToken();
    loadData(decodeURI(getURLParam('usernamet')));
}

/**
 * 加载数据
 * @param userName  用户名
 */
function loadData(userName) {
    $('#username').val(userName);
}


/**
 * 保存修改
 */
function save() {
    var dataArr = [];
    dataArr['username'] =  $('#username').val();
    dataArr['password'] =  $('#password').val();
    dataArr['passwordag'] =  $('#passwordag').val();
    if(dataArr['password'].length<=0){
        window.control.revisePassResult(false,"密码长度不能小于0");
        return;
    }
    if (dataArr['password'] != dataArr['passwordag'])
    {
        window.control.revisePassResult(false,"两次密码不一致");
        prompt('两次密码不一致');
        return;
    }


    var parameter = {password : dataArr['password'], token: tokenx};
    CYAjax.post(parameter , 'upms/updatePassword' ,function (res) {
        if(res.success){
            window.control.revisePassResult(true,"修改成功");
            prompt('修改成功');
            window.history.back();
        }else {
            window.control.revisePassResult(true,"修改失败");
            prompt('修改失败');
        }
    },function (error) {
        window.control.revisePassResult(true,"网络请求失败");
        prompt('网络请求失败');
    });

}