


var imgView;
window.onload = function () {
    imgView = new imageView('12px' , '14px 14px 0px 0px' , 3 , ['imgs/img_add_photo.png'] , './',$('.imgView')[0]);
}


function imgBoxClick(btn) {
    var className = $(btn).attr('class');
    if (className === '0imgs'){
        location.href = 'cy://openCamera';
    }else {
        var arr = [ $(btn).attr('imgPath') ];
        location.href = 'cy://zoomImageFromArr:&'+JSON.stringify(arr);
    }
}

function addimage(strArr) {
    $(strArr).each(function (index , value) {
        imgView.addimage(value);
    });
}



/**
 * 九宫格图像排列
 * @param spacing        元素间距
 * @param margin         外边距
 * @param rowMaxCount    每行最大个数
 * @param imgArr         图片名数组
 * @param imgPath        图片基础路径
 * @param fatherView     父标签
 * @return               图像View对象
 */

function imageView(spacing ,margin ,rowMaxCount,imgArr , imgPath ,fatherView) {
    /*! 承载图像的盒子 */
    this.contentBox = function () {
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
            var imgURL =  imgPath + imgArr[i];
            creatImage(contentBox , imgURL ,i)
        }
        return contentBox;
    }();

    /*! 添加图像方法 */
    this.addimage = function (imageURL) {
        imgArr.push(imageURL);
        creatImage(this.contentBox , imageURL ,imgArr.length-1);
    };

    /*! 生产图像方法 */
    function creatImage(contentBox , imgURL ,i) {
        var imgBox = $("<div class="+i+ "imgs onclick='imgBoxClick(this)'>");
        var imgWidth = ($(contentBox).width() - parseInt(spacing)*(rowMaxCount-1)) /rowMaxCount;
        imgBox.attr('imgPath' , imgURL);

        var rightMagin = (i - parseInt(i/rowMaxCount)*rowMaxCount +1) === rowMaxCount
            ? ' 0px '
            : spacing;

        // var bottomMagin = parseInt((imgArr.length-1)/rowMaxCount) === parseInt(i/rowMaxCount)
        //     ? ' '+parseFloat(spacing)/2+'px '
        //     : ' '+parseFloat(spacing)/2+'px ';

        var bottomMagin = ' '+parseFloat(spacing)/2+'px ';

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
}
