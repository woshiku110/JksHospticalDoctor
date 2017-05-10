/**
 * Created by zhangchunyu on 2017/4/18.
 */



var IP = 'http://123.207.243.224';
var URL = IP+'/jfs1.1/';
var IMGPATH = 'http://123.207.243.224/File/filebed/';
var tokenx = 'Sp4GQSyZVDqvhf1i6Mw2.23w86X5bBPlSZZOUDWwnEodRT/Lmto0lw__';
var _veritfyClassName;
function setToken(value) {
    tokenx = value;
}

/**
 * 弹出提示框
 * @param text  提示内容
 */
function prompt(text) {
    console.log(text);
}


/**
 * 返回对象的属性数组
 * @param   obj  对象
 * @return  属性名数组
 */
function displayProp(obj){
    var attributeArr = [];
    for(var value in obj){
        attributeArr.push(value);
    }
    return attributeArr;
}



/**
 * 拿到URL参数
 * @param   paramName  key
 */
function getURLParam(paramName) {
    paramValue = "";
    isFound = false;
    if (this.location.search.indexOf("?") === 0 && this.location.search.indexOf("=") > 1) {
        arrSource = decodeURIComponent(this.location.search).substring(1, this.location.search.length).split("&");
        i = 0;
        while (i < arrSource.length && !isFound)
        {
            if (arrSource[i].indexOf("=") > 0) {
                if (arrSource[i].split("=")[0].toLowerCase() === paramName.toLowerCase()) {
                    paramValue = arrSource[i].split("=")[1];
                    isFound = true;
                }
            }
            i++;
        }
    }
    return paramValue;
}


/**
 * -------------->    网络请求     <-----------------
 */
var CYAjax = {
    /**
     * 上传文件
     * @param   data            数据
     * @param   successResults  成功回调
     * @param   errorResults    失败回调
     */
    uploadFile:function(data , successResults , errorResults) {
        $.ajax({
            url: IP + '/File/uploadFile',
            type: 'POST',
            data: data,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (results) {
                if (successResults != undefined){successResults(results);}
            },
            error: function (error) {
                if (errorResults != undefined){errorResults(error);}
            }
        });
    },
    /**
     * post网络请求
     * @param   parametes       参数字典
     * @param   method          方法名
     * @param   successResults  成功回调
     * @param   errorResults    失败回调
     */
    post:function (parametes , method , successResults , errorResults) {
        $.ajax({
            url: URL + method,
            type: 'post',
            dataType:'json',
            async:true,
            data: parametes,
            success: function (results) {
                if (successResults != undefined){successResults(results);}
            },
            error: function (error) {
                if (errorResults != undefined){errorResults(error);}
            }
        });
    },
    /**
     * get网络请求
     * @param   parametes       参数字典
     * @param   method          方法名
     * @param   successResults  成功回调
     * @param   errorResults    失败回调
     */
    get:function (parametes , method , successResults , errorResults) {
        $.ajax({
            url: URL + method,
            type: 'get',
            dataType:'json',
            async:true,
            data: parametes,
            success: function (results) {
                if (successResults != undefined){successResults(results);}
            },
            error: function (error) {
                if (errorResults != undefined){errorResults(error);}
            }
        });
    }
}



/**
 * -------------->    Cookie管理     <-----------------
 */
var CYCookie = {
    get: function(key) {
        console.log("get key="+key);
        var that = this;
        var data = window.control.getValue(key);
        if (that.isJSONStr(data)){
            return JSON.parse(data);
        }
        return data;
    },
    set: function(key , value , time , path) {
        console.log("set key="+key+"and value="+value);
        var type = typeof (value);
        if (type == 'object') {
            value = JSON.stringify(value);
        }
        window.control.setKeyValue(key,value);
    },
    clear: function(key , value) {
    },
    isJSONStr:function(str) {
        if (typeof str == 'string') {
            try {
                JSON.parse(str);
                return true;
            } catch(error) {
                return false;
            }
        }
        console.log("%c 参数不是字符串", 'color: red');
    }
};
/*var CYCookie = {
    var that = this;
    *//**
     * 获取Cookie值
     * @param key  主键
     *//*
    get: function(key) {
        console.log("diaoyong get"+key);
        *//*var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg)){
            var data =  decodeURIComponent(arr[2]);
            if (this.isJSONStr(data)){
                return JSON.parse(data);
            }
            return data;
        }*//*
        *//*var data = window.control.getValue(String key);
        if (that.isJSONStr(data)){
             return JSON.parse(data);
        }
        return data;*//*
        return null;
    },
    *//**
     * 设置Cookie值
     * @param key    主键
     * @param value  值
     * @param time   时间(string类型 m1 代表1秒 s1 代表1分  h1 代表1小时 d1 代表1天  null则为浏览器关闭清除)
     * @param path   作用域地址(null则为全项目读取)
     *//*
    set: function(key , value , time , path) {
        *//*console.log("diaoyong set");
        var type = typeof (value);
        if (type == 'object') {
            value = JSON.stringify(value);
        }
        window.control.setKeyValue(key,value);*//*
        *//*path = path == null ? '/' : path;

        if (time != null && typeof (time) == 'string'){
            var firstLetter = time.substring(0,1);
            var base = parseInt(time.substring(1,time.length));
            if (firstLetter=="m") {
                base *= 1000;
            }
            else if (firstLetter=="s") {
                base *= 60*1000;
            }
            else if (firstLetter=="h") {
                base *= 60*60*1000;
            }
            else if (firstLetter=="d") {
                base *= 24*60*60*1000;
            }else {
                console.log("%c 时间参数请加前缀符, 比如 1秒:'m1' 1分钟:'s1' 1小时:'h1' 1天:'d1' ", 'color: red');
                return;
            }

            var exp = new Date();
            exp.setTime(exp.getTime() + base);
            document.cookie= key + '='+ value +";expires=" + exp.toGMTString() + ';path=' + path;
        }else {
            document.cookie= key + '='+ value + ';path=' + path;
        }*//*
    },
    *//**
     * 清除
     * @param key   主键
     * @param path  作用域地址(null则为全项目读取)
     *//*
    clear: function (key , path) {
        *//*path = (path == null || path == undefined) ? '/' : path;
        var exp = new Date();
        exp.setTime(exp.getTime() -1);
        document.cookie= key + '= ' +";expires=" + exp.toGMTString() + ';path=' + path;*//*
    },
    *//**
     * 判断是否是JSON格式
     * @param   str 需要判断的字符串
     * @return      布尔值
     *//*
    isJSONStr:function(str) {
        if (typeof str == 'string') {
            try {
                JSON.parse(str);
                return true;
            } catch(error) {
                return false;
            }
        }
        console.log("%c 参数不是字符串", 'color: red');
    }

};*/

