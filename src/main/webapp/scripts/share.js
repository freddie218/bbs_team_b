function dbShareClick(title,content){
        var r='http://www.douban.com/share/service?href='+
               encodeURIComponent(document.location.href)+
              '&title='+
               encodeURIComponent(decodeURI(title))+
              '&text='+
               encodeURIComponent(content),
            w=450,
            h=330,
            x=function(){
                if(!window.open(r,'douban',
                               'toolbar=0,resizable=1,scrollbars=yes,status=1,width='+
                                w+
                               ',height='+
                                h+
                               ',left='+
                                (screen.width-w)/2+
                               ',top='+
                                (screen.height-h)/2
                                )
                  ) {
                    location.href=r+'&r=1'
                }
             };
           if(/Firefox/.test(navigator.userAgent)){
               setTimeout(x,0)
           }else{
               x()
           }
}
function rrShareClick(title,content) {
		var rrShareParam = {
			resourceUrl : document.location.href,	//分享的资源Url
			srcUrl : '',	//分享的资源来源Url,默认为header中的Referer,如果分享失败可以调整此值为resourceUrl试试
			pic : '',		//分享的主题图片Url
			title : title,		//分享的标题
			description : content	//分享的详细描述
		};
		rrShareOnclick(rrShareParam);
}