/*
*@author:lk
*@date:20080904 
*/

/*可能流向数组
   二维数组
   [][0]=开始节点
   [][1]=结束节点
   [][2]=转移条件
   [][3]=转移类型
   [][4]=过程ID
   [][5]=过程版本
   
*/
var ArrConditions=null;
//初始化
function initPrpcessTrans()
{   if(Util.isEmpty(ArrConditions))
	 {
      var action="QUERY";
      var actionId="ARRAY";
      var para="select transitionstart,transitionend,(case when transitioncond is not null then transitioncond else '' end),(case when  transitioncondt is not null then transitioncondt else '' end),Processid,Version,( case when conddesc is not null then conddesc else '' end) from lwcondition ";
      var url="./active.jsp";
      Tip.innerHTML='<span class="Loading">正在进行初始化...</span>';
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
	    	  	  	    ArrCondition=_A;	 
	    	  	  	    Tip.innerHTML='';	  
	    	  	  	    hideTip();  	 	
	    	  	  	 }
                 else
                 {
                     Tip.innerHTML='<span class="Error">初始化失败</span>';                               
                 }
	    	  	  }
	    	  };
	    	  AjaxRequestObj.open("POST", url, true);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    }    
	 }     	
}
//获得流向条件
function getProcessTrans(processid,start,end,version)
{ 
	 flowtype=FlowConfig.flowType;
	 var temp = new Array();
	 temp[0]='';
	 temp[1]='';
	 temp[2]='';
	 //alert(processid+":"+start+":"+end+":"+version);
	 if(!Util.isEmpty(ArrCondition))
	 {     
	 	   for(var i=0;i<ArrCondition.length;i++)
	 	   { 
	 	   	   if(ArrCondition[i][4]==processid&&ArrCondition[i][5]==version)
	 	   	   {
                 
	 	   	   	   if(ArrCondition[i][0]==start&&ArrCondition[i][1]==end)
	 	   	   	   {  
	 	   	   	   		
	 	   	   	       // var temp = new Array();
	 	   	   	        temp[0] = ArrCondition[i][2];
	 	   	   	        temp[1] = ArrCondition[i][3];
	 	   	   	        temp[2] = ArrCondition[i][6];
	 	   	   	       // alert('find:'+temp[0]+':'+temp[1]);
	 	   	   	        return temp;
	 	   	   	    }		   	   	
	 	   	   }	 	     	
	 	   }
	 }
	 return temp;
}

function refreshTrans(processid,start,end,version,transarr)
{
	 if(!Util.isEmpty(ArrCondition))
	 {     
	 	   for(var i=0;i<ArrCondition.length;i++)
	 	   { 
	 	   	   if(ArrCondition[i][4]==processid&&ArrCondition[i][5]==version)
	 	   	   {
                 
	 	   	   	   if(ArrCondition[i][0]==start&&ArrCondition[i][1]==end)
	 	   	   	   {  
	 	   	   	   	//alert('refresh');
	 	   	   	   		
	 	   	   	       // var temp = new Array();
	 	   	   	       ArrCondition[i][2] = transarr[0];
	 	   	   	       ArrCondition[i][3] = transarr[1];
	 	   	   	       ArrCondition[i][6] = transarr[2];
	 	   	   	    }		   	   	
	 	   	   }	 	     	
	 	   }
	 }
}