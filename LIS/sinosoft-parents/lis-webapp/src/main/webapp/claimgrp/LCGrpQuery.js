//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage=new turnPageClass();
var mySql = new SqlClass();

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
    alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���  
}           



function getQueryResult()
{
  var arrSelected = null;
  tRow = GrpContGrid.getSelNo();

  if( tRow == 0 || tRow == null )
      return arrSelected;
  
  arrSelected = new Array();
  
  //������Ҫ���ص�����
  //edit by guo xiang at 2004-9-13 17:54
  arrSelected[0] = new Array();
  arrSelected[0] = GrpContGrid.getRowData(tRow-1);
  //arrSelected[0] = arrDataSet[tRow-1];
  
  return arrSelected;
}

function initQuery()
{

  /*var strSQL=" select  a.customerno, a.name, g.grpcontno,g.Peoples2 " +
        " from lcgrpcont g, LCGrpAppnt a " +
        " where a.grpcontno = g.grpcontno and g.appflag = '1' "
        + getWherePart("g.AppntNo", "CustomerNo" )
//        + getWherePart("a.Name", "GrpName" )
        + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LCGrpQuerySql");
		mySql.setSqlId("LCGrpQuerySql1");
		mySql.addSubPara(fm.CustomerNo.value );  
		mySql.addSubPara(fm.GrpContNo.value );  
      if(fm.all('GrpName').value!=''){
      //   strSQL += " and a.Name like '%%"+fm.all('GrpName').value+"%%'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LCGrpQuerySql");
		mySql.setSqlId("LCGrpQuerySql2");
		mySql.addSubPara(fm.CustomerNo.value );  
		mySql.addSubPara(fm.GrpContNo.value );
		mySql.addSubPara(fm.all('GrpName').value);  
         
      }
        
  turnPage.queryModal(mySql.getString(), GrpContGrid);
}

// ���ݷ��ظ�����
function returnParent()
{
  var arrReturn = new Array();
  var tSel = GrpContGrid.getSelNo();
  
  if( tSel == 0 || tSel == null )
    alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
  else
  {
    try
    {
      arrReturn = getQueryResult();
      top.opener.afterLCGrpQuery( arrReturn );
    }
    catch(ex)
    {
      alert( "û�з��ָ����ڵ�afterLCGrpQuery�ӿڡ�" + ex.message );
    }
    top.close();
  }
}