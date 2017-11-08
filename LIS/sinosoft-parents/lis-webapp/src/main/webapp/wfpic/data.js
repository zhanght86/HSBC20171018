/*
*@author:lk
*@date:20080904 
*/

/*������������
   ��ά����
   [][0]=ҵ������
   [][1]=��ʼ�ڵ�
   [][2]=�����ڵ�
   [][3]=��������
   [][4]=ת������
   [][5]=ת������
*/
var ArrCondition=null;
//��ʼ��
function initCondition()
{  
	 if(Util.isEmpty(ArrCondition))
	 {
      var action="QUERY";
      var actionId="ARRAY";
      var para="select busitype,transitionstart,transitionend,conddesc,transitioncond,transitioncondt from lwcondition order by busitype,transitionstart";
      var url="./active.jsp";

      Tip.innerHTML='<span class="Loading">���ڽ��г�ʼ��...</span>';
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
                     Tip.innerHTML='<span class="Error">��ʼ��ʧ��</span>';                               
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
//�����������
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
//�������֮���Ƿ��������
function checkConnect(start,end)
{

	 flowtype=FlowConfig.flowType;
	  
	 if(start=="end"||end=="begin")
	 {
	    	return false;
	 } 
	 //��ʼ����Ϳ�ʼ�ڵ�����
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
	 //��������ͽ����ڵ�����
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
/*ҵ��ڵ�����
   ��ά����
   [][0]=ҵ������
   [][1]=�ڵ�
   [][2]=�ڵ�����
   [][3]=�Ƿ����ڵ�
   [][4]=�Ƿ�ͷβ�ڵ�
*/
var ArrNode=null;

//��ʼ��
function initNode()
{ 
	 if(Util.isEmpty(ArrNode))
	 {
      var action="QUERY";
      var actionId="ARRAY";
      var para="select busitype,activityid,activityname,isneed,activityflag from lwactivity where busitype in (select code from ldcode where codetype='busitype') order by busitype,activityid";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">���ڽ��г�ʼ��...</span>';
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
                     Tip.innerHTML='<span class="Error">��ʼ��ʧ��</span>';                               
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

/*��������
   ��ά����
   [][0]=���̺�
   [][1]=xml
   [][2]=�汾��
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
      
      Tip.innerHTML='<span class="Loading">���ڲ�ѯ...</span>';
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
	    	  		 Tip.innerHTML='<span class="Error">��ѯʧ��</span>';  
	    	  		 return ""; 
	    	  }  
	    }             
}



//�ж�ͼ�Ƿ���Ա��浽���ݿ�
//���������п�ʼ�����ڵ㣬���б���ڵ㶼��
function checkCanSave()
{   
    var allNodes=document.all.chart.childNodes;
    var str="";
    var par="";
    //�ɼ��ڵ�
    for(var i=0;i<allNodes.length;i++)
    {
    	  node=allNodes.item(i);
    	  
    	  if(node.flowType=="connect")//����
    	  {  	 
            //�˵ط������ٿ���
    	  }
    	  else
       	{
       		  if(node.id!="")   //û�б༭���ܱ���
       		  {
       		      str=str+":"+node.id+":";
       		  }
       		  else
       		  {
       		  	  alert("����Ч�ڵ�");
       		  	  return false;
       		  }    
        }	
     }
     //�����ж�
     flowtype=FlowConfig.flowType;
     //���ж���û�п�ʼ����
     
     pat="begin";
     if(!isSearch(pat,str))
     {
         alert("û�п�ʼ�ڵ�");
       	 return false;       	
     }
     pat="end";
     if(!isSearch(pat,str))
     {
         alert("û�н����ڵ�");
       	 return false;       	
     }   
      //�жϱ���ڵ�
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
                        alert("û��"+ArrNode[k][1]+"����ڵ�");
                      	return false;       	
                    }  
	 	 	   	     }	 	   	   	
	 	 	      }		     	      	
	   	   }
	   }*/
     
     return true;   
}



/*���̺����ڵ�����
   ��ά����
   [][0]=mainMissionId
   [][1][0]=����
   [][1][1]=�ڵ��
     
*/
var ArrStatusNode=[];

function getStatus(MainMissionId)
{        
 
	     //�п��ܺ����ж���ڵ�
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
      var actionId="ARRAY";                                      //����ط���Ҫͳһ
      var para="select '1',activityid from lwmission where MainMissionId='"+MainMissionId+"' union select '2',activityid from lbmission where MainMissionId='"+MainMissionId+"'";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">���ڲ�ѯ...</span>';
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
              Tip.innerHTML='<span class="Error">��ѯʧ��</span>';  
              return null;                             
          }    	      
	    }       
}
/*�ع���������
   ��ά����
   [][0]=���̺�
   [][1]=xml
   [][2]=�汾��
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
      
      Tip.innerHTML='<span class="Loading">���ڲ�ѯ...</span>';
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
	    	  		 Tip.innerHTML='<span class="Error">��ѯʧ��</span>';  
	    	  		 //return ""; 
	    	  }  
	    }           	
}


/*���̺����ڵ�����
   ��ά����
   [][0]=mainMissionId
   [][1]=activityId
   [][2][0]=��ʼʱ��
   [][2][1]=����ʱ��
   [][2][3]=ִ����
*/
var ArrNodeHis=[];

function getNodeHis(MainMissionId,ActivityId)
{
	     //�п��ܺ����ж���ڵ�
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
      var actionId="ARRAY";                                      //����ط���Ҫͳ
      var para="select indate,intime,outdate,outtime,defaultoperator from lbmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"' union select indate,intime,null,'',defaultoperator from lwmission where MainMissionId='"+MainMissionId+"' and activityid='"+ActivityId+"'";
      var url="./active.jsp";
      
      Tip.innerHTML='<span class="Loading">���ڲ�ѯ...</span>';
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
              Tip.innerHTML='<span class="Error">��ѯʧ��</span>';  
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
      
      Tip.innerHTML='<span class="Loading">���ڱ任...</span>';
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
	    	  		 Tip.innerHTML='<span class="Error">�任ʧ��</span>';  
	    	  		 return ""; 
	    	  }  
	    }         	
}