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
   fm.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
   fm.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
   
   fm.all('tRptNo').value =nullToEmpty("<%=tClmNo%>");
   fm.all('tCustomerNo').value =nullToEmpty("<%=tCustomerNo%>");
   fm.all('tActivityID').value =nullToEmpty("<%=tActivityID%>");
   fm.all('tMissionID').value =nullToEmpty("<%=tMissionID%>");
   fm.all('tSubMissionID').value =nullToEmpty("<%=tSubMissionID%>");  

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
    initApplyPrepayNo(); //��ʼ��,�������κ�
    initLLClaimPrepayNodeQuery(); //[��ѯԤ���ڵ���Ϣ]��Ϊ���� ��˽ڵ� ׼���ṩ����
  }
  catch(re)
  {
    alert("LLClaimPrepayInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
    iArray[1][1]="80px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="�ְ���";             //����
    iArray[2][1]="80px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;   

    iArray[3]=new Array();
    iArray[3][0]="������";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=150;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�����˿ͻ���";             //����
    iArray[4][1]="100px";                //�п�
    iArray[4][2]=100;                  //�����ֵ
    iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[5]=new Array();
    iArray[5][0]="���α���";             //����
    iArray[5][1]="60px";                //�п�
    iArray[5][2]=200;                  //�����ֵ
    iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="������������";             //����
    iArray[6][1]="80px";                //�п�
    iArray[6][2]=120;                  //�����ֵ
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
    iArray[9][0]="�����⸶���";             //����
    iArray[9][1]="80px";                //�п�
    iArray[9][2]=200;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[10]=new Array();
    iArray[10][0]="Ԥ����־";             //����
    iArray[10][1]="0px";                //�п�
    iArray[10][2]=200;                  //�����ֵ
    iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[11]=new Array();
    iArray[11][0]="Ԥ�����";             //����
    iArray[11][1]="60px";                //�п�
    iArray[11][2]=200;                  //�����ֵ
    iArray[11][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

            
    LLClaimDetailGrid = new MulLineEnter( "fm" , "LLClaimDetailGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LLClaimDetailGrid.mulLineCount = 2;   
    LLClaimDetailGrid.displayTitle = 1;
    LLClaimDetailGrid.locked = 0;
    LLClaimDetailGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
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
    iArray[1][1]="90px";                //�п�
    iArray[1][2]=100;                  //�����ֵ
    iArray[1][3]=0;   

    iArray[2]=new Array();
    iArray[2][0]="�ְ���";             //����
    iArray[2][1]="90px";                //�п�
    iArray[2][2]=100;                  //�����ֵ
    iArray[2][3]=0;   

    iArray[3]=new Array();
    iArray[3][0]="������";             //����
    iArray[3][1]="120px";                //�п�
    iArray[3][2]=150;                  //�����ֵ
    iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
   
    iArray[4]=new Array();
    iArray[4][0]="������������";             //����
    iArray[4][1]="80px";                //�п�
    iArray[4][2]=120;                  //�����ֵ
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
    iArray[8][0]="Ԥ�����";             //����
    iArray[8][1]="60px";                //�п�
    iArray[8][2]=100;                  //�����ֵ
    iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[9]=new Array();
    iArray[9][0]="Ԥ������";             //����
    iArray[9][1]="80px";                //�п�
    iArray[9][2]=100;                  //�����ֵ
    iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
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
