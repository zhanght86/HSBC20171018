<%
//�������ƣ�LLClaimExemptInit.jsp
//�����ܣ����⴦���ʼ��
//�������ڣ�2005-07-19
//������  ��yuejw
//���¼�¼��
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                   
<script language="JavaScript">

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
	fm.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    fm.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
}

//��null���ַ���תΪ��
function nullToEmpty(String)
{
	if ((String == "null") || (String == "undefined"))
	{
		String = "";
	}
	return String;
}

function initInpBox()
{ 
    try
    {                                   
		ClearPagedata();
    }
    catch(ex)
    {
        alert("��LLClaimExemptInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }      
}

function initForm()
{
    try
    {
    	initParam();
        initInpBox();
        initLLClaimExemptGrid();  
        LLClaimExemptGridQuery();
    }
    catch(re)
    {
        alert("LLClaimExemptInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

//������Ϣ���ʼ��
function initLLClaimExemptGrid()
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
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����ͬ����";             //����
      iArray[2][1]="100px";                //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ͬ����";             //����
      iArray[3][1]="100px";                //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������ֺ���";             //����
      iArray[4][1]="100px";                //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���α���";             //����
      iArray[5][1]="50px";                //�п�
      iArray[5][2]=100;                  //�����ֵ
      iArray[5][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="���Ѽƻ�����";             //����
      iArray[6][1]="50px";                //�п�
      iArray[6][2]=100;                  //�����ֵ
      iArray[6][3]=0; 

      iArray[7]=new Array();
      iArray[7][0]="���Ѽƻ�����";             //����
      iArray[7][1]="80px";                //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ͷ��������";             //����
      iArray[8][1]="60px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="Ͷ���˿ͻ�����";             //����
      iArray[9][1]="100px";                //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      LLClaimExemptGrid = new MulLineEnter( "fm" , "LLClaimExemptGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLClaimExemptGrid.mulLineCount = 0;   
      LLClaimExemptGrid.displayTitle = 1;
      LLClaimExemptGrid.locked = 1;
      LLClaimExemptGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLClaimExemptGrid.selBoxEventFuncName = "LLClaimExemptGridClick"; //���RadioBoxʱ��Ӧ�ĺ����� 
      LLClaimExemptGrid.hiddenPlus=1;
      LLClaimExemptGrid.hiddenSubtraction=1;
      LLClaimExemptGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
