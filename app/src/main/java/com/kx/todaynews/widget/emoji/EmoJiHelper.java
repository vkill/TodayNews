package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */

public class EmoJiHelper {

    private Context mContext;
    private EditText et_input_container;
    private static int EMOJI_PAGE_COUNT = 20;
    private int mPageNum;
    private List<String> emojiResList;
    private int type;

    public EmoJiHelper(int type, Context mContext, EditText et_input) {
        this.mContext = mContext;
        this.et_input_container = et_input;
        this.type = type;
        this.emojiResList = EmoJiUtils.getResList(type);
        getPagers();
    }

    public List<View> getPagers() {
        List<View> pageViewList = new ArrayList<>();
        //每一页表情的view
        mPageNum = (int) Math.ceil(emojiResList.size() * 1.0f / EMOJI_PAGE_COUNT);
        for (int position = 1; position <= mPageNum; position++) {
            pageViewList.add(getGridView(position));
        }
        return pageViewList;
    }

    private View getGridView(int position) {
        List mEmoJiList = new ArrayList<>();
        View containerView = View.inflate(mContext, R.layout.container_gridview, null);
       GridView eg_gridView = containerView.findViewById(R.id.eg_gridView);
        eg_gridView.setGravity(Gravity.CENTER_VERTICAL);
        List<String> emojiPageList = null;
        //最后一页
        if (position == mPageNum) {
            emojiPageList = emojiResList.subList((position - 1) * EMOJI_PAGE_COUNT, emojiResList.size());
        } else {
            emojiPageList = emojiResList.subList((position - 1) * EMOJI_PAGE_COUNT, EMOJI_PAGE_COUNT * position);
        }
        mEmoJiList.addAll(emojiPageList);
        //添加删除表情
//        if (position == mPageNum) {
//            int res = EMOJI_PAGE_COUNT - emojiPageList.size();
//            for (int i = 0; i < res; i++)
//                mEmoJiList.add("[空格]");
//        }
        mEmoJiList.add("[删除]");

        final EmoJiAdapter mEmoJiAdapter = new EmoJiAdapter(type, mContext, position, mEmoJiList);
        eg_gridView.setAdapter(mEmoJiAdapter);
        eg_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionIndex, long id) {
                String fileName = mEmoJiAdapter.getItem(positionIndex);
                ToastUtils.showToast(fileName);
                // 不是删除键，显示表情
//                if (fileName != "[删除]") {
//                    showEmoJi(fileName);
//                    // 删除文字或者表情
//                } else {
//                    deleteContent();
//                }
            }
        });
        return containerView;
    }

    /**
     * 显示EmoJi表情
     *
     * @param fileName
     */
    private void showEmoJi(String fileName) {
        int selectionStart = et_input_container.getSelectionStart();
        String body = et_input_container.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(body);
        stringBuilder.insert(selectionStart, fileName);
        et_input_container.setText(EmoJiUtils.parseEmoJi(type, mContext, stringBuilder.toString()));
        et_input_container.setSelection(selectionStart + fileName.length());
    }

    /**
     * 删除表情或文字
     */
    private void deleteContent() {
        if (!TextUtils.isEmpty(et_input_container.getText())) {
            //获取光标位置
            int selectionStart = et_input_container.getSelectionStart();
            if (selectionStart > 0) {
                String body = et_input_container.getText().toString();
                //获取最后一个字符
                String lastStr = body.substring(selectionStart - 1, selectionStart);
                //表情
                if (("]").equals(lastStr)) {
                    //从中间开始删除
                    if (selectionStart < body.length()) {
                        body = body.substring(0, selectionStart);
                    }
                    int i = body.lastIndexOf("[");
                    if (i != -1) {
                        //截取表情码
                        String tempStr = body.substring(i, selectionStart);
                        //校验是否是表情
                        if (EmoJiUtils.getAllRes().contains(tempStr)) {
                            //删除表情
                            et_input_container.getEditableText().delete(i, selectionStart);
                        } else {
                            //删除一个字符
                            et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                        }
                    } else {
                        et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                    }
                } else {//非表情
                    et_input_container.getEditableText().delete(selectionStart - 1, selectionStart);
                }
            }
        }
    }
}
