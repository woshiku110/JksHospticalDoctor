/**
 * Created by zhangchunyu on 2017/4/18.
 */


window.onload = function () {
    loadDataFromWeb(getId());
};
/**
*@desc 用于获取原生的orderId
*/

function getId(){
    return window.control.getOrderId();
}

/**
 * 请求网络数据
 * @param value  订单的ID
 */
function loadDataFromWeb(value) {
    var paramete = {token:tokenx , id:value};
    CYAjax.post(paramete , 'yuyue/AppointmentSupport_getOrder' , function (res) {
        if(res.success){
            var msg = JSON.parse(res.msg);
            $('body').css({
                'display':'block'
            });
            updataUI(msg);
        }else {
            prompt('加载失败');
        }
    },function (error) {
        prompt('加载错误');
    });
}


/**
 * 更新UI
 * @param msg  数据数组
 */
function updataUI(msg) {
    $('#basicInfo-name').text(msg[2]);
    $('#basicInfo-address').text(msg[13]);
    $('#basicInfo-time').text(msg[7]);

    $('#part-part').text(msg[3]);
    $('#part-symptoms').text(msg[11]);

    $('#described-text').text(msg[4]);
    
    // 患处照片
    var imgArr = JSON.parse(msg[5]);
    if (imgArr.length > 0)
    {
        $('#image').css({
            'display':'block'
        });

        var imgWidth = $($('#image>.content')).width()/3 - 30;
        for (var i=0 ; i<imgArr.length ; i++)
        {
            var imgURL =  IMGPATH + imgArr[i];
            var imgBox = $("<div class='img' + i onclick='imgBoxClick(this)'>");
            imgBox.css({
                'display': 'inline-block',
                'width':imgWidth + 'px' ,
                'height':imgWidth + 'px',
                'margin': '0px 30px 30px 0px',
                'background-image': 'url(' + imgURL + ')',
                'background-size': 'cover'
            });
            imgBox.appendTo( $('#image>.content'));
        }

    }


    // 问答
    var str = "";
    var qaArr = JSON.parse(msg[6]);
    if (qaArr.length > 0)
    {
        for (var i=0 ; i<qaArr.length ; i++)
        {
            var dict = qaArr[i];
            str += 'Q:&nbsp; '+ dict["question"] + '</br>' + 'A:&nbsp; ';
            for(var j=0 ; j<dict["answers"].length ;j++)
            {
                str += dict["answers"][j];
                str +=  (j+1 == dict["answers"].length) ?  '</br>' : '、';
            }
        }
    }

    var qa = $('<p>'+str+'</p>');
    qa.appendTo( $('#qa .content'));
    var qaHeight = $(qa).height();
    qa.css({
        'margin-bottom':'10px' ,
        'line-height': qaHeight/5 +'px'
    });
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
