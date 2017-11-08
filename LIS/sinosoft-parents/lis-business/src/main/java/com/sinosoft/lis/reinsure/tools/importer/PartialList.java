

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   PartialList.java

package com.sinosoft.lis.reinsure.tools.importer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// Referenced classes of package org.jdom:
//            Element

class PartialList extends LinkedList {

	protected List backingList;
	protected Element parent;

	public PartialList(List backingList) {
		/* 106 */this(backingList, null);
	}

	public PartialList(List backingList, Element parent) {
		/* 93 */this.backingList = backingList;
		/* 94 */this.parent = parent;
	}

	public void add(int index, Object current) {
		/* 331 */if (index == size()) {
			/* 333 */addLast(current);
		} else {
			/* 336 */backingList.add(backingList.indexOf(get(index)), current);
			/* 338 */if (current instanceof Element) {
				/* 339 */((Element) current).setParent(parent);
			}
			/* 341 */super.add(index, current);
		}
	}

	public boolean add(Object o) {
		/* 182 */backingList.add(o);
		/* 184 */if (o instanceof Element) {
			/* 185 */((Element) o).setParent(parent);
		}
		/* 188 */return super.add(o);
	}

	public boolean addAll(int index, Collection c) {
		/* 260 */int insertIndex = backingList.indexOf(get(index));
		/* 262 */if (insertIndex != -1) {
			/* 263 */backingList.addAll(insertIndex, c);
		} else {
			/* 265 */backingList.addAll(c);
		}
		/* 268 */for (Iterator i = c.iterator(); i.hasNext();) {
			/* 269 */Object o = i.next();
			/* 270 */if (o instanceof Element) {
				/* 271 */((Element) o).setParent(parent);
			}
		}

		/* 275 */return super.addAll(index, c);
	}

	public boolean addAll(Collection c) {
		/* 227 */int index = backingList.indexOf(getLast());
		/* 229 */if (index != -1) {
			/* 230 */backingList.addAll(index, c);
		} else {
			/* 232 */backingList.addAll(c);
		}
		/* 235 */for (Iterator i = c.iterator(); i.hasNext();) {
			/* 236 */Object o = i.next();
			/* 237 */if (o instanceof Element) {
				/* 238 */((Element) o).setParent(parent);
			}
		}

		/* 242 */return super.addAll(c);
	}

	protected boolean addAllPartial(Collection c) {
		/* 386 */for (Iterator i = c.iterator(); i.hasNext(); addPartial(i
				.next())) {
		}
		/* 389 */return true;
	}

	public void addFirst(Object o) {
		/* 141 */int index = backingList.indexOf(getFirst());
		/* 142 */super.addFirst(o);
		/* 144 */if (index != -1) {
			/* 145 */backingList.add(index, o);
		} else {
			/* 147 */backingList.add(o);
		}
		/* 150 */if (o instanceof Element) {
			/* 151 */((Element) o).setParent(parent);
		}
	}

	public void addLast(Object o) {
		/* 160 */int index = backingList.indexOf(getLast());
		/* 161 */super.addLast(o);
		/* 163 */if (index != -1 && index < backingList.size()) {
			/* 164 */backingList.add(index + 1, o);
		} else {
			/* 166 */backingList.add(o);
		}
		/* 169 */if (o instanceof Element) {
			/* 170 */((Element) o).setParent(parent);
		}
	}

	protected void addPartial(Object o) {
		/* 374 */super.add(o);
	}

	public void clear() {
		/* 282 */for (Iterator i = iterator(); i.hasNext();) {
			/* 283 */Object o = i.next();
			/* 284 */backingList.remove(o);
			/* 285 */if (o instanceof Element) {
				/* 286 */((Element) o).setParent(null);
			}
		}

		/* 289 */super.clear();
	}

	public Object remove(int index) {
		/* 357 */Object o = super.remove(index);
		/* 358 */backingList.remove(o);
		/* 359 */if (o instanceof Element) {
			/* 360 */((Element) o).setParent(null);
		}
		/* 362 */return o;
	}

	public boolean remove(Object o) {
		/* 202 */backingList.remove(o);
		/* 204 */if (o instanceof Element) {
			/* 205 */((Element) o).setParent(null);
		}
		/* 208 */return super.remove(o);
	}

	public Object removeFirst() {
		/* 115 */Object o = super.removeFirst();
		/* 116 */backingList.remove(o);
		/* 117 */if (o instanceof Element) {
			/* 118 */((Element) o).setParent(null);
		}
		/* 120 */return o;
	}

	public Object removeLast() {
		/* 129 */Object o = super.removeLast();
		/* 130 */backingList.remove(o);
		/* 131 */if (o instanceof Element) {
			/* 132 */((Element) o).setParent(null);
		}
		/* 134 */return o;
	}

	public Object set(int index, Object current) {
		/* 303 */Object old = get(index);
		/* 304 */int backingIndex = backingList.indexOf(old);
		/* 305 */if (backingIndex != -1) {
			/* 306 */backingList.set(backingIndex, current);
		}
		/* 309 */if (old instanceof Element) {
			/* 310 */((Element) old).setParent(null);
		}
		/* 312 */if (current instanceof Element) {
			/* 313 */((Element) current).setParent(parent);
		}
		/* 316 */return super.set(index, current);
	}
}
