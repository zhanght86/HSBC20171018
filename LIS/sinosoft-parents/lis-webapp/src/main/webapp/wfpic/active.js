/*
*@author:lk
*@date:20080904 
*/
//初始化
function InitRequest()
{  
	 var C_req = null;
	 try
	 {
	 	  C_req = new ActiveXObject("Microsoft.XMLHTTP");
	 }
	 catch(e)
	 {
	 	  alert("对不起!您的浏览器不支持该功能,请使用Internet Explorer 6.0或FireFox浏览器!");
	 }
	 return C_req;
}


//生成流程号
function getFlowId()
{
	 
   var action="INSERT";
   var actionId="FlowId";
   var para=TabPage1.contentObject().document.all.flowType.value;
   var url="./active.jsp";
   
   if(para==null||para=="")
   {
      alert("业务类型不能为空!");
      return;	
   }
   
   TabPage1.contentObject().document.all.ActionTip.innerHTML='<span class="Loading">正在生成,请稍候...</span>';
   var AjaxRequestObj = InitRequest();
  
	 if (AjaxRequestObj != null)
	 {  
	 	  AjaxRequestObj.onreadystatechange = function ()
	 	  {
	 	  	  if (AjaxRequestObj.readyState == 4)
	 	  	  {  
	 	  	  	 if(AjaxRequestObj.responseText) 
	 	  	  	 {
	 	  	  	     eval(AjaxRequestObj.responseText);	 	  	
	 	  	  	     TabPage1.contentObject().document.all.flowId.value=flowId; 	 
	 	  	  	     TabPage1.contentObject().document.all.ActionTip.innerHTML='';
	 	  	  	     //tongmeng 2012-08-24 add
	 	  	  	     TabPage1.contentObject().document.all.flowVersion.value=versionId; 	 
	 	  	  	 }
	 	  	  	 else
	 	  	  	 {
	 	  	  			TabPage1.contentObject().document.all.ActionTip.innerHTML='<span class="Error">生成错误</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }   
}
//保存到库里
function SaveToDB()
{
	 if(!checkCanSave())
	  {
	     return ;
	  }
	  
	 	getChartXML();
	 	
    if(document.all.FlowXML.value=='') 
    {
	     alert('请先创建新流程！');
	     return;
    }
   var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
   var action="INSERT";
   if(isRebuild)
   {
      var actionId="RebuildFlow";
   }
   else
   {
   	  var actionId="SaveFlow";
   }  
   var para=XMLHEAD+document.all.FlowXML.value+"::"+Operator+"::"+ManageCom;
   var url="./active.jsp";    
   
   document.all.Tip.innerHTML='<span class="Loading">正在保存,请稍候...</span>';
   showTip() ;     
   var AjaxRequestObj = InitRequest();
  
	 if (AjaxRequestObj != null)
	 {  
	 	  AjaxRequestObj.onreadystatechange = function ()
	 	  {
	 	  	  if (AjaxRequestObj.readyState == 4)
	 	  	  {  
	 	  	  	 if(AjaxRequestObj.responseText) 
	 	  	  	 {
	 	  	  	     eval(AjaxRequestObj.responseText);	 	  	
	 	  	  	    
	 	  	  	     if(result=="success")
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span>保存成功</span>';
	 	  	  	        alert('点击\'确定\'关闭窗口');
	 	  	  	        window.close();       
	 	  	  	        //hideTip(); 
	 	  	  	     }
	 	  	  	     else
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span class="Error">保存失败:'+result+'</span>';
	 	  	  	     }	 	 
	 	  	  	 }
	 	  	  	 else
	 	  	  	 {
	 	  	  			document.all.Tip.innerHTML='<span class="Error">保存失败!</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }
	 
	
}
//从库里删除
function DelFlow()
{
	 	getChartXML();
    if(document.all.FlowXML.value=='') 
    {
	     alert('请先创建新流程！');
	     return;
    }
   var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
   var action="DELETE";
   var actionId="DelFlow";
   var para=XMLHEAD+document.all.FlowXML.value+"::"+Operator+"::"+ManageCom;
   var url="./active.jsp";    
   
   document.all.Tip.innerHTML='<span class="Loading">正在删除,请稍候...</span>';
   showTip() ;     
   var AjaxRequestObj = InitRequest();
  
	 if (AjaxRequestObj != null)
	 {  
	 	  AjaxRequestObj.onreadystatechange = function ()
	 	  {
	 	  	  if (AjaxRequestObj.readyState == 4)
	 	  	  {  
	 	  	  	 if(AjaxRequestObj.responseText) 
	 	  	  	 {
	 	  	  	     eval(AjaxRequestObj.responseText);	 	  	
	 	  	  	    
	 	  	  	     if(result=="success")
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span>删除成功</span>';
	 	  	  	        //hideTip(); 
	 	  	  	        ClearFlow();
	 	  	  	        
	 	  	  	        //清空data.js ArrPlanFlowXML  ArrFlowXML 相关值
	 	  	  	     }
	 	  	  	     else
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span class="Error">删除失败:'+result+'</span>';
	 	  	  	     }	 	 
	 	  	  	 }
	 	  	  	 else
	 	  	  	 {
	 	  	  			document.all.Tip.innerHTML='<span class="Error">删除失败!</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }       	
}

