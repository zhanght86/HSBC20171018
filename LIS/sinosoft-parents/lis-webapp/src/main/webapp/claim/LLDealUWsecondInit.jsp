
<%
	//�ļ�����LLDealUWsecondInit.jsp
	//���ܣ����˽���
	//���ڣ�2004-12-23 16:49:22
	//�����ˣ�zhangxing  ���ģ�yuejw
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
//���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

function initForm()
{
  try
  { 
 	initParam();
 	
  	initLLUnfinishedContGrid();	// δ���κ˱���ɵĺ�ͬ���б�ĳ�ʼ��   	
    initLLContGrid();	// �Ѿ����κ˱���ɵĺ�ͬ���б�ĳ�ʼ��  
    initLLPolGrid();
   
    initQueryLLContGrid();  
	initQueryLLUnfinishedContGrid(); 
	initLWMission();  
    
    if(fm.Flag.value == "N")	//����Ч���͡�����Ч���͡�������ˡ���ťֻ�ɡ����˽��۴����п��ã������Ĳ�ѯ�������ʾ
    {
    	fm.CvalidateButton.disabled = true;
    	fm.NotCvalidateButton.disabled = true;
    	fm.UWConclusionCancelButton.disabled = true;
   		//fm.cancelbutton1.disabled = true;
   		fm.cancelbutton2.disabled = true;
   		fm.cancelbutton3.disabled = true; 
    }
  }
  catch(ex)
  {
    alter("��LLDealUWsecondInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//�������ҳ�洫�ݹ����Ĳ���
function initParam()
{
    document.all('CaseNo').value    = nullToEmpty("<%= tCaseNo %>");
    document.all('InsuredNo').value = nullToEmpty("<%= tInsuredNo %>");
    document.all('Flag').value      = nullToEmpty("<%=tFlag%>");
    document.all('Operator').value = nullToEmpty("<%= tG.Operator %>");       
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

// ����������δ���κ˱���ɵĺ�ͬ���б�ĳ�ʼ�� 
function initLLUnfinishedContGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";              //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";              //�п�
      iArray[0][2]=10;                  //�����ֵ
      iArray[0][3]=0;                   //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="130px";              //�п�
      iArray[1][2]=130;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���κ�";             //����
      iArray[2][1]="80px";              //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ͬ����";           //����
      iArray[3][1]="120px";              //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[4]=new Array();
      iArray[4][0]="Ͷ���˺���";         //����
      iArray[4][1]="80px";               //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ͷ��������";         //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="�˱�״̬";           //����
      iArray[6][1]="60px";               //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[7]=new Array();
      iArray[7][0]="��ر�־";           //����
      iArray[7][1]="60px";               //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[8]=new Array();
      iArray[8][0]="Ͷ���齡����֪��ѯ�ʺ�"; //����
      iArray[8][1]="0px";                //�п�
      iArray[8][2]=200;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[9]=new Array();
      iArray[9][0]="��콡����֪��ѯ�ʺ�"; //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=200;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��Ӧδ��֪���";     //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=200;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[11]=new Array();
      iArray[11][0]="������Ŀǰ����״������";  //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=200;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[12]=new Array();
      iArray[12][0]="�˱�����";           //����
      iArray[12][1]="0px";                //�п�
      iArray[12][2]=200;                  //�����ֵ
      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[13]=new Array();
      iArray[13][0]="�˱����";           //����
      iArray[13][1]="0px";                //�п�
      iArray[13][2]=200;                  //�����ֵ
      iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�˱���";             //����
      iArray[14][1]="80px";                //�п�
      iArray[14][2]=200;                  //�����ֵ
      iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[15]=new Array();
      iArray[15][0]="�����������";       //����
      iArray[15][1]="80px";               //�п�
      iArray[15][2]=200;                  //�����ֵ
      iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    

      LLUnfinishedContGrid = new MulLineEnter( "document" , "LLUnfinishedContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLUnfinishedContGrid.mulLineCount      = 5;   
      LLUnfinishedContGrid.displayTitle      = 1;
      LLUnfinishedContGrid.locked            = 1;
      LLUnfinishedContGrid.canSel            = 1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLUnfinishedContGrid.hiddenPlus        = 1;
      LLUnfinishedContGrid.hiddenSubtraction = 1;
      LLUnfinishedContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


// ���������Ѿ����κ˱���ɵĺ�ͬ���б�ĳ�ʼ�� 
function initLLContGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";               //�п�
      iArray[0][2]=10;                   //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="130px";              //�п�
      iArray[1][2]=130;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���κ�";             //����
      iArray[2][1]="80px";              //�п�
      iArray[2][2]=80;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ͬ����";           //����
      iArray[3][1]="120px";              //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[4]=new Array();
      iArray[4][0]="Ͷ���˺���";         //����
      iArray[4][1]="80px";               //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="Ͷ��������";         //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="�˱�״̬";           //����
      iArray[6][1]="60px";               //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��ر�־";           //����
      iArray[7][1]="60px";               //�п�
      iArray[7][2]=200;                  //�����ֵ
      iArray[7][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ͷ���齡����֪��ѯ�ʺ�";             //����
      iArray[8][1]="0px";                //�п�
      iArray[8][2]=200;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="��콡����֪��ѯ�ʺ�"; //����
      iArray[9][1]="0px";                //�п�
      iArray[9][2]=200;                  //�����ֵ
      iArray[9][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��Ӧδ��֪���";     //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=200;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="������Ŀǰ����״������";             //����
      iArray[11][1]="0px";                //�п�
      iArray[11][2]=200;                  //�����ֵ
      iArray[11][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="�˱�����";           //����
      iArray[12][1]="0px";                //�п�
      iArray[12][2]=200;                  //�����ֵ
      iArray[12][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[13]=new Array();
      iArray[13][0]="�˱����";           //����
      iArray[13][1]="0px";                //�п�
      iArray[13][2]=200;                  //�����ֵ
      iArray[13][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="�˱���";             //����
      iArray[14][1]="80px";               //�п�
      iArray[14][2]=200;                  //�����ֵ
      iArray[14][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="�����������";       //����
      iArray[15][1]="80px";               //�п�
      iArray[15][2]=200;                  //�����ֵ
      iArray[15][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[16]=new Array();
      iArray[16][0]="��Ч��־";           //����
      iArray[16][1]="80px";               //�п�
      iArray[16][2]=200;                  //�����ֵ
      iArray[16][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    

      iArray[17]=new Array();
      iArray[17][0]="�����˿ͻ�����";           //����
      iArray[17][1]="80px";               //�п�
      iArray[17][2]=200;                  //�����ֵ
      iArray[17][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[18]=new Array();
      iArray[18][0]="����������";           //����
      iArray[18][1]="80px";               //�п�
      iArray[18][2]=200;                  //�����ֵ
      iArray[18][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������    
      
      LLContGrid = new MulLineEnter( "document" , "LLContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLContGrid.mulLineCount = 5;   
      LLContGrid.displayTitle = 1;
      LLContGrid.locked = 1;
      LLContGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLContGrid.selBoxEventFuncName = "LLContGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLContGrid.hiddenPlus=1;
      LLContGrid.hiddenSubtraction=1;
      LLContGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}


//����������ʼ��
function initLLPolGrid()
  {                               
      var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";               //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";               //�п�
      iArray[0][2]=10;                   //�����ֵ
      iArray[0][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ⰸ��";             //����
      iArray[1][1]="100px";              //�п�
      iArray[1][2]=100;                  //�����ֵ
      iArray[1][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ͬ��";             //����
      iArray[2][1]="100px";              //�п�
      iArray[2][2]=100;                  //�����ֵ
      iArray[2][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ܵ�Ͷ��������";     //����
      iArray[3][1]="0px";                //�п�
      iArray[3][2]=100;                  //�����ֵ
      iArray[3][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������ֺ���";       //����
      iArray[4][1]="120px";              //�п�
      iArray[4][2]=100;                  //�����ֵ
      iArray[4][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���ֱ���";           //����
      iArray[5][1]="80px";               //�п�
      iArray[5][2]=200;                  //�����ֵ
      iArray[5][3]=0; 

      iArray[6]=new Array();
      iArray[6][0]="��������";           //����
      iArray[6][1]="150px";              //�п�
      iArray[6][2]=200;                  //�����ֵ
      iArray[6][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="�˱�˳���";         //����
      iArray[7][1]="0px";                //�п�
      iArray[7][2]=100;                  //�����ֵ
      iArray[7][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�˱�����";           //����
      iArray[8][1]="0px";                //�п�
      iArray[8][2]=100;                  //�����ֵ
      iArray[8][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[9]=new Array();
      iArray[9][0]="�˱�����";           //����
      iArray[9][1]="80px";               //�п�
      iArray[9][2]=100;                  //�����ֵ
      iArray[9][3]=0;                    //�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[10]=new Array();
      iArray[10][0]="�˱����";           //����
      iArray[10][1]="0px";                //�п�
      iArray[10][2]=100;                  //�����ֵ
      iArray[10][3]=3;                    //�Ƿ���������,1��ʾ����0��ʾ������
      
      LLPolGrid = new MulLineEnter( "document" , "LLPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LLPolGrid.mulLineCount = 5;   
      LLPolGrid.displayTitle = 1;
      LLPolGrid.locked = 1;
      LLPolGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
      LLPolGrid.selBoxEventFuncName = "LLPolGridClick"; //���RadioBoxʱ��Ӧ�ĺ�����
      LLPolGrid.hiddenPlus=1;
      LLPolGrid.hiddenSubtraction=1;
      LLPolGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
