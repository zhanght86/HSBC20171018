<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<!--
@author:lk
@date:20080904 
-->
<%
    GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
    String action = request.getParameter("action");
    String flowId = request.getParameter("flowId");
    //zhaojiawei
    String Version = request.getParameter("Version");
  
%>
<HTML xmlns:v = "urn:schemas-microsoft-com:vml">
<HEAD>
   <TITLE>WorkFlow 1.0</TITLE> 
   <META http-equiv=Content-Type content="text/html; charset=gb2312">
   <LINK href="workflow.css" type=text/css rel=stylesheet>   
   <STYLE>v\:* {BEHAVIOR: url(#default#VML)}</STYLE>
<style type="text/css">
body{overflow: hidden;}
#divTip {
	display: block;
	position: absolute;
	height: 100%;
	width: 100%;
	padding-top: 10%;
	z-index: 1001;
}
</style>   
   <script>
       var ManageCom ="<%=tG.ManageCom%>";
       var Operator="<%=tG.Operator%>";
       var action="<%=action%>";
       var flowId="<%=flowId%>";
       var Version="<%=Version%>";
   </script>
   <SCRIPT language=VBScript>
    '判断是否取整的数是否小余 0 ，为0则 加1
   
   		Function FormatNumberDemo(MyNumber) 
   			if (isNaN(parseInt(FormatNumber(MyNumber,0)))) then
   				FormatNumberDemo = FormatNumber(MyNumber+1,0) ' 把 MySecant 格式化为不带小数点的数。
   				exit Function
   			end if
   			FormatNumberDemo = FormatNumber(MyNumber,0) ' 把 MySecant 格式化为不带小数点的数。
   		End Function    
    
   </SCRIPT>  
   
   <SCRIPT language=javascript src="workflow.js"></SCRIPT>
   <SCRIPT language=javascript src="other.js"></SCRIPT>  

   <script language=jscript src="active.js"></script>
   <script language=jscript src="processtrans.js"></script>
   <script language=jscript src="data.js"></script>
   <script language=jscript src="comp/function.js"></script>
   <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
   <META content="MSHTML 6.00.2900.3243" name=GENERATOR>
</HEAD>

<BODY onselectstart="javascript:return false;"  onload="javascript:initFlowXML();ChangeImgColor(6);initPrpcessTrans();initNode();iniWindow()">

<TABLE height="100%" width="100%" border=0  >
<!--  background-color: #DAE9F8; -->
     <TR  height="40" width="100%"  style="background: url(../common/images/bg_h2.jpg) repeat center;">
     <TD>
       <TABLE cellpadding="0" cellspacing="0" >
          <TR>          
            				<td><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>

							<td><img align="absmiddle" class="button" onclick="javascript:if(document.all.chart.childNodes.length>0&&!confirm('确实要新建吗?')) {ChangeImgColor(6) ;return} ;ClearFlow();NewFlow();recoverInit(); ChangeImgColor(6)"  onMouseDown=buttonDown(img9) onMouseOut=buttonOut(img9) onMouseOver=buttonOver(img9) id=img9 alt="" border="1"  hspace="0" src="images/flow11.gif" title="新建" vspace="0" height="28"  width="56"></td>
						
							<td><img align="absmiddle" class="button" onclick="javascript:if(document.all.chart.childNodes.length>0&&!confirm('确实要保存吗?')) {ChangeImgColor(6) ;return} ;SaveToDB(); ChangeImgColor(6)" onMouseDown=buttonDown(img11) onMouseOut=buttonOut(img11) onMouseOver=buttonOver(img11) id=img11 alt="" border="1" height="28" hspace="0" src="images/flow13.gif" title="保存到数据库" vspace="0" width="56" disabled></td>

					<!--
							<td><img align="absmiddle" class="button" onclick="javascript:if(document.all.chart.childNodes.length>0&&!confirm('确实要删除吗?')) {ChangeImgColor(6) ;return} ;DelFlow();ChangeImgColor(6)"  onMouseDown=buttonDown(img12) onMouseOut=buttonOut(img12) onMouseOver=buttonOver(img12) id=img12 alt="" border="1" height="28" hspace="0" src="images/flow14.gif" title="从数据库删除" vspace="0" width="56"></td><!--原程序就删除掉的->
                    -->
							<td><img align="absmiddle" class="button" onclick="javascript:if(document.all.chart.childNodes.length>0&&!confirm('确实要清空吗?')) {ChangeImgColor(6) ;return} ;ClearFlow();recoverInit(); ChangeImgColor(6)"  onMouseDown=buttonDown(img10) onMouseOut=buttonOut(img10) onMouseOver=buttonOver(img10) id=img10 alt="" border="1" height="28" hspace="0" src="images/flow12.gif" title="清空" vspace="0" width="56" ></td>

							<td><img align="absmiddle" class="button" onclick="javascript:SaveToXML(); ChangeImgColor(6)" onMouseDown=buttonDown(img7) onMouseOut=buttonOut(img7) onMouseOver=buttonOver(img7) id=img7 alt="" border="1" height="28" hspace="0" src="images/flow9.gif" title="保存到文件" vspace="0" width="56" disabled></td>

							<td><img align="absmiddle" class="button" onclick="javascript:LoadFromXML() ;ChangeImgColor(6)"  onMouseDown=buttonDown(img8) onMouseOut=buttonOut(img8) onMouseOver=buttonOver(img8) id=img8 alt="" border="1" height="28" hspace="0" src="images/flow10.gif" title="打开从文件" vspace="0" width="56"></td>

							<td><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>
					
							<td><img align="absmiddle class="button" onclick="javascript:AddFlow(6); ChangeImgColor(6)"  onMouseDown=buttonDown(img6) onMouseOut=buttonOut(img6) onMouseOver=buttonOver(img6) id=img6 alt="" border="1" height="28" hspace="0" src="images/flow6.gif" title="移动" vspace="0" width="56" ></td>		
												                                                                                                                                                                                       
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(1); ChangeImgColor(6);getChartXML()" onMouseDown=buttonDown(img1) onMouseOut=buttonOut(img1) onMouseOver=buttonOver(img1) id=img1 alt="" border="1" height="28" hspace="0" src="images/flow1.gif" title="开始" vspace="0" width="56" disabled></td>
						                                                                                                                                                                                       
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(2); ChangeImgColor(6)" onMouseDown=buttonDown(img2) onMouseOut=buttonOut(img2) onMouseOver=buttonOver(img2) id=img2 alt="" border="1" height="28" hspace="0" src="images/flow2.gif" title="过程" vspace="0" width="56" disabled></td>
							<!-- 聚合节点  jiyongtian 					
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(7); ChangeImgColor(6)" onMouseDown=buttonDown(img24) onMouseOut=buttonOut(img24) onMouseOver=buttonOver(img24) id=img24 alt="" border="1" height="28" hspace="0" src="images/flow24.gif" title="聚合节点" vspace="0" width="56" disabled></td>
	                         -->		                                                                                                                                                                        
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(3); ChangeImgColor(6);getChartXML()" onMouseDown=buttonDown(img3) onMouseOut=buttonOut(img3) onMouseOver=buttonOver(img3) id=img3 alt="" border="1" height="28" hspace="0" src="images/flow3.gif" title="决策" vspace="0" width="56" disabled style="display:none"></td>
                                                                                                                                                                                                   
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(4); ChangeImgColor(6);getChartXML()" onMouseDown=buttonDown(img4) onMouseOut=buttonOut(img4) onMouseOver=buttonOver(img4) id=img4 alt="" border="1" height="28" hspace="0" src="images/flow4.gif" title="结束" vspace="0" width="56" disabled></td>
                                                                                                                                                                                                   
							<td><img align="absmiddle" class="button" onclick="javascript:AddFlow(5); ChangeImgColor(5)" onMouseDown=buttonDown(img5) onMouseOut=buttonOut(img5) onMouseOver=buttonOver(img5) id=img5 alt="" border="1" height="28" hspace="0" src="images/flow5.gif" title="联线" vspace="0" width="56" disabled></td>
							
						    <td><img align="absmiddle" class="button" onclick="javascript: ExportImage(); ChangeImgColor(6)" onMouseDown=buttonDown(img20) onMouseOut=(img20) onMouseOver=buttonOver(img20) id=img20 alt="" border="1" height="28" hspace="0" src="images/flow99.gif" title="导出图片" vspace="0" width="70" disabled></td>
							
							<td><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>

						 	<td><img align="absmiddle" class="button" onclick="javascript: hideminiChart(); ChangeImgColor(6)" onMouseDown=buttonDown(img22) onMouseOut=buttonOut(img22) onMouseOver=buttonOver(img22) id=img22 alt="" border="1" height="28" hspace="0" src="images/flow22.gif" title="是否显示缩略图" vspace="0" width="70" ></td>   										
<!--  
                            <td><img align="absmiddle" class="button" onclick="javascript:ZoomOut();ChangeImgColor(6)"  onMouseDown=buttonDown(img15) onMouseOut=buttonOut(img15) onMouseOver=buttonOver(img15) id=img15 alt="" border="1" height="28" hspace="0" src="images/flow17.gif" title="放大试图" vspace="0" width="56"  ></td>

                            <td><img align="absmiddle" class="button" onclick="javascript:ZoomIn();ChangeImgColor(6)"  onMouseDown=buttonDown(img16) onMouseOut=buttonOut(img16) onMouseOver=buttonOver(img16) id=img16 alt="" border="1" height="28" hspace="0" src="images/flow18.gif" title="缩小试图" vspace="0" width="56"  ></td>
 -->                         
                            <td><img align="absmiddle" id=img23 alt="" border="0" height="28" hspace="0" src="images/flow23.gif" title="缩放试图" vspace="0" width="56" disabled style="display:none" ></td>
                            
                            <td><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>                                                      
                            <td>
                            <select   name="sltMagnifyGrid"   style="   width:80px; position:absolute;clip:rect(0   100%   100%   60px)"   onchange="document.getElementById('MagnifyGridtxt').value=this.value;  MagnifyGrid()" >
                               <option value=200>200%</option>
                               <option value=175>175%</option>
                               <option value=150>150%</option>
                               <option value=125>125%</option>
                               <option value=100 selected>100%</option>
                               <option value=75>75%</option>
                               <option value=50>50%</option>
                               <option value=25>25%</option>
                           </select>
                           <input   name="MagnifyGridtxt"  value="100" class="panel_style"  type="text"  style="height:23px;  width:60px;font-size:15px; border-bottom:   1px   solid   #a0a0a0;   background-color:#ffffff;" onkeydown="MagnifyGridtxtchange();">
                           </td> 
                           <td  width= 20px></td>
                           <td><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>                              
						   <td> 
							   <SELECT  onchange=changeGrid() name=sltGrid>
                                <OPTION value=100>100象素</OPTION>
                                <OPTION value=50>50象素</OPTION>
                                <OPTION value=25>25象素</OPTION>
                                 <OPTION value=0 selected>无网格</OPTION>
                              </SELECT>
                            </td>  
							<td><img border="0"  height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"><span class="separator"></span><img border="0" height="10" hspace="0" src="images/spacer.gif" vspace="0" width="2"></td>                
							<td> 
							   <SELECT  onchange=changeDegree() name=sltDegree disabled>
                                <OPTION value=1>必需</OPTION>
                                <OPTION value=2>重要</OPTION>
                                <OPTION value=3 selected>普通</OPTION>
                              </SELECT>
              </td> 
          </TR>
       </TABLE>     
     </TD>
  </TR>                    
  <TR  width="100%">  
     <TD>
       <TABLE height="100%" width="100%" cellSpacing=1 cellPadding=0>
          <TR>                    
              <TD width="15%" height="100%" valign=top class="panel_style" id="frmTree">
              
                <INPUT TYPE="hidden" id= "XML" name="FlowXML" value="">
                <form name="form1" id=form1 method="post" action="">
                </form>

                <INPUT TYPE="file" name=ofName id=ofName style="DISPLAY: none" >
              
                <TABLE cellspacing="0" cellpadding="0" height="100%">
                  <TR height="28px">
                     <TD align=center style="background: url(../common/images/bg_h2.jpg) repeat center;BORDER-BOTTOM: #919cd0 1px solid ;"><font style="font-size:10pt;color:#ffffff">流程导航视图</font></TD>
                  </TR>               
	                <TR > 
	                  <TD valign=top align=left >
	                     <iframe id=iframeTree src="_flowtree.html" frameborder=0 width="100%" height="100%"></iframe>
	                  </TD>   
	                </TR>  
	              </TABLE>       
	                 
              </TD>
              <TD width=1% bgcolor="#708EC7" onClick="switchSysBar();" style="background: url(../common/images/bg_btm.jpg) repeat center;"><font style="FONT-SIZE: 9pt; CURSOR: default; COLOR: #ffffff">  <span class="navPoint" id="switchPoint" title="关闭/打开左栏">3</span>屏幕切换</font></TD>
              <TD height="100%"  id=WorkFlow class="panel_style" valign=top align=left  style="BACKGROUND-IMAGE: url('');MARGIN: 0px;background-repeat: repeat">
     	          
     	          <TABLE cellSpacing=0 cellPadding=0 >
	                <TR>            
	                	  <TD height="100%" width="100%" >   
	                         <div id="divWorkChart" style="display:block ; overflow: scroll; width: 851px;height: 650px; " >
                                <v:group class=WorkFlowGroup id=chart style="WIDTH: 800px; HEIGHT: 500px; POSITION: absolute;  v-text-anchor: middle-center"  coordsize = "800,500" onmouseup="ReleaseObj()" onmousemove= "MoveObj()" onmousedown= "FindObj()"  onselectstart="javascript:return false;" ondragstart="javascript:return false;">
                           
                                </v:group>
                  		    </div>
                       </TD>
                   </TR>
                  </TABLE>              
             </TD>
         </TR>          
      </TABLE>  
    </TD>          
  </TR>
</TABLE> 
<SCRIPT LANGUAGE="JavaScript">
   function showTip()
   {
      divTip.style.display="block";          
   }
   function hideTip()
   {       
      divTip.style.display="none";
    
   }
</SCRIPT> 

<div id="divTip"  style="left: 0px;top: 0px; display:block">
   <table width="50%" border="0" cellpadding="3" cellspacing="1"  
         style="background: #ff7300; position:static;filter:progid:DXImageTransform.Microsoft.DropShadow(color=#666666,offX=4,offY=4,positives=true)" 
         align="center">
     <tr style="cursor: move;">
       <td><font color="#FFFFFF">温馨提示：</font></td>
       <td align="right"><span class="navPoint" onClick="document.getElementById('divTip').style.display='none'"; title="关闭">r</span></td>
     </tr>
     <tr>
       <td colspan="2" width="100%" bgcolor="#FFFFFF" height="150" align="middle"><span id="Tip"></span></td>
     </tr>
   </table>
</div>
 
<div  id = "divFlow"  style="display: block;position: absolute;height:30px; width: 300px;z-index: 801; right: 26px; top: 45px;"class="panel_style">            		            
                 <!--  <td class="panel_style" style= "background: url(../common/images/bg_h.gif) repeat center;" >  -->
     <table  style="position: static; background:url(../common/images/bg_h2.jpg) repeat center;" width="300px" height="30px" cellpadding="0" cellspacing="0"> 
           <tr >            
               <td align="left"  width="280px" style="color :#ffffff ;font-size:10pt;" >缩略图显示</td>
               <td align="right" width="10px"  ><span class="navPoint" onClick="minimizetabMiniAture()"  title="最小化">0</span></td>
               <td align="right" width="10px"  ><span class="navPoint" onClick="maximizetabMiniAture()" title="最大化">1</span></td>                  
               <td align="right" width="10px"  ><span class="navPoint" onClick="closetabMiniAture()" title="关闭">r</span></td>                                          
            </tr>
     </table> 
</div>
<div id="divMiniGroup" style="display:block;position:absolute;height:240px;width:300px;z-index:801;right:26px;top:72px;" class="panel_style">                                                       
      <table id= "tabMiniAture"  width="300px" height="240px" style="background-color: buttonface; position: static;">                   
          <tr>
          <td style=" background-color: #ffffff;">
             <v:group class="WorkFlowGroup" id="miniChart" style="width: 300px; height: 240px; position: absolute; top: 0px ;left:0px; v-text-anchor: middle-center;z-index: 0;"  coordsize = "1500,1200">                                                                                                                                                               
             </v:group> 
          </td>
          </tr>  
      </table>                                 
</div>
  
<div id="divRect" style="width: 300px; height: 255px; position: absolute;  right:28px; top: 74px; display: block ;z-index: 880;">
      <v:group class="WorkFlowGroup" id="miniRect" style="width: 300px; height: 240px;position: absolute; top: 0px;left:0px; v-text-anchor: middle-center;z-index: 600;"  coordsize = "1500,1200">                                                                                                                             
      </v:group> 
</div> 
<script type="text/javascript">
var tWorkChart = document.getElementById('divWorkChart');
var tdivRect = document.getElementById('divRect');
var fixRectTop = tdivRect.style.posTop;
var fixRectRight = tdivRect.style.posRight;
var redBoxY=640;
var redBoxX=0;
     if (switchPoint.innerText==3)
     {
	   var redBoxX=825;
     }
     if(switchPoint.innerText==4)
     {
	  var redBoxX=950;
    }
    tWorkChart.onscroll=function scrollit()
    {       	    	
       var speed =Speed();
	   if(tWorkChart.scrollTop !="0")
	   {
	       var target_Rect=tWorkChart.scrollTop*speed+fixRectTop;
	       var target1=Math.abs(target_Rect);
	       var goalTop = Math.abs(240*(1-redBoxY/(miniRect.coordsize.y))+fixRectTop);
           if(target1 <= goalTop)
           {  
//        	   if(Math.abs(tdivRect.style.posTop-target_Rect)>0.5)
//        	   {
//        		   tdivRect.style.posTop=tdivRect.style.posTop+0.1;
//        	   }  
        	   tdivRect.style.posTop=target_Rect;
           }       	  
        	  /**
        	   186的计算：240(1-640/1200) + 74 即240(1-640/miniRect。coordsize。y)-divRect.style.posTop
        	  */
	   }
	   if(tWorkChart.scrollLeft !="0")
	   {   		   
		   var target_Rect=fixRectRight-tWorkChart.scrollLeft*speed;
		   var targer2=Math.abs(target_Rect);
		   /*
		   107的计算方法：红框所在的div宽300px，红框宽(825/1500)*300=165,红框移动到边界需300-165=135px，divRect据右边界26px，(135-268=109)px
		          即  300(1-825/miniRect.coordsize.x)-tdivRect.style.posRight 
		   */
		   var goalLeft = Math.abs(300*(1-redBoxX/(miniRect.coordsize.x))-fixRectRight);
		   if(targer2 <= goalLeft)
		   {   
//			   if(Math.abs(tdivRect.style.posRight-target_Rect)>0.5)
//			   {
//				   tdivRect.style.posRight=tdivRect.style.posRight+0.1;
//			   }
			   tdivRect.style.posRight=target_Rect;
		   }		  
	   } 
   }      
</script>
</BODY>
<iframe name="save" style="display:none;"></iframe>
</HTML>
