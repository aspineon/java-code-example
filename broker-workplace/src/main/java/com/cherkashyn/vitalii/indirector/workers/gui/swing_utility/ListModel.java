package com.cherkashyn.vitalii.indirector.workers.gui.swing_utility;

import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

public class ListModel<T> extends AbstractListModel<T>{

	private static final long serialVersionUID = 1L;
	
	private final List<T> data;
	
	@SuppressWarnings("unchecked")
	public ListModel(List<T> listData){
		if(listData==null){
			this.data=Collections.EMPTY_LIST;
		}else{
			this.data=listData;
		}
	}
	
	@Override
	public T getElementAt(int index) {
		return this.data.get(index);
	}

	@Override
	public int getSize() {
		return this.data.size();
	}
	
}
