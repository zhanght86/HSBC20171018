<%
//**************************************************************************************************
//Name��LLClaimPrepayMissInit.jsp
//Function������˹������й���������Ϣ��׼�����ڡ�Ԥ��������
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
    fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
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
    initLLClaimAuditGrid();
    initLLClaimPrepayGrid()
    LLClaimPrepayGridQuery();
    //LLClaimAuditGridQuery();
  }
  catch(re)
  {
    alert("LLClaimAuditMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLLClaimAuditGrid()
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
    iArray[1][0]="������";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�����˱���";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";             //����
    iArray[4][1]="120px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="��������";             //����
    iArray[7][1]="0px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3; 
    
    iArray[8]=new Array();
    iArray[8][0]="��������";             //����
    iArray[8][1]="0px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3; 
    
    iArray[9]=new Array();
    iArray[9][0]="��������";             //����
    iArray[9][1]="0px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3; 
    
    iArray[10]=new Array();
    iArray[10][0]="����ʦȨ��";             //����
    iArray[10][1]="0px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;     

    iArray[11]=new Array();
    iArray[11][0]="Ԥ����־";             //����
    iArray[11][1]="0px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=3;     
    
    iArray[12]=new Array();
    iArray[12][0]="����";             //����
    iArray[12][1]="0px";                //�п�
    iArray[12][2]=200;                  //�����ֵ
    iArray[12][3]=3;     
    
    iArray[13]=new Array();
    iArray[13][0]="��˲�����";             //����
    iArray[13][1]="0px";                //�п�
    iArray[13][2]=200;                  //�����ֵ
    iArray[13][3]=3;     

    iArray[14]=new Array();
    iArray[14][0]="����";             //����
    iArray[14][1]="0px";                //�п�
    iArray[14][2]=200;                  //�����ֵ
    iArray[14][3]=3;   

    iArray[15]=new Array();
    iArray[15][0]="Missionid";             //����
    iArray[15][1]="0px";                //�п�
    iArray[15][2]=200;                  //�����ֵ
    iArray[15][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[16]=new Array();
    iArray[16][0]="submissionid";             //����
    iArray[16][1]="0px";                //�п�
    iArray[16][2]=200;                  //�����ֵ
    iArray[16][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[17]=new Array();
    iArray[17][0]="activityid";             //����
    iArray[17][1]="0px";                //�п�
    iArray[17][2]=200;                  //�����ֵ
    iArray[17][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������            
  
    iArray[18]=new Array();
    iArray[18][0]="ComFlag";             //����---------Ȩ�޿缶��־
    iArray[18][1]="0px";                //�п�
    iArray[18][2]=200;                  //�����ֵ
    iArray[18][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������         
        
    LLClaimAuditGrid = new MulLineEnter( "fm" , "LLClaimAuditGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimAuditGrid.mulLineCount = 0;   
    LLClaimAuditGrid.displayTitle = 1;
    LLClaimAuditGrid.locked = 0;
    LLClaimAuditGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimAuditGrid.selBoxEventFuncName ="LLClaimAuditGridClick"; //��Ӧ�ĺ���������������   
    LLClaimAuditGrid.hiddenPlus=1;
    LLClaimAuditGrid.hiddenSubtraction=1;
    LLClaimAuditGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initLLClaimPrepayGrid()
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
    iArray[1][0]="������";             //����
    iArray[1][1]="150px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="����״̬";             //����
    iArray[2][1]="100px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�����˱���";             //����
    iArray[3][1]="100px";                //�п�
    iArray[3][2]=200;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����������";             //����
    iArray[4][1]="120px";                //�п�
    iArray[4][2]=500;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[5]=new Array();
    iArray[5][0]="�������Ա�";             //����
    iArray[5][1]="80px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0; 

    iArray[6]=new Array();
    iArray[6][0]="��������";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=200;                  //�����ֵ
    iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="Missionid";             //����
    iArray[7][1]="80px";                //�п�
    iArray[7][2]=200;                  //�����ֵ
    iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="submissionid";             //����
    iArray[8][1]="100px";                //�п�
    iArray[8][2]=200;                  //�����ֵ
    iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="activityid";             //����
    iArray[9][1]="100px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������                 
    
    LLClaimPrepayGrid = new MulLineEnter( "fm" , "LLClaimPrepayGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimPrepayGrid.mulLineCount =0;   
    LLClaimPrepayGrid.displayTitle = 1;
    LLClaimPrepayGrid.locked = 0;
    LLClaimPrepayGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
    LLClaimPrepayGrid.selBoxEventFuncName ="LLClaimPrepayGridClick"; //��Ӧ�ĺ���������������
    LLClaimPrepayGrid.hiddenPlus=1;
    LLClaimPrepayGrid.hiddenSubtraction=1;
    LLClaimPrepayGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
