package com.like.customview;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.like.adapter.DropdownListAdapter;
import com.like.fitness.R;

public class TimePopWindow extends PopupWindow implements OnItemClickListener {
	private Context mContext;
	private ListView mListView;
	private List<Integer> mDatas;
	private String mUnit;
	private DropdownListAdapter mAdapter;
	private TextView mTargetView;
	private IOnItemSelect mOnItemSelect;
	private int currentValue;

	public TimePopWindow(Context context, TextView target, List<Integer> datas,
			String unit) {
		super();
		this.mContext = context;
		this.mDatas = datas;
		this.mUnit = unit;
		this.mTargetView = target;
		init();
	}

	public void updateDatas(List<Integer> datas) {
		this.mDatas = datas;
		if(mAdapter == null) {
			return;
		}
		mAdapter.updateList(mDatas);
	}

	public void setOnItemSelect(IOnItemSelect onItemSelect) {
		this.mOnItemSelect = onItemSelect;
	}

	private void init() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.custom_spinner_dropdown, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);

		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);

		mListView = (ListView) view.findViewById(R.id.dropdown_list);

		mAdapter = new DropdownListAdapter(mContext, mDatas, mUnit);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String data = (mDatas.get(position) >= 10 ? mDatas.get(position) + ""
				: "0" + mDatas.get(position));
		this.currentValue = mDatas.get(position);
		
		this.mTargetView.setText(data + mUnit);
		if (mOnItemSelect != null) {
			mOnItemSelect.onItemSelect(mTargetView.getId(), position);
		}

		dismiss();

	}

	public interface IOnItemSelect {
		public void onItemSelect(int targetId, int position);
	}
	
	public int getCurrentValue(){
		return currentValue;
	}

}
