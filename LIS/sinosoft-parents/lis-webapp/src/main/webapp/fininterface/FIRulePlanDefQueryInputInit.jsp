 <%
//�������ƣ�FIRulePlanDefQueryInputInit.jsp
//�����ܣ�У��ƻ�����
//�������ڣ�2008-09-17
//������  �����  
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{
  try
  {     
  	fm.reset();
  	document.all('VersionNo').value = VersionNo;      
	  document.all('RulesPlanID').value = '';        
    document.all('RulesPlanName').value = '';
    document.all('RulePlanState').value = '';        
    document.all('RulePlanStateName').value = '';  
    document.all('CallPointID').value = '';        
    document.all('CallPointIDName').value = '';        
  }
  catch(ex)
  {
    alert("��FIRulePlanDefQueryInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��FIRulePlanDefQueryInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIRulePlanDefQueryGrid();
  }
  catch(re)
  {
    alert("��FIRulePlanDefQueryInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initFIRulePlanDefQueryGrid()
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
      iArray[2][0]="У��ƻ�����";    	//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="У��ƻ�����";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="У��ƻ�״̬";         			//����
      iArray[4][1]="70px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
			iArray[5]=new Array();
      iArray[5][0]="���ýڵ�ID";         			//����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����";         			//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      FIRulePlanDefQueryGrid = new MulLineEnter( "document" , "FIRulePlanDefQueryGrid" ); 
      FIRulePlanDefQueryGrid.mulLineCount = 5;   
      FIRulePlanDefQueryGrid.displayTitle = 1;
      FIRulePlanDefQueryGrid.canSel=1;
      FIRulePlanDefQueryGrid.locked = 1;	
	 	 	FIRulePlanDefQueryGrid.hiddenPlus = 1;
	  	FIRulePlanDefQueryGrid.hiddenSubtraction = 1;
      FIRulePlanDefQueryGrid.loadMulLine(iArray);  
      FIRulePlanDefQueryGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
