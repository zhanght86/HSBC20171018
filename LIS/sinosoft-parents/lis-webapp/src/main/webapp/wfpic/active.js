/*
*@author:lk
*@date:20080904 
*/
//��ʼ��
function InitRequest()
{  
	 var C_req = null;
	 try
	 {
	 	  C_req = new ActiveXObject("Microsoft.XMLHTTP");
	 }
	 catch(e)
	 {
	 	  alert("�Բ���!�����������֧�ָù���,��ʹ��Internet Explorer 6.0��FireFox�����!");
	 }
	 return C_req;
}


//�������̺�
function getFlowId()
{
	 
   var action="INSERT";
   var actionId="FlowId";
   var para=TabPage1.contentObject().document.all.flowType.value;
   var url="./active.jsp";
   
   if(para==null||para=="")
   {
      alert("ҵ�����Ͳ���Ϊ��!");
      return;	
   }
   
   TabPage1.contentObject().document.all.ActionTip.innerHTML='<span class="Loading">��������,���Ժ�...</span>';
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
	 	  	  			TabPage1.contentObject().document.all.ActionTip.innerHTML='<span class="Error">���ɴ���</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }   
}
//���浽����
function SaveToDB()
{
	 if(!checkCanSave())
	  {
	     return ;
	  }
	  
	 	getChartXML();
	 	
    if(document.all.FlowXML.value=='') 
    {
	     alert('���ȴ��������̣�');
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
   
   document.all.Tip.innerHTML='<span class="Loading">���ڱ���,���Ժ�...</span>';
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
	 	  	  	        document.all.Tip.innerHTML='<span>����ɹ�</span>';
	 	  	  	        alert('���\'ȷ��\'�رմ���');
	 	  	  	        window.close();       
	 	  	  	        //hideTip(); 
	 	  	  	     }
	 	  	  	     else
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span class="Error">����ʧ��:'+result+'</span>';
	 	  	  	     }	 	 
	 	  	  	 }
	 	  	  	 else
	 	  	  	 {
	 	  	  			document.all.Tip.innerHTML='<span class="Error">����ʧ��!</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }
	 
	
}
//�ӿ���ɾ��
function DelFlow()
{
	 	getChartXML();
    if(document.all.FlowXML.value=='') 
    {
	     alert('���ȴ��������̣�');
	     return;
    }
   var XMLHEAD = '<?xml version="1.0" encoding="GBK"?>';
   var action="DELETE";
   var actionId="DelFlow";
   var para=XMLHEAD+document.all.FlowXML.value+"::"+Operator+"::"+ManageCom;
   var url="./active.jsp";    
   
   document.all.Tip.innerHTML='<span class="Loading">����ɾ��,���Ժ�...</span>';
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
	 	  	  	        document.all.Tip.innerHTML='<span>ɾ���ɹ�</span>';
	 	  	  	        //hideTip(); 
	 	  	  	        ClearFlow();
	 	  	  	        
	 	  	  	        //���data.js ArrPlanFlowXML  ArrFlowXML ���ֵ
	 	  	  	     }
	 	  	  	     else
	 	  	  	     {
	 	  	  	        document.all.Tip.innerHTML='<span class="Error">ɾ��ʧ��:'+result+'</span>';
	 	  	  	     }	 	 
	 	  	  	 }
	 	  	  	 else
	 	  	  	 {
	 	  	  			document.all.Tip.innerHTML='<span class="Error">ɾ��ʧ��!</span>';
	 	  	  	 }
	 	  	  }
	 	  };
	 	  AjaxRequestObj.open("POST", url, true);
	 	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	 	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	 	  AjaxRequestObj.send(data);
	 }       	
}

