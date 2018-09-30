package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.kx.todaynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */

public class EmoJiHelper {

    private Context mContext;
    private EditText sendEt;
    private static int EMOJI_PAGE_COUNT = 20;
    private int mPageNum;
    private List<String> emojiResList;
    private int type;

    public EmoJiHelper(int type, Context mContext, EditText sendEt) {
        this.mContext = mContext;
        this.sendEt = sendEt;
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
       GridView egGridView = containerView.findViewById(R.id.eg_gridView);
        egGridView.setGravity(Gravity.CENTER_VERTICAL);
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
        mEmoJiList.add(EMOJI_DETELE);

        final EmoJiAdapter mEmoJiAdapter = new EmoJiAdapter(type, mContext, position, mEmoJiList);
        egGridView.setAdapter(mEmoJiAdapter);
        egGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionIndex, long id) {
                String fileName = mEmoJiAdapter.getItem(positionIndex);
                // 不是删除键，显示表情
                if (!TextUtils.equals(EMOJI_DETELE,fileName)) {
                    showEmoJi(fileName);
                } else {
                    // 删除文字或者表情
                    deleteContent();
                }
            }
        });
        return containerView;
    }

    /**
     * 显示EmoJi表情
     */
    private void showEmoJi(String fileName) {
        int selectionStart = sendEt.getSelectionStart();
        String body = sendEt.getText().toString();
        StringBuilder stringBuilder = new StringBuilder(body);
        stringBuilder.insert(selectionStart, fileName);
        sendEt.setText(EmoJiUtils.parseEmoJi(sendEt,mContext, stringBuilder.toString()));
        sendEt.setSelection(selectionStart + fileName.length());
    }

    /**
     * 删除表情或文字
     */
    private void deleteContent() {
        if (!TextUtils.isEmpty(sendEt.getText())) {
            //获取光标位置
            int selectionStart = sendEt.getSelectionStart();
            if (selectionStart > 0) {
                String body = sendEt.getText().toString();
                //获取最后一个字符
                String lastStr = body.substring(selectionStart - 1, selectionStart);
                //表情
                if ((EMOJI_END).equals(lastStr)) {
                    //从中间开始删除
                    if (selectionStart < body.length()) {
                        body = body.substring(0, selectionStart);
                    }
                    int i = body.lastIndexOf(EMOJI_START);
                    if (i != -1) {
                        //截取表情码
                        String tempStr = body.substring(i, selectionStart);
                        //校验是否是表情
                        if (EmoJiUtils.getAllRes().contains(tempStr)) {
                            //删除表情
                            sendEt.getEditableText().delete(i, selectionStart);
                        } else {
                            //删除一个字符
                            sendEt.getEditableText().delete(selectionStart - 1, selectionStart);
                        }
                    } else {
                        sendEt.getEditableText().delete(selectionStart - 1, selectionStart);
                    }
                } else {//非表情
                    sendEt.getEditableText().delete(selectionStart - 1, selectionStart);
                }
            }
        }
    }
    private static final String EMOJI_START = "[";
    private static final String EMOJI_END = "]";
    private static final String EMOJI_DETELE = "[删除]";
}
