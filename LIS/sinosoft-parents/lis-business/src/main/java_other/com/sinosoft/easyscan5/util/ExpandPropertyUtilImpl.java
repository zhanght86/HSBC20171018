package com.sinosoft.easyscan5.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.lis.db.ES_DOC_PROPERTYDB;
import com.sinosoft.lis.schema.ES_DOC_PROPERTYSchema;
import com.sinosoft.lis.vschema.ES_DOC_PROPERTYSet;

import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.core.vo.ExpPropVo;
import com.sinosoft.easyscan5.core.vo.ExpandPropertyVo;
import com.sinosoft.easyscan5.entity.EsDocTypeExpProperty;
import com.sinosoft.easyscan5.entity.EsDocTypeExpPropertyHis;
import com.sinosoft.easyscan5.entity.EsExppropertyDef;


public class ExpandPropertyUtilImpl {



	public List<ExpandPropertyVo> queryExpNameAndValueChannel(String docId) throws Exception{
		List<ExpandPropertyVo> result = new ArrayList<ExpandPropertyVo>();
			ES_DOC_PROPERTYDB es_DOC_PROPERTYDB = new ES_DOC_PROPERTYDB();
			es_DOC_PROPERTYDB.setDocID(docId);
			ES_DOC_PROPERTYSet es_DOC_PROPERTY_PSet = es_DOC_PROPERTYDB.query();
			if(es_DOC_PROPERTY_PSet!=null&&es_DOC_PROPERTY_PSet.size()>0){
				ES_DOC_PROPERTYSchema es_DOC_PROPERTY_PSchema = es_DOC_PROPERTY_PSet.get(1);
				if(es_DOC_PROPERTY_PSchema.getP1()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP1())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P1");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP1());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP2()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP2())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P2");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP2());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP3()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP3())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P3");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP3());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP4()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP4())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P4");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP4());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP5()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP5())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P5");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP5());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP6()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP6())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P6");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP6());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP7()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP7())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P7");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP7());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP8()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP8())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P8");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP8());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP9()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP9())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P9");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP9());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP10()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP10())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P10");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP10());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP11()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP11())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P11");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP11());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP12()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP12())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P12");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP12());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP13()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP13())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P13");
					
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP13());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP14()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP14())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P14");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP14());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP15()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP15())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P15");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP15());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP16()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP16())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P16");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP16());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP17()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP17())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P17");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP17());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP18()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP18())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P18");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP18());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP19()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP19())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P19");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP19());
					result.add(esExpandPropertyVo);
				}
				if(es_DOC_PROPERTY_PSchema.getP20()!=null&&!"".equals(es_DOC_PROPERTY_PSchema.getP20())){
					ExpandPropertyVo esExpandPropertyVo = new ExpandPropertyVo();
					esExpandPropertyVo.setProName("P20");
					esExpandPropertyVo.setProName(es_DOC_PROPERTY_PSchema.getP20());
					result.add(esExpandPropertyVo);
				}
			}

		return result;
	}
}
