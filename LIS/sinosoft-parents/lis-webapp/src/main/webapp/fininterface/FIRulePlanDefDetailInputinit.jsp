<%
//Creator :���	
//Date :2008-08-18
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
    fm.all('VersionNo').value = VersionNo;
    fm.all('RulePlanID').value = RulePlanID;
  	fm.all('RuleID').value = '';        
  	fm.all('RuleID1').value = '';
    fm.all('RuleIDName').value = '';
    fm.all('Sequence').value = '';    
    fm.all('RuleState').value = '';   
    fm.all('RuleStateName').value = '';  
	if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			fm.all('addbutton').disabled = true;	
			fm.all('updatebutton').disabled = true;			
			fm.all('deletebutton').disabled = true;	
		} 
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
    alert("FIRulePlanDefDetailInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{

  try
  {
    initInpBox();
    initSelBox();
    initFIRulePlanDefDetailInputGrid();
  }
  catch(re)
  {
    alert("FIRulePlanDefDetailInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initFIRulePlanDefDetailInputGrid()
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
      iArray[1][0]="�汾���";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="У��ƻ�����";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="У��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="У��˳��";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[5]=new Array();
      iArray[5][0]="У�����״̬";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      FIRulePlanDefDetailInputGrid = new MulLineEnter( "fm" , "FIRulePlanDefDetailInputGrid" ); 
      FIRulePlanDefDetailInputGrid.mulLineCount = 0;   
      FIRulePlanDefDetailInputGrid.displayTitle = 1;
      FIRulePlanDefDetailInputGrid.canSel=1;
      FIRulePlanDefDetailInputGrid.selBoxEventFuncName = "ReturnData";
      FIRulePlanDefDetailInputGrid.locked = 1;	
	 	 	FIRulePlanDefDetailInputGrid.hiddenPlus = 1;
	  	FIRulePlanDefDetailInputGrid.hiddenSubtraction = 1;
      FIRulePlanDefDetailInputGrid.loadMulLine(iArray);  
      FIRulePlanDefDetailInputGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
