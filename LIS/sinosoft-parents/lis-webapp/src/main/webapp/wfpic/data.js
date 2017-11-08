/*
*@author:lk
*@date:20080904 
*/

/*可能流向数组
   二维数组
   [][0]=业务类型
   [][1]=开始节点
   [][2]=结束节点
   [][3]=流向描述
   [][4]=转移条件
   [][5]=转移类型
*/
var ArrCondition=null;
//初始化
function initCondition()
{  
	 if(Util.isEmpty(ArrCondition))
	 {
      var action="QUERY";
      var actionId="ARRAY";
      var para="select busitype,transitionstart,transitionend,conddesc,transitioncond,transitioncondt from lwcondition order by busitype,transitionstart";
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
function getCondition(start,end)
{ 
	 flowtype=FlowConfig.flowType;
	 if(!Util.isEmpty(ArrCondition))
	 { 
	 	   for(var i=0;i<ArrCondition.length;i++)
	 	   {
	 	   	   if(ArrCondition[i][0]==flowtype)
	 	   	   {
	 	   	   	   if(ArrCondition[i][1]==start&&ArrCondition[i][2]==end)
	 	   	   	   {    var temp = new Array();
	 	   	   	        temp[0] = ArrCondition[i][3];
	 	   	   	        temp[1] = ArrCondition[i][4];
	 	   	   	        temp[2] = ArrCondition[i][5];
	 	   	   	        return temp;
	 	   	   	   }	 	   	   	
	 	   	   }	 	     	
	 	   }
	 }
	 return "";
}
//检查两点之间是否可以连线
function checkConnect(start,end)
{

	 flowtype=FlowConfig.flowType;
	  
	 if(start=="end"||end=="begin")
	 {
	    	return false;
	 } 
	 //开始必须和开始节点相连
	 if(start=="begin")
	 {
	     if(!Util.isEmpty(ArrNode))
	     { 
	     	   for(var j=0;j<ArrNode.length;j++)
	     	   {
	 	   	      if(ArrNode[j][0]==flowtype)
	 	   	      {
	 	   	   	     if(ArrNode[j][1]==end&&ArrNode[j][4]=="1")
	 	   	   	     {
	 	   	   	        return true;
	 	   	   	     }	 	   	   	
	 	   	      }		     	      	
	     	   }
	     	   return false;
	     }
	     else
	     {
	        return false;	
	     }	
	 }
	 //结束必须和结束节点相连
	 if(end=="end")
	 {
	     if(!Util.isEmpty(ArrNode))
	     { 
	     	   for(var k=0;k<ArrNode.length;k++)
	     	   {
	 	   	      if(ArrNode[k][0]==flowtype)
	 	   	      {
	 	   	   	     if(ArrNode[k][1]==start&&ArrNode[k][4]=="3")
	 	   	   	     {
	 	   	   	        return true;
	 	   	   	     }	 	   	   	
	 	   	      }		     	      	
	     	   }
	     	   return false;
	     }
	     else
	     {
	        return false;	
	     }	
	 }	 
	
	 if(!Util.isEmpty(ArrCondition))
	 { 
	 	   for(var i=0;i<ArrCondition.length;i++)
	 	   {
	 	   	   if(ArrCondition[i][0]==flowtype)
	 	   	   {
	 	   	   	   if(ArrCondition[i][1]==start&&ArrCondition[i][2]==end)
	 	   	   	   {
	 	   	   	        return true;
	 	   	   	   }	 	   	   	
	 	   	   }	 	     	
	 	   }
	 }
	 return false;
}
/*业务节点数组
   二维数组
   [][0]=业务类型
   [][1]=节点
   [][2]=节点名称
   [][3]=是否必须节点
   [][4]=是否头尾节点
*/
var ArrNode=null;

//初始化
function initNode()
{ 
	 if(Util.isEmpty(ArrNode))
	 {
      var action="QUERY";
      var actionId="ARRAY";
      var para="select busitype,activityid,activityname,isneed,activityflag from lwactivity where busitype in (select code from ldcode where codetype='busitype') order by busitype,activityid";
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
	    	  	  	    ArrNode=_A;	    
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

/*流程数组
   二维数组
   [][0]=流程号
   [][1]=xml
   [][2]=版本号
*/
var ArrFlowXML=[];

function getFlow(flowId,flowVersion)
{
    
    for(var i=0;i<ArrFlowXML.length;i++)
    {
        if(ArrFlowXML[i][0]==flowId||ArrFlowXML[i][2]==flowVersion)	
        {
        	  return ArrFlowXML[i][1];
        }
    }
    
      var action="QUERY";
      var actionId="BLOB";
      var para="WFXML::LWPROCESSXML:: and processid='"+flowId+"' and version='"+flowVersion+"'";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">正在查询...</span>';
      showTip() ;     
      var AjaxRequestObj = InitRequest();
      
	    if (AjaxRequestObj != null)
	    {  
	    	  AjaxRequestObj.open("POST", url, false);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    	  
	    	  
	    	  eval(AjaxRequestObj.responseText);
	    	  if(_XML!='')
	    	  {   		
	    	      ArrFlowXML[ArrFlowXML.length]=[flowId,_XML];	  	
	    	      Tip.innerHTML='';	  
	    	      hideTip();  
	    	      return _XML;	  
	    	  }
	    	  else
	    	  {
	    	  		 Tip.innerHTML='<span class="Error">查询失败</span>';  
	    	  		 return ""; 
	    	  }  
	    }             
}



//判断图是否可以保存到数据库
//条件必须有开始结束节点，所有必须节点都有
function checkCanSave()
{   
    var allNodes=document.all.chart.childNodes;
    var str="";
    var par="";
    //采集节点
    for(var i=0;i<allNodes.length;i++)
    {
    	  node=allNodes.item(i);
    	  
    	  if(node.flowType=="connect")//联线
    	  {  	 
            //此地方限制再考虑
    	  }
    	  else
       	{
       		  if(node.id!="")   //没有编辑不能保存
       		  {
       		      str=str+":"+node.id+":";
       		  }
       		  else
       		  {
       		  	  alert("有无效节点");
       		  	  return false;
       		  }    
        }	
     }
     //进行判断
     flowtype=FlowConfig.flowType;
     //先判断有没有开始结束
     
     pat="begin";
     if(!isSearch(pat,str))
     {
         alert("没有开始节点");
       	 return false;       	
     }
     pat="end";
     if(!isSearch(pat,str))
     {
         alert("没有结束节点");
       	 return false;       	
     }   
      //判断必须节点
      /*
	   if(!Util.isEmpty(ArrNode))
	   { 
	   	   for(var k=0;k<ArrNode.length;k++)
	   	   {
	 	 	      if(ArrNode[k][0]==flowtype)
	 	 	      {
	 	 	   	     if(ArrNode[k][3]=="Y")
	 	 	   	     {
                    pat=ArrNode[k][1];
                    if(!isSearch(pat,str))
                    {
                        alert("没有"+ArrNode[k][1]+"必需节点");
                      	return false;       	
                    }  
	 	 	   	     }	 	   	   	
	 	 	      }		     	      	
	   	   }
	   }*/
     
     return true;   
}



/*流程后续节点数组
   二维数组
   [][0]=mainMissionId
   [][1][0]=类型
   [][1][1]=节点号
     
*/
var ArrStatusNode=[];

function getStatus(MainMissionId)
{        
 
	     //有可能后续有多个节点
	    //ArrStatusNode=[][];
	  	 if(!Util.isEmpty(ArrStatusNode))
	     { 
          for(var i=0;i<ArrStatusNode.length;i++)
          {
              if(ArrStatusNode[i][0]==MainMissionId)	
              {
              	  return ArrStatusNode[i][1];
              }
          }
    	 }   
      var action="QUERY";
      var actionId="ARRAY";                                      //这个地方还要统一
      var para="select '1',activityid from lwmission where MainMissionId='"+MainMissionId+"' union select '2',activityid from lbmission where MainMissionId='"+MainMissionId+"'";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">正在查询...</span>';
      showTip() ;     
      var AjaxRequestObj = InitRequest();
     
	    if (AjaxRequestObj != null)
	    {  
	    	  AjaxRequestObj.open("POST", url, false);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    	 
	    	  if(AjaxRequestObj.responseText)
	    	  {
	    	     eval(AjaxRequestObj.responseText);
	    	     ArrStatusNode[ArrStatusNode.length]=[MainMissionId,_A];
	    	     Tip.innerHTML='';	  
	    	     hideTip(); 
	    	     return  ArrStatusNode[ArrStatusNode.length-1][1];	 	
	    	  }
          else
          {
              Tip.innerHTML='<span class="Error">查询失败</span>';  
              return null;                             
          }    	      
	    }       
}
/*重构流程数组
   二维数组
   [][0]=流程号
   [][1]=xml
   [][2]=版本号
*/
var ArrRebuildFlow=[];

function getRebuildFlow(flowId,flowVersion)
{    

    for(var i=0;i<ArrRebuildFlow.length;i++)
    {
        if(ArrRebuildFlow[i][0]==flowId)	
        {
        	  return ;
        }
    }
    
      var action="QUERY";
      var actionId="REBUILD";
      //zhaojiawei
      var para=flowId+"::"+flowVersion;
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">正在查询...</span>';
      showTip() ;     
      var AjaxRequestObj = InitRequest();
      
	    if (AjaxRequestObj != null)
	    {  
	    	  AjaxRequestObj.open("POST", url, false);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    	  
	    	  
	    	  eval(AjaxRequestObj.responseText);
	   
	    	  if(_XML!='')
	    	  {   		
	    	      ArrRebuildFlow[ArrRebuildFlow.length]=[flowId,_XML];	  	
	    	      Tip.innerHTML='';	  
	    	      hideTip();  
	    	      //return _XML;	  
	    	  }
	    	  else
	    	  {
	    	  		 Tip.innerHTML='<span class="Error">查询失败</span>';  
	    	  		 //return ""; 
	    	  }  
	    }           	
}


/*流程后续节点数组
   二维数组
   [][0]=mainMissionId
   [][1]=activityId
   [][2][0]=开始时间
   [][2][1]=结束时间
   [][2][3]=执行人
*/
var ArrNodeHis=[];

function getNodeHis(MainMissionId,ActivityId)
{
	     //有可能后续有多个节点
	    //ArrStatusNode=[][];
	  	 if(!Util.isEmpty(ArrNodeHis))
	     { 
          for(var i=0;i<ArrNodeHis.length;i++)
          {
              if(ArrNodeHis[i][0]==MainMissionId&&ArrNodeHis[i][1]==ActivityId)	
              {
              	  return ArrNodeHis[i][2];
              }
          }
    	 }   
      var action="QUERY";
      var actionId="ARRAY";                                      //这个地方还要统
      var para="select indate,intime,outdate,outtime,defaultoperator from lbmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"' union select indate,intime,null,'',defaultoperator from lwmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"'";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">正在查询...</span>';
      showTip() ;     
      var AjaxRequestObj = InitRequest();
     
	    if (AjaxRequestObj != null)
	    {  
	    	  AjaxRequestObj.open("POST", url, false);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    	 
	    	  if(AjaxRequestObj.responseText)
	    	  {
	    	     eval(AjaxRequestObj.responseText);
	    	     ArrNodeHis[ArrNodeHis.length]=[MainMissionId,ActivityId,_A];
	    	     Tip.innerHTML='';	  
	    	     hideTip(); 
	    	     return  ArrNodeHis[ArrNodeHis.length-1][2];	 	
	    	  }
          else
          {
              Tip.innerHTML='<span class="Error">查询失败</span>';  
              return null;                             
          }    	      
	    }       
}

function  getDegreeFlow(flowId,degree,flowVersion)
{    
      var action="QUERY";
      var actionId="DEGREE";
      var para=flowId+"::"+degree+"::"+flowVersion;
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">正在变换...</span>';
      showTip() ;     
      var AjaxRequestObj = InitRequest();
      
	    if (AjaxRequestObj != null)
	    {  
	    	  AjaxRequestObj.open("POST", url, false);
	    	  AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	    	  var data="Action="+action+"&ActionId="+actionId+"&Para="+para;
	    	  AjaxRequestObj.send(data);
	    	  
	    	  
	    	  eval(AjaxRequestObj.responseText);
	    	  if(_XML!='')
	    	  {   		
   	
	    	      Tip.innerHTML='';	  
	    	      hideTip();  
	    	      return _XML;	  
	    	  }
	    	  else
	    	  {
	    	  		 Tip.innerHTML='<span class="Error">变换失败</span>';  
	    	  		 return ""; 
	    	  }  
	    }         	
}