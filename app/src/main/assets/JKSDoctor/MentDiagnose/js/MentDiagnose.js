/**
 * Created by zhangchunyu on 2017/4/18.
 */


window.onload = function () {
    var id = getId();
    console.log("id:"+id);
    loadDataFromWeb(id);
}
function getId(){
    return window.control.getOrderId();
}
/**
 * 请求网络数据
 * @param value  订单的ID
 */
function loadDataFromWeb(value) {
    $.ajax({
        url: IP + "yuyue/AppointmentSupport_getOrder",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            id:value
        },
        success: function(res) {
            if(res.success){
                var msg = JSON.parse(res.msg);
                updataUI(msg);
            }else {
                loadDataFromWebFailure();
            }
        },
        error:function(res){
            loadDataFromWebError();
        }
    });
}

function loadDataFromWebFailure() {
    console.log('加载失败');
}
function loadDataFromWebError() {
    console.log('加载错误');
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
            var imgBox = $("<div class='img' + i>");
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
