<html>
<%//@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<title>Sinosoft</title> 
</head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>  
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>  
<body onload="onload()">
<OBJECT CLASSID = "clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
	<PARAM NAME="LPKPath" VALUE="f16.lpk">
</OBJECT>
<br><br>
<table width="75%" border="0">
	<tr> 
		<td>
			<div align="right"> 
				<input type="button" name="btnPrint"      class= cssButton value=" ��       ӡ " onClick="fnPrint()">
			</div>
		</td>
		<td>&nbsp;</td>
		<td>
			<input type="button" name="btnSaveAs"     class= cssButton value=" ��       Ϊ " onClick="fnSaveAs()">
		</td>
	</tr>
  <tr> 
    <td> <div align="right"> 
        <input type="button" name="btnPreview"    class= cssButton value=" �� ӡ Ԥ �� " onClick="fnPreview()">
      </div></td>
    <td>&nbsp;</td>
    <td><input type="button" name="makeExcle"     class= cssButton value=" �� �� EXCEL "    onClick="MakeExcel(true)"></td>
  </tr>
  <tr> 
    <td> <div align="right"> 
        <input type="button" name="btnPrintSetup" class= cssButton value=" ҳ �� �� �� " onClick="fnPrintSetup()">
      </div></td>
    <td>&nbsp;</td>
    <td><input type="button" name="makeWord"      class= cssButton value=" �� �� Word  "    onClick="MakeWord()"></td>
  </tr>
  <tr> 
    <td> <div align="right"> 
        <input type="button" name="btnClose"      class= cssButton value=" ��       �� " onClick="window.close()">
      </div></td>
    <td>&nbsp;</td>
    <td><input type="button" name="btnPreview2"    class= cssButton value=" �� ʽ �� �� " onClick="fnSetFormate()"></td>
  </tr>
</table>
<br>
	<br>
	<br>
	<table class= "common" width="100%"><tr><td class= "common">
	<OBJECT classid="clsid:B0475003-7740-11D1-BDC3-0020AF9F8E6E"
	  codebase="f16.cab" align="middle" id="F1Book1" style="LEFT: 0px; WIDTH: 800px; TOP: 0px; HEIGHT: 500px" >
	  <PARAM NAME="_ExtentX" VALUE="14367">
	  <PARAM NAME="_ExtentY" VALUE="6271">
	  <PARAM NAME="_0" VALUE="-A@A@*?*?@@@@J@_B@@+m@@@D@@P@@;F@@I@FE@F@@@+nDAF@@+oT@@@*?@@*?*?@@.????????3?.}R@@@@@@@@x@@@@@A@)XBNA@qU+H@@@*??*PA@@@@*F@E@)A2)i!)lqU+H@@@*??*|B@@@@*F@E@)A2)i!)lqU+H@B@*??*PA@@@@*F@E@)A2)i!)lqU+H@B@*??*|B@@@@*F@E@)A2)i!)lqU+H@@@*??*PA@@@@*F@E@)A2)i!)l^kE@S@Ab@*%*?b@c@l@c@c@p@{@b@*%*?b@(1C@m@c@l@c@c@p@^uF@X@Ab@*%*?b@c@l@c@c@p@{@)[@)R@%@$@)]@b@*%*?b@(1C@m@c@l@c@c@p@^wG@Y@Ab@*%*?b@c@l@c@c@p@n@p@p@{@b@*%*?b@(1C@m@c@l@c@c@p@n@p@p@^)AH@^@Ab@*%*?b@c@l@c@c@p@n@p@p@{@)[@)R@%@$@)]@b@*%*?b@(1C@m@c@l@c@c@p@n@p@p@^)Qj@f@Ab@*%*?b@j@`@)_@m@c@l@c@c@p@{@b@*%*?b@j@`@(1C@m@c@l@c@c@p@{@b@*%*?b@j@`@)_@m@b@m@b@{@)@@^^i@Y@@j`clccp{j`(1Cmclccp{j`bmb{)@^!l@n@Ab@*%*?b@j@`@)_@m@c@l@c@c@p@n@p@p@{@b@*%*?b@j@`@(1C@m@c@l@c@c@p@n@p@p@{@b@*%*?b@j@`@)_@m@b@m@b@)?@)?@{@)@@^fk@a@@j`clccpnpp{j`(1Cmclccpnpp{j`bmb)?)?{)@^ZW@U@@(1Cdclccp)_i{(1Ch(1Cdclccp(1Ci^_X@Z@@(1Cdclccp)_i{)[)R%$)](1Ch(1Cdclccp(1Ci^`Y@[@@(1Cdclccpnpp)_i{(1Ch(1Cdclccpnpp(1Ci^eZ@`@@(1Cdclccpnpp)_i{)[)R%$)](1Ch(1Cdclccpnpp(1Ci+mE@@@@@+lC@@@+`X@@@@*5*?`@@@@@@@@@@@+@`@@@@+`XA@@@*5*?,699D">
	  <PARAM NAME="_1" VALUE="-`@@*4@@@@@@@@+@`@@@@+`XA@@@*5*?`@@*4@@@@@@@@+@`@@@@+`XB@@@*5*?`@@*4@@@@@@@@+@`@@@@+`XB@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@*5*?`@@*4@@@@@@@@+@`@@@@+`X@@@@A@`@@@@@@@@@@@+@`@@@@+`XA@k@*5*?`@@*8@@@@@@@@+@`@@@@+`XA@i@*5*?`@@*8@@@@@@@@+@`@@@@+`XA@l@*5*?`@@*8@@@@@@@@+@`@@@@+`XA@j@*5*?`@@*8@@@@@@@@+@`@@@@+`XA@I@*5*?`@@*8@@@@@@@@+@`@@@@+`XD@@@*0*?R@@)H@@@@@@@@+@`@@@@*EJ@@F@)S)h%%4q*LA@)V@JI@FP@F@@@MA@L$@QA@P.<i1-R.MbP?_-A@j@@kA@e@@*?@*A+ADTEB@@f)AUJG@@)P!'%`f)P*C@@*D@@f@@@@@@+h)?g@@@@@@+h)?h@@@@@@*0)?i@@@@@@*0)?*abA@$@A@A@A@F@@@@@@@@@@@+`)?@@@@@@+`)?A@@@@@@@@@@@@@@@@*7X*R@.?)L???*@-TPH-*@?*@??.@@@@@@@@*2P@@@@@@@@@@@@C@.??6.U@U@U@*?@)@F=@@*?@fIO@B@)F@~R*vF@@@@@@@@@@@@@@@@]OC@@@@@@A@@@@@@@*`$@$@*YfIJ@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,830A">
	  <PARAM NAME="_2" VALUE="-@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,0000">
	  <PARAM NAME="_3" VALUE="-@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,0000">
	  <PARAM NAME="_4" VALUE="-@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B@@@C@@@I@)l4@@@@@@F@,B22C">
	  <PARAM NAME="_count" VALUE="5">
	  <PARAM NAME="_ver" VALUE="2">
	</OBJECT></td></tr>
</table>
	
	

<form name=fmDownLoad action="">
</form>
<script>
    //--------------------------------���幫��Ԫ������ʼ---------------------------------------
    //   
    //GridName�����봫����Ԫ��MulLine
    //turnPageName�����봫����Ԫ��EasyQuery
    //ͨ����ȡwindow.showModalDialog��dialogArguments
    //�õ������ڵľ����������ķ���  update by guoxiang
    //var MulLine = eval("window.opener." + GridName);             //MulLine��־
    //var TurnPage=eval("window.opener." + turnPageName);         //EasyQuery��־
    
    var Flag=<%=request.getParameter("Flag")%>;                  //��ӡ����
    var GridName = "<%=request.getParameter("GridName")%>";
    var turnPageName="<%=request.getParameter("turnPageName")%>";
    var win=dialogArguments;
    var MulLine = eval("win." + GridName);  
    var TurnPage = eval("win." + turnPageName);
    var AllRecordCount=TurnPage.queryAllRecordCount;
    
    var showInfo;
    var f1RowsStartPosition=1;                                 //f1book���п�ʼλ��
	var f1ColStartPosition=1;                                  //f1book���п�ʼλ��   
    var ArrayRow=1;                                            //һά����������չ��ʽ
    var ArrayCol=0;                                            //һγ���������չ��ʽ
    var f1ToMutilineHight=1;                                   //��ҳ�涥�˵�mutilineͷ���ľ���
    var f1ToEasyQueryHight=2;                                  //��ҳ�涥�˵�������ľ���
    var f1ToEasyQueryWidth=2;                                  //��ҳ����˵�������ľ���
    
    
    //-------------------------------���幫��Ԫ���������---------------------------------------
    
    //-------------------------------������չԪ������ʼ---------------------------------------
    
    //--------------����easyQueryPrintEx()��Ԫ��---------------

    var PrintArray;                                             //��������
    var ArrayName;                                               //��������ľ�� 
    var ArrayLengh;                                             //�������鳤��               
    var ReportFootLength;                                       //��β,�ϼ��еĳ��������̶�  
    var ReportHeadLength;                                       //��ͷ�ĳ��������̶�         
    <%
    if(request.getParameter("PrintArray")!=null)
    {
    %>
    ArrayName="<%=request.getParameter("PrintArray")%>";
    //PrintArray=eval("window.opener."+ArrayName);
    PrintArray=eval("win."+ArrayName);
    ArrayLengh=getArrayRowCount(PrintArray);                   //�������鳤��
    ReportSUMLength=1;                                         //�ϼ��еĳ��������̶�  
    ReportFootLength=2;                                        //��β,�ϼ��еĳ��������̶�  
    ReportHeadLength=ArrayLengh-ReportFootLength;              //��ͷ�ĳ��������̶�  
    <%
    }
    %>
    //---------����easyQueryPrintShowCodeName()��Ԫ��------------
    var ShowCodeName;
 
    <%
    if(request.getParameter("ShowCodename")!=null)
    {
    %>
    ShowCodeName="<%=request.getParameter("ShowCodename")%>";
    <%
    }
    %>
    //-------------------------------������չԪ���������---------------------------------------
    
    
    
    function onload()
	{	  
	    //��ʼ��
	    
	  
	    var intPageWidth=screen.availWidth;
		var intPageHeight=screen.availHeight;
	    
	    window.resizeTo(intPageWidth,intPageHeight);
		window.screentop=-1;
		window.screenleft=-1;
		window.focus();
		
		
		
		
    
		document.all.F1Book1.ShowColHeading  =false;
        document.all.F1Book1.ShowFormulas=false;

		document.all.F1Book1.ShowEditBar =false;
        document.all.F1Book1.ShowColHeading=false; 
        document.all.F1Book1.ShowRowHeading=false;
        document.all.F1Book1.ShowGridLines=false;
        document.all.F1Book1.ShowTabs =0 ;
        document.all.F1Book1.DoSafeEvents  = true; 



      
        var arrHeader=new Array();
        var arrOrder=new Array();
		var arrTableBody=new Array();
	    var arrRow=new Array();
	
		//Header
	    //ͷ����Ŀ��
	  
������  for (var i=0; i<MulLine.arraySave.length; i++) {     
         //document.all.F1Book1.ColWidth(i+1)=window.opener.replace(MulLine.arraySave[i][1],"px","")*35;
          
          document.all.F1Book1.ColWidth(i+1)=win.replace(MulLine.arraySave[i][1],"px","")*35;
        }
	
	    
	    //print ��ӡMultiLine��ǰҳ
	    if(Flag==0){                             
            //��ӡMultiLine
            if(PrintArray==null){   
	          //��ͷ����
	          arrHeader=getMultiLineHead();
	          f1Array(arrHeader,f1ToMutilineHight,f1ColStartPosition,ArrayCol);
              //�������
              arrOrder=getMultiLineOrderNo();
		      f1Array(arrOrder,f1ToEasyQueryHight,f1RowsStartPosition,ArrayRow); 
		      if(ShowCodeName==null){ //����TurnPage.arrDataCacheSet�ķ�ʽ
		      
		        //������
                arrTableBody=TurnPage.getData(TurnPage.arrDataCacheSet, TurnPage.pageIndex*TurnPage.pageLineNum, TurnPage.pageLineNum);
        ����    f1ArrayGroup(arrTableBody,f1ToEasyQueryHight,f1ToEasyQueryWidth);
              }
              if(ShowCodeName!=null){ //����MulLine.getRowData�ķ�ʽ
                 if(arrOrder.length<=TurnPage.pageLineNum){                
                   for(var RowNo=1;RowNo<=arrOrder.length;RowNo++ ){
                    arrRow=MulLine.getRowData(RowNo-1);
                    f1Array(arrRow,f1ToEasyQueryHight++,f1ToEasyQueryWidth,ArrayCol);
                   }  
                 }else{
                   alert("����ҳ�泤��");
                 }
              }
            
            
            }
            //��ӡ�嵥����
            if(PrintArray!=null){
              
              //��ͷ����
              var HeadArray=getReportHeadArray(PrintArray);
              //����ͷ����
              f1ArrayGroup(HeadArray,f1RowsStartPosition,f1ColStartPosition);
              //��ͷ����
              arrHeader=getMultiLineHead();
              //�����ͷ����
	          f1Array(arrHeader,ReportHeadLength+f1ToMutilineHight,f1ColStartPosition,ArrayCol);
	          //�������
              arrOrder=getMultiLineOrderNo();
              //����������
		      f1Array(arrOrder,ReportHeadLength+f1ToEasyQueryHight,f1RowsStartPosition,ArrayRow); 
              //��������
              arrTableBody=TurnPage.getData(TurnPage.arrDataCacheSet, TurnPage.pageIndex*TurnPage.pageLineNum, TurnPage.pageLineNum); //������
        ����  //����������
              f1ArrayGroup(arrTableBody,ReportHeadLength+f1ToEasyQueryHight,f1ToEasyQueryWidth);
              //����βд���ָ���еĺϼ� 
              f1Sum(arrTableBody,PrintArray);
            }
	
		
		}
		if(Flag==1){ //print ��ӡ����
		    
		  if(PrintArray==null){   
              //��ͷ����
	          arrHeader=getMultiLineHead();
	          f1Array(arrHeader,f1ToMutilineHight,f1ColStartPosition,ArrayCol);
              //�������
              arrOrder=getEasyQueryOrderNO();
              //�������д��F1
		      f1Array(arrOrder,f1ToEasyQueryHight,f1RowsStartPosition,ArrayRow); 
		      
		      if(ShowCodeName==null){ //����TurnPage.arrDataCacheSet�ķ�ʽ
		        //������
                arrTableBody=TurnPage.arrDataCacheSet;
                //������д��F1
                f1ArrayGroup(arrTableBody,f1ToEasyQueryHight,f1ToEasyQueryWidth);
              }
              
              if(ShowCodeName!=null){ //����MulLine.getRowData�ķ�ʽ
                 if(arrOrder.length<=TurnPage.pageLineNum){                
                   for(var RowNo=1;RowNo<=arrOrder.length;RowNo++ ){
                    arrRow=MulLine.getRowData(RowNo-1);
                    f1Array(arrRow,f1ToEasyQueryHight++,f1ToEasyQueryWidth,ArrayCol);
                   }  
                 }else{
                   alert("����ҳ�泤��");
                 }
              }
          
          
          }  
          if(PrintArray!=null){
             
              //��ͷ����
              var HeadArray=getReportHeadArray(PrintArray);
              //����ͷ����
              f1ArrayGroup(HeadArray,f1RowsStartPosition,f1ColStartPosition);
              //��ͷ����
              arrHeader=getMultiLineHead();
              //�����ͷ����
	          f1Array(arrHeader,ReportHeadLength+f1ToMutilineHight,f1ColStartPosition,ArrayCol);
	          //�������
              arrOrder=getEasyQueryOrderNO();
              //����������
		      f1Array(arrOrder,ReportHeadLength+f1ToEasyQueryHight,f1RowsStartPosition,ArrayRow); 
              //��������
              arrTableBody=TurnPage.arrDataCacheSet;
              //����������
              f1ArrayGroup(arrTableBody,ReportHeadLength+f1ToEasyQueryHight,f1ToEasyQueryWidth);
              //����βд���ָ���еĺϼ� 
              f1Sum(arrTableBody,PrintArray);
          }
		}
		if (Flag==2)
		{
		  
		  //��ͷ����
	      arrHeader=getMultiLineHead();
	      //����ͷ����
	      f1Array(arrHeader,f1ToMutilineHight,f1ColStartPosition,ArrayCol);
		
	      arrOrder=getEasyQueryALLOrderNO();
		
		  //�������
	      if(arrOrder.length==0){
		     arrOrder=getEasyQueryOrderNO();
		
		  }
		  
	      f1Array(arrOrder,f1ToEasyQueryHight,f1ColStartPosition,ArrayRow);
		 
		 
		  //����������
		  allDatePrint(TurnPage,2,2);

		
		}
		
	    
  
      
	}
   
   //
   //��ȡEasyQuery���������
   //
   function getEasyQueryOrderNO(){
     var arrOrderNo = new Array();
     for (var i=0; i<TurnPage.arrDataCacheSet.length; i++) {
        arrOrderNo.push(i + 1);
     }
     return arrOrderNo;
    }
   
   function getEasyQueryALLOrderNO()
   {
   	
   	
   	var arrOrderNo = new Array();
     for (var i=0; i<TurnPage.queryAllRecordCount; i++) {
        arrOrderNo.push(i + 1);
        
     }
     return arrOrderNo;
   }
    //
    //��ȡMultiLine��ͷ������
    //
    function getMultiLineHead() {
 	
     var arrHead = new Array();
     
     for (var i=0; i<MulLine.arraySave.length; i++) {     
       arrHead.push(MulLine.arraySave[i][0]);
     }
     return arrHead;
   }
   
   //
   //��ȡMultiLine��ͷ���������
   //
   function getMultiLineOrderNo() {
     var arrOrderNo = new Array();
     for (var i=0; i<MulLine.mulLineCount; i++) {
      arrOrderNo.push(MulLine.recordNo + i + 1);
     }
  
     return arrOrderNo;
   }
	//
	//����һά����
	//
	function f1Array(nArr,nRow,nCol,nFlag){
	    for (var k=0; k<nArr.length; k++) {
          if(nFlag=='0') //����
          { 
            document.all.F1Book1.TextRC(nRow,nCol+k)=nArr[k]; 
          }
          if(nFlag=='1') //����
          {
            document.all.F1Book1.TextRC(nRow+k,nCol)=nArr[k]; 
          }         
        }
	}


	
	
	
	//
	//���ܶ�ά����
	//
	function f1ArrayGroup(nArr,nRow,nCol){
	    var i,j;
        for( i = 0; i < nArr.length; i++ ){
	      for(j = 0; j < nArr[0].length; j++){	         
	        document.all.F1Book1.TextRC(i+nRow,j+nCol)=nArr[i][j];    
          }
        }
	}
	
    //
	//����������βд���ָ���еĺϼ�+��β
	//
	function f1Sum(nArr,mArr){
	    var i,j;
	    try{
	      for(j = 0; j < mArr.length; j++){	         
	        if(j==0){
	           document.all.F1Book1.TextRC(nArr.length+ArrayLengh,f1RowsStartPosition)="�ϼ�";     
	        }
	        else if(mArr[j][0]=='1'){
	          var sumValue=0;
	          for( i = 0; i < nArr.length; i++ ){
	            sumValue=sumValue+nArr[i][j-1]*1;  
	          }
             document.all.F1Book1.TextRC(nArr.length+ArrayLengh,j+f1RowsStartPosition)=sumValue;
            }

            document.all.F1Book1.TextRC(nArr.length+ArrayLengh+ReportSUMLength,j+f1RowsStartPosition)=mArr[j][ReportSUMLength];
           }  
	    
	     }catch(err){
                // alert("�����쳣���������");
��������        // alert("��������: " + err.name);
��������         //alert("������Ϣ:" + err.message);
         }   
	    
	} 
	
	//
	//���ܴ�������ĳ���
	//
	function getArrayRowCount(nArr){
	    var ArrayRowCount;
	    try{
	       ArrayRowCount=nArr[0].length
	     }catch(err){
                // alert("�����쳣���������");
��������        // alert("��������: " + err.name);
��������         //alert("������Ϣ:" + err.message);
         }   
	    return ArrayRowCount;
	} 
    //
	//���ܴ���������嵥����β����
	//
   function getReportHeadArray(nArr)
   {
   	 var reportHeadArray = new Array();
   	
       for (var i=ReportFootLength; i<nArr[0].length; i++) {
         reportHeadArray[i-ReportFootLength]= new Array();
         for(j = 0; j < nArr.length; j++){	         
	        reportHeadArray[i-ReportFootLength].push(nArr[j][i]);    
         }
       }
     return reportHeadArray;
   }
    
	//
	//��֤����  
	//
	function f1confirm()    
	{
	    //��û�а�װformula one,��ִ��
		if(undefined==document.all.F1Book1.ShowEditBar)
	    {
	        window.location = "F1setup.jsp";
	    }
	}
	function allDatePrint(nTurnPage,nRow,nCol)
	{
	    
	    var showStr="���ڴ�ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
         
	    
	    
	    var allCount=nTurnPage.queryAllRecordCount;
	    var currCount=nTurnPage.arrDataCacheSet.length;
	    var pastCount=currCount;
	    var startRowPosition=nRow;
	    var startColPosition=nCol;
	    

       
       
        var arrTableBody=nTurnPage.arrDataCacheSet;
	    f1ArrayGroup(arrTableBody,startRowPosition,startColPosition);	   
	    startRowPosition=startRowPosition+currCount;
	    

	    while (pastCount<allCount)
	    {
	    	
	      var intStart=pastCount+1;
	      nTurnPage.strQueryResult = easyQueryVer3(nTurnPage.strQuerySql, nTurnPage.synchronization, nTurnPage.useCache, intStart);
		  if (!nTurnPage.strQueryResult)
		  {
			break;
		  }
          nTurnPage.arrDataCacheSet = decodeEasyQueryResult(nTurnPage.strQueryResult, 0, 0, nTurnPage);
          arrTableBody=nTurnPage.arrDataCacheSet;                
          currCount=nTurnPage.arrDataCacheSet.length;
          pastCount=pastCount+currCount;               
          f1ArrayGroup(arrTableBody,startRowPosition,startColPosition);    
          startRowPosition=startRowPosition+currCount;                        
               
       }
       showInfo.close();     
    
	 

	
	
	}
	//
	//��ӡ����
	//
	function fnPrint()     
	{
	    
	   
	    var bShowPrintDlg=true;
	    var bPrintWorkbook=true;
	    try{
	       document.all.F1Book1.FilePrintEx(bShowPrintDlg, bPrintWorkbook);
        }
        catch(err)
        {
          //alert("�����쳣���������");
��������  //alert("��������: " + err.name);
��������  //alert("������Ϣ--��ӡ����������:" + err.message);
        }   
    }
	
	//��ӡ���ú���
	function fnPrintSetup()
	{
		try{
		   F1Book1.FilePageSetupDlgEx(2147483647);
		  
		}
        catch(err)
        {
          //alert("�����쳣���������");
��������  //alert("��������: " + err.name);
��������  //alert("������Ϣ--��ӡ����������:" + err.message);
        }   
	}
	
	
	
	//��ʽ���ú���
	function fnSetFormate(){
	 try{
	    document.all.F1Book1.FormatCellsDlg(8+4+2+1); 
     }
      catch(err)
      {
          //alert("�����쳣���������");
��������  //alert("��������: " + err.name);
�������� //alert("������Ϣ:" + err.message);
      }  
	
	}
	//Ԥ������
	function fnPreview()
	{
		try{
		   F1Book1.FilePrintPreview();
		}
        catch(err)
        {
          //alert("�����쳣���������");
��������  //alert("��������: " + err.name);
��������  //alert("������Ϣ--��ӡ����������:" + err.message);
        }   
	}
	
	//���溯��
	function fnSaveAs()
	{
	
	  //document.all.F1Book1.FileName="sinosoft.xls";
	  try{
	     var pFileInfo = new ActiveXObject("TTF16.F1FileSpec");
         pFileInfo.Name = F1Book1.Title;
         pFileInfo.Type = 11;                
      }catch(err){
        alert("�����쳣���������");
��������alert("��������: " + err.name);
��������alert("������Ϣ: " + err.message);
      }
       try{ 
       F1Book1.SaveFileDlgEx("sinosoft", pFileInfo);
      }catch(err){
        //alert("�����쳣���������");
��������//alert("��������: " + err.name);
��������//alert("������Ϣ: " + err.message);

      }
      try{
        F1Book1.WriteEx(pFileInfo.Name, pFileInfo.Type);
      }catch(err){
        //alert("�����쳣���������");
��������//alert("��������: " + err.name);
��������//alert("������Ϣ: " + err.message);

      }
	}
	
		

	

	
	
	//���뺯�� ADD AT 2004-10-13 9:53
	
    function MakeExcel(ProceVisible)
	{

       try {
          var xls    = new ActiveXObject ( "Excel.Application" );
       }
       catch(e) {
          alert( "Ҫ�������ݵ�Excel�������밲װExcel���ӱ�������ͬʱ����IE������İ�ȫ��������ʹ�á�ActiveX �ؼ�������");
          return "";
       }
       if (typeof(ProceVisible)=="undefined"){
          xls.visible = true;
       }
       else{
          if (ProceVisible=="true"){
             xls.visible = true;
          }
          else
          {
            xls.visible = false;
           }
       }
      var xlBook = xls.Workbooks.Add;
      var xlsheet = xlBook.Worksheets(1);
      xls.Cells.Select;
      xls.Selection.NumberFormatLocal = "@";
       
      //�õ�col�ĳ���
	  arrHeader=getMultiLineHead();
	  var colLen=arrHeader.length;
      
      
      //�õ��еĳ���

      var rowlen=AllRecordCount*1+0.0;
      if(rowlen==0){
         arrOrder=getEasyQueryOrderNO();
         rowlen=arrOrder.length;
      }
      
      
      
      //�õ�����f1������
      for (var  i=0;i<rowlen+1 ;i++){
        for (var j=0;j<colLen ;j++){
       
          xlsheet.Cells(i+1,j+1).Value=document.all.F1Book1.TextRC(i+1,j+1);
       }
      }
      var strFileName="";
      
      strFileName=prompt("��������Ҫ�����·�����ļ�����*.xls��","c:/sinosoft.xls") ;
      try{
        xlBook.SaveAs(strFileName);
      }
      catch(e) {
      
      }
      xlBook.Close (savechanges=false);
      xls.visible = false;
      xls.Quit();
      xls=null;
      
      
      //����excel���̣��˳����
      //window.setInterval("Cleanup();",1);
 }

//add guoxiang 2004-10-22 15:08
     function MakeWord(){

        WordApp = new ActiveXObject('Word.Application');
        WordApp.Application.Visible = true;
        
        
        var mydoc=WordApp.Documents.Add('',0,0);

        myRange =mydoc.Range(0,0);

        //�õ�col�ĳ���
	    arrHeader=getMultiLineHead();
	    var colLen=arrHeader.length;
        
        //�õ��еĳ���

       var rowlen=AllRecordCount*1+0.0;
      if(rowlen==0){
         arrOrder=getEasyQueryOrderNO();
         rowlen=arrOrder.length;
      }
        var WordTable= mydoc.Tables.Add(myRange,rowlen+1,colLen);
 
       
        //�õ�����f1������
        for (var  i=0;i<rowlen+1 ;i++){
          for (var j=0;j<colLen ;j++){
      
          WordTable.Cell(i+1,j+1).Range.InsertAfter(document.all.F1Book1.TextRC(i+1,j+1));
         }
        }
        WordTable.Borders.InsideLineStyle = 1 ;  
        WordTable.Borders.OutsideLineStyle = 1;
        

     }
</script>
</body>
</html>
