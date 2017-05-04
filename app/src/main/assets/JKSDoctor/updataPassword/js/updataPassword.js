/**
 * Created by zhangchunyu on 2017/4/21.
 */



window.onload = function () {
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
    if (dataArr['password'] != dataArr['passwordag'])
    {
        prompt('两次密码不一致');
        return;
    }


    var parameter = {password : dataArr['password'], token: tokenx};
    CYAjax.post(parameter , 'upms/updatePassword' ,function (res) {
        if(res.success){
            prompt('修改成功');
            window.history.back();
        }else {
            prompt('修改失败');
        }
    },function (error) {
        prompt('网络请求失败');
    });

}