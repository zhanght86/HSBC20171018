//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "f1print.PersonPolPrintCountInputSql";
//�򵥲�ѯ
function easyQueryClick()
{
	
	if( fm.ComCode.value!="86")
	{
		alert("ֻ���ܹ�˾������Ȩ�ޣ��뷵��!");	
		return false;	
		}
	// ��ʼ�����
	initCodeGrid();
	
	if(fm.StartDate.value=="" || fm.EndDate.value=="" )
	{
		alert("������ͳ�ƺ���ʱ��");	
		return false;	
	}
   if(dateDiff(document.all('StartDate').value,document.all('EndDate').value,"D")>=15)
   {
        	if(!confirm("ͳ��ʱ�������ϵͳ���ܻ�������Ƿ����"))
        	{
        		return;
        	}
   } 
	// ��дSQL���
	/*
	var strSQL = "select prtno,grpcontno as a ,managecom,signdate,signtime"
						 + " from lcgrpcont a "
						 + " where appflag = '1' and printcount=0"
						 + getWherePart('managecom', 'ManageCom','like','0') 
						 + getWherePart('signdate','StartDate','>=','0')
						 + getWherePart('signdate','EndDate','<=','0') 
						 +" union "
						 +" select prtno,contno as a ,managecom,signdate,signtime"
						 + " from lccont a "
						 + " where grpcontno = '00000000000000000000' and appflag = '1' and printcount=0 "
						 + getWherePart('managecom', 'ManageCom','like','0') 
						 + getWherePart('signdate','StartDate','>=','0')
						 + getWherePart('signdate','EndDate','<=','0') 
						 + "order by signdate,ManageCom,a ";
*/
var sqlid1="PersonPolPrintCountInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
	
	
	turnPage.queryModal(mySql1.getString(), CodeGrid);
 if(CodeGrid.mulLineCount<=0)
 {
 		alert("û�в�ѯ��¼���뷵��");	
		return false;	
 	}
}

function easyPrint()
{
	if( fm.ComCode.value!="86")
	{
		alert("ֻ���ܹ�˾������Ȩ�ޣ��뷵��!");	
		return false;	
		}
 if(CodeGrid.mulLineCount<=0)
 {
 		alert("û�в�ѯ��¼���뷵��");	
		return false;	
 	}
	easyQueryPrint(2,'CodeGrid','turnPage');
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}
