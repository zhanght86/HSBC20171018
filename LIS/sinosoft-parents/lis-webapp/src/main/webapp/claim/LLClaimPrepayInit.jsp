<%
//**************************************************************************************************
//Name��LLClaimPrepayInit.jsp
//Function����Ԥ�������������ʼ���ļ�
//Author��yuejw
//Date: 2005-7-5 16:00
//���¼�¼��
//**************************************************************************************************
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

//���ղ���
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   
   document.all('RptNo').value =nullToEmpty("<%=tClmNo%>");
   document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
   
   document.all('CustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
   document.all('CustomerName').value =nullToEmpty("<%=tCustomerName%>");
   document.all('CustomerSex').value =nullToEmpty("<%=tCustomerSex%>");

   
   document.all('ActivityID').value =nullToEmpty("<%=tActivityID%>");
   document.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
   document.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");  
   document.all('tAuditer').value =nullToEmpty("<%=mAuditer%>");  
   //alert(document.all('tAuditer').value);

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

function initForm()
{
  try
  {
    initParam(); 
    initLLClaimDetailGrid();
    initLLPrepayDetailGrid();   
    
    initLLClaimDetailGridQuery();   //��ʼ����ѯ�� ����������Ϣ�б�
    initLLPrepayDetailGridQuery();   //��ʼ����ѯ�� �ѱ����Ԥ��������Ϣ�б�
    
    initSubReportGrid(); //��������Ϣ
    initApplyPrepayNo(); //��ʼ��,�������κ�
    initLLClaimPrepayNodeQuery(); //[��ѯԤ���ڵ���Ϣ]��Ϊ���� ��˽ڵ� ׼���ṩ����

    queryRegister();
  }
  catch(re)
  {
    alert("LLClaimPrepayInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


/**
��������Ϣ��ʼ��
*/
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

//    iArray[3]=new Array();
//    iArray[3][0]="ҽԺ����";
//    iArray[3][1]="0px";
//    iArray[3][2]=60;
//    iArray[3][3]=3;
//    iArray[3][4]="HospitalCode";
//    iArray[3][5]="3|4";                                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
//    iArray[3][6]="0|1";                        //��������з������ô����еڼ�λֵ
//    iArray[3][9]="ҽԺ����|NOTNULL";

//iArray[4]=new Array()
//iArray[4][0]="ҽԺ����";
//iArray[4][1]="0px";
//iArray[4][2]=100;
//iArray[4][3]=3;

//iArray[5]=new Array()
//iArray[5][0]="��Ժ����";
//iArray[5][1]="0px";
//iArray[5][2]=100;
//iArray[5][3]=3;

    iArray[6]=new Array()
    iArray[6][0]="�籣��־����";
    iArray[6][1]="50px";
    iArray[6][2]=100;
    iArray[6][3]=3;
    
    iArray[7]=new Array()
    iArray[7][0]="�籣��־";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0; 

//    iArray[6]=new Array();
//    iArray[6][0]="����ϸ��";
//    iArray[6][1]="0px";
//    iArray[6][2]=100;
//    iArray[6][3]=3;
//
//    iArray[7]=new Array();
//    iArray[7][0]="����ҽԺ";
//    iArray[7][1]="0px";
//    iArray[7][2]=100;
//    iArray[7][3]=3;
//
//    iArray[8]=new Array();
//    iArray[8][0]="�������";
//    iArray[8][1]="0px";
//    iArray[8][2]=100;
//    iArray[8][3]=3;
//
//    iArray[9]=new Array();
//    iArray[9][0]="������ʶ";
//    iArray[9][1]="0px";
//    iArray[9][2]=100;
//    iArray[9][3]=3;
//
//    iArray[10]=new Array();
//    iArray[10][0]="��ע��Ϣ";
//    iArray[10][1]="0px";
//    iArray[10][2]=100;
//    iArray[10][3]=3;

    SubReportGrid = new MulLineEnter("fm","SubReportGrid");
    SubReportGrid.mulLineCount = 0;
    SubReportGrid.displayTitle = 1;
    SubReportGrid.locked = 0;
//    SubReportGrid.canChk =1;
    SubReportGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //��Ӧ�ĺ���������������
    SubReportGrid.selBoxEventFuncParm ="1"; //����Ĳ���,����ʡ�Ը���  
    SubReportGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SubReportGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
    SubReportGrid.loadMulLine(iArray);
}
catch(ex)
{
alter(ex);
}
}

// �⸶��ϸ����Ϣ�б�ĳ�ʼ��
function initLLClaimDetailGrid()
{                               
    var iArray = new Array();   
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=150;                  //�����ֵ
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="�ְ���";             //����
    iArray[2][1]="80px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=3;   

    iArray[3]=new Array();
    iArray[3][0]="������";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=150;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�ͻ�����";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=100;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="���α���";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=80;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="������������";             //����
    iArray[6][1]="150px";                //�п�
    iArray[6][2]=150;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="�������α���";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=120;                  //�����ֵ
    iArray[7][3]=0; 
    
    iArray[8]=new Array();
    iArray[8][0]="�����¹ʺ�";             //����
    iArray[8][1]="0px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;    //�Ƿ���������,1��ʾ����0��ʾ������ 
    
    iArray[9]=new Array();
    iArray[9][0]="����";      	   		//����
    iArray[9][1]="80px";            			//�п�
    iArray[9][2]=20;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
    
    iArray[10]=new Array();
    iArray[10][0]="�����⸶���";             //����
    iArray[10][1]="80px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[11]=new Array();
    iArray[11][0]="Ԥ����־";             //����
    iArray[11][1]="0px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[12]=new Array();
    iArray[12][0]="Ԥ�����";             //����
    iArray[12][1]="60px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[13]=new Array();
    iArray[13][0]="�����������ʹ���";             //����
    iArray[13][1]="80px";                //�п�
    iArray[13][2]=120;                  //�����ֵ
    iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[14]=new Array();
    iArray[14][0]="���տͻ�����";             //����
    iArray[14][1]="80px";                //�п�
    iArray[14][2]=120;                  //�����ֵ
    iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

            
    LLClaimDetailGrid = new MulLineEnter( "fm" , "LLClaimDetailGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimDetailGrid.mulLineCount = 2;   
    LLClaimDetailGrid.displayTitle = 1;
    LLClaimDetailGrid.locked = 0;
    LLClaimDetailGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimDetailGrid.selBoxEventFuncName ="LLClaimDetailGridClick"; //��Ӧ�ĺ���������������   
    LLClaimDetailGrid.hiddenPlus=1;
    LLClaimDetailGrid.hiddenSubtraction=1;
    LLClaimDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// Ԥ����ϸ��¼�б�ĳ�ʼ��
function initLLPrepayDetailGrid()
{                               
    var iArray = new Array();   
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";                //�п�
    iArray[0][2]=10;                  //�����ֵ
    iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�ⰸ��";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=150;                  //�����ֵ
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="�ְ���";             //����
    iArray[2][1]="90px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=3;   

    iArray[3]=new Array();
    iArray[3][0]="������";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=150;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
   
    iArray[4]=new Array();
    iArray[4][0]="������������";             //����
    iArray[4][1]="150px";                //�п�
    iArray[4][2]=150;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������α���";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=120;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="Ԥ�����κ�";             //����
    iArray[6][1]="0px";                //�п�
    iArray[6][2]=100;                  //�����ֵ
    iArray[6][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Ԥ�����";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=100;                  //�����ֵ
    iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="����";      	   		//����
    iArray[8][1]="80px";            			//�п�
    iArray[8][2]=20;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="Ԥ�����";             //����
    iArray[9][1]="60px";                //�п�
    iArray[9][2]=100;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="Ԥ������";             //����
    iArray[10][1]="80px";                //�п�
    iArray[10][2]=100;                  //�����ֵ
    iArray[10][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    LLPrepayDetailGrid = new MulLineEnter( "fm" , "LLPrepayDetailGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLPrepayDetailGrid.mulLineCount = 0;   
    LLPrepayDetailGrid.displayTitle = 1;
    LLPrepayDetailGrid.locked = 0;
    LLPrepayDetailGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLPrepayDetailGrid.hiddenPlus=1;
    LLPrepayDetailGrid.hiddenSubtraction=1;
    LLPrepayDetailGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
