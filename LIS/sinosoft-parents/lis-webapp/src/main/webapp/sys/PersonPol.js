//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var tDisplay;
var turnPage = new turnPageClass();
var turnPageP = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


// ������ѯ
function initQueryGo()
{
//var  strsql="select ContNo,Prtno,AppntName,GrpContNo,ProposalContNo,InsuredNo,InsuredName from LCCont where appntno='"+customerNo+"'";
  var   mySql1=new SqlClass();
		mySql1.setResourceName("sys.PersonPolSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("PersonPolSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(customerNo);//ָ������Ĳ���
   var  strsql= mySql1.getString();
  
   try{
   turnPageP.queryModal(strsql, PolGrid);
   }
   catch(ex){
   alert(ex);
   }
}


// �����ת������ϸҳ��
function clickQuery()
{
var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        alert( "����ѡ��һ����¼���ٵ����ϸ��ť��" );
    else
    {
        var ContNo = PolGrid.getRowColData(tSel - 1,1);
        if (ContNo == "")
            return;
            window.open("PolDetailQueryMain.jsp?ContNo="+ContNo+"&IsCancelPolFlag=0");
    }
}