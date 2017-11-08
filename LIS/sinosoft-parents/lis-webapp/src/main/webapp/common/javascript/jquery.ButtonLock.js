var $jclickdelay = jQuery.noConflict();
/**
 *	点击cssButton类后，使被点对象disable3秒钟 2012-01-09 add by HuangLiang
 *	默认cssButton类，3秒
 *	不需要该功能的对象，添加属性buttonLockFlag为false即可不添加该功能
 */
(function($){
	//点击后的周期事件与延迟事件
    $.fn.clickDelay = function(options){
        var defaults = {
            afterClickDuring: 3000,//延迟时间
            intervalDuring: 1000,//周期时间
            clickEvent: function(){
                $.noop();//复写点击事件
            },
            afterClickEvent: function(){
                $.noop();//复写延迟事件
            },
            intervalEvent: function(){
                $.noop();//复写周期事件
            },
            ignoreJudge: function(){
            	$.noop();//复写忽略判断
            }
        };
        var sets = $.extend(defaults,options || {});   
        return $(this).each(function(){
        	var $this = $(this);
        	var temp = $this.attr("buttonLockFlag");
   	        var afterClickTimer, intevralTimer;
   	        if(sets.ignoreJudge(this)){//如果忽略判断为true，则不绑定click事件
   	        	return null;
   	        }
            $this.attr("afterClickTime",0).click(function(){
            	$this.attr("afterClickTime",sets.afterClickDuring);
            	var a=this;//将对象作为参数传入
            	sets.clickEvent(a);
                intervalTimer = setInterval(function(){sets.intervalEvent(a)},sets.intervalDuring);
                afterClickTimer = setTimeout(function(){sets.afterClickEvent(a)}, sets.afterClickDuring);
                $this.attr("intervalTimer",intervalTimer).attr("afterClickTimer",afterClickTimer);
            });
        });
    }
    //点击事件：disable按钮
    $.fn.buttonLock = function(tObjButtonSelect){
		$(tObjButtonSelect).clickDelay({
		    clickEvent: function(tObjInstance){
		    	var $this =$(tObjInstance);
		    	var t=parseInt($this.attr("afterClickTime"))/this.intervalDuring;
				var v = $this.val(); 
		    	var n = v + " ("+t+")";
				$this.attr("disabled","true").attr("oldV",v).val(n);;//点击使对象disable，保存原有值
				
		    },
		    intervalEvent: function(tObjInstance){
				var $this=$(tObjInstance);
				if($this.attr("disabled")!=""){
					var t=parseInt($this.attr("afterClickTime"))-this.intervalDuring;
					if(t<=0){//周期事件全部执行完，使对象可用，恢复原有值
						t=0;
						$this.attr("afterClickTime",t).removeAttr("disabled","").val($this.attr("oldV"));
						clearInterval($this.attr("intervalTimer"));
					}else{//执行周期事件，修改显示值
		    			var v = $this.attr("oldV");    			
		    			var k =t/this.intervalDuring;
		    			v = v + " ("+k+")";
		    			$this.val(v).attr("afterClickTime",t);
		    		}
		    	}else{
		    		$this.attr("afterClickTime",0).removeAttr("disabled","").val($this.attr("oldV"));
		    		clearInterval($this.attr("intervalTimer"));
		    	}
		    },
		    afterClickEvent: function(tObjInstance){
				var $this=$(tObjInstance);
		    	if(parseInt($this.attr("afterClickTime"))<=this.intervalDuring 
		    		&& $this.attr("disabled")!=""){//执行延迟事件，使对象可用，恢复原有值
		    		$this.removeAttr("disabled","").attr("afterClickTime",0).val($this.attr("oldV"));
		    	}
		    	clearTimeout($this.attr("afterClickTimer"));
		    },
		    ignoreJudge: function(tObjInstance){
		    	var $this=$(tObjInstance);
		    	var flag = $this.attr("buttonLockFlag");
		    	var tValue = $this.val();
		    	tValue = tValue.replace(/ /g,"");//去空格
		    	var tFliter = {//过滤词条，为了满足国际化，放在此处可以减少出错
		    		a:"首页".replace(/ /g,""),//去空格;
		    		b:"上一页".replace(/ /g,""),
		    		c:"下一页".replace(/ /g,""),
		    		d:"尾页".replace(/ /g,"")
		    	};
		    	if(flag == "true"){//需要添加
			    	return false;
			    }else if(flag == "false"){//有忽略标记
			    	return true;
			    }else{
			    	for(var item in tFliter){
			    		if(item!="Clone"){
			    			if(tFliter[item]==tValue){
			    				return true;
			    			}
			    		}
			    	}
			    	return false;
			    }
			}
		});
	}
/**
 *	调用功能
 */
    var tObjButtonCss = ".cssButton";//需要设置点击延迟事件的类或者id
	$(document).ready(function () {
		$(this).buttonLock(tObjButtonCss);
	});
})(jQuery);