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
var mNowMonth = "";
var mNowDay = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");  
    mNowYear = mCurrentDate.substring(0,4);
    mNowMonth = mCurrentDate.substring(5,7);
    mNowDay = mCurrentDate.substring(8,10);
      
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    fm.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    fm.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    fm.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
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

//    fm.AddReport.disabled=true;
        fm.addbutton.disabled=true;
//    fm.updatebutton.disabled=true;
        fm.DoHangUp.disabled=true;
        fm.CreateNote.disabled=true;
        fm.BeginInq.disabled=true;
        fm.ViewInvest.disabled=true;
        fm.Condole.disabled=true;
        fm.SubmitReport.disabled=true;
        fm.ViewReport.disabled=true;
        fm.AccidentDesc.disabled=true;
//        fm.QueryCont1.disabled=true;
        fm.QueryCont2.disabled=true;
        fm.QueryCont3.disabled=true;
        fm.RptConfirm.disabled=true;
        
        fm.MedCertForPrt.disabled = true;
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
        //***************************************************************************Beg
        //�����Ƿ�ɲ�ѯ�����˱�������Ӧ�޸�
        //***************************************************************************
        //        if(fm.isNew.value == 'false') //�Ǳ���
        //        {
        //            initQuery();
        //            //���ò�����
        //            readonlyFormElements();
        //            fm.goBack.disabled = false;
        //            fm.AccidentDesc.disabled = false;
        //        }
        //***************************************************************************End
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
        iArray[1][0]="�ͻ�����"; //ԭ�¹��߿ͻ���
        iArray[1][1]="150px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա�";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

//        iArray[3]=new Array();
//        iArray[3][0]="ҽԺ����";
//        iArray[3][1]="0px";
//        iArray[3][2]=60;
//        iArray[3][3]=3;
//        iArray[3][4]="HospitalCode";
//        iArray[3][5]="3|4";                                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
//        iArray[3][6]="0|1";                        //��������з������ô����еڼ�λֵ
//        iArray[3][9]="ҽԺ����|NOTNULL";

//    iArray[4]=new Array()
//    iArray[4][0]="ҽԺ����";
//    iArray[4][1]="0px";
//    iArray[4][2]=100;
//    iArray[4][3]=3;

//    iArray[5]=new Array()
//    iArray[5][0]="��Ժ����";
//    iArray[5][1]="0px";
//    iArray[5][2]=100;
//    iArray[5][3]=3;

        iArray[5]=new Array()
        iArray[5][0]="VIP��־";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;

//        iArray[6]=new Array();
//        iArray[6][0]="����ϸ��";
//        iArray[6][1]="0px";
//        iArray[6][2]=100;
//        iArray[6][3]=3;
//
//        iArray[7]=new Array();
//        iArray[7][0]="����ҽԺ";
//        iArray[7][1]="0px";
//        iArray[7][2]=100;
//        iArray[7][3]=3;
//
//        iArray[8]=new Array();
//        iArray[8][0]="�������";
//        iArray[8][1]="0px";
//        iArray[8][2]=100;
//        iArray[8][3]=3;
//
//        iArray[9]=new Array();
//        iArray[9][0]="������ʶ";
//        iArray[9][1]="0px";
//        iArray[9][2]=100;
//        iArray[9][3]=3;
//
//        iArray[10]=new Array();
//        iArray[10][0]="��ע��Ϣ";
//        iArray[10][1]="0px";
//        iArray[10][2]=100;
//        iArray[10][3]=3;

        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
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