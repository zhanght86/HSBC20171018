//��¼�����ֵ����⴦��
function specDealByRisk(){
	
  //��¼��������������⴦��
  try{
		
	 /*  if(document.all("Mult").value!=null && document.all("Mult").value!="" )
	   {  
	     try
	     { 
	     	
	   //  	var tSql="";
			//tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
			var arrResult=easyExecSql(tSql);
			if(arrResult[0]=="2" ){
	     		//У��¼��ķ����Ƿ�Ϊ����
		        if(!isPosInteger(document.all("Mult").value)){
		        	return false;
		        }
	        }
	        if(document.all("CalRule").value!='3')//������Լ�����ʣ��������������۵����֣����������㣬�򽫽��汣�ѡ�������ա�
	        {
	          document.all("Amnt").value="";
	          document.all("Prem").value="";
	        }
	        
	      
	      }
	      catch(e)
	      {
           document.all("Amnt").value="";
	         document.all("Prem").value="";
	      }
       }
       */
   }
   catch(e)
   {
   }
   

	  //����Ǹ��˳�����
	  if(document.all('RiskCode').value=='112401')
	  {
	      if(document.all("InsuYear").value!=null &&document.all('InsuYear').value!='')
	      {
	          if(document.all('InsuYear').value=='A')
	          {
	              document.all('InsuYear').value='88';
	              document.all('InsuYearFlag').value='A';
	          }
	          else if(document.all('InsuYear').value=='B')
	          {
	              document.all('InsuYear').value=20+Number(document.all('GetYear').value);
	              document.all('InsuYearFlag').value='A';
	          }
	          else
	          {
	              alert("�����ڼ����ѡ��");
	              return false;
	          }

	      }
	      
	      if(document.all("PayEndYear").value!=null &&document.all('PayEndYear').value!='')
	      {
	          if(document.all('PayEndYear').value=='A')
	          {
	              document.all('PayEndYear').value='88';
	              document.all('PayEndYearFlag').value='A';
	          }
	          else if(document.all('PayEndYear').value=='B')
	          {
	              document.all('PayEndYear').value=20+Number(document.all('GetYear').value);
	              document.all('PayEndYearFlag').value='A';
	          }
	          else
	          {
	        	  
	          }

	      }
	      
	      return true;
	  }
	  
	  
	
	//108���ָ����Ա�����ȡ����
	if(document.all('RiskCode').value.substring(0,5)=="00108")
	{
			if (document.all('Sex').value==0)
				document.all('GetYear').value=60
			else
				document.all('GetYear').value=55
	}
	//145��128���ֱ����ڼ��������ڼ�֮��Ĺ�ϵ
	if(document.all('RiskCode').value.substring(0,5)=="00128"|| document.all('RiskCode').value=="00145000")
	{
			//��������������ʱ��,�����ڼ�Ϊ����,����ʱ����������
			if (document.all('GetDutyKind').value=='1'||document.all('GetDutyKind').value=='2')
			{
				document.all('InsuYear').value=1000
			}
			else
				document.all('InsuYear').value=document.all('GetYear').value
	}
	//�����ձ����ڼ䣬�ɷ����޸�����һ��
	//alert(document.all('RiskCode').value);
	if(document.all('RiskCode').value=="00332000"||document.all('RiskCode').value=="00277000"||document.all('RiskCode').value=="00293000"||document.all('RiskCode').value=="00294000"||document.all('RiskCode').value=="00245000" )
	{
			//alert(document.all('MainPolNo').value);
			var tSql="";
			//alert(document.all('MainPolNo').value);
			
  		tSql="select InsuYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql);   	
    	tSql="select PayIntv from lcpol where polno='"+document.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("���ѷ�ʽ"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	//alert("�����ڼ�"+PayEndYear);
    	//alert("�����ڼ�"+InsuYear);
    	document.all('InsuYear').value=InsuYear;
    	document.all('PayEndYear').value=PayEndYear;
    	document.all('PayIntv').value=PayIntv;
    	if (InsuYear==null || PayEndYear==null || InsuYear=='' || PayEndYear=='')
    	{
    		alert("ȡ���ձ����ڼ���߽ɷ�����ʧ�ܣ�");
    	}
    	//alert("ok");
    	return true;
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}
	
	if(document.all('RiskCode').value=="00715000")
	{
			var tSql="select InsuYear,insuyearflag,PayIntv, PayEndYear,PayEndYearFlag,Amnt  from lcpol where polno='"+document.all('MainPolNo').value+"'";
	    	var InsuYear = easyExecSql(tSql);   	
	    	var PayIntv =InsuYear[0][2] ;
			document.all('InsuYear').value=InsuYear[0][0];
	    	document.all('InsuYearFlag').value=InsuYear[0][1];
	    	document.all('PayIntv').value=PayIntv;
	    	document.all('Amnt').value=InsuYear[0][5]
	    	if(PayIntv=='0')
	    	{
	    	document.all('PayEndYear')='1000';	
	    	}
	    	else
	    	{
			document.all('PayEndYear').value=InsuYear[0][3];
	    	document.all('PayEndYearFlag').value=InsuYear[0][4];
	    	}
			return true;
	}
	

	if(document.all('RiskCode').value=="00280000")
	{
			var tSql="";
			//alert(document.all('MainPolNo').value);
  		tSql="select InsuYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql); 
    	//alert("�����ڼ�"+InsuYear);
    	tSql="select PayIntv from lcpol where polno='"+document.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("���ѷ�ʽ"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	
    	document.all('InsuYear').value=InsuYear;
    	document.all('PayEndYear').value=PayEndYear;
    	document.all('PayIntv').value=PayIntv;
    	//alert("�����ڼ�1"+InsuYear); 	 
    	DutyGrid.setRowColData(0,3,document.all('InsuYear').value);
    	//alert(DutyGrid.getRowColData(0,3)+"oo");
    	DutyGrid.setRowColData(0, 5, document.all('PayEndYear').value);
    	DutyGrid.setRowColData(1, 3, document.all('InsuYear').value);
    	DutyGrid.setRowColData(1, 5, document.all('PayEndYear').value);
    	//alert("asdfas"+PayIntv);
    	DutyGrid.setRowColData(0,18,document.all('PayIntv').value);
    	DutyGrid.setRowColData(1,18,document.all('PayIntv').value);
    	//alert(DutyGrid.getRowColData(0,18));
    	if (InsuYear==null||PayEndYear==null||InsuYear==''||PayEndYear=='')
    	{
    		alert("ȡ���ձ����ڼ���߽ɷ�����ʧ�ܣ�");
    	}
    	
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}

	if( document.all('RiskCode').value=="00228000")
	{
			//alert(document.all('MainPolNo').value);
			var tSql="";
			//alert(document.all('MainPolNo').value);
			
  		tSql="select InsuYear,insuyearflag from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql);   	

    	document.all('InsuYear').value=InsuYear[0][0];
    	document.all('InsuYearFlag').value=InsuYear[0][1];
    	if (InsuYear==null || InsuYear=='' )
    	{
    		alert("ȡ���ձ����ڼ�ʧ�ܣ�");
    	}
    	//alert("ok");
    	return true;
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}
	
	//���幤���ս����ʴ���lcpol.StandbyFlag3�ֶΣ������ڼ���
  if(document.all('RiskCode').value=="00348000" || document.all('RiskCode').value=="00349000")
  {
  	document.all('StandbyFlag3').value=document.all('Salary').value;
  }
  //��Խ����գ����¼�뱣��ֹ�ڣ���ô����ݱ���ֹ�ڣ���Ч��(��Ͷ����)����������ڼ�
  if(document.all('RiskCode').value=="00329000" || document.all('RiskCode').value=="00334000" || document.all('RiskCode').value=="00283000" )
  {
  	try
  	{  	
	  	if(document.all('EndDate').value!=null && document.all('EndDate').value!="")
	  	{
	  		document.all('InsuYear').value=dateDiff(document.all('CValiDate').value,document.all('EndDate').value,'D');
	  		document.all('InsuYearFlag').value="D";//D�����족
	  	}
    }
    catch(ex)
    {}
  }
  //
	
	var chkNo=0;
	for (var index=0;index<DutyGrid.mulLineCount;index++){
	  if(DutyGrid.getChkNo(index)==true){
	    chkNo=chkNo+1;	
	  }	
	}
	if(DutyGrid.mulLineCount>0 && chkNo==0){
	  alert("��ѡ�����Σ�");
	  return false;	
	}
	if(document.all('RiskCode').value=="00607000")
	{
			
			var tpayintv=DutyGrid.getRowColData(0,18);
			var tamnt=DutyGrid.getRowColData(0,14);
			if (tpayintv==0)//����
			{
				if(DutyGrid.getChkNo(1)==true)
				{
					alert("��������ѡ���������");
	  			return false;	
				}
			}
			if(DutyGrid.getChkNo(1)==true)
			{
				DutyGrid.setRowColData(1, 14, tamnt);
				DutyGrid.setRowColData(1, 18, tpayintv);
			}
	}
	
	
	
	
	if(DutyGrid.mulLineCount>0){ 
		//���Զ���������У��
		var strChooseDuty="";
		for(i=0;i<=DutyGrid.mulLineCount-1;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 8, document.all('GetYear').value);//�������ڵ�λ
				if(document.all('RiskCode').value=="00280000")
				{
					DutyGrid.setRowColData(i, 3, document.all('InsuYear').value);//��������
					DutyGrid.setRowColData(i, 5, document.all('PayEndYear').value);//��������
				}
				//DutyGrid.setRowColData(i, 3, document.all('InsuYear').value);//��������
				//DutyGrid.setRowColData(i, 4, document.all('InsuYearFlag').value);//�������ڵ�λ
				//if(DutyGrid.getRowColData(i,18)==null||DutyGrid.getRowColData(i,18)==""){ //���治¼��ɷѼ����ȡĬ��ֵ
				 // DutyGrid.setRowColData(i, 18, document.all('PayIntv').value);//�ɷѼ��
				//}
			}
			else
			{
				strChooseDuty=strChooseDuty+"0";
			}
		}
	}
	if(!isSubRisk(document.all('RiskCode').value))
	{
	if(PremGrid.mulLineCount>0){
		chkNo=0;
		for (var index=0;index<PremGrid.mulLineCount;index++){
	  		if(PremGrid.getChkNo(index)==true){
	    		chkNo=chkNo+1;	
	  		}	
		}
		if(chkNo==0){
	  		alert("��ѡ�񽻷��");
	  		return false;	
		}
	}
	}
	
/*if(document.all('RiskCode').value=="208000"||document.all('RiskCode').value=="208001"||document.all('RiskCode').value=="208002"||document.all('RiskCode').value=="208003")
{
			if(PremGrid.mulLineCount>0){
		DutyGrid.setRowColData(i,13,DutyGrid.getRowColData(i,23)*document.all('InsuredPeoples').value);//�ܱ���=���˱���*����
			DutyGrid.setRowColData(i,14,DutyGrid.getRowColData(i,24)*document.all('InsuredPeoples').value);//�ܱ���=���˱���*����
    
      	                }
      	       else
      		{
                        document.all('Prem').value=document.all('Pprem').value*document.all('InsuredPeoples').value;
                        document.all('Amnt').value=document.all('Pamnt').value*document.all('InsuredPeoples').value;
      	}
      		alert(document.all('StandbyFlag1').value);	
      		alert(document.all('Prem').value);	
      		alert(document.all('Amnt').value);
      		alert(document.all('Pprem').value);	
      		alert(document.all('Pamnt').value);
      		alert(document.all('InsuredPeoples').value);	
    
      }*/

        
      	return true;	

}

//�����б�
function initDutyGrid()
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
				iArray[1][0]="���α���";
				iArray[1][1]="0px";
				iArray[1][2]=100;
				iArray[1][3]=3;
				iArray[1][4]="DutyCode";
				iArray[1][15]="RiskCode";
				//iArray[1][16]=fm.RiskCode.value;
				
				iArray[2]=new Array();
				iArray[2][0]="��������";
				iArray[2][1]="0px";
				iArray[2][2]=100;
				iArray[2][3]=3;
				
				iArray[3]=new Array();
				iArray[3][0]="�����ڼ�";
				iArray[3][1]="0px";
				iArray[3][2]=100;
				iArray[3][3]=3;
				
				iArray[4]=new Array();
				iArray[4][0]="�����ڼ䵥λ";
				iArray[4][1]="0px";
				iArray[4][2]=100;
				iArray[4][3]=3;
				
				iArray[5]=new Array();
				iArray[5][0]="�ս���������";
				iArray[5][1]="0px";
				iArray[5][2]=100;
				iArray[5][3]=3;
				
				iArray[6]=new Array();
				iArray[6][0]="�ս��������ڱ�־";
				iArray[6][1]="0px";
				iArray[6][2]=100;
				iArray[6][3]=3;
				
				iArray[7]=new Array();
				iArray[7][0]="��ȡ�������ڱ�־";
				iArray[7][1]="0px";
				iArray[7][2]=100;
				iArray[7][3]=3;
				
				iArray[8]=new Array();
				iArray[8][0]="��ȡ��������";
				iArray[8][1]="0px";
				iArray[8][2]=100;
				iArray[8][3]=3;
				
				iArray[9]=new Array();
				iArray[9][0]="���������ֶ�1";
				iArray[9][1]="0px";
				iArray[9][2]=100;
				iArray[9][3]=3;
				
				iArray[10]=new Array();
				iArray[10][0]="���������ֶ�2";
				iArray[10][1]="0px";
				iArray[10][2]=100;
				iArray[10][3]=3;
				
				iArray[11]=new Array();
				iArray[11][0]="���������ֶ�3";
				iArray[11][1]="0px";
				iArray[11][2]=100;
				iArray[11][3]=3;
				
				iArray[12]=new Array();
				iArray[12][0]="���㷽��";
				iArray[12][1]="0px";
				iArray[12][2]=100;
				iArray[12][3]=3;
				
				iArray[13]=new Array();
				iArray[13][0]="����";
				iArray[13][1]="0px";
				iArray[13][2]=100;
				iArray[13][3]=3;
				
				
				iArray[14]=new Array();
				iArray[14][0]="����";
				iArray[14][1]="0px";
				iArray[14][2]=100;
				iArray[14][3]=3;
				
				
					
				iArray[15]=new Array();
				iArray[15][0]="����";
				iArray[15][1]="0px";
				iArray[15][2]=100;
				iArray[15][3]=3;			
																																					
				iArray[16]=new Array();
				iArray[16][0]="�������";
				iArray[16][1]="0px";
				iArray[16][2]=100;
				iArray[16][3]=3;
				iArray[16][4]="PolCalRule";
				
				iArray[17]=new Array();
				iArray[17][0]="����/�ۿ�";
				iArray[17][1]="0px";
				iArray[17][2]=100;
				iArray[17][3]=3;
				
				iArray[18]=new Array();
				iArray[18][0]="���ѷ�ʽ";
				iArray[18][1]="0px";
				iArray[18][2]=100;
				iArray[18][3]=3;
				//iArray[18][4]="PayIntv"
				
			
				
				iArray[19]=new Array();
				iArray[19][0]="�����";
				iArray[19][1]="0px";
				iArray[19][2]=100;
				iArray[19][3]=3;

 				iArray[20]=new Array();
				iArray[20][0]="�⸶����";
				iArray[20][1]="0px";
				iArray[20][2]=100;
				iArray[20][3]=3;
				       
				iArray[21]=new Array();
				iArray[21][0]="�籣���";
				iArray[21][1]="0px";
				iArray[21][2]=100;
				iArray[21][3]=3;
				       
				iArray[22]=new Array();
				iArray[22][0]="�ⶥ��";
				iArray[22][1]="0px";
				iArray[22][2]=100;
				iArray[22][3]=3;
				
				iArray[23]=new Array();
				iArray[23][0]="����ÿ��";
				iArray[23][1]="0px";
				iArray[23][2]=100;
				iArray[23][3]=3;
				
				iArray[24]=new Array();
				iArray[24][0]="����ÿ��";
				iArray[24][1]="0px";
				iArray[24][2]=100;
				iArray[24][3]=3;
				
				iArray[25]=new Array();
				iArray[25][0]="����ֹ��";
				iArray[25][1]="0px";
				iArray[25][2]=100;
				iArray[25][3]=3;
     

//tongmeng 2011-01-10 modify
 if(document.all('inpNeedDutyGrid').value==1){
 	//  //���δ���
	  iArray[1][1]="60px";            		//�п�
    iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  
	//  //��������
	  iArray[2][1]="120px";            		//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //����
    iArray[13][1]="80px";            		//�п�
    iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //����
        //  iArray[14][1]="80px";            		//�п�
        //  iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
}

        //if(document.all('RiskCode').value=="211672"){
        //  //alert("riskCode:"+document.all('RiskCode').value);
        //  iArray[0][1]="30px";         			//�п�
        //  iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  
	//  //���δ���
	//  iArray[1][1]="60px";            		//�п�
        //  iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  
	//  //��������
	//  iArray[2][1]="120px";            		//�п�
        //  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //����
        //  iArray[13][1]="80px";            		//�п�
        //  iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //����
        //  iArray[14][1]="80px";            		//�п�
        //  iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //��������
        //  //iArray[13][1]="40px";            		//�п�
        //  //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	//  //���ڵ�λ
        //  //iArray[12][1]="40px";            		//�п�
        //  //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
        //  //�������
        //  iArray[16][1]="40px";            		//�п�
        //  iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	//  //����
        //  iArray[17][1]="40px";            		//�п�
        //  iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	//  //���㷽��
        //  iArray[12][1]="40px";            		//�п�
        //  iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
        //
        //}
       /*if(document.all('RiskCode').value=="00108003"||document.all('RiskCode').value=="00607000"||document.all('RiskCode').value=='00108001'||document.all('RiskCode').value=='00108000'){
          //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	        iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
    //���ѷ�ʽ      
        iArray[12][1]="120px";
				iArray[12][3]=2;
				iArray[12][10]="PayIntv";
				iArray[12][11]="0|^0|����^12|�꽻";
				
          
    //����
				iArray[13][1]="80px";
				iArray[13][3]=1;
	  //����
          iArray[15][1]="80px";            		//�п�
          iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //����
          //iArray[14][1]="80px";            		//�п�
          //iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //��������
          //iArray[13][1]="40px";            		//�п�
          //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //���ڵ�λ
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�������
          //iArray[16][1]="40px";            		//�п�
          //iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //����
          //iArray[17][1]="40px";            		//�п�
          //iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  
       }*/
       
        if(document.all('RiskCode').value=="00108003" || document.all('RiskCode').value=="00108002"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
         //���ѷ�ʽ  				
					iArray[18][1]="120px";
					iArray[18][3]=2;
					iArray[18][10]="PayIntv";
				  iArray[18][11]="0|^0|����^12|�꽻";
					
					//����
				iArray[15][1]="80px";
				iArray[15][3]=1;
	  //����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //����
          //iArray[14][1]="80px";            		//�п�
          //iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //��������
          //iArray[13][1]="40px";            		//�п�
          //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //���ڵ�λ
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�������
          //iArray[16][1]="40px";            		//�п�
          //iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //����
          //iArray[17][1]="40px";            		//�п�
          //iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	        
	      }
	      
	       if(document.all('RiskCode').value=="00108000" || document.all('RiskCode').value=="00108001"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
         //���ѷ�ʽ  				
					//iArray[18][1]="120px";
					//iArray[18][3]=2;
					//iArray[18][10]="PayIntv";
				  //iArray[18][11]="0|^0|����^12|�꽻";
					
					//����
				iArray[15][1]="80px";
				iArray[15][3]=1;
	  //����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //����
          //iArray[14][1]="80px";            		//�п�
          //iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //��������
          //iArray[13][1]="40px";            		//�п�
          //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //���ڵ�λ
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�������
          //iArray[16][1]="40px";            		//�п�
          //iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //����
          //iArray[17][1]="40px";            		//�п�
          //iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	        
	      }
	       if(document.all('RiskCode').value=="00328000" ){
         
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      //alert("riskCode:"+document.all('RiskCode').value);
          //��������
	  			iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���ѷ�ʽ  				
					//iArray[18][1]="120px";
					//iArray[18][3]=2;
					//iArray[18][10]="PayIntv";
				  //iArray[18][11]="0|^0|����^12|�꽻";
					 //��������
          iArray[3][1]="40px";            		//�п�
          iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	        //���ڵ�λ
          iArray[4][1]="40px";            		//�п�
          iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[4][10]="InsuYearFlag";          
          iArray[4][11]="0|^D|��^M|��^Y|��";
					//����
				  iArray[15][1]="80px";
				  iArray[15][3]=1;
	        //����
          iArray[13][1]="60px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	        //����
          iArray[14][1]="60px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	       
          //�������
          iArray[16][1]="40px";            		//�п�
          iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[16][10]="CalRule";      
          iArray[16][11]="0|^0|������^2|�������ۿ�^3|Լ������";
      	  //����/�ۿ�
          iArray[17][1]="40px";            		//�п�
          iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�����
				  iArray[19][1]="40px";
				  iArray[19][3]=3;

 				  //�⸶����
				  iArray[20][1]="40px";
				  iArray[20][3]=3;
          
          //����ֹ��
          iArray[25][1]="40px"; 
	        iArray[25][3]=1;      
	      }
	      
	      
	      if(document.all('RiskCode').value=="00330000" ){
         
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //alert("riskCode:"+document.all('RiskCode').value);
          //��������
	  			iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
         //���ѷ�ʽ  				
					//iArray[18][1]="120px";
					//iArray[18][3]=2;
					//iArray[18][10]="PayIntv";
				  //iArray[18][11]="0|^0|����^12|�꽻";
					 //��������
          iArray[3][1]="40px";            		//�п�
          iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	        //���ڵ�λ
          iArray[4][1]="40px";            		//�п�
          iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[4][10]="InsuYearFlag";          
          iArray[4][11]="0|^D|��^M|��^Y|��";
					//����
				//iArray[15][1]="80px";
				//iArray[15][3]=1;
	        //����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	        //����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	       
          //�������
          iArray[16][1]="40px";            		//�п�
          iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[16][10]="CalRule";      
          iArray[16][11]="0|^0|������^2|�������ۿ�^3|Լ������";
      	  //����/�ۿ�
          iArray[17][1]="40px";            		//�п�
          iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //����ֹ��
          iArray[25][1]="40px"; 
	        iArray[25][3]=1;      
	      }
	      
   if(document.all('RiskCode').value=="00607000"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="80px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���ѷ�ʽ  			
					iArray[18][1]="120px";
					iArray[18][3]=2;
					iArray[18][10]="PayIntv";
				  iArray[18][11]="0|^0|����^12|�꽻";
          
	  //����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //��������
          //iArray[13][1]="40px";            		//�п�
          //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //���ڵ�λ
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�������
        //  iArray[16][1]="40px";            		//�п�
       //   iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //����
        //  iArray[17][1]="40px";            		//�п�
        //  iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
        //  iArray[12][1]="40px";            		//�п�
        //  iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	      //  iArray[9][0]="��ְ/����";						
				//	iArray[9][1]="40";
				//	iArray[9][3]=2; 
				//	iArray[9][10]="StandbyFlag1";
				//	iArray[9][11]="0|^0|��ְ^1|����/����";
	      }
	      
	   
	      
	    
	      if(document.all('RiskCode').value=="00280000"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="80px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //�����ڼ�
          iArray[3][0]="�����ڼ�";
					iArray[3][1]="0px";
					iArray[3][3]=3;
			
					
					//�ɷ��ڼ�			
					iArray[5][0]="�ɷ��ڼ�";
					iArray[5][1]="0px";
					iArray[5][3]=3;
	          
          //���ѷ�ʽ  			
					iArray[18][1]="0px";
					iArray[18][3]=3;
					iArray[18][0]="PayIntv";
				  
          
	  			
	  			//����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  			
	  			//����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
        	//����
					iArray[15][1]="80px";
					iArray[15][3]=1;
				
	  //��������
          //iArray[13][1]="40px";            		//�п�
          //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  //���ڵ�λ
          //iArray[12][1]="40px";            		//�п�
          //iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          //�������
        //  iArray[16][1]="40px";            		//�п�
       //   iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //����
        //  iArray[17][1]="40px";            		//�п�
        //  iArray[17][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      	  //���㷽��
        //  iArray[12][1]="40px";            		//�п�
        //  iArray[12][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	      //  iArray[9][0]="��ְ/����";						
				//	iArray[9][1]="40";
				//	iArray[9][3]=2; 
				//	iArray[9][10]="StandbyFlag1";
				//	iArray[9][11]="0|^0|��ְ^1|����/����";
			}
				if(document.all('RiskCode').value=="1120011"||document.all('RiskCode').value=="1220051"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="80px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //����
          iArray[9][0]="����";
					iArray[9][1]="80px";
					iArray[9][3]=2;
					iArray[9][10]="StandbyFlag1";
					iArray[9][11]="0|^1|һ��^2|����^3|����^4|�ĵ�^5|�嵵^6|����";

	  			
	  			//����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  			
	  			//����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
        	
	      }
	      if(document.all('RiskCode').value=="1150041"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="80px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //��������
          iArray[3][1]="40px";            		//�п�
          iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	        //���ڵ�λ
          iArray[4][1]="40px";            		//�п�
          iArray[4][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
          iArray[4][10]="InsuYearFlag";          
          iArray[4][11]="0|^D|��^M|��^Y|��";
          
	  			//����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  			
	  			//����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	      }
	      if(document.all('RiskCode').value=="1250091"){
         //alert("riskCode:"+document.all('RiskCode').value);
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //���δ���
	  			iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  			iArray[2][1]="80px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //�����
          iArray[19][0]="�����";
          iArray[19][1]="80px";            		//�п�
          iArray[19][3]=2;
					iArray[19][10]="Getlimit";
				  iArray[19][11]="0|^50|50Ԫ^100|100Ԫ";
          
	  			//����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  			
	  			//����
          iArray[14][1]="80px";            		//�п�
          iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
	      }
   
      DutyGrid = new MulLineEnter( "document" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      //DutyGrid.checkBoxSel(1);
      
      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        //alert(ex);
      }
}


//�������б�
function initPremGrid()
{
    var iArray = new Array();
      try
      {
      	
      	//alert(tCurrencyflag);
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѱ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ʻ���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[7]=new Array();
      iArray[7][0]="�ʻ��������";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[8]=new Array();
      iArray[8][0]="����ѱ���";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����ռԱ�����ʱ���";         			//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
	 
   	
   		 iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="0px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[10][4]="Currency";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[10][9]="����|code:Currency";
   	
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //��Щ���Ա�����loadMulLineǰ
	 	     PremGrid.mulLineCount = 0;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	      PremGrid.hiddenSubtraction = 1;
           PremGrid.canSel=0;
           PremGrid.hiddenPlus=1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}

/*function insertClick()
{
    if(document.all('StandbyDuty').value!=null&&document.all('StandbyDuty').value!=""){
      var rowCount = DutyGrid.mulLineCount;
      DutyGrid.addOne();
      saveValue(rowCount);
    }
    else{
      alert("��ѡ�������Σ�");
    } 
}
function saveValue(index){
    var mulLineObj = DutyGrid;
    var StandbyDutyCode = document.all('StandbyDuty').value;
    var StandbyDutyCodeName = document.all('StandbyDutyName').value;
    //alert("Code:"+document.all('StandbyDuty').value+"  name:"+document.all('StandbyDutyName').value);
    mulLineObj.setRowColData(index,1,StandbyDutyCode);
    mulLineObj.setRowColData(index,2,StandbyDutyCodeName);
    DutyGrid.checkBoxSel(index+1);
    
}

function setInsuYearToGrid(){
	var strChooseDuty="";
		for(i=0;i<=DutyGrid.mulLineCount-1;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 13, document.all('InsuYear').value);//��������
				DutyGrid.setRowColData(i, 12, document.all('InsuYearFlag').value);//�������ڵ�λ
				//DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//�ɷѷ�ʽ
			}
			else
			{
				strChooseDuty=strChooseDuty+"0";
			}
		}
}
*/


//�����㱣�ѵ����ַ�������Ϊ��
//modify by malong 2005-7-8
function chkMult(){
  var tSql="";
  tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
  var arrResult=easyExecSql(tSql);
  
  if(arrResult[0]=="2" && document.all('inpNeedDutyGrid').value == "1"){
  	for (var index=0;index<DutyGrid.mulLineCount;index++)
  	{
    	//alert(DutyGrid.getRowColData(index,15));
    	if(DutyGrid.getChkNo(index)){
    		if(DutyGrid.getRowColData(index,15)==0)
    		{
      		alert("��������Ϊ��!");
      		return false;	
    		}
    		/*
    		else if (!isInteger(DutyGrid.getRowColData(index,15)))
    		{
    			alert("��������Ϊ����!");
    			return false;
    		} */
    	}    	
    	
  	}
	}
  return true;	
}


//У��CheckBox��108��280����ֻ����ͬʱѡ��һ��������
//create by malong 2005-7-13
function chkDutyNo(){
	if(document.all('RiskCode').value=="00108003"||document.all('RiskCode').value=="00108002"||document.all('RiskCode').value=='00108001'||document.all('RiskCode').value=='00108000' || document.all('RiskCode').value=='00280000'|| document.all('RiskCode').value=='00328000'){
		var DutyNo=0;
		//alert(DutyGrid.getRowColData(0,5));
		for (var index=0;index<DutyGrid.mulLineCount;index++){
		  if(DutyGrid.getChkNo(index)==true){
		    DutyNo=DutyNo+1;	
		  }	
		}
		if(DutyNo>1){
		alert("��������ֻ��ͬʱѡ��һ��������!");
		return false;
		}
		else{
		return true;
		}		
	}
	return true;
	
}		
	//�ж�¼��ķ����Ƿ�Ϊ�������粻�Ƿ���false
	  function  isPosInteger(inputVal)
	  {
		var vArr = inputVal.match(/^[0-9]+$/);
    	if (vArr == null)
    	{
    		alert("¼��ķ�������Ϊ������");
        	return false;
    	}

		if(inputVal<1){
			alert("¼��ķ�������Ϊ1��������");
			return false;
		} 
		return ture;
	  }
/*function showFloatRate(tContPlanCode){
  //alert("calRule:"+document.all('CalRule').value+"plan:"+tContPlanCode+"grpcontno:"+document.all('GrpContNo').value);
  var arrResult=null;
  var tSql="";
  if(tContPlanCode!=null&&tContPlanCode!=""){
      	
  }
  if(document.all('CalRule').value=="1"){
    divFloatRate.style.display="none";
    divFloatRate2.style.display="";	
  }
  else if(document.all('CalRule').value=="2"){
    divFloatRate.style.display="";
    divFloatRate2.style.display="none";
  }
  else {
    divFloatRate.style.display="none";
    divFloatRate2.style.display="none";	
  }	
}*/

/*function getOtherInfo(payEndYear){
  //alert("payIntv:"+document.all('PayIntv').value);
  if(document.all('RiskCode').value=="208300"){
    if(payEndYear==1000){
      document.all('PayEndYearFlag').value='A';
      document.all('GetYear').className='code';
      //document.all('GetYearFlag').className='code';
      //document.all('GetYear').CodeData="0|^1|��ʱ��ȡ^55|55����ȡ^60|60����ȡ";
      document.all('GetYear').value="";
      document.all('GetYearFlag').value="";	
    }
    else {		
      if(payEndYear<22){
        document.all('PayEndYearFlag').value='A';	
      }	
      else {
        document.all('PayEndYearFlag').value='A';	
      }
      document.all('GetYear').className='readonly';
      document.all('GetYearFlag').className='readonly';
      if(payEndYear<22){
      	document.all('GetYear').value=1;
        document.all('GetYearFlag').value='A';	
      }
      else{
        document.all('GetYear').value=payEndYear;
       document.all('GetYearFlag').value='A';
     }
    }
  }
}*/

/*function getGetYearFlag(getYear){
  if(document.all('RiskCode').value=='208300'){
    if(getYear=='1'){
      document.all('GetYearFlag').value='A';	
    }	
    else {
      document.all('GetYearFlag').value='A';	
    }
  }	
}*/

/*function getPayRuleInfo(){
if(document.all('RiskCode').value=="208300"){
 var tSql="select distinct payrulecode,payrulename from lcpayrulefactory where grpcontno='"+document.all('GrpContNo').value+"' and riskcode='"+document.all('RiskCode').value+"'";
  var arr=easyExecSql(tSql);
  if (arr.length>0){
    var tCodeData="0|";
   for (var i=0;i<arr.length;i++){
   	tCodeData=tCodeData+"^"+arr[i][0]+"|"+arr[i][1];
    }
   document.all('PayRuleCode').CodeData=tCodeData;
    
  	}
	}
}*/
