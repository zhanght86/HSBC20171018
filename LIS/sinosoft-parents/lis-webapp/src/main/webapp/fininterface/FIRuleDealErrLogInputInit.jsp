<%
//Creator :���	
//Date :2008-09-19
%>

<!--�û�У����-->


<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">

function initInpBox()
{
  try
  {

    fm.reset();
    document.all('RuleDealBatchNo').value = RuleDealBatchNo;
    document.all('DataSourceBatchNo').value = DataSourceBatchNo;
  }
  catch(ex)
  {
    alert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}


// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("FIRuleDealErrLogInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{

  try
  {
    initInpBox();
    initSelBox();
    initFIRuleDealErrLogGrid();
  }
  catch(re)
  {
    alert("FIRuleDealErrLogInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initFIRuleDealErrLogGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="У�����κ���";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Դ���κ���";         			//����
      iArray[2][1]="0px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="ƾ֤����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="������Ϣ";         			//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="ҵ������ʶ";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="У��ƻ�";         			//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="У�����";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����״̬";         			//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="������ˮ����";         			//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=0;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      

      FIRuleDealErrLogGrid = new MulLineEnter( "fm" , "FIRuleDealErrLogGrid" ); 
      FIRuleDealErrLogGrid.mulLineCount = 10;   
      FIRuleDealErrLogGrid.displayTitle = 1;
      FIRuleDealErrLogGrid.canSel=1;
      FIRuleDealErrLogGrid.selBoxEventFuncName = "ReturnData";
      FIRuleDealErrLogGrid.locked = 1;	
	  FIRuleDealErrLogGrid.hiddenPlus = 1;
	  FIRuleDealErrLogGrid.hiddenSubtraction = 1;
      FIRuleDealErrLogGrid.loadMulLine(iArray);  
      FIRuleDealErrLogGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
