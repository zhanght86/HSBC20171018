

//程序名称：PD_LMDutyGetClmCal.js
//程序功能：责任给付赔付扩充计算公式
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}

function arrayToJson(o) { 
var r = []; 
if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\""; 
if (typeof o == "object") { 
if (!o.sort) { 
for (var i in o) 
r.push(i + ":" + arrayToJson(o[i])); 
if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) { 
r.push("toString:" + o.toString.toString()); 
} 
r = "{" + r.join() + "}"; 
} else { 
for (var i = 0; i < o.length; i++) { 
r.push(arrayToJson(o[i])); 
} 
r = "[" + r.join() + "]"; 
} 
return r; 
} 
return o.toString(); 
}


function save()
{
	//获取所有选择的列
	var tSelected = $('#riskGrid').datagrid('getSelections');
	
	
	//alert(tSelected);
 var finalData = new Array();
  var tRiskParamsValue = "";
  //alert('1');
 // tRiskParamsValue = arrayToJson(o);
  //alert('2');
	for( i=0;i<tSelected.length;i++)
	{
		$('#riskGrid').datagrid('endEdit', i);
		//tRiskParamsValue =  tRiskParamsValue + tSelected[i].riskParamCode + "#";
		finalData[i] = new Array();
		finalData[i][0] = tSelected[i].riskParamCode;
		//finalData[i][1] = tSelected[i].riskParamName;
		
		var tSQL = tSelected[i].fileterSql;
		var reg=new RegExp("\,","g"); //

		tSQL = tSQL.replace(reg,'@');
		
		if(tSQL==null||tSQL=='')
		{
			tSQL = "null";
		}
		//alert(tSQL);
		finalData[i][1] = tSQL;
	}
	
	
	
	var dutyChoose = $('#DutyChoose').attr("checked");
	var payChoose = $('#PayChoose').attr("checked");
	
	//alert('dutyChoose:'+dutyChoose);
	//责任列表
	var DutySelData = new Array();
	var dutyCount = 0;
	if(dutyChoose)
	{
		//alert('1');
		$("[name='checkBoxDuty']:checkbox").each(function(){
			//alert('2');
				DutySelData[dutyCount] = new Array();
				DutySelData[dutyCount][0] = $(this).attr("id");
  	 if($(this).attr("checked")==true){
   			DutySelData[dutyCount][1] = '1';
   		}
   		else
   		{
				DutySelData[dutyCount][1] = '0';
   		}
   		dutyCount ++;
		})

	}
	
	//alert('DutySelData:'+DutySelData.length);
	
	//缴费列表
	var PaySelData = new Array();
	var payCount = 0;
	//alert('payChoose:'+payChoose);
	if(payChoose)
	{
		//alert('1');
		$("[name='checkBoxPay']:checkbox").each(function(){
			//alert('2');
			PaySelData[payCount] = new Array();
			PaySelData[payCount][0] = $(this).attr("id");
  	 	if($(this).attr("checked")==true){
   			PaySelData[payCount][1] = '1';
   		}
   		else
   		{
   			PaySelData[payCount][1] = '0';
   		}
   		payCount ++;
		 })

	}
	//alert('PaySelData:'+PaySelData.length);
	
	//alert(dutyChoose);
	//return ;
	$('#RiskParams').attr('value',tRiskParamsValue);
		lockPage(""+"数据保存中..........."+"");
	   $.ajax({
            type:'POST',
            url:'PD_RiskFaceDefSave.jsp',
            data:'arr='+finalData+'&RiskCode='+jRiskCode+'&DutyChoose='+dutyChoose+'&PayChoose='+payChoose+'&DutySel='+DutySelData+'&PaySel='+PaySelData+'&StandbyFlag1='+jStandbyFlag1+"&contopt=" +contOpt,
            success:function(data){
            	unLockPage();
            
            	 $('#TeacherDiv').tabs('select', ""+"险种界面定义"+"");
            	 refresh_tab();
       				 
                if(data){
                   // $.messager.alert('warning',data,'warning',function(){
                    //    $('#tt').datagrid('beginEdit', index);
                   // }
                  //  )
                }else{
                    $('#riskGrid').datagrid('reload');
                }
            }
        });
	
		
}

function refresh_tab()
{
	var refresh_tab = $('#TeacherDiv').tabs('getSelected');
								if(refresh_tab && refresh_tab.find('iframe').length > 0){
										var _refresh_ifram = refresh_tab.find('iframe')[0];
										var refresh_url = 'showRiskFace.jsp?RiskCode='+jRiskCode+'&StandbyFlag1='+jStandbyFlag1+ '&contopt=' + contOpt;
										var refresh_url2 = 'PDSugInsureControlEleMain.jsp?RiskCode='+jRiskCode+'&StandbyFlag1='+jStandbyFlag1+ '&contopt=' + contOpt;
										if(refresh_tab[0].id=="ControlElement"){
											_refresh_ifram.contentWindow.location.href=refresh_url2;
										}else if(refresh_tab[0].id=="TaskPlanMonitoring"){
											_refresh_ifram.contentWindow.location.href=refresh_url;	
										}
										
									}
}

function checkDuty()
{
	//alert($('#DutyChoose').attr("checked"));
	if($('#DutyChoose').attr("checked")==true)
	{
		$('#DutyColSelDiv').show();
	}
	else
	{
		$('#DutyColSelDiv').hide();
	}
}

function checkPay()
{
	//alert($('#DutyChoose').attr("checked"));
	if($('#PayChoose').attr("checked")==true)
	{
		$('#PayColSelDiv').show();
	}
	else
	{
		$('#PayColSelDiv').hide();
	}
}
