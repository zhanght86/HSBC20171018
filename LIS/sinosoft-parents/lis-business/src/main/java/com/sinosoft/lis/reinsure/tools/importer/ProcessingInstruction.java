

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   ProcessingInstruction.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

// Referenced classes of package org.jdom:
//            IllegalTargetException, Verifier

public class ProcessingInstruction implements Serializable, Cloneable {

	protected String target;
	protected String rawData;
	protected Map mapData;

	public ProcessingInstruction(String target, String data) {
		String reason;
		/* 127 */if ((reason = Verifier
				.checkProcessingInstructionTarget(target)) != null) {
			/* 128 */throw new IllegalTargetException(target, reason);
		} else {
			/* 131 */this.target = target;
			/* 132 */setData(data);
			/* 125 */return;
		}
	}

	public ProcessingInstruction(String target, Map data) {
		String reason;
		/* 108 */if ((reason = Verifier
				.checkProcessingInstructionTarget(target)) != null) {
			/* 109 */throw new IllegalTargetException(target, reason);
		} else {
			/* 112 */this.target = target;
			/* 113 */setData(data);
			/* 106 */return;
		}
	}

	public final Object clone() {
		/* 375 */ProcessingInstruction pi = new ProcessingInstruction(target,
				rawData);
		/* 377 */return pi;
	}

	public final boolean equals(Object ob) {
		/* 352 */return ob == this;
	}

	public String getData() {
		/* 154 */return rawData;
	}

	public final String getSerializedForm() {
		/* 331 */return "<?" + target + " " + rawData + "?>";
	}

	public String getTarget() {
		/* 143 */return target;
	}

	public String getValue(String name) {
		/* 200 */if (mapData.containsKey(name)) {
			/* 201 */return (String) mapData.get(name);
		} else {
			/* 204 */return "";
		}
	}

	public final int hashCode() {
		/* 363 */return super.hashCode();
	}

	private Map parseData(String rawData) {
		/* 279 */Map data = new HashMap();
		/* 283 */for (StringTokenizer s = new StringTokenizer(rawData); s
				.hasMoreTokens();) {
			/* 289 */StringTokenizer t = new StringTokenizer(s.nextToken(),
					"='\"");
			/* 291 */if (t.countTokens() >= 2) {
				/* 292 */String name = t.nextToken();
				/* 293 */String value = t.nextToken();
				/* 295 */data.put(name, value);
			}
		}

		/* 299 */return data;
	}

	public boolean removeValue(String name) {
		/* 236 */if (mapData.remove(name) != null) {
			/* 237 */rawData = toString(mapData);
			/* 238 */return true;
		} else {
			/* 241 */return false;
		}
	}

	public ProcessingInstruction setData(String data) {
		/* 166 */rawData = data;
		/* 167 */mapData = parseData(data);
		/* 168 */return this;
	}

	public ProcessingInstruction setData(Map data) {
		/* 182 */rawData = toString(data);
		/* 183 */mapData = data;
		/* 184 */return this;
	}

	public ProcessingInstruction setValue(String name, String value) {
		/* 221 */mapData.put(name, value);
		/* 222 */rawData = toString(mapData);
		/* 223 */return this;
	}

	public final String toString() {
		/* 314 */return "[Processing Instruction: " + getSerializedForm() + "]";
	}

	private String toString(Map mapData) {
		/* 252 */StringBuffer rawData = new StringBuffer();
		String name;
		String value;
		/* 254 */for (Iterator i = mapData.keySet().iterator(); i.hasNext(); rawData
				.append(name).append("=\"").append(value).append("\" ")) {
			/* 256 */name = (String) i.next();
			/* 257 */value = (String) mapData.get(name);
		}

		/* 264 */rawData.setLength(rawData.length() - 1);
		/* 266 */return rawData.toString();
	}
}
