<%
//**************************************************************************************************
//Name: LLClaimRegisterInit.jsp
//function�����������ʼ��
//author: zl
//Date: 2005-6-14 20:03
//**************************************************************************************************
%>
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
mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql54");
		mySql.addSubPara("1"); 
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //ȡ��������ʱ����Ϊϵͳ��ǰ����
	var sysdatearr=easyExecSql(mySql.getString());
	mCurrentDate=sysdatearr[0][0];
 
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('clmState').value = nullToEmpty("<%= tClmState %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('ActivityID').value = nullToEmpty("<%= tActivityID %>");
	
    mNowYear = mCurrentDate.substring(0,4);

    spanConclusion1.style.display="none";


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
        initPolDutyKindGrid()
        initPolDutyCodeGrid();
        //******************************************************************************************Beg
        //ͨ���������isNew��clmState�ж���ʾ[�޸�]����[����]��ť
        //--������isNew=0 ��ֱ����ʾ[����]��ť
        //--����ػ������У�isNew=1&&clmState=10 ����������Ϣ�����ڣ���ѯ��������ʾ[����]��ť
        //                    isNew=1&&clmState=20||30 ����������Ϣ���ڣ���ѯ��������ʾ[�޸�]��ť
        //******************************************************************************************
        if(fm.isNew.value == '0')
        {
            fm.isReportExist.value = "false";
            fm.isRegisterExist.value = "false";
            //��ʾ[����]��ť��
            operateButton21.style.display="";
            //���ÿɲ�����ť
            disabledButton();
            fm.QueryPerson.disabled = false;
			fm.QueryReportClm.disabled=false;
            fm.QueryReport.disabled = false;
            fm.addbutton.disabled = false;
            fm.goBack.disabled = false;
        //  fm.printDelayRgt.disabled = false; //�ɴ�ӡ���䵥֤
            document.all('MedicalAccidentDate').disabled=true;
//             document.all('OtherAccidentDate').disabled=true;
        }
        else
        {
            if(fm.clmState.value == '10')
            {
    			fm.QueryReportClm.disabled=false;
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "false";    
                //��ʾ[����]��ť��
                operateButton21.style.display="";
                //��ѯ����
                queryReport();
                //��ѯ������
                queryProposer();
                //���ÿɲ�����ť
                disabledButton();
                fm.addbutton.disabled = false;
                fm.QueryCont2.disabled = false;
                fm.QueryCont3.disabled = false;
                fm.goBack.disabled = false;  
			//	fm.printDelayRgt.disabled = false; //�ɴ�ӡ���䵥֤
                document.all('MedicalAccidentDate').disabled=true;
                document.all('OtherAccidentDate').disabled=true;             
            }
            else
            {
    			fm.QueryReportClm.disabled=false;
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "true";             
                //��ʾ[�޸�]��ť��
                operateButton22.style.display="";
                //��ѯ����
                queryRegister();
                //���ÿɲ�����ť
                fm.QueryPerson.disabled = true;
                fm.QueryReport.disabled = true;

                document.all('MedicalAccidentDate').disabled=false;
                document.all('OtherAccidentDate').disabled=false;   
            }
        }
        
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
        iArray[1][1]="230px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ�����"; //�¹�������
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

        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
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
        iArray[2][2]=80;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="Ԥ�����";
        iArray[3][1]="80px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="������";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="�����⸶���";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="������";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=3;
        

        ClaimGrid = new MulLineEnter("document","ClaimGrid");
        ClaimGrid.mulLineCount = 5;       // ��ʾ����
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        ClaimGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
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
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="�Է�/�Ը����";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="���������";      //Ҳ����������
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="�����ϼ�������";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="�籣����";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="����������";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="�����⸶���";
        iArray[10][1]="80px";
        iArray[10][2]=80;
        iArray[10][3]=0;
        

        DutyKindGrid = new MulLineEnter("document","DutyKindGrid");
        DutyKindGrid.mulLineCount = 5;       // ��ʾ����
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
        DutyKindGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
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
	    iArray[1][0]="��ͬ��";
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
        iArray[9][3]=0;
        
        
	    PolDutyKindGrid = new MulLineEnter("document","PolDutyKindGrid");
	    PolDutyKindGrid.mulLineCount = 5;       // ��ʾ����
	    PolDutyKindGrid.displayTitle = 1;
	    PolDutyKindGrid.locked = 0;
	//  PolDutyKindGrid.canChk =1;              // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
	    PolDutyKindGrid.canSel =1;                  // ��ѡ��ť��1 ��ʾ ��0 ���أ�ȱʡֵ��
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
	    iArray[3][2]=60;
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
        iArray[16][0]="�����ڼ��ʶ";
        iArray[16][1]="80px";
        iArray[16][2]=60;
        iArray[16][3]=0;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����
        

        iArray[17]=new Array();
        iArray[17][0]="dutycode";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;                    //1��ʾ����0��ʾ������,2��ʾ����ѡ��,3����


	    PolDutyCodeGrid = new MulLineEnter("document","PolDutyCodeGrid");
	    PolDutyCodeGrid.mulLineCount = 5;        // ��ʾ����
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
/**=========================================================================
    �޸�״̬������
    �޸�ԭ������Ϊ�������Ľ�������ֱ���ʾΪ�ⰸ�����⣬�������������ͣ������������͵ı���
    �� �� �ˣ�����
    �޸����ڣ�2005.06.25
   =========================================================================
**/

</script>
