<%
//**************************************************************************************************/
//Name: LLGrpClaimRegisterInit.jsp
//function���������������ʼ��
//author: pd
//Date: 2005-10-20
//**************************************************************************************************/
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
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
    document.all('clmState').value = nullToEmpty("<%= tClmState %>");
    document.all('isClaimState').value = nullToEmpty("<%= tIsClaimState %>"); //�ж���������״̬��ѯ�Ľڵ�
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    mManageCom = nullToEmpty("<%= tG.ManageCom %>");
    document.all('tOperator').value = nullToEmpty("<%= tG.Operator %>");
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

//��ʼ����
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSubReportGrid();
        initClaimGrid();        
        initDutyKindGrid();
        initPolDutyKindGrid();
        initPolDutyCodeGrid();
        initLPEdorItemGrid();
        QueryRgtState();
        //******************************************************************************************Beg
        //ͨ���������isNew��clmState�ж���ʾ[�޸�]����[����]��ť
        //--������isNew=0 ��ֱ����ʾ[����]��ť
        //--����ػ������У�isNew=1&&clmState=10 ����������Ϣ�����ڣ���ѯ��������ʾ[����]��ť
        //                    isNew=1&&clmState=20||30 ����������Ϣ���ڣ���ѯ��������ʾ[�޸�]��ť
        //******************************************************************************************
        //alert(fm.isNew.value);
        if(fm.isNew.value == '0')
        {
            fm.isReportExist.value = "false";
            fm.isRegisterExist.value = "false";
            //��ʾ[����]��ť��
            operateButton21.style.display="";
            operateButton23.style.display="";
            //���ÿɲ�����ť
            disabledButton();
            fm.QueryPerson.disabled = false;
            //fm.QueryReport.disabled = false;
            fm.addbutton.disabled = false;
            fm.deletebutton.disabled=false;
            fm.goBack.disabled = false;
            //fm.QueryCont4.disabled = false;
          //��½������������,��½��������С��4λʱ���������
//         if(document.allManageCom.value.length < 4)
//         {
//           fm.updatebutton.disabled=true;
//           fm.deletebutton.disabled=true;
//           fm.QueryReport2.disabled=true;
//           fm.CreateNote2.disabled=true;
//           fm.CreateNote.disabled=true;
//           fm.BeginInq.disabled=true;

//          fm.printPassRgt.disabled=true;
//          fm.printDelayRgt.disabled=true;
//          fm.conclusionSave.disabled=true;
//          fm.printNoRgt.disabled=true;

//          fm.addUpdate.disabled=true;
//          fm.saveUpdate.disabled=true;

//          fm.MedicalFeeInp.disabled=true;
//          fm.dutySet.disabled=true;
//          fm.conclusionSave1.disabled=true;
//          fm.RgtConfirm.disabled=true;
//         }
        }
        else
        {
            if(fm.clmState.value == '10')
            {
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "false";    
                //��ʾ[����]��ť��
                operateButton21.style.display="";
                operateButton22.style.display="";
                operateButton23.style.display="";
                //��ѯ����
                queryReport();
                //��ѯ������
                //queryProposer();
                //���ÿɲ�����ť
                disabledButton();
                fm.QueryPerson.disabled = false;
                //fm.QueryReport.disabled = "";
                fm.addbutton.disabled = false;
                //fm.updatebutton.disabled=false;
                //fm.deletebutton.disabled=false;
                fm.QueryCont2.disabled = false;
                fm.QueryCont3.disabled = false;
                fm.goBack.disabled = false;               
                //fm.QueryCont4.disabled = false;
               //��½������������,��½��������С��4λʱ���������
//                if(document.allManageCom.value.length < 4)
//                {
//                  fm.updatebutton.disabled=true;
//                  fm.deletebutton.disabled=true;
//                  fm.QueryReport2.disabled=true;
//                  fm.CreateNote2.disabled=true;
//                  fm.CreateNote.disabled=true;
//                  fm.BeginInq.disabled=true;
                
//                  fm.printPassRgt.disabled=true;
//                  fm.printDelayRgt.disabled=true;
//                  fm.conclusionSave.disabled=true;
//                  fm.printNoRgt.disabled=true;
                
//                  fm.addUpdate.disabled=true;
//                  fm.saveUpdate.disabled=true;
                
//                  fm.MedicalFeeInp.disabled=true;
//                  fm.dutySet.disabled=true;
//                  fm.conclusionSave1.disabled=true;
//                  fm.RgtConfirm.disabled=true;                 
//               }
               //�ж���������״̬��ѯ�Ľڵ��������Ϣ��ѯ����
                  if(fm.isClaimState.value == '1')
                  {                 
                   fm.updatebutton.disabled=true;
                   fm.deletebutton.disabled=true;
//                   fm.QueryReport2.disabled=true;
                   fm.CreateNote.disabled=true;
                   fm.CreateNote2.disabled=true;
                   fm.BeginInq.disabled=true; 
                   fm.printPassRgt.disabled=true;
                   fm.printDelayRgt.disabled=true;
                   fm.conclusionSave.disabled=true;
                   fm.printNoRgt.disabled=true;
                   fm.addUpdate.disabled=true;
                   fm.saveUpdate.disabled=true;
                   fm.MedicalFeeInp.disabled=true;
                   fm.dutySet.disabled=true;
//                   fm.conclusionSave1.disabled=true;
                   fm.RgtConfirm.disabled=true;
 //                  fm.goBack.disabled=true;
                  }
            }
            else
            {               
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "true";             
                //��ʾ[�޸�]��ť��
                operateButton21.style.display="";
                operateButton22.style.display="";
                operateButton23.style.display="";
                
                fm.addbutton.disabled = true;
                fm.updatebutton.disabled=false;
                fm.deletebutton.disabled=false;
                //��ѯ����
                queryRegister();
                //���ÿɲ�����ť
                fm.QueryPerson.disabled = false;
                //fm.QueryReport.disabled = true;
                //fm.QueryCont4.disabled = false;

              //��½������������,��½��������С��4λʱ���������
//              if(document.allManageCom.value.length < 4)
//              {
//               fm.updatebutton.disabled=true;
//               fm.deletebutton.disabled=true;
//               fm.QueryReport2.disabled=true;
//               fm.CreateNote.disabled=true;
//               fm.CreateNote2.disabled=true;
//               fm.BeginInq.disabled=true;
    
//               fm.printPassRgt.disabled=true;
//               fm.printDelayRgt.disabled=true;
//               fm.conclusionSave.disabled=true;
//               fm.printNoRgt.disabled=true;
    
//               fm.addUpdate.disabled=true;
//               fm.saveUpdate.disabled=true;
    
//               fm.MedicalFeeInp.disabled=true;
//               fm.dutySet.disabled=true;
//               fm.conclusionSave1.disabled=true;
//               fm.RgtConfirm.disabled=true;
//              }
              //�ж���������״̬��ѯ�Ľڵ��������Ϣ��ѯ����

              if(fm.isClaimState.value == '1')
              { 
                            
               fm.updatebutton.disabled=true;               
               fm.deletebutton.disabled=true;             
//               fm.QueryReport2.disabled=true;                
               fm.CreateNote.disabled=true;
               fm.CreateNote2.disabled=true;              
               fm.BeginInq.disabled=true; 
               fm.printPassRgt.disabled=true;
               fm.printDelayRgt.disabled=true;
               fm.conclusionSave.disabled=true;               
               fm.printNoRgt.disabled=true;
               fm.addUpdate.disabled=true;
               fm.saveUpdate.disabled=true;
               fm.MedicalFeeInp.disabled=true;
               fm.dutySet.disabled=true;
//               fm.conclusionSave1.disabled=true;
               fm.RgtConfirm.disabled=true;
//              fm.goBack.disabled=true;                
              }
            }
        }
     //zy 2009-07-28 15:07 ���Ϊ������Ʒ����ʾ�˻�������ť
	   getLLEdorTypeCA();
	   if(fm.AccRiskCode.value=='211901')
	   {
	     fm.LCInsureAcc.disabled = false;
	   }
    fm.addUpdate.disabled = true; //����޸�
    fm.saveUpdate.disabled = true; //�����޸�
        //******************************************************************************************End
    }
    catch(ex)
    {
        alert("��LLRegisterInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//ҳ���������ʼ��
function initInpBox()
{
    try
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        //fm.MedCertForPrt.disabled = true;
        //zy 2009-07-28 15:07 ���Ϊ������Ʒ����ʾ�˻�������ť
        fm.LCInsureAcc.disabled = true ;
    }
    catch(ex)
    {
        alert("��LLReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

//��������Ϣ�б��ʼ��
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
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�Ա����";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�Ա�";
        iArray[4][1]="50px";
        iArray[4][2]=50;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="100px";
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
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
        SubReportGrid.selBoxEventFuncName ="allSubReportGridClick"; //��Ӧ�ĺ���������������
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


/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ������Ϊ�������Ľ�������ֱ���ʾΪ�ⰸ���������ͣ��������������ͣ������������͵ı���
    �� �� �ˣ�����
    �޸����ڣ�2005.06.25
   =========================================================================
**/

//�ⰸ�ܽ��
function initClaimGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;
        
        iArray[1]=new Array();
	      iArray[1][0]="����";      	   		//����
	      iArray[1][1]="80px";            			//�п�
	      iArray[1][2]=20;            			//�����ֵ
	      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[2]=new Array();
        iArray[2][0]="�⸶���";
        iArray[2][1]="80px";
        iArray[2][2]=30;
        iArray[2][3]=7;
        iArray[2][22]="col1";
				iArray[2][23]="0";

        iArray[3]=new Array();
        iArray[3][0]="Ԥ�����";
        iArray[3][1]="80px";
        iArray[3][2]=30;
        iArray[3][3]=7;
        iArray[3][22]="col1";
				iArray[3][23]="0";

        iArray[4]=new Array();
        iArray[4][0]="������";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=7;
        iArray[4][22]="col1";
				iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="�����⸶���";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][22]="col1";
				iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="������";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=7;
        iArray[6][22]="col1";
				iArray[6][23]="0";
        

        ClaimGrid = new MulLineEnter("fm","ClaimGrid");
        ClaimGrid.mulLineCount = 0;       // ��ʾ����
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}



//�ⰸ����������
function initDutyKindGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="�������ʹ���";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������������";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
	      iArray[3][0]="����";      	   		//����
	      iArray[3][1]="80px";            			//�п�
	      iArray[3][2]=20;            			//�����ֵ
	      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[4]=new Array();
        iArray[4][0]="�˵����";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][22]="col3";
				iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="�Է�/�Ը����";      //Ҳ����������
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][22]="col3";
				iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="�����ϼ�������";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=7;
        iArray[6][22]="col3";
				iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="�籣����";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=7;
        iArray[7][22]="col3";
				iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="����������";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=7;
        iArray[8][22]="col3";
				iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="�����⸶���";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=7;
        iArray[9][22]="col3";
				iArray[9][23]="0";


        DutyKindGrid = new MulLineEnter("fm","DutyKindGrid");
        DutyKindGrid.mulLineCount = 0;       // ��ʾ����
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        DutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//�������������ͼ����ʼ��
function initPolDutyKindGrid()
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
      iArray[1][0]="���˱�����";
      iArray[1][1]="105px";
      iArray[1][2]=105;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="������";
      iArray[2][1]="105px";
      iArray[2][2]=105;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��������";
      iArray[3][1]="60px";
      iArray[3][2]=60;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="��Ч����";
      iArray[4][1]="70px";
      iArray[4][2]=60;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="��������";
      iArray[5][1]="70px";
      iArray[5][2]=60;
      iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="���ִ���";
        iArray[6][1]="60px";
        iArray[6][2]=60;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��������";
        iArray[7][1]="140px";
        iArray[7][2]=140;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
	      iArray[8][0]="����";      	   		//����
	      iArray[8][1]="80px";            			//�п�
	      iArray[8][2]=20;            			//�����ֵ
	      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[9]=new Array();
        iArray[9][0]="������";
        iArray[9][1]="60px";
        iArray[9][2]=60;
        iArray[9][3]=7;
        iArray[9][22]="col8";
				iArray[9][23]="0";
        
        
      PolDutyKindGrid = new MulLineEnter("fm","PolDutyKindGrid");
      PolDutyKindGrid.mulLineCount = 0;       // ��ʾ����
      PolDutyKindGrid.displayTitle = 1;
      PolDutyKindGrid.locked = 0;
  //  PolDutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      PolDutyKindGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      PolDutyKindGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyKindGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//��������ʼ��
/*
function initPolDutyCodeGrid()
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
      iArray[1][0]="������";
      iArray[1][1]="110px";
      iArray[1][2]=110;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="��������";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";
      iArray[3][1]="60px";
      iArray[3][2]=60;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="�������";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="��������";
      iArray[5][1]="110px";
      iArray[5][2]=110;
      iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="��������";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="����ֹ��";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;    
        
        iArray[9]=new Array();
	      iArray[9][0]="����";      	   		//����
	      iArray[9][1]="80px";            			//�п�
	      iArray[9][2]=20;            			//�����ֵ
	      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    

      iArray[10]=new Array();
      iArray[10][0]="����";
      iArray[10][1]="60px";
      iArray[10][2]=60;
      iArray[10][3]=0;
      iArray[10][22]="col9";
			iArray[10][23]="0";

      iArray[11]=new Array();
      iArray[11][0]="��Ⱥ���";
      iArray[11][1]="60px";
      iArray[11][2]=60;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="���˺���";
      iArray[12][1]="60px";
      iArray[12][2]=60;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="������";
      iArray[13][1]="60px";
      iArray[13][2]=60;
      iArray[13][3]=0;
      iArray[13][22]="col9";
			iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="��������";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=2;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[14][4]="llpayregconclusion"; //�Ƿ����ô���: null����" "Ϊ������
        iArray[14][5]="13|14";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
        iArray[14][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��

        iArray[15]=new Array();
        iArray[15][0]="��������";
        iArray[15][1]="60px";
        iArray[15][2]=60;
        iArray[15][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[16]=new Array();
        iArray[16][0]="������Դ";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        

        iArray[17]=new Array();
        iArray[17][0]="dutycode";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����


      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // ��ʾ����
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
  //  PolDutyCodeGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      PolDutyCodeGrid.canSel =1;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      PolDutyCodeGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}
*/
function initPolDutyCodeGrid()
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
        iArray[1][0]="������";
        iArray[1][1]="130px";
        iArray[1][2]=130;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="��������";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="���ֱ���";
        iArray[3][1]="60px";
        iArray[3][2]=600;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="�������";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="200px";
        iArray[5][2]=200;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
          
        iArray[7]=new Array();
        iArray[7][0]="����ֹ��";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="��������";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
	      iArray[9][0]="����";      	   		//����
	      iArray[9][1]="80px";            			//�п�
	      iArray[9][2]=20;            			//�����ֵ
	      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

        iArray[10]=new Array();
        iArray[10][0]="����";
        iArray[10][1]="60px";
        iArray[10][2]=60;
        iArray[10][3]=7;
        iArray[10][22]="col9";
				iArray[10][23]="0";
      
        iArray[11]=new Array();
        iArray[11][0]="��Ⱥ���";
        iArray[11][1]="60px";
        iArray[11][2]=60;
        iArray[11][3]=3;
      
        iArray[12]=new Array();
        iArray[12][0]="���˺���";
        iArray[12][1]="60px";
        iArray[12][2]=60;
        iArray[12][3]=3;
      
        iArray[13]=new Array();
        iArray[13][0]="������";
        iArray[13][1]="60px";
        iArray[13][2]=60;
        iArray[13][3]=7;
        iArray[13][22]="col9";
				iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="��������";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[14][4]="llpayconclusion";    //�Ƿ����ô���: null����" "Ϊ������
        iArray[14][5]="14|15";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
        iArray[14][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��


        iArray[15]=new Array();
        iArray[15][0]="��������";
        iArray[15][1]="60px";
        iArray[15][2]=60;
        iArray[15][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[16]=new Array();
        iArray[16][0]="�ܸ�ԭ�����";
        iArray[16][1]="100px";
        iArray[16][2]=100;
        iArray[16][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[16][4]="llprotestreason";    //�Ƿ����ô���: null����" "Ϊ������
        iArray[16][5]="16|17";              //���ô�����Ϣ�ֱ���ڵ�13�к͵�14�У�'|'Ϊ�ָ��
        iArray[16][6]="0|1";                //���ô�������ĵ�0����룩���ڵ�1��,��1����ƣ����ڵ�2��

        iArray[17]=new Array();
        iArray[17][0]="�ܸ�ԭ������";
        iArray[17][1]="100px";
        iArray[17][2]=100;
        iArray[17][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        
        iArray[18]=new Array();
        iArray[18][0]="�ܸ�����";
        iArray[18][1]="60px";
        iArray[18][2]=60;
        iArray[18][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����

        iArray[19]=new Array();
        iArray[19][0]="���ⱸע";
        iArray[19][1]="60px";
        iArray[19][2]=60;
        iArray[19][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
              

        iArray[20]=new Array();
        iArray[20][0]="Ԥ�����";
        iArray[20][1]="60px";
        iArray[20][2]=60;
        iArray[20][3]=7;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[20][22]="col9";
				iArray[20][23]="0";
        
        iArray[21]=new Array();
        iArray[21][0]="";
        iArray[21][1]="60px";
        iArray[21][2]=60;
        iArray[21][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
           
        iArray[22]=new Array();
        iArray[22][0]="�������";
        iArray[22][1]="60px";
        iArray[22][2]=60;
        iArray[22][3]=7;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        iArray[22][22]="col9";
				iArray[22][23]="0";

        iArray[23]=new Array();
        iArray[23][0]="����ԭ�����";
        iArray[23][1]="100px";
        iArray[23][2]=100;
        iArray[23][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
                                
        iArray[24]=new Array();
        iArray[24][0]="����ԭ������";
        iArray[24][1]="100px";
        iArray[24][2]=100;
        iArray[24][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
              
        iArray[25]=new Array();
        iArray[25][0]="������ע";
        iArray[25][1]="60px";
        iArray[25][2]=60;
        iArray[25][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        
        iArray[26]=new Array();
        iArray[26][0]="Ԥ����־����";
        iArray[26][1]="60px";
        iArray[26][2]=60;
        iArray[26][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        
        iArray[27]=new Array();
        iArray[27][0]="Ԥ����־";
        iArray[27][1]="60px";
        iArray[27][2]=60;
        iArray[27][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        
        iArray[28]=new Array();
        iArray[28][0]="������Դ";
        iArray[28][1]="60px";
        iArray[28][2]=60;
        iArray[28][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����                  
                    
          iArray[29]=new Array();
          iArray[29][0]="dutycode";
          iArray[29][1]="60px";
          iArray[29][2]=60;
          iArray[29][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
                    
          iArray[30]=new Array();
          iArray[30][0]="�ͻ���";
          iArray[30][1]="60px";
          iArray[30][2]=60;
          iArray[30][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
          
          
          //������20070418�޸�,ԭ��:��MULLINE�м��뱣��״̬��
          iArray[31]=new Array();
          iArray[31][0]="����״̬";
          iArray[31][1]="60px";
          iArray[31][2]=60;
          iArray[31][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // ��ʾ����
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
//      PolDutyCodeGrid.canChk =1;               // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
     if(fm.isClaimState.value == '1')
     {
       PolDutyCodeGrid.canSel =0;                   // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
     }else
     {
       PolDutyCodeGrid.canSel =1;   
     }
        //PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //��Ӧ�ĺ���������������
//        PolDutyCodeGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���  
      PolDutyCodeGrid.hiddenPlus=1;        //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}


function initLPEdorItemGrid()
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
      iArray[1][0]="���˱�����";
      iArray[1][1]="80px";
      iArray[1][2]=110;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="��ȫ����";
      iArray[3][1]="60px";
      iArray[3][2]=150;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="��ȫ��Ч����";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;
      
      iArray[5]=new Array();
	    iArray[5][0]="����";      	   		//����
	    iArray[5][1]="80px";            			//�п�
	    iArray[5][2]=20;            			//�����ֵ
	    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�˷ѽ��";
      iArray[6][1]="110px";
      iArray[6][2]=110;
      iArray[6][3]=7;
      iArray[6][22]="col5";
			iArray[6][23]="0";


//      iArray[6]=new Array();
//      iArray[6][0]="��������";
//      iArray[6][1]="80px";
//      iArray[6][2]=80;
//      iArray[6][3]=0;
        
//      iArray[7]=new Array();
//      iArray[7][0]="����ֹ��";
//      iArray[7][1]="80px";
//      iArray[7][2]=80;
//      iArray[7][3]=0;
   
    
      
      LPEdorItemGrid = new MulLineEnter("fm","LPEdorItemGrid");
      LPEdorItemGrid.mulLineCount = 0;       // ��ʾ����
      LPEdorItemGrid.displayTitle = 1;
      LPEdorItemGrid.locked = 0;
  //  LPEdorItemGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      LPEdorItemGrid.canSel =0;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
      LPEdorItemGrid.hiddenPlus=1;           //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LPEdorItemGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LPEdorItemGrid.loadMulLine(iArray);
 
  }
  catch(ex)
  {
    alert(ex);
  }  
}
/**=========================================================================
    �޸�״̬������
    �޸�ԭ������Ϊ�������Ľ�������ֱ���ʾΪ�ⰸ�����⣬�������������ͣ������������͵ı���
    �� �� �ˣ�����
    �޸����ڣ�2005.06.25
   =========================================================================
**/

</script>
