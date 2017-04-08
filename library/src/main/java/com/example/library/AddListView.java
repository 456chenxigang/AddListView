package com.example.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yiwei on 16/7/9.
 */
public class AddListView extends LinearLayout {

    private int itemColor;
    private int itemSelectorColor;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemDelBtnClickListener onItemDelBtnClickListener;
    private OnItemAddBtnClickListener onItemAddBtnClickListener;
    private List<String> mDatas;
    private LayoutInflater layoutInflater;
    private View convertView;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemDelBtnClickListener getOnItemDelBtnClickListener() {
        return onItemDelBtnClickListener;
    }

    public void setOnItemDelBtnClickListener(OnItemDelBtnClickListener onItemDelBtnClickListener){
        this.onItemDelBtnClickListener = onItemDelBtnClickListener;
    }

    public void setOnItemAddBtnClickListener(OnItemAddBtnClickListener onItemAddBtnClickListener){
        this.onItemAddBtnClickListener = onItemAddBtnClickListener;
    }

    public void setDatas(List<String> datas){
        if(datas == null ){
            datas = new ArrayList<String>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<String> getDatas(){
        return mDatas;
    }

    public AddListView(Context context) {
        super(context);
        notifyDataSetChanged();
    }

    public AddListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        notifyDataSetChanged();
    }

    public AddListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        notifyDataSetChanged();
    }

    private void initView() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_add, null, false);
        }
        ImageButton addBtn = (ImageButton) convertView.findViewById(R.id.add_image_button);
        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemAddBtnClickListener != null){
                    onItemAddBtnClickListener.onItemAddClick();
                }
            }
        });
        addView(convertView, -1, layoutParams);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        }finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged(){

        removeAllViews();
        if(mDatas == null || mDatas.size() == 0){
            initView();
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<mDatas.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }
        initView();
    }

    private View getView(final int position){
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = layoutInflater.inflate(R.layout.item_comment, null, false);

        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        ImageButton delBtn = (ImageButton) convertView.findViewById(R.id.del_imageButton);
        //final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

        final String name = mDatas.get(position);

        SpannableStringBuilder builder = new SpannableStringBuilder();
//        builder.append(": ");

        builder.append(setClickableSpan(name,"处理", null));
        commentTv.setText(builder);

        //commentTv.setMovementMethod(circleMovementMethod);
        delBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemDelBtnClickListener != null){
                    onItemDelBtnClickListener.onItemDelClick(position);
                }
            }
        });
        commentTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (circleMovementMethod.isPassToTv()) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(position);
                       // onItemDelBtnClickListener.onItemAddClick(position);
                    }
                //}
            }
        });
        return convertView;
    }

    @NonNull
    private SpannableString setClickableSpan(final String name, final String textStr, final String time) {
        SpannableString subjectSpanText = new SpannableString(name);
//        subjectSpanText.setSpan(new SpannableClickable(itemColor){
//                                    @Override
//                                    public void onClick(View widget) {
//                                        if (time != null){
//                                            Toast.makeText(MyApplication.getContext(), textStr+"时间:" + time, Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            Toast.makeText(MyApplication.getContext(),"未获得时间", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }, 0, subjectSpanText.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(int position);
    }

    public static interface OnItemDelBtnClickListener{
        public void onItemDelClick(int position);
    }

    public static interface OnItemAddBtnClickListener{
        public void onItemAddClick();
    }
}
