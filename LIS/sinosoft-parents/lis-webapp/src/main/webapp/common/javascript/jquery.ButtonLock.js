var $jclickdelay = jQuery.noConflict();
/**
 *	���cssButton���ʹ�������disable3���� 2012-01-09 add by HuangLiang
 *	Ĭ��cssButton�࣬3��
 *	����Ҫ�ù��ܵĶ����������buttonLockFlagΪfalse���ɲ���Ӹù���
 */
(function($){
	//�����������¼����ӳ��¼�
    $.fn.clickDelay = function(options){
        var defaults = {
            afterClickDuring: 3000,//�ӳ�ʱ��
            intervalDuring: 1000,//����ʱ��
            clickEvent: function(){
                $.noop();//��д����¼�
            },
            afterClickEvent: function(){
                $.noop();//��д�ӳ��¼�
            },
            intervalEvent: function(){
                $.noop();//��д�����¼�
            },
            ignoreJudge: function(){
            	$.noop();//��д�����ж�
            }
        };
        var sets = $.extend(defaults,options || {});   
        return $(this).each(function(){
        	var $this = $(this);
        	var temp = $this.attr("buttonLockFlag");
   	        var afterClickTimer, intevralTimer;
   	        if(sets.ignoreJudge(this)){//��������ж�Ϊtrue���򲻰�click�¼�
   	        	return null;
   	        }
            $this.attr("afterClickTime",0).click(function(){
            	$this.attr("afterClickTime",sets.afterClickDuring);
            	var a=this;//��������Ϊ��������
            	sets.clickEvent(a);
                intervalTimer = setInterval(function(){sets.intervalEvent(a)},sets.intervalDuring);
                afterClickTimer = setTimeout(function(){sets.afterClickEvent(a)}, sets.afterClickDuring);
                $this.attr("intervalTimer",intervalTimer).attr("afterClickTimer",afterClickTimer);
            });
        });
    }
    //����¼���disable��ť
    $.fn.buttonLock = function(tObjButtonSelect){
		$(tObjButtonSelect).clickDelay({
		    clickEvent: function(tObjInstance){
		    	var $this =$(tObjInstance);
		    	var t=parseInt($this.attr("afterClickTime"))/this.intervalDuring;
				var v = $this.val(); 
		    	var n = v + " ("+t+")";
				$this.attr("disabled","true").attr("oldV",v).val(n);;//���ʹ����disable������ԭ��ֵ
				
		    },
		    intervalEvent: function(tObjInstance){
				var $this=$(tObjInstance);
				if($this.attr("disabled")!=""){
					var t=parseInt($this.attr("afterClickTime"))-this.intervalDuring;
					if(t<=0){//�����¼�ȫ��ִ���꣬ʹ������ã��ָ�ԭ��ֵ
						t=0;
						$this.attr("afterClickTime",t).removeAttr("disabled","").val($this.attr("oldV"));
						clearInterval($this.attr("intervalTimer"));
					}else{//ִ�������¼����޸���ʾֵ
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
		    		&& $this.attr("disabled")!=""){//ִ���ӳ��¼���ʹ������ã��ָ�ԭ��ֵ
		    		$this.removeAttr("disabled","").attr("afterClickTime",0).val($this.attr("oldV"));
		    	}
		    	clearTimeout($this.attr("afterClickTimer"));
		    },
		    ignoreJudge: function(tObjInstance){
		    	var $this=$(tObjInstance);
		    	var flag = $this.attr("buttonLockFlag");
		    	var tValue = $this.val();
		    	tValue = tValue.replace(/ /g,"");//ȥ�ո�
		    	var tFliter = {//���˴�����Ϊ��������ʻ������ڴ˴����Լ��ٳ���
		    		a:"��ҳ".replace(/ /g,""),//ȥ�ո�;
		    		b:"��һҳ".replace(/ /g,""),
		    		c:"��һҳ".replace(/ /g,""),
		    		d:"βҳ".replace(/ /g,"")
		    	};
		    	if(flag == "true"){//��Ҫ���
			    	return false;
			    }else if(flag == "false"){//�к��Ա��
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
 *	���ù���
 */
    var tObjButtonCss = ".cssButton";//��Ҫ���õ���ӳ��¼��������id
	$(document).ready(function () {
		$(this).buttonLock(tObjButtonCss);
	});
})(jQuery);