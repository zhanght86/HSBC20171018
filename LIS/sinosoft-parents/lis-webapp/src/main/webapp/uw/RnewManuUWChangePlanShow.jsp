<STYLE>
	 #changeRiskPlan a {
      float:left;
      background:url("../common/images/tableft1.gif") no-repeat left top;
      margin:0;
      padding:0 0 0 4px;
      text-decoration:none;
      }
      #changeRiskPlan a span {
      float:left;
      display:block;
      background:url("../common/images/tabright1.gif") no-repeat right top;
      padding:5px 15px 4px 6px;
      color:#627EB7;
      }
    /* Commented Backslash Hack hides rule from IE5-Mac \*/
    #changeRiskPlan a span {float:none;}
    /* End IE5-Mac hack */
    #changeRiskPlan a:hover span {
      color:#627EB7;
      }
    #changeRiskPlan a:hover {
      background-position:0% -42px;
      }
    #changeRiskPlan a:hover span {
      background-position:100% -42px;
      }

      #changeRiskPlan #current a {
              background-position:0% -42px;
      }
      #changeRiskPlan #current a span {
              background-position:100% -42px;
      }
</STYLE>

<Div   style= "display:none">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,changeRiskPlan);">
    		</td>
    		<td class= titleImg>
    			���ռƻ����
    		</td>
    	 </tr>
       </table>	
</Div>
<Div  id="changeRiskPlan" class="maxbox" style= "display: none">
<table class=common >
  <tr>
    <td class="common">
    	<a href="#" onclick="onRiskTabChange(1);">
    		<span>��ǰ���ռƻ�</span>
    	</a>
    	<a href="#" onclick="onRiskTabChange(2);">
    	  <span>������ռƻ�</span>
    	</a>
    	<div id="divOperator" style= "display: none">
    	<a href="#" onclick="onRiskTabChange(3)">
    	  <span>���������޸�</span>
    	</a>
    	</div>
    </td>
  </tr>
 
  <tr>
    <td class="common">
        <div id="tab1c">
    <table  class= common class='muline' border='0' cellspacing='0' cellpadding='1'>  
        <tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanOldRislPlanGrid" >
  					</span> 
  			  	</td>
  			</tr>
    </table>
    <br> 
    <INPUT type= "button" id="button1" name= "sure" value="ȷ  ��" class= cssButton  onclick="makeRiskUWState();">
        </div>
        <div id="tab2c" style="display:none">
         <table  class= common>
	         <!--span id="spanNewRislPlanGrid" >
  					</span-->
  					<iframe id= "NewRiskPlan" src="" width=100% height=500 SCROLLING='Auto'>
  				  </iframe> 
	      </table>
        </div>
    </td>
  </tr>
</table>
</div>
