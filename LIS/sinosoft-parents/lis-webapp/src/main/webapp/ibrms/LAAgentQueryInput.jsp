<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LAAgentQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 15:31:08
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
 

<html>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./LAAgentQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LAAgentQueryInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
  <title>代理人查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./LAAgentQuerySubmit.jsp" method=post id=fm   name=fm target="fraSubmit">
  <!--代理人查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            <td class= titleImg>
                代理人查询条件
            </td>
            </td>
    	</tr>
     </table>
  <Div  id= "divLAAgent" class=maxbox style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          代理人编码 
        </TD>
        <TD  class= input> 
          <Input class="common wid" id=AgentCode   name=AgentCode >
        </TD>        
        <TD  class= title> 
          姓名
        </TD>
        <TD  class= input>
          <Input id=Name   name=Name class="common wid" verify="姓名|NotNull&len<=20" elementtype=nacessary>
        </TD>
        <TD  class= title>
          性别 
        </TD>
        <TD  class= input>
          <Input id=Sex  name=Sex class="codeno" elementtype=nacessary verify="性别|code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" 
          onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input id=SexName   name=SexName class='codename' readonly=true>
        </TD> 
      </TR> 
      <TR  class= common> 
        <TD  class= title>
          出生日期 
        </TD>
        <TD  class= input>
          <Input id=Birthday   name=Birthday class="common wid"  verify="出生日期|NotNull&Date" elementtype=nacessary> 
        </TD>
        <TD  class= title>
          证件号码类型
        </TD>
        <TD  class= input> 
          <Input id=IDNoType   name=IDNoType class="codeno" verify="证件号码类型|code:idtype" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="idtype" ondblclick="return showCodeList('idtype',[this,IDNoTypeName],[0,1]);" 
          onkeyup="return showCodeListKey('idtype',[this,IDNoTypeName],[0,1]);"><input id=IDNoTypeName   name=IDNoTypeName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          证件号码 
        </TD>
        <TD  class= input> 
          <Input id=IDNo   name=IDNo class="common wid" > 
        </TD>
        </TR> 
        <TR  class= common> 
        <TD  class= title>
          民族
        </TD>
        <TD  class= input>
          <Input name=Nationality class="codeno" id="Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('Nationality',[this,NationalityName],[0,1]);"
           onkeyup="return showCodeListKey('Nationality',[this,NationalityName],[0,1]);" ><input id=NationalityName   name=NationalityName class='codename' readonly=true> 
        </TD>
        <TD  class= title> 
          籍贯
        </TD>
        <TD  class= input>
          <Input id=NativePlace   name=NativePlace class="codeno" verify="籍贯|code:nativeplacebak" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="nativeplacebak" ondblclick="return showCodeList('nativeplacebak',[this,NativePlaceName],[0,1]);" 
          onkeyup="return showCodeListKey('nativeplacebak',[this,NativePlaceName],[0,1]);"><input id=   name =NativePlaceName class='codename' readonly=true>
        </TD>   
        <TD  class= title>
          政治面貌 
        </TD>
        <TD  class= input> 
          <Input id=PolityVisage  name=PolityVisage class="codeno" verify="政治面貌|code:polityvisage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="polityvisage" ondblclick="return showCodeList('polityvisage',[this,PolityVisageName],[0,1]);"
           onkeyup="return showCodeListKey('polityvisage',[this,PolityVisageName],[0,1]);" ><input id=PolityVisageName   name=PolityVisageName class='codename' readonly=true> 
        </TD> 
      </TR>
      <TR  class= common> 
        <TD  class= title>
          户口所在地
        </TD>
        <TD  class= input> 
          <Input id=RgtAddress   name=RgtAddress class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('NativePlaceBak',[this,RgtAddressName],[0,1]);" 
          onkeyup="return showCodeListKey('NativePlaceBak',[this,RgtAddressName],[0,1]);"><input id=RgtAddressName   name=RgtAddressName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          学历 
        </TD>
        <TD  class= input> 
          <Input  name=Degree class="codeno" 
          verify="学历|code:degree" id="degree" 
          ondblclick="return showCodeList('degree',[this,DegreeName],[0,1]);" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          onkeyup="return showCodeListKey('degree',[this,DegreeName],[0,1]);"><input id=DegreeName   name=DegreeName class='codename' readonly=true> 
        </TD>
       <TD  class= title> 
          毕业院校
        </TD>
        <TD  class= input> 
          <Input id=GraduateSchool   name=GraduateSchool class="common wid" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          专业 
        </TD>
        <TD  class= input> 
          <Input id=Speciality   name=Speciality class="common wid" > 
        </TD>
        <TD  class= title>
          职称 
        </TD>
        <TD  class= input> 
          <Input id=PostTitle name=PostTitle class='codeno' verify="职称|code:posttitle" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
           ondblclick="return showCodeList('posttitle',[this,PostTitleName],[0,1]);" 
           onkeyup="return showCodeListKey('posttitle',[this,PostTitleName],[0,1]);" ><input id=   name=PostTitleName class='codename' readonly=true>
        </TD>
        <TD  class= title>
          家庭地址 
        </TD>
        <TD  class= input> 
          <Input id=HomeAddress   name=HomeAddress class="common wid" > 
        </TD>
      </TR>
       <TR  class= common> 
       	<TD  class= title>
          通讯地址 
        </TD>
        <TD  class= input> 
          <Input id=PostalAddress   name=PostalAddress class="common wid" > 
        </TD>
        <TD  class= title>
          邮政编码 
        </TD>
        <TD  class= input> 
          <Input id=ZipCode   name=ZipCode class="common wid" > 
        </TD>
        <TD  class= title>
          电话 
        </TD>
        <TD  class= input> 
          <Input id=Phone   name=Phone class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title> 
          传呼
        </TD>
        <TD  class= input>
          <Input id=BP   name=BP class="common wid" > 
        </TD-->  
        <TD  class= title>
          手机 
        </TD>
        <TD  class= input> 
          <Input id=Mobile   name=Mobile class="common wid" > 
        </TD>
        <TD  class= title>
          E_mail 
        </TD>
        <TD  class= input> 
          <Input id=EMail   name=EMail class="common wid" > 
        </TD>
        <TD  class= title>
          从业年限 
        </TD>
        <TD  class= input> 
          <Input id=Workage   name=Workage class="common wid"  > 
        </TD>
      </TR>
       <TR  class= common>
        <TD  class= title>
          银行编码
        </TD>
        <TD  class= input> 
          <Input id=BankCode   name=BankCode class='codeno' verify="银行编码|code:HeadOffice" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('HeadOffice',[this,BankCodeName],[0,1]);" 
          onkeyup="return showCodeListKey('HeadOffice',[this,BankCodeName],[0,1]);"><input id=BankCodeName   name=BankCodeName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          银行账户 
        </TD>
        <TD  class= input> 
          <Input id=BankAccNo   name=BankAccNo class="common wid" > 
        </TD>
        <TD  class= title>
         保证金收据号
        </TD>
        <TD  class= input> 
          <Input id=ReceiptNo   name=ReceiptNo class="common wid" verify="保证金收据号|notnull&value>0"> 
        </TD>        
      </TR>
        <TR  class= common>
        <TD  class= title>
          原工作单位 
        </TD>
        <TD  class= input> 
          <Input id=OldCom   name=OldCom class="common wid" > 
        </TD>
        <TD  class= title> 
          原职业 
        </TD>
        <TD  class= input> 
          <Input id=OldOccupation   name=OldOccupation class='codeno' verify="原职业|code:occupation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('occupation',[this,OldOccupationName],[0,1]);"
           onkeyup="return showCodeListKey('occupation',[this,OldOccupationName],[0,1]);"><input id=OldOccupationName   name=OldOccupationName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          工作职务 
        </TD>
        <TD  class= input> 
          <Input id=HeadShip   name=HeadShip class="common wid"  > 
        </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>
          资格证号码 
        </TD>
        <TD  class= input> 
          <Input id=QuafNo   name=QuafNo class="common wid" > 
        </TD>
        <TD  class= title>
          资格证发放日期
        </TD>
        <TD  class= input> 
          <Input id=GrantDate   name=GrantDate class='coolDatePicker' dateFormat='short'onClick="laydate({elem: '#GrantDate'});" ><span class="icon"><a onClick="laydate({elem: '#GrantDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title>
          展业证号码 
        </TD>
        <TD  class= input> 
          <Input id=DevNo1   name=DevNo1 class="common wid" > 
        </TD>   
      </TR>
        <TR  class= common>  
        <!--TD  class= title>
          代理人VIP属性 
        </TD>
        <TD  class= input> 
          <Input id=   name=VIPProperty class="common wid"> 
        </TD-->
       
       <TD  class= title>
          入司时间 
        </TD>
        <TD  class= input> 
           <Input id=EmployDate   name=EmployDate class='coolDatePicker' dateFormat='short' verify="入司时间|notnull&Date" elementtype=nacessaryonClick="laydate({elem: '#EmployDate'});" ><span class="icon"><a onClick="laydate({elem: '#EmployDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        
        </TR>
    </table>
    
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent3);">
            <td class= titleImg>
                行政信息
            </td>
            </td>
    	</tr>
     </table>
     <Div id= "divLAAgent3" class=maxbox style= "display: ''">
       <table class=common>
       <tr class=common>
        <TD width="11%" class= title>
          职级
        </TD>
        <TD width="27%" class= input>
          <Input id=AgentGrade   name=AgentGrade class="codeno" 
          verify="代理人职级|notnull&code:AgentGrade" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('AgentGrade',[this,AgentGradeName],[0,1],null,'<%=tSqlAgentGrade%>','1');" 
          onkeyup="return showCodeListKey('AgentGrade',[this,AgentGradeName],[0,1],null,'<%=tSqlAgentGrade%>','1');" >\
		  <input id=AgentGradeName   name=AgentGradeName class="codename" readonly=true elementtype=nacessary >
          
        </TD>
        
         <TD class= title>
          管理机构
       </TD>
        <TD class= input>
          <input id=ManageCom   name=ManageCom class="common" elementtype=nacessary verify="管理机构|notnull">
          <input type=button class="cssButton" value="选择" onClick="SelectCom();">
        </TD>

        <TD class= title>
          销售机构
        </TD>
        <TD class= input>
          <Input type=text class="common" id=BranchCode   name=BranchCode elementtype=nacessary verify="销售机构|notnull" onBlur="return checkManageCom();">
		  <input type=button id=selectBtn   name=selectBtn class="cssButton" value="选择" onClick="SelectBranch();">
        </TD>
        
   <!--孙义慧于2007-12-03对下面的代码进行修改-->
   <TR>
       <TD width="11%" class= title  >推荐人代码
         </TD>
        <TD width="23%" class= input>
        <input class="common wid" id=IntroAgency   name=IntroAgency  onChange="return changeIntroAgency();">       
         </TD>
        <TD  class= title  >推荐人姓名
         </TD>
        <TD  class= input><input class="readonly wid"   id=IntroAgencyName   name=IntroAgencyName >
         </TD>
     </TR>
    
      <!--
      <TR class=comm> 

        <TD class= title>
          管理机构
       </TD>
        <TD class= input>
          <input id=ManageCom   name=ManageCom class="common wid" elementtype=nacessary verify="管理机构|notnull">
          <input type=button class="cssButton" value="选择" onClick="SelectCom();">
        </TD>

        <TD class= title>
          销售机构
        </TD>
        <TD class= input>
          <Input type=text class="common wid" id=BranchCode   name=BranchCode elementtype=nacessary verify="销售机构|notnull" onBlur="return checkManageCom();">
		 		 <input type=button id=selectBtn   name=selectBtn class="cssButton" value="选择" onClick="SelectBranch();">
        </TD>
        <TD class= title  >
          育成人
        </TD>
        <TD class= input>
          <Input id=RearAgent   name=RearAgent class="common wid"   verify="组育成人|len<=10" onchange="return disPlayRearAgentName()">
        </TD>
      </TR>
  
        <tr class=common>
        	
        <TD class= title  >
          增部人
        </TD>
        <TD class= input>
          <Input id=RearDepartAgent   name=RearDepartAgent     class="common wid" verify="增部人|len<=10" onchange="return disPlayRearDepartAgentName()">
        </TD>
        <TD class= title >
          育成人姓名
        </TD>
        <TD class= input>
          <Input id=RearAgentName   name=RearAgentName class="readonly wid" >
        </TD>
        <TD class= title  > 
          增部人姓名
        </TD>
        <TD class= input>
          <Input id=RearDepartAgentName   name=RearDepartAgentName class="readonly wid"  >
        </TD>-->
        </tr> 
        </table>
        <!--ly 20070611 为了不改其他文件，直接在这里将一些字段置成隐藏的，其实这些都是没用的-->
        <input class="readonly wid"   type=hidden  id=IntroAgency   name=IntroAgency >
        <input class="readonly wid"   type=hidden  id=IntroAgencyName   name=IntroAgencyName >
        <input class="readonly wid"   type=hidden  id=RearAgent   name=RearAgent >
        <input class="readonly wid"   type=hidden  id=RearDepartAgent   name=RearDepartAgent >
        <input class="readonly wid"   type=hidden  id=RearAgentName   name=RearAgentName >
        <input class="readonly wid"   type=hidden  id=RearDepartAgentName   name=RearDepartAgentName >
        
         <!--代理人系列--> <Input id=AgentSeries   name=AgentSeries type=hidden>
    </Div>
    
          <input type=hidden id=BranchType   name=BranchType value=''>
          <INPUT VALUE="查  询" class="cssButton" TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="returnParent();"> 	
    </Div>      
          				
    <table>
    	<tr>
        	<td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 代理人结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanAgentGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class="cssButton90" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class="cssButton93" TYPE=button onclick="turnPage.lastPage();"> 						
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br /><br /><br /><br />
  </form>
</body>
</html>
