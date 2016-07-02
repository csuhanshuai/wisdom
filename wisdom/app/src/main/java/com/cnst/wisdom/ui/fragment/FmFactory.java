package com.cnst.wisdom.ui.fragment;

import com.cnst.wisdom.ui.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

public class FmFactory {

	private static final int ONE = 0;
	private static final int TWO = 1;
	private static final int THREE = 2;
	private static final int FOUR = 3;

	private Map<Integer,BaseFragment> fmCache = new HashMap<Integer,BaseFragment>();
	public BaseFragment createFragment(int type) {
		BaseFragment fragment = fmCache.get(type);
		if (fragment == null) {
			switch (type) {
			case ONE:
				fragment = new MsgFm();
				break;
			case TWO:
				fragment = new DynamicFm();
				break;
			case THREE:
				fragment = new TeachFm();
				break;
			case FOUR:
				fragment = new MeFm();
				break;
			}
			fmCache.put(type, fragment);
		}
		return fragment;
	}
}
