<%
//�������ƣ�EdorAppManuUWInit.jsp
//�����ܣ���ȫ�˹��˱�
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������liurongxiao    ��������   2005-06-17  ����ԭ��/���� ������Լ����ȡ��һ��
%>

<script language="JavaScript">

//ҳ���ʼ��
function initForm()
{
  try
  {
      initParam();
      initInpBox();
      initEdorMainGrid();
      initPolAddGrid();
      initPolRiskGrid();
      initQuery();
      initUWErrGrid();
      //easyQueryClickUW();
  }
  catch (ex)
  {
      alert("�� EdorAppManuUWInit --> InitForm �����з����쳣:��ʼ��������� ");
  }
}

//���մӹ�������ȫ����ҳ�洫�ݹ����Ĳ���
function initParam()
{
   document.all('ContNo').value   		= NullToEmpty("<%= tContNo %>");
   document.all('PrtNo').value   		= NullToEmpty("<%= tPrtNo %>");
   //alert(document.all('PrtNo').value);
   //makeWorkFlow();alert(36);
}

//��ʼ��ҳ��Ԫ��
function initInpBox()
{

  try
  { 
    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('NativePlace').value = '';
    
    document.all('EdorUWState').value = "";
    document.all('EdorUWStateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
  }
  catch (ex)
  {
    alert("�� EdorAppManuUWInit.jsp --> InitInpBox �����з����쳣:��ʼ��������� ");
  }
}

// ���������б�ĳ�ʼ��
function initEdorMainGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                  //�п�
        iArray[0][2]=30;                      //�����ֵ
        iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="������";
        iArray[1][1]="150px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="�ͻ���";
        iArray[2][1]="150px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��������";
        iArray[3][1]="100px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="Ͷ����";
        iArray[4][1]="150px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="140px";
        iArray[5][2]=200;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="������Ч����";
        iArray[6][1]="140px";
        iArray[6][2]=200;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="����״̬";
        iArray[7][1]="60px";
        iArray[7][2]=0;
        iArray[7][3]=0;

        iArray[8] = new Array();
        iArray[8][0] = "�����������";
	      iArray[8][1] = "0px";
	      iArray[8][2] = 0;
	      iArray[8][3] = 3;
	
	      iArray[9] = new Array();
	      iArray[9][0] = "�������������";
	      iArray[9][1] = "0px";
	      iArray[9][2] = 100;
	      iArray[9][3] = 3;
	
	      iArray[10] = new Array();
	      iArray[10][0] = "�������ID";
	      iArray[10][1] = "0px";
	      iArray[10][2] = 0;
	      iArray[10][3] = 3;
	
	      iArray[11] = new Array();
	      iArray[11][0] = "��������ǰ�״̬";
	      iArray[11][1] = "0px";
	      iArray[11][2] = 0;
	      iArray[11][3] = 3;
	      
	      iArray[12] = new Array();
	      iArray[12][0] = "�������˱���";
	      iArray[12][1] = "0px";
	      iArray[12][2] = 0;
	      iArray[12][3] = 3;
	      
	      iArray[13] = new Array();
	      iArray[13][0] = "Ͷ������";
	      iArray[13][1] = "0px";
	      iArray[13][2] = 0;
	      iArray[13][3] = 3;
	      
	      iArray[14] = new Array();
	      iArray[14][0] = "����";
	      iArray[14][1] = "0px";
	      iArray[14][2] = 0;
	      iArray[14][3] = 3;

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //��Щ���Ա�����loadMulLineǰ
        EdorMainGrid.mulLineCount = 3;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.loadMulLine(iArray);
        EdorMainGrid. selBoxEventFuncName = "showDetailInfo";
    }
    catch (ex)
    {
        alert(ex);
    }
}
function initPolAddGrid()
  {
      var iArray = new Array();

      try
      {
        
		      iArray[0]=new Array();
		      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		      iArray[0][1]="30px";            		//�п�
		      iArray[0][2]=30;            			//�����ֵ
		      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[1]=new Array();
		      iArray[1][0]="�ͻ�����";         		//����
		      iArray[1][1]="60px";            		//�п�
		      iArray[1][2]=100;            			//�����ֵ
		      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		
		      iArray[2]=new Array();
		      iArray[2][0]="����";         		//����
		      iArray[2][1]="60px";            		//�п�
		      iArray[2][2]=100;            			//�����ֵ
		      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		   
		     	iArray[3]=new Array();
		      iArray[3][0]="�Ա�";         		//����
		      iArray[3][1]="40px";            		//�п�
		      iArray[3][2]=100;            			//�����ֵ
		      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			  	iArray[3][4]="sex";              	        //�Ƿ����ô���:null||""Ϊ������
		    	iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
		
		  		iArray[4]=new Array();
		      iArray[4][0]="����";         		//����
		      iArray[4][1]="40px";            		//�п�
		      iArray[4][2]=100;            			//�����ֵ
		      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		      iArray[5]=new Array();
		      iArray[5][0]="��Ͷ���˹�ϵ";         		//����
		      iArray[5][1]="60px";            		//�п�
		      iArray[5][2]=200;            			//�����ֵ
		      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			  	iArray[5][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
		    	iArray[5][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
		
		     	iArray[6]=new Array();
		      iArray[6][0]="���������˹�ϵ";         		//����
		      iArray[6][1]="60px";            		//�п�
		      iArray[6][2]=200;            			//�����ֵ
		      iArray[6][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			  	iArray[6][4]="relation";              	        //�Ƿ����ô���:null||""Ϊ������
		    	iArray[6][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
		
		    	iArray[7]=new Array();
		      iArray[7][0]="����";         		//����
		      iArray[7][1]="40px";            		//�п�
		      iArray[7][2]=200;            			//�����ֵ
		      iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
			  	iArray[7][4]="nativeplace";              	        //�Ƿ����ô���:null||""Ϊ������
		    	iArray[7][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��

          PolAddGrid = new MulLineEnter("fm", "PolAddGrid");
          //��Щ���Ա�����loadMulLineǰ
          PolAddGrid.mulLineCount = 3;
          PolAddGrid.displayTitle = 1;
          PolAddGrid.locked = 1;
          PolAddGrid.canSel = 1;
          PolAddGrid.hiddenPlus = 1;
          PolAddGrid.hiddenSubtraction = 1;
          PolAddGrid.loadMulLine(iArray);
          //PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
          PolAddGrid.selBoxEventFuncName = "getInsuredDetail";
      }
      catch (ex)
      {
        alert(ex);
      }
}
function initPolRiskGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
   
     	iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

  		iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����ڼ�";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	
     	iArray[6]=new Array();
      iArray[6][0]="�����ڼ�";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

    	iArray[7]=new Array();
      iArray[7][0]="����Ƶ��";         		//����
      iArray[7][1]="40px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
	


      iArray[8]=new Array();
      iArray[8][0]="��׼����";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="ְҵ�ӷ�";         		//����
      iArray[9][1]="40px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="�����ӷ�";         		//����
      iArray[10][1]="40px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="���ֺ�";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[12]=new Array();
      iArray[12][0]="��ס�ؼӷ�";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="���üӷ�";         		//����
      iArray[13][1]="40px";            		//�п�
      iArray[13][2]=200;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[14]=new Array();
      iArray[14][0]="����״̬";         		//����
      iArray[14][1]="40px";            		//�п�
      iArray[14][2]=200;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[15]=new Array();
      iArray[15][0]="���ں˱�����";         		//����
      iArray[15][1]="60px";            		//�п�
      iArray[15][2]=200;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolRiskGrid.mulLineCount = 3;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      //��Щ����������loadMulLine����
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// �Զ��˱���Ϣ��ʾ
function initUWErrGrid()
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
      iArray[1][0]="Ͷ������";    	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="��������";    	//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ֱ���";    	//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
     
      iArray[4]=new Array();
      iArray[4][0]="��������";    	//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�˱����";    	//����
      iArray[5][1]="300px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�˱�����";    	//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����״̬";    	//����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="Ͷ������";    	//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="��ˮ��";    	//����
      iArray[9][1]="0px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
//��ͬ��,������,���ֱ���,��������,�˱���Ϣ,�޸�ʱ��,�Ƿ�����,
//Ͷ������,��ˮ��,�˱����к�,��ͬ���ֱ��,Ͷ������

      iArray[10]=new Array();
      iArray[10][0]="�˱�˳���";         			//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��ͬ���ֱ��";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[12]=new Array();
      iArray[12][0]="Ͷ������";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=100;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      UWErrGrid.mulLineCount = 0;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.canChk = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.hiddenPlus = 1;
      UWErrGrid.hiddenSubtraction = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
{
}
</script>
