package com.sinosoft.easyscan5.core.service.getcentersettings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.easyscanxml.GetCenterSettingResXmlConstants;
import com.sinosoft.easyscan5.core.service.getcentersettings.IGetCenterSettingsService;
import com.sinosoft.easyscan5.core.vo.ExpPropVo;
import com.sinosoft.easyscan5.entity.EsDocDef;
import com.sinosoft.easyscan5.entity.EsDocDefId;
import com.sinosoft.easyscan5.entity.EsTwainDef;
import com.sinosoft.easyscan5.entity.SysCompany;
import com.sinosoft.lis.db.ES_DOC_DEFDB;
import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;
import com.sinosoft.lis.db.ES_TWAIN_DEFDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.schema.ES_DOC_DEFSchema;
import com.sinosoft.lis.schema.ES_PROPERTY_DEFSchema;
import com.sinosoft.lis.schema.ES_TWAIN_DEFSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.ES_DOC_DEFSet;
import com.sinosoft.lis.vschema.ES_PROPERTY_DEFSet;
import com.sinosoft.lis.vschema.ES_TWAIN_DEFSet;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class GetCenterSettingsServiceImpl extends BaseServiceImpl implements
		IGetCenterSettingsService {

	private static Logger logger = Logger
			.getLogger(GetCenterSettingsServiceImpl.class);
	private String oldPropVersion;
	private Map resultMap = new HashMap<String, Object>();
	private Map versionMap = new HashMap<String, String>();
	private String returnStr = null;

	private HashMap codeMap = new HashMap();

	/*
	 * 获取中心配置
	 */
	public String getCenterSettings(Map<String, String> param, String channel) {
		// 获取单证信息
		try {
			String xmlDocDef = Constants.XML_PARAM_NAME_DOCDEF;
			if (param.containsKey(xmlDocDef)) {
				String oldVersion = param.get(xmlDocDef);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = "999";
				// 生成单证业务类型及单证细类xml
				resultMap.put(xmlDocDef, this.getDocDef(channel));
				versionMap.put(xmlDocDef, getVersion(oldV));
			}
		} catch (Exception e) {
			logger.error("获取单证类型信息失败", e);
			returnStr = "获取单证类型信息失败" + e.getMessage();
			return returnStr;
		}
		// 获取机构信息
		try {
			String xmlManagecom = Constants.XML_PARAM_NAME_MANAGECOMS;
			if (param.containsKey(xmlManagecom)) {
				String oldVersion = param.get(xmlManagecom);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = "999";
				// 生成单证业务类型及单证细类xml
				resultMap.put(xmlManagecom, this.getManagecom());
				versionMap.put(xmlManagecom, getVersion(oldV));
			}
		} catch (Exception e) {
			logger.error("获取机构信息失败", e);
			returnStr = "获取机构信息失败" + e.getMessage();
			return returnStr;
		}
		// 获取扩展属性信息
		try {
			String xmlPropDef = Constants.XML_PARAM_NAME_PROPDEF;
			if (param.containsKey(xmlPropDef)) {
				String oldVersion = param.get(xmlPropDef);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = oldVersion;
				// 生成单证业务类型及单证细类xml
				// if(!newVersion.equals(oldVersion)) {
				resultMap.put(xmlPropDef, this.getDocProp());
				versionMap.put(xmlPropDef, getVersion(oldV));
				// 获取扩展属性字典
				resultMap.put(GetCenterSettingResXmlConstants.XML_CODELIST,
						this.getPropDict());
				// }else{
				// versionMap.put(xmlPropDef, oldVersion);
				// }
			}
		} catch (Exception e) {
			logger.error("获取扩展属性信息失败", e);
			returnStr = "获取扩展属性信息失败" + e.getMessage();
			return returnStr;
		}

		// 获取扫描仪配置
		try {
			String xmlTwainDef = Constants.XML_PARAM_NAME_SCANDEF;
			if (param.containsKey(xmlTwainDef)) {
				String oldVersion = param.get(xmlTwainDef);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = "999";
				// if(!newVersion.equals(oldVersion)) {
				resultMap.put(xmlTwainDef, this.getTwainDef());
				versionMap.put(xmlTwainDef, getVersion(oldV));
				// }else{
				// versionMap.put(oldVersion, oldVersion);
				// }
			}
		} catch (Exception e) {
			logger.error("获取扫描仪配置信息失败", e);
			returnStr = "获取扫描仪配置信息失败" + e.getMessage();
			return returnStr;
		}
		try {
			String paramsettingXml = Constants.XML_PARAM_NAME_PARAMSETTINGS;
			if (param.containsKey(paramsettingXml)) {
				String oldVersion = param.get(paramsettingXml);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = "999";
				// if(!newVersion.equals(oldVersion)) {
				versionMap.put(paramsettingXml, getVersion(oldV));
				// }else{
				// versionMap.put(oldVersion, oldVersion);
				// }
			}
		} catch (Exception e) {
			logger.error("获取扫描仪配置信息失败", e);
			returnStr = "获取扫描仪配置信息失败" + e.getMessage();
			return returnStr;
		}

		try {
			String batchsettingsXml = Constants.XML_PARAM_NAME_BATCHSETTINGS;
			if (param.containsKey(batchsettingsXml)) {
				String oldVersion = param.get(batchsettingsXml);
				int oldV = Integer.parseInt(oldVersion);
				String newVersion = "999";
				// if(!newVersion.equals(oldVersion)) {
				versionMap.put(batchsettingsXml, getVersion(oldV));
				// }else{
				// versionMap.put(oldVersion, oldVersion);
				// }
			}
		} catch (Exception e) {
			logger.error("获取扫描仪配置信息失败", e);
			returnStr = "获取扫描仪配置信息失败" + e.getMessage();
			return returnStr;
		}
		return returnStr;
	}

	/**
	 * ��ѯES_DOC_DEF
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map getDocDef(String channel) throws Exception {
		List list = queryDocDef(channel);
		Map docDefMap = new HashMap();
		List subList = null;
		EsDocDef esDocDef = null;
		String buss = "";
		// ���busstype
		for (Object obj : list) {
			esDocDef = (EsDocDef) obj;
			buss = esDocDef.getId().getBussType();
			if (docDefMap.containsKey(buss)) {
				subList = (List) docDefMap.get(buss);
				subList.add(esDocDef);
			} else {
				subList = new ArrayList();
				subList.add(esDocDef);
			}
			docDefMap.put(buss, subList);
		}
		return docDefMap;
	}

	public List<EsDocDef> queryDocDef(String channel) {
		List<EsDocDef> docDefList = new ArrayList<EsDocDef>();
		ES_DOC_DEFDB es_doc_defdb = new ES_DOC_DEFDB();
		StringBuffer querySql = new StringBuffer("select * from es_doc_def ");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(querySql.toString());
		ES_DOC_DEFSet eSet = es_doc_defdb.executeQuery(sqlbv);
		// ExeSQL nExeSQL = new ExeSQL();
		// String checkDefSql = "select sysvarvalue from ldsysvar where sysvar =
		// 'EASYSCANFILETYPE'";
		// SSRS checks = nExeSQL.execSQL(checkDefSql);
		for (int i = 1; i <= eSet.size(); i++) {
			ES_DOC_DEFSchema eSchema = eSet.get(i);
			EsDocDefId esDocDefId = new EsDocDefId();
			esDocDefId.setBussType(eSchema.getBussType());
			esDocDefId.setChannel(channel);
			esDocDefId.setSubType(eSchema.getSubType());
			EsDocDef esDocDef = new EsDocDef();
			esDocDef.setId(esDocDefId);
			esDocDef.setBussTypeName(eSchema.getBussTypeName());
			esDocDef.setSubtypeName(eSchema.getSubTypeName());
			esDocDef.setCodeFlag(eSchema.getCodeFlag());
			esDocDef.setPassFlag(eSchema.getNewCaseFlag());
			String codeLen = eSchema.getCodeLen() + "";
			esDocDef.setCodeLen(codeLen);

			esDocDef.setUpFileType("0");//尚在文件类型


			esDocDef.setFileSaveType("2");//保存文件类型
			esDocDef.setJpgQuality("60");//JPG压缩质量
			esDocDef.setIsSign("N");
			esDocDef.setKeyword("");
			esDocDef.setSaveColorMode("2");//色彩模式
			docDefList.add(esDocDef);
		}
		return docDefList;
	}

	/**
	 * ��ȡ����Ϣ
	 * 
	 * @return
	 */
	private List getManagecom() throws Exception {
		// List list = getCenterSettingsDao.QueryListWithHQL("from SysCompany
		// where " +
		// "companyState='1' and delFlag = '0'");
		List sysList = new ArrayList();
		LDComDB sys_companydb = new LDComDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from ldcom");
		LDComSet sysset = sys_companydb.executeQuery(sqlbv);
		for (int i = 1; i <= sysset.size(); i++) {
			LDComSchema sySchema = sysset.get(i);
			SysCompany sysCompany = sysSchemaConvertToEndtity(sySchema);
			sysList.add(sysCompany);
		}
		return sysList;
	}

	/**
	 * ��ȡ��չ����
	 * 
	 * @param sySchema
	 * @return
	 */
	private SysCompany sysSchemaConvertToEndtity(LDComSchema sySchema) {
		SysCompany sysCompany = new SysCompany();
		sysCompany.setAddress(sySchema.getAddress());
		sysCompany.setCompanyName(sySchema.getName());
		sysCompany.setCompanyNo(sySchema.getComCode());
		return sysCompany;
	}

	/**
	 * 查询扩展属性
	 * 
	 * @return
	 * @throws Exception
	 */
	private List getDocProp() throws Exception {
		List sysList = new ArrayList();

		ES_PROPERTY_DEFDB es_PROPERTY_DEFDB = new ES_PROPERTY_DEFDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from ES_PROPERTY_DEF");
		ES_PROPERTY_DEFSet prodefSet = es_PROPERTY_DEFDB
				.executeQuery(sqlbv);
		for (int i = 1; i <= prodefSet.size(); i++) {
			ES_PROPERTY_DEFSchema prodefSchema = prodefSet.get(i);
			ExpPropVo expPropVo = prodefSchemaConvertToVO(prodefSchema);
			sysList.add(expPropVo);
		}
		return sysList;
	}

	private ExpPropVo prodefSchemaConvertToVO(ES_PROPERTY_DEFSchema prodefSchema) {
		// TODO Auto-generated method stub
		ExpPropVo expPropVo = null;
		if (prodefSchema != null) {
			expPropVo = new ExpPropVo();
			expPropVo.setPropColumn(prodefSchema.getPropField());
			expPropVo.setPropName(prodefSchema.getPropName());
			expPropVo.setBussType(prodefSchema.getBussType());
			expPropVo.setSubType(prodefSchema.getSubType());
			expPropVo.setPropCtrlType(prodefSchema.getCtrlType());
			expPropVo.setPropDictType(prodefSchema.getListCodeType());
			expPropVo.setPropDefaultValue(prodefSchema.getCtrlDefaultValue());
			if(Constants.COMBOBOX.equals(prodefSchema.getCtrlType())){
				codeMap.put(prodefSchema.getListCodeType(), prodefSchema.getPropName());
			}
			
		}
		return expPropVo;
	}

	/**
	 * ��ȡ��չ�����ֵ�
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map getPropDict() throws Exception {
		List list = null;
		Map map = new HashMap();
		List codeList = null;

		String dictType = "";
		Set codeSet = codeMap.keySet();
		Iterator codeIte = codeSet.iterator();
		while (codeIte.hasNext()) {
			dictType = codeIte.next().toString();
			ExeSQL exeSql = new ExeSQL();
			SSRS nSSRS;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select code,codename from ldcode where codetype = '?dictType?");
			sqlbv.put("dictType", dictType);
			nSSRS = exeSql
					.execSQL(sqlbv);
			for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
				Object[] objarr = new Object[4];
				objarr[0] = dictType;
				objarr[1] = codeMap.get(dictType);
				if (map.containsKey(dictType)) {
					codeList = (List) map.get(dictType);
					objarr[2] = nSSRS.GetText(i, 1);
					objarr[3] = nSSRS.GetText(i, 2);
					codeList.add(objarr);

				} else {
					codeList = new ArrayList();
					objarr[2] = nSSRS.GetText(i, 1);
					objarr[3] = nSSRS.GetText(i, 2);
					codeList.add(objarr);
				}
				map.put(dictType, codeList);
			}
		}
		return map;
	}

	/**
	 * 获取扫描策略
	 * 
	 * @return
	 * @throws Exception
	 */
	private List getTwainDef() throws Exception {
		// List list = getCenterSettingsDao.findEsTwainDef();
		List twainList = new ArrayList();
		ES_TWAIN_DEFDB es_TWAIN_DEFDB = new ES_TWAIN_DEFDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from es_twain_def");
		ES_TWAIN_DEFSet es_TWAIN_DEFSet = es_TWAIN_DEFDB.executeQuery(sqlbv);
		if (es_TWAIN_DEFSet != null && es_TWAIN_DEFSet.size() > 0) {
			for (int i = 1; i <= es_TWAIN_DEFSet.size(); i++) {
				ES_TWAIN_DEFSchema es_TWAIN_DEFSchema = es_TWAIN_DEFSet.get(i);
				EsTwainDef nesTwainDef = new EsTwainDef();
				nesTwainDef.setDefsettingcode(es_TWAIN_DEFSchema
						.getDefSettingName());
				nesTwainDef.setDefsettingname(es_TWAIN_DEFSchema
						.getDefSettingName());
				nesTwainDef.setPages("-1");

				nesTwainDef.setDpi(new Double(es_TWAIN_DEFSchema.getDpi())
						.longValue());
				nesTwainDef.setBitdepth(es_TWAIN_DEFSchema.getBitDepth());
				nesTwainDef.setPagermode(es_TWAIN_DEFSchema.getPageMode());
				nesTwainDef.setDuplex(es_TWAIN_DEFSchema.getDuplex());
				twainList.add(nesTwainDef);
			}
		}
		return twainList;
	}

	/*
	 * ��ȡ���
	 * 
	 * @see com.sinosoft.base.service.impl.BaseServiceImpl#getResult()
	 */
	public Map getResultMap() {
		return resultMap;
	}

	public Map getVersionMap() {
		return versionMap;
	}

	/*
	 * 处理null&&“null”
	 */
	private String DealNull(Object str) {

		return (str == null || "null".equals(str.toString().toLowerCase())) ? ""
				: str.toString();
	}

	public void updateVersion(String tableName, String userNo) {
		// TODO Auto-generated method stub

	}

	private String getVersion(int oldVersion) {
		String versions = "1".equals(oldVersion + "") ? "2" : "1";
		return versions;
	}


}
