/**
 * Created by zhangchunyu on 2017/4/27.
 */

//医院地址
var hospitalAddress = function (ID,name) {
    this.addressID = ID; /** 地址ID */
    this.addressName = name; /** 地址名 */
}

//地址
var address = function (arr) {
    this.addressID = arr[0]; /** 地址ID */
    this.seeingAddress = arr[1]; /** 就诊地址 */
    this.watingAddress = arr[2]; /** 候诊地址 */
    this.hospitalName = arr[3]; /** 医院名称 */
    this.defaultAddress = arr[4]; /** 是否为默认地址 */
    this.hospitalID = arr[5]; /** 医院ID */
}

var isDefault;
var selectHosptial; //选择的医院对象
var addressx = CYCookie.get('addressX'); //传入的对象
var state = CYCookie.get('addressManageMentState');//保存还是更新状态

/*
 *       README
 *
 *       1.需要在loadDataFromWeb前  给tokenx赋值   在Global.js中
 *
 *       2.需要原生实现弹出提示框，已在适当时候提示用户  在Global.js中 prompt这个方法已经给回调了
 *
 *       3.调用save() 来确定保存地址
 *
 * */

window.onload = function () {
    //接收更新地址 的 传值
    if (state == 'DocterPersonalCenter_updateDoctorAddress'
        && (addressx == null || addressx == undefined ||addressx == '')){
        addressx = JSON.parse(decodeURI(getURLParam('data')));
        CYCookie.set('addressX',addressx);
        CYCookie.set('chooseHospitalData',new hospitalAddress(addressx.hospitalID , addressx.hospitalName));
        CYCookie.set('isDefaultAddress',addressx.defaultAddress);
        $('#WaitAddress').find('textarea').val(addressx.watingAddress);
        $('#SeeAddress').find('textarea').val(addressx.seeingAddress);
    }


    //选择医院
    //CYCookie.set('chooseHospitalData',"123");
    selectHosptial = CYCookie.get('chooseHospitalData');
    if (selectHosptial != null){
        try{
            $('#selectAccepts').text(selectHosptial.addressName);
        }catch(err){
        }
    }

    //默认选择
    isDefault = CYCookie.get('isDefaultAddress');
    if (state == 'DocterPersonalCenter_updateDoctorAddress'){
        $('#setDefault').remove();
        $('title').text('修改地址');
    }else {
        var str = isDefault == 1 ? './../Global/imgs/btn_s_pre.png' : './../Global/imgs/btn_s.png';
        $('#setDefault').find('img').attr('src',str);
    }
}


/**
 * 设置默认
 */
function setDefaults(button) {
    if (isDefault == 1){
        $(button).find('img').attr('src','./../Global/imgs/btn_s.png');
        isDefault = 0;
    }else {
        $(button).find('img').attr('src','./../Global/imgs/btn_s_pre.png');
        isDefault = 1;
    }
    CYCookie.set('isDefaultAddress',isDefault);
}

/**
 * 保存
 */
function save() {
    var waitAddressStr = $('#WaitAddress').find('textarea').val();
    var seeAddressStr = $('#SeeAddress').find('textarea').val();

    if (waitAddressStr == '' || waitAddressStr == null || waitAddressStr == undefined){
        prompt('候诊地址不能为空');
        return;
    }
    if (waitAddressStr == '' || seeAddressStr == null || seeAddressStr == undefined){
        prompt('就诊地址不能为空');
        return;
    }
    if (selectHosptial == null || selectHosptial == undefined ||selectHosptial == ''){
        prompt('接诊医院不能为空');
        return;
    }

    var defaultAddressStr =  isDefault == true ? '1' : '0';
    var dzidStr = (addressx != null && addressx != undefined &&addressx != '') ? addressx.addressID : '';
    var array = [dzidStr , seeAddressStr , waitAddressStr , selectHosptial.addressName, defaultAddressStr,selectHosptial.addressID ];
    //var array = ['' , seeAddressStr , waitAddressStr , 'dulala', defaultAddressStr,'1'];
    //alert(array[0]+"\t"+array[1]+array[2]+"\t"+array[3]+"\t"+array[4]+"\t"+array[5]);
    loadDataFromWeb(new address(array));
}



/**
 * 请求网络数据
 */
function loadDataFromWeb(data) {

    var parameter = {
        jzdz : data.seeingAddress,
        hzdz : data.watingAddress ,
        sfmr : data.defaultAddress ,
        yyid : data.hospitalID ,
        dzid : data.addressID,
        token: tokenx
    };
   
    CYAjax.post(parameter ,'yuyue/'+state ,function (res) {
        if(res.success){
            prompt('更新成功');
            window.history.back();
        }else {
            prompt('更新失败');
        }
    },function (error) {
        prompt('网络请求失败');
    });
}

/**
 * 跳转选择医院界面
 */
function choseHosptial() {
    location.href= encodeURI(encodeURI("../ChooseHospital/ChooseHospital.html"));
}


/**
 * 文本框自适应高度
 */
(function() {
    function adjustHeight(el, minHeight) {

        // compute the height difference which is caused by border and outline
        var outerHeight = parseInt(window.getComputedStyle(el).height, 10);
        var diff = outerHeight - el.clientHeight;

        // set the height to 0 in case of it has to be shrinked
        el.style.height = 0;

        // set the correct height
        // el.scrollHeight is the full height of the content, not just the visible part
        el.style.height = Math.max(minHeight, el.scrollHeight + diff) + 'px';
    }


    // we use the "data-adaptheight" attribute as a marker
    var textAreas = document.querySelectorAll('textarea[data-adaptheight]');

    // iterate through all the textareas on the page
    for (var i = 0, l = textAreas.length; i < l; i++) {
        var el = textAreas[i];

        // we need box-sizing: border-box, if the textarea has padding
        el.style.boxSizing = el.style.mozBoxSizing = 'border-box';

        // we don't need any scrollbars, do we? :)
        el.style.overflowY = 'hidden';

        // the minimum height initiated through the "rows" attribute
        var minHeight = el.scrollHeight;

        el.addEventListener('input', function() {
            adjustHeight(this, minHeight);
        });

        // we have to readjust when window size changes (e.g. orientation change)
        window.addEventListener('resize', function() {
            adjustHeight(this, minHeight);
        });

        // we adjust height to the initial content
        adjustHeight(el, minHeight);
    }
}());