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


function query()
{
	var arr=new Array();
	var strSQL = "";
 		
  mySql = new SqlClass();
  mySql.setResourceName("workflow.RebuildDefSql");
  mySql.setSqlId("RebuildDefSql1");
  mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value);   	  
  mySql.addSubPara(fm.ProcessID.value);   	
	mySql.addSubPara(fm.BusiType.value);   
  
  initWorkFlowGrid();
	turnPage.queryModal(mySql.getString(), WorkFlowGrid);
}


var dialogURL="";

function rebuildClick()
{
 	var selno = WorkFlowGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�ع������̣�");
	      return;
	}	
  var processId =WorkFlowGrid.getRowColData(selno,1);
  //jiyongtian 2012-7-12
  var version =WorkFlowGrid.getRowColData(selno,7);

//  dialogURL="../wfpic/workflow.jsp?action=rebuild&flowId="+processId+"&Version="+version;  
//	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:700px");
	 dialogURL="../workflow/workflow.jsp?action=rebuild&flowId="+processId+"&Version="+version;  
	 window.open(dialogURL);
}

function checkBusiType()
{
	if (document.all('BusiType').value==""||document.all('BusiType').value==null)
	{
		alert("����ѡ��һ��ҵ������")
		return false;
	}
}