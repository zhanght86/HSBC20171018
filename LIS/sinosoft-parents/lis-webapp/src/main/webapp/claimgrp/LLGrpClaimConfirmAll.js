//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //ֱ��ȡ��������ת����������
        var i = LLClaimSimpleGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimSimpleGrid.getRowColData(i,1);
            var tClmState = LLClaimSimpleGrid.getRowColData(i,2);
            var tMissionID = LLClaimSimpleGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,8);
            location.href="LLClaimSimpleInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
    var backstr=fm.all("ContNo").value;
    mSwitch.deleteVar("ContNo");
    mSwitch.addVar("ContNo", "", backstr);
    mSwitch.updateVar("ContNo", "", backstr);
    location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("������")
    return;
    var selno = SelfPolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��һ������");
          return;
    }
  
    var MissionID = SelfPolGrid.getRowColData(selno, 6);
    var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
    var ActivityID = SelfPolGrid.getRowColData(selno, 8);
    var PrtNo = SelfPolGrid.getRowColData(selno, 2);
    if(PrtNo == null || PrtNo == "")
    {
      alert("ӡˢ��Ϊ�գ�");
      return;
    }
    var NoType = "1";
  
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}


// ��ʼ�����2
function querySelfGrid()
{
    initSelfLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom    = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator     = fm.Operator.value;
    
  /*  strSQL = "select RgtNo,decode(trim(ClmState),'60','�᰸','������'),"
           +" AppntNo,GrpName,Peoples2,RgtDate,Operator,MngCom "
           +" from LLRegister a where appntno is not null  and "
           +" rgtstate in('03') and ClmState not in('20','25') and ClmState != '60'"
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'AppntNo' ,'CustomerNo')*/
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimConfirmAllInputSql");
			mySql.setSqlId("LLGrpClaimConfirmAllSql1");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               //strSQL = strSQL + " and GrpName like '%%" +tCustomerName + "%%'"
                mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimConfirmAllInputSql");
			mySql.setSqlId("LLGrpClaimConfirmAllSql2");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(tCustomerName); 
           }
           if(tManageCom != '%')
           {
               //strSQL = strSQL + " and MngCom like '"+tManageCom+"%%'";
                mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimConfirmAllInputSql");
			mySql.setSqlId("LLGrpClaimConfirmAllSql3");
			mySql.addSubPara(fm.RptNo.value ); 
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(tCustomerName); 
			mySql.addSubPara(tManageCom); 
           }
    //strSQL = strSQL + " order by RgtNo,RgtDate";

    turnPage2.queryModal(mySql.getString(),SelfLLClaimSimpleGrid);
}

//LLClaimSimpleGrid����¼�
function LLClaimSimpleGridClick()
{
}

//SelfLLClaimSimpleGrid����¼�
function SelfLLClaimSimpleGridClick()
{
    var i = SelfLLClaimSimpleGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tRptNo = SelfLLClaimSimpleGrid.getRowColData(i,1);

        if(fm.Operator.value == SelfLLClaimSimpleGrid.getRowColData(i,7)){
           alert("�����͸��˲���ͬһ������Ա����!");
           return false;
        }

        location.href="LLGrpSimpleAllInput.jsp?RptNo="+tRptNo+"&Flag=2";
    }

}

//[����]��ť
function ApplyClaim()
{
location.href="LLGrpSimpleInput.jsp?'";
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.submit(); //�ύ
    tSaveFlag ="0";
}