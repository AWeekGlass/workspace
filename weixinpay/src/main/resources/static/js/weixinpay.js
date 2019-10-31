    function pay(orderNo) {
    	//判断是否是微信浏览器 是微信浏览器走公众号支付 普通浏览器走H5支付
    	var useragent = navigator.userAgent;
    	if (useragent.match(/MicroMessenger/i) == 'MicroMessenger') {
    		 gzhpay(orderNo);
    	}else{
    	    $.ajax({
    	    type : "POST",
    	    url : "wx/h5pay,
    	    data:{"orderNo":orderNo}
    	    dataType:"json",
			timeout : 5000,
			async:false,
    	    success : function(res){
    	    	 var url = res.url;
    	    	 top.location=url; 
    	       
    	    },
    	    error : function(XMLHttpRequest, textStatus,
    	            errorThrown) {
    	        alert('支付异常，请联系客服！');
    	    },
    	    complete : function(XMLHttpRequest, textStatus) {
    	    }
    	});
    	}
	}
    
    
    function gzhpay(orderNo) {
        var url = "http://www.xxxxxx.com/Presell/wx/gzhpay"; //注意此处的basePath是没有端口号的域名地址。如果包含:80,在提交给微信时有可能会提示 “redirect_uri参数错误” 。
        //money为订单需要支付的金额
        //state中存放的为商品订单号
        console.log(url);
        var weixinUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd95dcc04f0fd65a8&redirect_uri=" + encodeURI(url) + "&response_type=code&scope=snsapi_base&state="+orderNo+"#wechat_redirect";
        var s=encodeURI(weixinUrl);
        window.location.href=encodeURI(weixinUrl);

    }
    
    
    
    
    
    
    
    
    
    
    
    