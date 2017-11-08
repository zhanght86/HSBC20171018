//2015-7-21hwp
	$(function(){
		afterload(0);
	});
	
	function afterload(num) {
		var numArray = new Array(num);
		for(var i = 0 ; i < num ; i++) {
		$(".thirdmenu"+i).addClass("third");
		$(".thirdmenu"+i).click(
				function(event){
					$(".thirdmenu"+this.id).siblings(".fourthmenu"+this.id).slideToggle(300);
					if(numArray[this.id]%2){numArray[this.id]++;
						$(".thirdmenu"+this.id).addClass("third");
					}else{numArray[this.id]++;
						$(".thirdmenu"+this.id).addClass("third");
					}
				}
			);
		}
		
		$(".middle").hover(function(){
			$(".middle").show();
			},
			c);
		var i0=0;
		var i1=0;
		$(".kaka").click(
			function(){
				$(".kaka").siblings(".11").slideToggle(300);
				/*if(i0%2){i0++;
					$(".kaka").css("background","url(../common/images/butCollapse.gif) no-repeat 30px center");
				}else{i0++;
					$(".kaka").css("background","url(../common/images/butExpand.gif) no-repeat 30px center");
				}*/
				
			}
		);
		$(".baodan").click(
			function(){
				$(".baodan").siblings(".22").slideToggle(300);
				/*if(i1%2){i1++;
					$(".baodan").css("background","url(../common/images/butCollapse.gif) no-repeat 30px center");
				}else{i1++;
					$(".baodan").css("background","url(../common/images/butExpand.gif) no-repeat 30px center");
				}*/
			}
		);
		
		$("li").mouseover(function(){
			$(".22").hide();
		})
	}
	
	
	function show(){
		var c=document.getElementById('all');
		c.style.width="356px";
	}
function hide(){
		var c=document.getElementById('all');
		c.style.width="168px";
	}



	
	
	function a(){
		//隐藏主菜单
		document.getElementById("divMenuTree").style.display="none";
		//隐藏主菜单
		document.getElementById("divMenuTreeCopy").style.display="block";
		
	}
	
	
	function b(obj,num){
		var cols = parent.document.getElementById("fraSet").cols;
		var index = cols.indexOf(",");
		var obj = cols.split(",");
		afterload(num);
//		var i=obj;
		//显示菜单弹出层
		document.getElementById("middle-self").style.display="block";
		//添加iframe边框宽展
		parent.document.getElementById("fraSet").cols="405,*,"+obj[2]+",0%";
		//背景颜色加深
		//var example=document.getElementById("divMenuTreeCopy").getElementsByTagName("li")[i];
		var example=document.getElementById(obj);
		//example.style.backgroundColor="#1faeff";
		//example.style.color="white";
	}
	
	
	function c(){
		var cols = parent.document.getElementById("fraSet").cols;
		var index = cols.indexOf(",");
		var obj = cols.split(",");
		//隐藏菜单弹出层
		document.getElementById("middle-self").style.display="none";
		//添加iframe边框宽展
		parent.document.getElementById("fraSet").cols="180,*,"+obj[2]+",0%";
		//清理样式li背景
//		for(i=4;i<7;i++){//有问题
//		var example=document.getElementById("divMenuTreeCopy").getElementsByTagName("li")[i];
//		example.style.backgroundColor="white";
//		example.style.color="#2d83ce";
//		}
		var example=document.getElementById("divMenuTreeCopy").getElementsByTagName("li");
		for(i=0;i<example.length;i++){
			var ex=example[i];
			//ex.style.backgroundColor="white";
			//ex.style.color="#2d83ce";
		}
		
	}
	var cols = parent.document.getElementById("fraSet").cols;
	var index = cols.indexOf(",");
	var o = cols.split(",");
	function d(num){
		//隐藏主菜单
		document.getElementById("divMenuTree").style.display="none";
		//隐藏菜单弹出层
		document.getElementById("middle-self").style.display="none";
		//添加iframe边框宽展
		parent.document.getElementById("fraSet").cols="12,*,"+num+",0%";
		console.log("11111");
	}
	
	function e(num){
		//隐藏主菜单
		document.getElementById("divMenuTree").style.display="block";
		//添加iframe边框宽展
		parent.document.getElementById("fraSet").cols="180,*,"+num+",0%";
	}
	var i=0;
	function fivo(){
		var cols = parent.document.getElementById("fraSet").cols;
		var index = cols.indexOf(",");
		var obj = cols.split(",");
		if(i%2){
			d(obj[2]);
		}else{
			e(obj[2]);
		}
		i++;
	}
	function fivo2(){
		//隐藏主菜单
		document.getElementById("divMenuTree").style.display="none";
		//隐藏菜单弹出层
		document.getElementById("middle-self").style.display="none";
		//添加iframe边框宽展
		parent.document.getElementById("fraSet").cols="12,*,12,0%";
	}
	
	function getMenuStatus() {
		return 	i%2;
	}


