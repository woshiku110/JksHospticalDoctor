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
}



window.onload = function () {
    loadDataFromWeb(getId());
}

function getId(){
    return window.control.getOrderId();
}

/**
 * 请求网络数据
 * @param value  订单的ID
 */
var objectx = new prescription();
function loadDataFromWeb(value) {
    var parameter = {id:value};
    CYAjax.post(parameter , "yuyue/MedicinalSupport_getMedicinalDetail" ,function (res) {
        if(res.success){
            var msg = JSON.parse(res.msg);
            for (var i=0 ; i<msg.length; i++)
            {
                objectx[i] = i>5 ? JSON.parse(msg[i]) : msg[i];
            }
            updataUI(objectx);
        }else {
            prompt('确认失败');
        }
    },function (error) {
        prompt('网络请求失败');
    });
}


/**
 * 点击删除
 */
function deleteBtnClick(button) {
    //移除数据源
    var className = $(button.parentNode.parentNode).attr('class');
    $(objectx[6]).each(function (index,domEle) {
        if (domEle[6] == className){
            objectx[6].pop(index);
        }
    });
    //移除UI
    $(button.parentNode.parentNode.parentNode).remove();
}

/**
 * 点击编辑
 */
function editBtnClick(button) {
    var className = $(button.parentNode.parentNode).attr('class');
    //你修改完成后 掉一下这个函数，传class名字 和 新数量
    /*edit(className , 99);*/
    _veritfyClassName = className;//把当前量给classname
    openAndroidDialog();
}

function openAndroidDialog(){
    window.control.openAmountDialog();
}

function androidReviseAmount(amount){
    edit(_veritfyClassName,amount);
}
//编辑成功后 修改UI和数据
function edit(className , newNumber) {
    $(objectx[6]).each(function (index,domEle) {
        if (domEle[6] == className){
            var obj = $('.'+className+' p.number');
            domEle[6] = newNumber;
            obj.text(newNumber + domEle[3]);
        }
    });
}


/**
 * 新增药品(未测试)
 * @param drug  单个药单的数组
 */
function addDrug(drug) {
    drug = JSON.parse(drug);
    objectx[6].push(drug);
    var title = tagBox('录入药单').appendTo($('body'));
    creatDrugBox(drug,'15px').appendTo(title);
}


/**
 * 核实药单
 *  @param tokenx  令牌
 */
function ok(tokenx) {
    var parameter = {
        token:tokenx,
        orderId:objectx[0],
        ydlr:objectx[6]
    };

    CYAjax.post(parameter , "yuyue/MedicinalProcess_doctorAffirm" ,function (res) {
        if(res.success){
            submitOk(true,"确认成功");
            prompt('确认成功');
        }else {
            submitOk(true,"确认失败");
            prompt('确认失败');
        }
    },function (error) {
        submitOk(false,"网络请求失败");
        prompt('网络请求失败');
    });
}

function submitOk(isOk,desc){
    window.control.checkOk(isOk,desc);
}

/**
 * 初始化UI
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
    var parentsBox = $('<div class='+arr[6]+'></div>').css({
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



    //规格 和 数量
    var infoBox = $('<div></div>').css({
        'float':'right',
        'display': 'flex' ,
        'justify-content': 'flex-start' ,
        'width':'145px'
    }).appendTo(parentsBox);

    $('<p>' + arr[2] + '</p>').css({

        'font-size':'14px',
        'width':'50px'
    }).appendTo(infoBox);



    var numberStr = arr[4]+arr[3];
    $('<p class="number" >' + numberStr + '</p>').css({
        'font-size':'14px',
        'margin-left':'48px'
    }).appendTo(infoBox);

    //分割线
    $('<hr>').css({
        'border':'0',
        'border-bottom':'1px solid #dddddd',
        'margin-left': '-' +  parseInt(margin) + 'px' ,
        'margin-right':'-' +  parseInt(margin) + 'px'
    }).appendTo(parentsBox);


    //按钮
    var btnBox = $('<div></div>').css({
        'display': 'flex' ,
        'justify-content': 'flex-end' ,
        'width':'100%'
    }).appendTo(parentsBox);

    creatButton('编辑数量','editBtnClick(this)' ,"./imgs/ico_edit.png").css({
        'width':'80px',
        'margin-right':'15px'
    }).appendTo(btnBox);
    creatButton('删除','deleteBtnClick(this)' ,"./imgs/ico_delete.png").appendTo(btnBox);
    return parentsBox;
}

/**
 * 生成按钮
 * @param title         标题
 * @param onclickfunc   点击方法
 * @param imgSrc        图像路径
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
}