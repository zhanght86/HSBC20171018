window.onbeforeunload=function() //�رմ����¼�
{
	returnParent();
} 


var mElementsArr = new Array();
var mPropsArr = new Array();

var turnPage = new turnPageClass();
	
		//������Ԫ�ذ��¼�
		function bindElemnts()
		{
		  if(jLimitType=='1')
		  {
		  	$("#northDiv").panel({
				title:"�Ŷ�Լ������Ԥ��"
    		});
    		
    		$("#target").panel({
				title:"�Ŷ�Լ������"
    		});
    		
    		$(window.parent.document).attr('title','�Ŷ�Լ������');
    		
    		$('#previewButton').hide();
		  }	
		$(document).bind('contextmenu',function(e){
				return false;
			});
			var tAccept= "";
			for(i=0;i<mElementsArr.length;i++)
			{
				tAccept = tAccept  + '#'+mElementsArr[i].key;
				if(i!=mElementsArr.length-1)
				{
					tAccept = tAccept + ",";
				}
			}
			
			$('.drag').draggable({
				//proxy:'clone',
				proxy:function(source){
						var n = $('<div class="draggable-model-proxy"></div>');
						n.html($(source).html()).appendTo('body');
						return n;
					},
				deltaX:0,
				deltaY:0,
				revert:true,
				cursor:'auto',
				onStartDrag:function(){
					$(this).draggable('options').cursor='not-allowed';
					//$(this).draggable('proxy').addClass('dp');
				},
				onStopDrag:function(){
					$(this).draggable('options').cursor='auto';
				}
			})
			$('#target').droppable({ helper: 'clone', 
				accept:tAccept,
				onDragEnter:function(e,source){
					$(source).draggable('options').cursor='auto';
					$(source).draggable('proxy').css('border','1px solid red');
					$(this).addClass('over');
				},
				onDragLeave:function(e,source){
					$(source).draggable('options').cursor='not-allowed';
					$(source).draggable('proxy').css('border','1px solid #ccc');
					$(this).removeClass('over');
				
					},
				onDrop:function(e,source){
					var tNewId = $(source).attr('id')+'clone';
					if(typeof($('#'+tNewId).attr('id'))!='undefined'&&$('#'+tNewId).attr('id')==tNewId)
					{
						alert('�Ŷι������Ѿ�����:\''+$('#'+tNewId).html()+'\',�������ظ����!');
						$(this).removeClass('over');
						return false;
					}
					if(jLimitType=='1'&& $(source).attr('id')=='SerialNo')
					{
						alert('��ˮ�Ų��������Ŷ�Լ��!');
						$(this).removeClass('over');
						return false;
					}
					var tElementId = $(source).attr('id');
					//copy 
					var tFindFlag = false;
					var tShowStr = new Array();
					var tPropertiesArr = new Array();
					for(i=0;i<mPropsArr.length;i++)
					{
						if(tNewId==mPropsArr[i].key)
						{
								tFindFlag = true;
								tPropertiesArr = mPropsArr[i].prop;
								break;
						}
					}
			
					if(!tFindFlag)
					{
							//����
							//elementId
							for(i=0;i<mElementsArr.length;i++)
							{
								if($(source).attr('id')==mElementsArr[i].key)
								{
									tFindFlag = true;
								for(n=0;n< mElementsArr[i].prop.length;n++)
							{
									  var tArray = new Array();
									  tArray = mElementsArr[i].prop[n];
									  //"prop":[{"showflag":"1","propvalue":"5","modifyflag":"1","propcode":"Length","propname":"����"}
									  var showflag = tArray.showflag;
									  var propvalue = tArray.propvalue;
									  var modifyflag = tArray.modifyflag;
									  var propcode = tArray.propcode;
									  var propname = tArray.propname;
									  
										tPropertiesArr[tPropertiesArr.length] = {'showflag':showflag,'propvalue':propvalue,'modifyflag':modifyflag,'propcode':propcode,'propname':propname};
							}
									//tPropertiesArr = mElementsArr[i].prop;
									break;
								}
						
					}
				
					if(tFindFlag)
					{
							mPropsArr[mPropsArr.length] = {key:tNewId,prop:tPropertiesArr,elementKey:tElementId};
							for(n=0;n<tPropertiesArr.length;n++)
							{
								if(tPropertiesArr[n].showflag==1)
								{
										tShowStr[tShowStr.length] = {name:tPropertiesArr[n].propname,value:tPropertiesArr[n].propvalue};
								}
							}
					}
				
				}
				
				 addCache(($(source).attr('id')+'clone'),$(source).html());
				 
				for(i=0;i<ruleCache.length;i++)
				{
					if(tNewId==ruleCache[i].id)
					{
						ruleCache[i].show = tShowStr;
					}
				}
					
					//end copy
					//alert($('#'+tNewId).attr('id'));
					
					 showDetail();
					 
					$(this).append($(source).clone().attr('id',$(source).attr('id')+'clone').attr('elementId',tElementId).bind('contextmenu',function(e){
						$('#hiddenElement').attr('value',$(this).attr('id'));
						$('#hiddenElement').attr('elementId',$(this).attr('elementId'));
				$('#NoMenu').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
				return false;
			}).bind('dblclick',function(e){
						$('#hiddenElement').attr('elementId',$(this).attr('elementId'));
						$('#hiddenElement').attr('value',$(this).attr('id'));
						showProperties()
			}));
					$('#'+tNewId).css('background','#AABBCC');
					$(this).removeClass('over');
					//$(this).addClass('finally');
					
			
				}
			});
		
			
			$('#dProperties').dialog({
				/*toolbar:[{
					text:'Ӧ��',
					iconCls:'icon-ok',
					handler:function(){
						proToCache();
					}
				}],*/onBeforeClose:function(){
					if(confirm('�Ƿ�Ӧ�ñ��α��'))
					{
						proToCache();
					}
			}
			});
		//	$('#dProperties').dialog('close');
		}
		
		//ɾ��Ԫ��
		function deleteElement()
		{
			removeCache($('#hiddenElement').attr('value'));
			showDetail();
			$('#'+$('#hiddenElement').attr('value')).remove();
			
		}

		//��ʾ����
		function showProperties()
		{
			//initMaxNoGrid();
			//��ȡ������Ϣ
			//����-  �ȴӻ�����ȡ,���������û��,�ٸ���Ԫ�ص����Թ���,�����ص�������.
			var tKey = $('#hiddenElement').attr('value');
			var tElementId = $('#hiddenElement').attr('elementId');
			var tFindFlag = false;
			var tPropertiesArr = new Array();
			var tShowSet = new Array();
			//alert(tKey);
			for(i=0;i<mPropsArr.length;i++)
			{
				if(tKey==mPropsArr[i].key)
				{
					tFindFlag = true;
					tPropertiesArr = mPropsArr[i].prop;
					break;
				}
			}
			
			if(!tFindFlag)
			{
				//����
				//elementId
				for(i=0;i<mElementsArr.length;i++)
				{
						if(tElementId==mElementsArr[i].key)
						{
							tFindFlag = true;
							//tPropertiesArr = mElementsArr[i].prop;
							
							for(n=0;n< mElementsArr[i].prop.length;n++)
							{
									  var tArray = new Array();
									  tArray = mElementsArr[i].prop[n];
									  //"prop":[{"showflag":"1","propvalue":"5","modifyflag":"1","propcode":"Length","propname":"����"}
									  var showflag = tArray.showflag;
									  var propvalue = tArray.propvalue;
									  var modifyflag = tArray.modifyflag;
									  var propcode = tArray.propcode;
									  var propname = tArray.propname;
									  
										tPropertiesArr[tPropertiesArr.length] = {'showflag':showflag,'propvalue':propvalue,'modifyflag':modifyflag,'propcode':propcode,'propname':propname};
							}
							break;
						}
						
				}
				
				if(tFindFlag)
				{
					mPropsArr[mPropsArr.length] = {key:tKey,prop:tPropertiesArr,elementKey:tElementId};
				}
				
			}
			
			//propcode,propname,propvalue,modifyflag,showflag
			
			//ת����
			var tHtmlProps = "";
			tHtmlProps = tHtmlProps + "<div id=\"proTable\" ><table class=\"common\">";
		
			if(tPropertiesArr.length==0)
			{
				alert('��Ԫ��û�����Կ�������');
				return ;
			}
			for(i=0;i<tPropertiesArr.length;i++)
			{
				var propcode = tPropertiesArr[i].propcode;
				var propname = tPropertiesArr[i].propname;
				var propvalue = tPropertiesArr[i].propvalue;
				var modifyflag = tPropertiesArr[i].modifyflag;
				var showflag = tPropertiesArr[i].showflag;
				
				tHtmlProps = tHtmlProps  + " <tr class=\"common\">"
										+ "<td class=\"title\">"+propname+"</td>"
										+ "<td CLASS=\"input\">";
				if(modifyflag==1)
				{
					//�����޸�
					tHtmlProps = tHtmlProps  +
										"<input id=\""+propcode+"\" class=\"common\" showflag=\""+showflag+"\" value=\""+propvalue+"\"><font color=red>&nbsp;*</font>";
				}
				else
				{
					tHtmlProps = tHtmlProps  +
										"<input id=\""+propcode+"\" class=\"readonly\" readonly showflag=\""+showflag+"\"  value=\""+propvalue+"\">";
				}
									
				tHtmlProps = tHtmlProps  + "</td>"
				           + "</tr>";
				//var tArray = new Array(propcode,propname,propvalue,modifyflag,showflag,tElementId);
				//tShowSet[tShowSet.length]=tArray;
				
				
			}
			tHtmlProps = tHtmlProps  + "</table></div>";
		  $('#proTable').remove();
		  
  		$('#divProps').append(tHtmlProps);
  		//$('#proTable').show();
			$('#dProperties').dialog('open');
		}
		
		function proToCache()
		{
			//var tmPropsArr= mPropsArr;
			var tShowStr = new Array();
			var tID =  $('#hiddenElement').attr('value');
			//alert($("div :input").length);
			$("div :input").each(function(){
					
						var propcode = $(this).attr('id');
						var propvalue = $(this).attr('value');
						var showflag = $(this).attr('showflag');
						for(i=0;i<mPropsArr.length;i++)
						{
							if(tID==mPropsArr[i].key)
							{
								var tempArray;
								for (n=0;n<mPropsArr[i].prop.length;n++)
								{
									if(mPropsArr[i].prop[n].propcode==propcode)
									{
										mPropsArr[i].prop[n].propvalue = propvalue;
										
										if(mPropsArr[i].prop[n].showflag==1)
										{
										tShowStr[tShowStr.length] = {name:mPropsArr[i].prop[n].propname,value:mPropsArr[i].prop[n].propvalue};
										}
									}
								}
								break;
							}
						}
				});
				//������ʾ��Ϣ
				
				for(i=0;i<ruleCache.length;i++)
				{
					if(tID==ruleCache[i].id)
					{
						ruleCache[i].show = tShowStr;
					}
				}
				
				showDetail();
		}
				
		var ruleCache = new Array();
				
	  function showDetail()
	  {
	  	var tShow = "";
	  	for(i=0;i<ruleCache.length;i++)
	  	{
	  		var tempShow = ruleCache[i].value;
	  		var tshowArr = ruleCache[i].show;
	  		if(tshowArr!=null&&typeof(tshowArr)!="undefined")
	  		{
	  			for(n=0;n<tshowArr.length;n++)
	  			{
	  				if(n==0)
	  				{
	  					tempShow = tempShow  + "( ";
	  				}
	  				tempShow = tempShow + tshowArr[n].name+":"+tshowArr[n].value;
	  				if(n!=tshowArr.length-1)
	  				{
	  					tempShow = tempShow + " , ";
	  				}
	  				if(n==tshowArr.length-1)
	  				{
	  					tempShow = tempShow + " )";
	  				}
	  			}
	  		}
	  		tShow = tShow + tempShow;
	  		if(i!=ruleCache.length-1)
	  		{
	  			//tShow = tShow + " + ";
	  			tShow = tShow + " and  ";
	  		}
	  	}
			$("#ShowDetail").html(tShow);  //������

	  }
	  
	 
	  //��ʾ����Ĺ���
	  function addCache(tKey,tValue)
	  {
	  	ruleCache[ruleCache.length] = {id:tKey,value:tValue};
	  }
	  //�ӻ��������
	  function removeCache(tKey)
	  {
	  	var tempArray = new Array();
	  	tempArray = ruleCache;
	  	ruleCache = new Array();
	  	
	  	for(i=0;i<tempArray.length;i++)
	  	{
	  		if(tempArray[i].id==tKey)
	  		{
	  			continue;
	  		}
	  		else
	  		{
	  			ruleCache[ruleCache.length] = tempArray[i];
	  		}
	  	}
	  	
	  	//�Ƴ���������
	  	tempArray = new Array(); 
	  	tempArray = mPropsArr;
	  	mPropsArr = new Array(); 
	  	for(i=0;i<tempArray.length;i++)
	  	{
	  		if(tempArray[i].key==tKey)
	  		{
	  			continue;
	  		}
	  		else
	  		{
	  			mPropsArr[mPropsArr.length] = tempArray[i];
	  		}
	  	}
	  }
	  
	  //��ʼ����Ϣ
	  function initLDMaxNoElement()
		{
	       lockPart("div_layout","�Ŷ�Ԫ�ؼ�����......");
  			 jQuery.post(
  										"./initLDMaxNo.jsp?Type=Element&LimitType="+jLimitType,
  										//params,
  					function(data) {
							var ResulaArray = data;
							try{
								 
									for(i=0;i<ResulaArray.length;i++)
									{
											//code,name,detail
											var tCode = ResulaArray[i].code;
											var tName = ResulaArray[i].name;
											var tDetail = ResulaArray[i].detail;
											
											var tPropS = ResulaArray[i].prop;
											//<div id="d1" class="drag">��ˮ��</div>
											
											var tHtml = "<div id=\""+tCode+"\" class=\"drag\">"+tName+"</div>";
											$('#source').append(tHtml);
											mElementsArr[mElementsArr.length] = {key:tCode,prop:tPropS,detail:tDetail};
									}
									unlockPart("div_layout");
								
									 bindElemnts();
									 initLDMaxNoRule();
									 
									 //unlockPage();
		  				}
		  				catch(ex)
		  				{
		  					//alert(ex);
		  					unlockPart("div_layout");
		  				}
					},"json" 
				);
	}
	
	
	function initLDMaxNoRule()
	{
		     lockPart("div_layout","�Ŷι��������......");
  			 jQuery.post(
  										"./initLDMaxNo.jsp?Type=Rule&NoCode="+jNoCode+"&LimitType="+jLimitType,
  										//params,
  					function(data) {
							var ResulaArray1 = data;
							try{
								 
									for(i=0;i<ResulaArray1.length;i++)
									{
											//code,name,detail
											//nocode,idx,code,rulecode 
											var tShowStr = new Array();
											var nocode = ResulaArray1[i].nocode;
											var idx = ResulaArray1[i].idx;
											var code = ResulaArray1[i].code;
											var rulecode = ResulaArray1[i].rulecode;
											var tName = ResulaArray1[i].name;
											var tPropS = ResulaArray1[i].prop;
											//<div id="d1" class="drag">��ˮ��</div>
											
											var tHtml = "<div id=\""+rulecode+"\" class=\"drag\" elementId=\""+nocode+"\">"+tName+"</div>";
											$('#target').append(tHtml);
											mPropsArr[mPropsArr.length] = {key:rulecode,prop:tPropS,elementKey:code};
											
											for(n=0;n<tPropS.length;n++)
											{
												if(tPropS[n].showflag==1)
												{
														tShowStr[tShowStr.length] = {name:tPropS[n].propname,value:tPropS[n].propvalue};
												}
											}
											
											 addCache(rulecode,tName);
				 
											for(n=0;n<ruleCache.length;n++)
											{
													if(rulecode==ruleCache[n].id)
													{
															ruleCache[n].show = tShowStr;
													}
											}
									}
									 showDetail();
									$('#target').find("div").each(function(){
										$(this).css('background','#AABBCC');
										$(this).bind('contextmenu',function(e){
											$('#hiddenElement').attr('value',$(this).attr('id'));
											$('#hiddenElement').attr('elementId',$(this).attr('elementId'));
											$('#NoMenu').menu('show', {
													left: e.pageX,
													top: e.pageY
												});
														return false;
											}).bind('dblclick',function(e){
												$('#hiddenElement').attr('elementId',$(this).attr('elementId'));
												$('#hiddenElement').attr('value',$(this).attr('id'));
												showProperties()
											});
										});
									unlockPart("div_layout");
		  				}
		  				catch(ex)
		  				{
		  					//alert(ex);
		  					unlockPart("div_layout");
		  				}
					},"json" 
				);
	}
	//�������
	function saveRule()
	{
		//alert($('#target').find('div').length);
		var tFinalArray = new Array();
		//����target,�ҵ����й���Ԫ��
		$('#target').find('div').each(function(){
				var tID = $(this).attr('id');
				//alert(tID);
				//ͨ��ID,�ҵ���Ӧ������
					for(i=0;i<mPropsArr.length;i++)
						{
							if(tID==mPropsArr[i].key)
							{
								tFinalArray[tFinalArray.length] = mPropsArr[i];
								break;
							}
						}
			});
			if(tFinalArray.length==0)
			{
				alert('û�����ù���!');
				return false;
			}
			//�ύ��̨
			lockPart("div_layout","���ݱ�����......");
			//
			var tPost = JSON.stringify(tFinalArray);
			var tShowRule = $('#ShowDetail').html();
			//alert(tShowRule);
			//prompt('',tPost);
			 $.ajax({
            type:'POST',
            url:'ConfigLDMaxNoRuleSave.jsp',
            data:'arr='+tPost+'&NoCode='+jNoCode+'&ShowRule='+tShowRule+'&LimitType='+jLimitType,
            dataType: 'json',

            success:function(json){
            	  unlockPart("div_layout");
            	  //alert(json[0].success);
            	  if(!json[0].success)
            	  {
            	    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + "����ʧ��,ԭ����:"+json[0].msg ;  
    			    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
					var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
					var iWidth=550;      //�������ڵĿ��; 
					var iHeight=350;     //�������ڵĸ߶�; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();
            	  	//alert('����ʧ��,ԭ����:'+json[0].msg);
            	  }
            	  else{
					var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + "����ɹ�" ;  
					//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
					var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
					var iWidth=550;      //�������ڵĿ��; 
					var iHeight=250;     //�������ڵĸ߶�; 
					var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
					var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
					showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

					showInfo.focus();
            	  	}
            },
            error:function(json){
            	  unlockPart("div_layout");
            }
            
        });
        
			//alert(tFinalArray.length);
	}


	function returnParent()
	{
		try{
			if(confirm('��Ҫ����������Ϣô?'))
			{
				saveRule();
			}
			window.dialogArguments.fm.ShowRule.value=$("#ShowDetail").html();
			window.dialogArguments.initForm();
			
		}
		catch(E)
		{
		}
	}
	
	//�Ŷ�Ԥ��
	function previewRule()
	{
		lockPart("div_layout","��ȡ�Ŷ���,���Ժ�......");
  			 jQuery.post(
  										"./initLDMaxNo.jsp?Type=previewRule&NoCode="+jNoCode+"&LimitType="+jLimitType,
  										//params,
  					function(data) {
							var ResulaArray2 = data;
							try{
								 
									for(i=0;i<ResulaArray2.length;i++)
									{
											//type,value
											var tShowStr = new Array();
											var tType = ResulaArray2[i].type;
											var tValue = ResulaArray2[i].value;
											
											//<div id="d1" class="drag">��ˮ��</div>
											
											var tHtml = " <div id='previewDetail' style=\"height:20px\">"+tValue+"</div> "
											          ;
											$('#previewDetail').remove();
											$('#divPreview').append(tHtml);
											$('#dPreview').dialog('open');
									}	
										unlockPart("div_layout");
		  				}
		  				catch(ex)
		  				{
		  					//alert(ex);
		  					unlockPart("div_layout");
		  				}
					},"json" 
				);
	}