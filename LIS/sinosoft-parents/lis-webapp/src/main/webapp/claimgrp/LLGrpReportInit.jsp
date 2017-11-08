<%
//**************************************************************************************************
//Name: LLGrpReportInit.jsp
//function�����屨�������ʼ��
//author: pd
//Date: 2005-10-19
//**************************************************************************************************
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");  
    mNowYear = mCurrentDate.substring(0,4);
    mNowMonth = mCurrentDate.substring(5,7);
    mNowDay = mCurrentDate.substring(8,10);
   
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('isClaimState').value = nullToEmpty("<%= tIsClaimState %>"); //�ж���������״̬��ѯ�Ľڵ�
    document.all('rgtstate').value = nullToEmpty("<%=trgtstate%>");    
    //alert("rgtstate:"+document.all('rgtstate').value);
    
    if (document.all('rgtstate').value=="" || document.all('rgtstate').value==null)
    {
       spanText7.disabled = false; 
    }
    else if (document.all('rgtstate').value!="" || document.all('rgtstate').value!=null)
    {      
      switch (document.all('rgtstate').value)
      {       
        case "11" :   
             fm.rgtstate[0].checked=true;
             break;
        case "03":
             fm.rgtstate[1].checked=true;
             divreport1.style.display="none";  
           	 divreport2.style.display="none"; 
           	 divreport3.style.display="none";
           	 divreport4.style.display="";
             initDiskErrQueryGrid();    
             divDiskErr.style.display="";  

             break;
        case "02":
             fm.rgtstate[2].checked=true;
             divreport1.style.display="none";  
           	 divreport2.style.display="none"; 
           	 divreport3.style.display="none";
           	 divreport4.style.display="";
           	 divreport5.style.display="";
             break;
      }
      spanText7.disabled = true;
    }

    mManageCom = nullToEmpty("<%= tG.ManageCom %>");
    document.all('AllManageCom').value = mManageCom;             //ȡ����½������ȫ������ ���ڲ�ѯ�����������
    document.all('ManageCom').value = mManageCom.substring(0,2); //ȡ����½������ǰ�������� ���ڲ�ѯҽԺ����
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

function initInpBox()
{
    try
    {
        fm.AccidentDate.disabled=false;
        fm.occurReason.disabled=false;
        fm.accidentDetail.disabled=false;
        fm.claimType.disabled=false;
        fm.Remark.disabled=false;

        fm.DoHangUp.disabled=true;
        fm.CreateNote.disabled=true;
        fm.BeginInq.disabled=true;
        fm.ViewInvest.disabled=true;
        fm.Condole.disabled=true;
        fm.SubmitReport.disabled=true;
        fm.ViewReport.disabled=true;
        fm.AccidentDesc.disabled=true;
        //fm.QueryCont2.disabled=true;
        //fm.QueryCont3.disabled=true;
        fm.RptConfirm.disabled=true;        
        fm.MedCertForPrt.disabled = true;
    }
    catch(ex)
    {
        alert("��LLGrpReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("��LLGrpReportInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
        initParam();
        initInpBox();
        initSelBox();
        initSubReportGrid();
        //initDiskErrQueryGrid();

        //*******************************************************************
        //�����ж�(��������������)
        //������0
        //�������У�1
        //*******************************************************************
        if(fm.isNew.value == '0')
        {
            //��ʾ������ť��
            showPage(this,operateButton2);
            showPage(this,operateButton1);
            showPage(this,operateButton3);
        }
        else if(fm.isNew.value == '1')
        {
            //��ʾ�޸İ�ť��
            if(fm.rgtstate[1].checked == true)
            {
               	operateButton2.style.display="none";
            }
            else
            {
                showPage(this,operateButton2);
            }

            showPage(this,operateButton1);
            showPage(this,operateButton3); //alert(150);
            initQuery();
            
            //���ÿɲ�����ť
            //fm.QueryPerson.disabled = true;
            if(fm.rgtstate[0].checked == true || fm.rgtstate[1].checked == true)
    		{
            	fm.QueryReport.disabled = false;
            }else{
            	fm.QueryReport.disabled = true;
            }
            fm.RptConfirm.disabled = false;

          if(fm.isClaimState.value == '1')
           {
           fm.addbutton.disabled=true;
           fm.deletebutton.disabled=true;
           fm.updatebutton.disabled=true;
           fm.CreateNote.disabled=true;
           fm.CreateNote2.disabled=true;
           fm.BeginInq.disabled=true;
           fm.RptConfirm.disabled=true;
           fm.QueryReport2.disabled=true;           
       //    fm.goBack.disabled=true;
           }
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
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

		iArray[3]=new Array();
        iArray[3][0]="�Ա����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="120px";
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
        
        iArray[8]=new Array()
        iArray[8][0]="֤������";
        iArray[8][1]="60px";
        iArray[8][2]=80;
        iArray[8][3]=0;  
        
        iArray[9]=new Array()
        iArray[9][0]="֤������";
        iArray[9][1]="200px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
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

function initDiskErrQueryGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";  //�п�
    iArray[0][2]=10;    //�����ֵ
    iArray[0][3]=0;      //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";
    iArray[1][1]="200px";
    iArray[1][2]=200;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="������";
    iArray[3][1]="60px";
    iArray[3][2]=100;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="������Ϣ";
    iArray[4][1]="200px";
    iArray[4][2]=100;
    iArray[4][3]=0;

    iArray[5]=new Array();
    iArray[5][0]="�����ļ���";
    iArray[5][1]="100px";
    iArray[5][2]=100;
    iArray[5][3]=0;

    iArray[6]=new Array();
    iArray[6][0]="�������";
    iArray[6][1]="30px";
    iArray[6][2]=30;
    iArray[6][3]=0;
    
    iArray[7]=new Array();
    iArray[7][0]="�������Ա";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="��������";
    iArray[8][1]="80px";
    iArray[8][2]=100;
    iArray[8][3]=0;        

    iArray[9]=new Array();
    iArray[9][0]="����ʱ��";
    iArray[9][1]="80px";
    iArray[9][2]=100;
    iArray[9][3]=0;   

    DiskErrQueryGrid = new MulLineEnter( "fmload" , "DiskErrQueryGrid" );
    //��Щ���Ա�����loadMulLineǰ
    DiskErrQueryGrid.mulLineCount = 0;
    DiskErrQueryGrid.displayTitle = 1;
    DiskErrQueryGrid.locked = 0;
    DiskErrQueryGrid.hiddenPlus=1;
    DiskErrQueryGrid.canChk =0; //��ѡ��ť��1��ʾ��0����
    DiskErrQueryGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    DiskErrQueryGrid.hiddenSubtraction=1;
    DiskErrQueryGrid.loadMulLine(iArray);
    //��Щ����������loadMulLine����
  }
  catch(ex)
  {
    alert(ex);
  }
}
</script>
