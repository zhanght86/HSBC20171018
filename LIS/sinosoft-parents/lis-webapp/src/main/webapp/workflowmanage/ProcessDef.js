//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
/*******************************************************************************
 * <p>Title       : �������</p>
 * <p>Description : ������ϵͳ���Ѿ������������������Ա��</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        : 
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���


//******************************************************************

function ReleaseClick(){
   var row = WorkFlowGrid.getSelNo()	//���ѡ�е�Radio�к�  
    var rows = row-1;
    var Date = new Array; //����һ����     
    Date = WorkFlowGrid.getRowData(rows)  //ȡ��ָ����ĳһ�е�ֵ
    var priorityid = Date[0];
    var busitype1= Date[2];
    var version = Date[6]
    var busitype = encodeURI(encodeURI(busitype1));
    var tempdate ="&OperFlag="+"RE||Condition"+"&ProcessID="+priorityid+"&Busitype="+busitype+"&Version="+version;
    var urli = "ProcessRe.jsp?OperFlag=RE||Condation";
    var AjaxRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
    AjaxRequestObj.open("POST", urli, true);
    AjaxRequestObj.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    AjaxRequestObj.send(tempdate); 
    AjaxRequestObj.onreadystatechange = function(){
     if ( AjaxRequestObj.readyState == 4 &&  AjaxRequestObj.status == 200){
                 var result =  AjaxRequestObj.responseText;
                 var showStr = result.substr(1,5);
		  query();                
                 if(showStr==("�����ɹ�!")){
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
               }else{
                 var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + " ����ʧ��! " ;
                 //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");          
				 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
             }
          }
      }


}

function query()
{
	var arr=new Array();
	var strSQL = "";
    mySql = new SqlClass();
    mySql.setResourceName("workflow.ProcessDefSql");
    mySql.setSqlId("ProcessDefSql1");
    mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value); 		
    initWorkFlowGrid();
	turnPage.queryModal(mySql.getString(), WorkFlowGrid);
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if(typeof(showInfo)=="object"){showInfo.close();if(typeof(showInfo.parent)=="object" && typeof(showInfo.parent) != "unknown"){showInfo.parent.focus();if(typeof(showInfo.parent.parent)=="object" && typeof(showInfo.parent.parent) != "unknown"){showInfo.parent.parent.blur();}}}

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    query();
  }
}
var dialogURL="";
function newClick()
{
//	  dialogURL="../wfpic/workflow.jsp?action=new&flowId=''" ;  
//	  showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	  dialogURL="../workflow/workflow.jsp?action=new&flowId=''" ; 
	  window.open(dialogURL);
}
function updateClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�޸ĵ����̣�");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);  
  var version =WorkFlowGrid.getRowColData(selno,7);

//  dialogURL="../wfpic/workflow.jsp?action=update&flowId="+processId+"&Version="+version;  
//	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	dialogURL="../workflow/workflow.jsp?action=update&flowId="+processId+"&Version="+version;  
	  window.open(dialogURL);
}
function copyClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���Ƶ����̣�");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);  
  var version =WorkFlowGrid.getRowColData(selno,7);
  //dialogURL="../wfpic/workflow.jsp?action=copy&flowId="+processId+"&Version="+version;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	dialogURL="../workflow/workflow.jsp?action=copy&flowId="+processId+"&Version="+version;
	window.open(dialogURL);

}
function deleteClick()
{
 	   var selno = WorkFlowGrid.getSelNo()-1;
	   if (selno <0)
	   {
	      alert("��ѡ��Ҫɾ�������̣�");
	      return;
	   }	
     var processId =WorkFlowGrid.getRowColData(selno,1);  
     var version =WorkFlowGrid.getRowColData(selno,7); 
     var showStr="����ɾ�����̶�����Ϣ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	 var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.action="./ProcessSave.jsp?hideOperate=DELETE||MAIN&ProcessID="+processId+"&Version="+version; 
     document.getElementById("fm").submit();
}


function queryClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�鿴�����̣�");
	      return;
	}	
	//zhaojiawei
  var processId =WorkFlowGrid.getRowColData(selno,1); 
  var version =WorkFlowGrid.getRowColData(selno,7);   

  //dialogURL="../wfpic/workflow.jsp?action=query&flowId="+processId+"&Version="+version;
  dialogURL="../workflow/workflow.jsp?action=query&flowId="+processId+"&Version="+version;
  window.open(dialogURL);
  //showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
}

function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("����ѡ��һ��ҵ������")
		return false;
	}
}