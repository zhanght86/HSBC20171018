//***********************************************
//�������ƣ�EdorQuery.js
//�����ܣ���ȫ��ѯ
//�������ڣ�2005-6-10 15:16
//������  ��guomy
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.EdorQueryInputSql";
/*********************************************************************
 *  ��ѯ������ȫ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryEdor(){
  //var aSQL=	"select a.edorno,(select d.appntname from lccont d where d.contno=a.contno ),a.edorappdate,a.edorvalidate,(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(a.uwstate)=trim(b.code)) from LPEdorMain a where a.contno in (select c.contno from lccont c where insuredno='"+customerNo+"')";
  
  	var sqlid1="EdorQueryInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);
		
  
  turnPage.queryModal(mySql1.getString(), EdorGrid);

}
 

function getEdorDetail(){
	//alert();
	
} 

function getEdorItemInfo(){

  var tSelNo = EdorGrid.getSelNo()-1;
  var tedorno= EdorGrid.getRowColData(tSelNo,1);
 
  //var aSQL = "select a.edorno,a.contno,a.edortype,(select distinct b.edorname from lmedoritem b where trim(b.edorcode)= trim(a.edortype)),a.EdorAppDate,a.EdorValiDate from LPEdorItem a where a.edorno='"+tedorno+"'";
//  initPolGrid();
//  alert(aSQL);

var sqlid2="EdorQueryInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tedorno);

  turnPage2.queryModal(mySql2.getString(), EdorItemGrid);
  
}

/********************************************************************* 
 *  ��ѯ�ͻ���Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPersonInfo(){
 // var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
  var sqlid3="EdorQueryInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql3.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}


/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


//Ӱ�����ϲ�ѯ
function showImage(){ 
	
	 var tsel=EdorItemGrid.getSelNo()-1;
	 
	 //alert(ContNo); 
	 if(tsel<0){
     alert("��ѡ�񱣵�!");
     return;	 
   }
   //alert("��������Ӱ������");
   var ContNo=EdorItemGrid.getRowColData(tsel,2); 
   	window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");								
    
}  
function EdorUWQuery()
{
	if(!EdorItemGrid.getSelNo())
	{
		alert("����ѡ��һ��������Ŀ��Ϣ��");
	}
	var tsel=EdorItemGrid.getSelNo()-1;
	var tContNo=EdorItemGrid.getRowColData(tsel,1);
	window.open("./EdorUWQueryMain.jsp?ContNo="+tContNo,"window1",sFeatures);
}