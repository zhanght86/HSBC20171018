 <%
//�������ƣ�FIRuleDefQueryInputInit.jsp
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
  }
  catch(ex)
  {
    alert("��FIRuleDefQueryInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��FIRuleDefQueryInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFIRuleDefQueryGrid();
  }
  catch(re)
  {
    alert("��FIRuleDefQueryInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initFIRuleDefQueryGrid()
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
      iArray[2][0]="У��������";    	//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="У���������";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="У�鴦��ʽ";         			//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="У�鷵������";         			//����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      FIRuleDefQueryGrid = new MulLineEnter( "document" , "FIRuleDefQueryGrid" ); 
      FIRuleDefQueryGrid.mulLineCount = 5;   
      FIRuleDefQueryGrid.displayTitle = 1;
      FIRuleDefQueryGrid.canSel=1;
      FIRuleDefQueryGrid.locked = 1;	
	 	 	FIRuleDefQueryGrid.hiddenPlus = 1;
	  	FIRuleDefQueryGrid.hiddenSubtraction = 1;
      FIRuleDefQueryGrid.loadMulLine(iArray);  
      FIRuleDefQueryGrid.detailInfo="������ʾ��ϸ��Ϣ";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
