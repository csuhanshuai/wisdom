package com.cnst.wisdom.ui.view;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cnst.wisdom.R;
import com.cnst.wisdom.ui.adapter.TeachPlanDialogSelAd;

import java.util.List;

/**
 * <一句话功能描述>
 * <功能详细描述>
 *
 * @author hanshuai
 * @see [相关类/方法]
 * @since [产品/模板版本]
 */
public class ListDialog extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener
{
    private final List list;
    private final AppCompatActivity mActivity;
    private final Drawable selDrawable;
    private final Drawable unSelDrawable;
    private ListView mListView;
    private int selIndex;
    private int selPositon = -1;
    private TeachPlanDialogSelAd adapter;
    private int oldIndex;

    public ListDialog(AppCompatActivity activity, List list)
    {
        super(activity,R.style.no_title_dialog);
        this.list = list;
        this.mActivity = activity;
        this.selDrawable = activity.getResources().getDrawable(android.R.drawable.radiobutton_on_background);
        this.unSelDrawable = activity.getResources().getDrawable(android.R.drawable.radiobutton_off_background);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_list);
        Window window = getWindow();
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        window.setBackgroundDrawableResource(R.drawable.bg_dialog);
        window.setLayout((int)( width*0.8 ), ViewGroup.LayoutParams.WRAP_CONTENT);
        mListView = (ListView)findViewById(R.id.sel_list);
        adapter = new TeachPlanDialogSelAd(mActivity, list, selDrawable, unSelDrawable);
        adapter.setCurPosition(0);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        adapter.setCurPosition(position);
        adapter.notifyDataSetChanged();
        selIndex = position;
    }

    public TeachPlanDialogSelAd getAdapter()
    {
        return adapter;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.cancel:
                selIndex = oldIndex;
                dismiss();
                break;
            case R.id.confirm:
                oldIndex = selIndex;
                dismiss();
                break;
        }
    }

    public int getSelIndex()
    {
        return selIndex;
    }

    public void setSelIndex(int selText)
    {
        this.selIndex = selText;
    }

    public int getSelPositon()
    {
        return selPositon;
    }

    public void setSelPositon(int selPositon)
    {
        this.selPositon = selPositon;
    }
}
