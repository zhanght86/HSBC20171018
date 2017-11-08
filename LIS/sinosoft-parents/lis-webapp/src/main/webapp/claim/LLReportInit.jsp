<%
//**************************************************************************************************
//Name: LLReportInit.jsp
//function�����������ʼ��
//author: zl
//Date: 2005-6-10 13:51
//**************************************************************************************************
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";


//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql54");
		mySql.addSubPara("1");
	var sysdatearr=easyExecSql(mySql.getString());
	mCurrentDate=sysdatearr[0][0];
      
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");



    
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

//
function initInpBox()
{
    try
    {
        fm.AccidentDate.disabled=false;
        fm.occurReason.disabled=false;
        fm.accidentDetail.disabled=false;
        fm.claimType.disabled=false;
        fm.Remark.disabled=false;

        fm.addbutton.disabled=true;
        fm.DoHangUp.disabled=true;
        fm.CreateNote.disabled=true;
        fm.BeginInq.disabled=true;
        fm.ViewInvest.disabled=true;
        //fm.Condole.disabled=true;
        //fm.SubmitReport.disabled=false;
        //fm.ViewReport.disabled=true;
        fm.AccidentDesc.disabled=true;
        fm.QueryCont2.disabled=true;
        fm.QueryCont3.disabled=true;
        fm.RptConfirm.disabled=true;
        
        
    }
    catch(ex)
    {
        alert("��LLReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}
function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("��LLReportInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSelBox();
        initSubReportGrid();
        
        //*******************************************************************
        //�����ж�(��������������)
        //������0
        //�������У�1
        //*******************************************************************
        if(fm.isNew.value == '0')
        {
            //��ʾ������ť��
            showPage(this,operateButton2);

            document.all('MedicalAccidentDate').disabled=true;
            document.all('OtherAccidentDate').disabled=true;
        }
        else if(fm.isNew.value == '1')
        {
            //��ʾ�޸İ�ť��
            showPage(this,operateButton3);
            initQuery();
            //���ÿɲ�����ť
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
            fm.RptConfirm.disabled = false;
        }
        else if(fm.isNew.value == '3')
        {
        	  initQuery();
              //���ÿɲ�����ť
              fm.QueryPerson.disabled = true;
              fm.QueryReport.disabled = true;
              fm.RptConfirm.disabled = true;
              fm.MedCertForPrt.disabled = true;
              fm.BarCodePrint.disabled = true;
              fm.ColQueryImage.disabled = true;
              fm.DoHangUp.disabled = true;
              fm.CreateNote.disabled = true;
              fm.PrintCertify.disabled = true;
              fm.BeginInq.disabled = true;
              fm.SubmitReport.disabled = true;
              fm.ViewReport.disabled = true;

              fm.QueryCont2.disabled = false;
              fm.QueryCont3.disabled = false;
              fm.ViewInvest.disabled = false;
              fm.AccidentDesc.disabled = false;
        }

    }
    catch(re)
    {
        alert("��LLReportInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}
function initSubReportGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�ͻ�����";
        iArray[1][1]="230px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����";
        iArray[2][1]="150px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="150px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                

        iArray[6]=new Array()
        iArray[6][0]="�籣��־����";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array()
        iArray[7][0]="�籣��־";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  


        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
        SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //��Ӧ�ĺ���������������
        SubReportGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���
        SubReportGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        SubReportGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
