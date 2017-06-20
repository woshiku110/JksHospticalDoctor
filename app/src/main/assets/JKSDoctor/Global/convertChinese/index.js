

var PinYin = require('./lib/unicode.js');

// 汉字转拼音
function covert(l1) {
    var l2 = l1.length;
    var I1 = "";
    var reg = new RegExp('[a-zA-Z0-9\- ]');
    for (var i = 0; i < l2; i++) {
        var val = l1.substr(i, 1);
        var name = arraySearch(val, PinYin);
        if (reg.test(val)) {
            I1 += val;
        } else if (name !== false) {
            I1 += name;
        }

    }
    I1 = I1.replace(/ /g, '-');
    while (I1.indexOf('--') > 0) {
        I1 = I1.replace('--', '-');
    }
    return I1;
}
// 在对象中搜索
function arraySearch(l1, l2) {
    for (var name in PinYin) {
        if (PinYin[name].indexOf(l1) != -1) {
            return ucfirst(name); break;
        }
    }
    return false;
}
// 首字母大写
function ucfirst(l1) {
    if (l1.length > 0) {
        var first = l1.substr(0, 1).toUpperCase();
        var spare = l1.substr(1, l1.length);
       return first + spare;
      // return first;
    }
}

module.exports = covert;