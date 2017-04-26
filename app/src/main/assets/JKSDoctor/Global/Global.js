/**
 * Created by zhangchunyu on 2017/4/18.
 */




var IP = 'http://123.207.243.224/jfs1.1/';

var IMGPATH = 'http://123.207.243.224/File/filebed/';


/**
 * 拿到URL参数
 * @param   paramName  key
 */
function getParam(paramName) {
    paramValue = "";
    isFound = false;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&");
        i = 0;
        while (i < arrSource.length && !isFound)
        {
            if (arrSource[i].indexOf("=") > 0) {
                if (arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase()) {
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