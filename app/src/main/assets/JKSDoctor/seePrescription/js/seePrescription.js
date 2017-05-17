/**
 * Created by zhangchunyu on 2017/4/21.
 */


//处方
var prescription = function () {
    this.id = "";
    this.name = "";
    this.sex = "";
    this.phone = "";
    this.address = "";
    this.drugData = [];
    this.CaseimgArr = [];
    this.PrescriptionimgArr = [];
};



window.onload = function () {
    loadDataFromWeb(getId());
};
function getId(){
    return window.control.getOrderId();
}
/**
 * 请求网络数据
 * @param value  订单的ID
 */
function loadDataFromWeb(value) {
    var parameter = {id : value};
    CYAjax.post(parameter , 'yuyue/MedicinalSupport_getMedicinalDetail' ,function (res) {
        if(res.success){
            var msg = JSON.parse(res.msg);
            var obj = new prescription();
            for (var i=0 ; i<msg.length; i++) {
                obj[i] = i>5 ? JSON.parse(msg[i]) : msg[i];
            }
            updataUI(obj);
        }else {
            prompt('加载失败');
        }
    },function (error) {
        prompt('加载错误');
    });
}


/**
 * 更新UI
 * @param obj  数据数组
 */
function updataUI(obj) {
    var body = $('body');

    /*! 处方照片 */
    var imgView = tagBox('处方照片').appendTo(body);
    imageLocation('30px' , '15px', 3 , obj[8] , imgView);

    /*! 生成药单 */
    for (var i=0;i<obj[6].length ; i++){
        var title = tagBox('录入药单').appendTo(body);
        creatDrugBox(obj[6][i],'15px').appendTo(title);
    }
}

/**
 * 生成药单
 * @param arr       数据数组
 * @param margin    边距
 * @return          药单的盒子
 */
function creatDrugBox(arr,margin) {
    //arr内容  0:id  1:药品名称 2:规格 3:单位 4:剂量 5:状态 6:记录id 7:药品编号
    var parentsBox = $('<div></div>').css({
        'margin':margin
    });

    //编号
    $('<p>' + 'No.' + arr[7] + '</p>').css({
        'font-size':'14px',
    }).appendTo(parentsBox);

    //药品名
    $('<p>' + arr[1] + '</p>').css({
        'font-size':'14px',
        'display':'inline-block'
    }).appendTo(parentsBox);

    //数量
    $('<p>' + arr[4]+arr[3] + '</p>').css({
        'font-size':'14px',
        'float':'right',
        'margin-right':'25px'
    }).appendTo(parentsBox);


    //规格
    $('<p>' + arr[2] + '</p>').css({
        'font-size':'14px',
        'float':'right',
        'margin-right':'49px'
    }).appendTo(parentsBox);

    return parentsBox;
}


/**
 * 生成带标题的母体盒子
 * @param tagText  标题
 * @return         母体盒子
 */
function tagBox(tagText) {
    //母体
    var imgView = $('<div ></div>').css({
        'width':$($(window)).width() + 'px',
        'background-color':'white',
        'border-bottom':'1px solid #DDDDDD',
        'border-top':'1px solid #DDDDDD',
        'margin-bottom':'8px'
    });

    /*! 头部 */
    var imgView_head = $('<div></div>').css({
        'height':'24px',
        'margin':'5px 15px',
        'display': 'flex' ,
        'align-items': 'center',
        'justify-content': 'flex-start' ,
    }).appendTo(imgView);

    $('<hr>').css({
        'border':'0',
        'border-bottom':'1px solid #dddddd',
        'margin':'0px 15px'
    }).appendTo(imgView);

    //色块
    $('<p></p>').css({
        'background-color':"#5683DA",
        'width':'7px',
        'height':'15px'
    }).appendTo(imgView_head);

    //文本标签
    $('<p>&nbsp;&nbsp;'+tagText+'</p>').css({
        'font-size':'16px'
    }).appendTo(imgView_head);

    return imgView;
}


/**
 * 九宫格图像排列
 * @param spacing        元素间距
 * @param margin         外边距
 * @param rowMaxCount    每行最大个数
 * @param imgArr         图片名数组
 * @param fatherView     父标签
 * @return               承载图像的盒子
 */
function imageLocation(spacing ,margin ,rowMaxCount,imgArr , fatherView) {
    spacing = ' '+spacing+' ';
    var contentBox = $('<div></div>').css({
        'margin':margin
    }).appendTo(fatherView);

    var imgWidth = ($(contentBox).width() - parseInt(spacing)*(rowMaxCount-1)) /rowMaxCount;
    if (imgWidth < 0){
        console.log('%cError msg:%s',"color:red", '单个图像的宽度不能小于0，没有意义!');
        return;
    }
    for (var i=0 ; i<imgArr.length ; i++)
    {
        var imgURL =  IMGPATH + imgArr[i];
        var imgBox = $("<div class=i+'img' onclick='imgBoxClick(this)'>");

        var rightMagin = (i - parseInt(i/rowMaxCount)*rowMaxCount +1) === rowMaxCount
            ? ' 0px '
            : spacing;
        var bottomMagin = parseInt((imgArr.length-1)/rowMaxCount) === parseInt(i/rowMaxCount)
            ? ' 0px '
            : spacing;

        imgBox.css({
            'display': 'inline-block',
            'width':imgWidth + 'px' ,
            'height':imgWidth + 'px',
            'margin': '0px' + rightMagin +bottomMagin + '0px',
            'background-image': 'url(' + imgURL + ')',
            'background-size': 'cover'
        });
        imgBox.appendTo(contentBox);
    }
    return contentBox;
}

/**
 * 图像被点击
 * @param box      图片所在盒子
 */
function imgBoxClick(box) {
    console.log('图片被点击'+box);
    var path = box.style.backgroundImage;
    window.control.showPic(getClickedImage(path));
}
