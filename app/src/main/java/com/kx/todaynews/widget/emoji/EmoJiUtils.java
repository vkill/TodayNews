package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.kx.todaynews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmoJiUtils {

    
    public static List<String> toutiaoList;
    public static List<String> allRes;
    
    public static ArrayMap<String, Integer> toutiaoMap = null;
    public static ArrayMap<String, Integer> allMap = null;

    private static final Spannable.Factory factory = Spannable.Factory.getInstance();
    

    

    static {
        toutiaoList = new ArrayList<>();
        allRes = new ArrayList<>();
        toutiaoMap = new ArrayMap<>();
        allMap = new ArrayMap<>();

      //  mEmojiBeans.get


/**************************************************头条表情包**************************************************/
        toutiaoMap.put("[捂脸]", R.drawable.arl);     //  19
        toutiaoMap.put("[允悲]", R.drawable.arl);     //  19
        toutiaoMap.put("[大笑]", R.drawable.arq);     //  21
        toutiaoMap.put("[坏笑]", R.drawable.atg); //  8
        toutiaoMap.put("[呲牙]", R.drawable.atg); //  8
        toutiaoMap.put("[色]", R.drawable.arm);     //  2
        toutiaoMap.put("[流泪]", R.drawable.asv);      //  6
        toutiaoMap.put("[害羞]", R.drawable.aqy);      // 10
        toutiaoMap.put("[灵光一闪]", R.drawable.arj);  // 17
        toutiaoMap.put("[发怒]", R.drawable.at6);       // 7
        toutiaoMap.put("[抠鼻]", R.drawable.ask);   // 5
        toutiaoMap.put("[酷拽]", R.drawable.as_);   //4
        toutiaoMap.put("[耶]", R.drawable.ark);        //18
        toutiaoMap.put("[可爱]", R.drawable.ar9);      //11
        toutiaoMap.put("[机智]", R.drawable.arh);         //  15
        toutiaoMap.put("[打脸]", R.drawable.arp);        //  20
        toutiaoMap.put("[笑哭]", R.drawable.as8);   //30
        toutiaoMap.put("[我想静静]", R.drawable.asx);     // 61
        toutiaoMap.put("[泪奔]", R.drawable.ase);        // 44
        toutiaoMap.put("[赞]", R.drawable.asn);         // 52
        toutiaoMap.put("[祈祷]", R.drawable.asp);      // 54
        toutiaoMap.put("[玫瑰]", R.drawable.ast);           //  58

        /*********** 第二页 **********/
        toutiaoMap.put("[微笑]", R.drawable.aqx);        //  1
        toutiaoMap.put("[哈欠]", R.drawable.arr);             //  22
        toutiaoMap.put("[震惊]", R.drawable.ars);
        toutiaoMap.put("[送心]", R.drawable.art);
        toutiaoMap.put("[困]", R.drawable.aru);
        toutiaoMap.put("[what]", R.drawable.arv);
        toutiaoMap.put("[泣不成声]", R.drawable.arw);
        toutiaoMap.put("[小鼓掌]", R.drawable.arx);
        toutiaoMap.put("[大金牙]", R.drawable.ary);
        toutiaoMap.put("[偷笑]", R.drawable.as0);
        toutiaoMap.put("[石化]", R.drawable.as1);
        toutiaoMap.put("[思考]", R.drawable.as2);
        toutiaoMap.put("[吐血]", R.drawable.as3);
        toutiaoMap.put("[可怜]", R.drawable.as4);
        toutiaoMap.put("[嘘]", R.drawable.as5);
        toutiaoMap.put("[撇嘴]", R.drawable.as6);
        toutiaoMap.put("[黑线]", R.drawable.as7);
        toutiaoMap.put("[鼾睡]", R.drawable.atr);
        toutiaoMap.put("[雾霾]", R.drawable.as9);
        toutiaoMap.put("[奸笑]", R.drawable.asa);
        /*********** 第三页 **********/
        toutiaoMap.put("[得意]", R.drawable.asb);
        toutiaoMap.put("[憨笑]", R.drawable.asc);
        toutiaoMap.put("[抓狂]", R.drawable.asd);
        toutiaoMap.put("[惊呆]", R.drawable.arz);
        toutiaoMap.put("[钱]", R.drawable.asf);
        toutiaoMap.put("[吻]", R.drawable.asg);
        toutiaoMap.put("[恐惧]", R.drawable.ash);

        toutiaoMap.put("[笑]", R.drawable.asi);
        toutiaoMap.put("[快哭了]", R.drawable.asl);
        toutiaoMap.put("[翻白眼]", R.drawable.asl);
        toutiaoMap.put("[互粉]", R.drawable.asm);
        toutiaoMap.put("[皱眉]", R.drawable.atk);
        toutiaoMap.put("[擦汗]", R.drawable.atl);
        toutiaoMap.put("[红脸]", R.drawable.atm);

        toutiaoMap.put("[做鬼脸]", R.drawable.ato);
        toutiaoMap.put("[尬笑]", R.drawable.atn);
        toutiaoMap.put("[汗]", R.drawable.ati);
        toutiaoMap.put("[吐]", R.drawable.ats);
        toutiaoMap.put("[惊喜]", R.drawable.att);
        toutiaoMap.put("[摸头]", R.drawable.atj);

        /*********** 第四页 **********/

        toutiaoMap.put("[来看我]", R.drawable.ari);
        toutiaoMap.put("[委屈]", R.drawable.asy);
        toutiaoMap.put("[舔屏]", R.drawable.asz);
        toutiaoMap.put("[鄙视]", R.drawable.at0);
        toutiaoMap.put("[飞吻]", R.drawable.at1);
        toutiaoMap.put("[紫薇别走]", R.drawable.at3);
        toutiaoMap.put("[吐彩虹]", R.drawable.atw);

        toutiaoMap.put("[听歌]", R.drawable.at4);//
        toutiaoMap.put("[求抱抱]", R.drawable.at5);
        toutiaoMap.put("[周冬雨的凝视]", R.drawable.at7);
        toutiaoMap.put("[马思纯的微笑]", R.drawable.at8);
        toutiaoMap.put("[敲打]", R.drawable.atu);
        toutiaoMap.put("[绿帽子]", R.drawable.ath);
        toutiaoMap.put("[再见]", R.drawable.at2);

        toutiaoMap.put("[吃瓜群众]", R.drawable.atf);
        toutiaoMap.put("[强]", R.drawable.atp);
        toutiaoMap.put("[如花]", R.drawable.atq);
        toutiaoMap.put("[奋斗]", R.drawable.atv);
        toutiaoMap.put("[衰]", R.drawable.arf);
        toutiaoMap.put("[闭嘴]", R.drawable.arg);
        /*********** 第五页 **********/
        toutiaoMap.put("[晕]", R.drawable.are);
        toutiaoMap.put("[大哭]", R.drawable.atx);
        toutiaoMap.put("[左上]", R.drawable.ar0);
        toutiaoMap.put("[good]", R.drawable.aso);
        toutiaoMap.put("[握手]", R.drawable.ar1);
        toutiaoMap.put("[比心]", R.drawable.aty);
        toutiaoMap.put("[加油]", R.drawable.atz);

        toutiaoMap.put("[碰拳]", R.drawable.au0);
        toutiaoMap.put("[ok]", R.drawable.au1);
        toutiaoMap.put("[击掌]", R.drawable.aqz);
        toutiaoMap.put("[kiss]", R.drawable.asq);
        toutiaoMap.put("[去污粉]", R.drawable.asr);
        toutiaoMap.put("[666]", R.drawable.ass);
        toutiaoMap.put("[胡瓜]", R.drawable.asu);

        toutiaoMap.put("[撒花]", R.drawable.arb);
        toutiaoMap.put("[啤酒]", R.drawable.asw);
        toutiaoMap.put("[心]", R.drawable.ar6);
        toutiaoMap.put("[伤心]", R.drawable.ar7);
        toutiaoMap.put("[屎]", R.drawable.ar8);
        toutiaoMap.put("[18禁]", R.drawable.ar2);

        /*********** 第六页 **********/

        toutiaoMap.put("[吐舌]", R.drawable.at9);
        toutiaoMap.put("[呆无辜]", R.drawable.at_);
        toutiaoMap.put("[看]", R.drawable.ata);
        toutiaoMap.put("[白眼]", R.drawable.atb);
        toutiaoMap.put("[熊吉]", R.drawable.atc);
        toutiaoMap.put("[不看]", R.drawable.arc);
        toutiaoMap.put("[黑脸]", R.drawable.ate);

        toutiaoMap.put("[骷髅]", R.drawable.atd);
        toutiaoMap.put("[炸弹]", R.drawable.ard);
        toutiaoMap.put("[蛋糕]", R.drawable.ara);
        toutiaoMap.put("[礼物]", R.drawable.ar_);
        toutiaoMap.put("[刀]", R.drawable.ar3);
        toutiaoMap.put("[V5]", R.drawable.ar4);
        toutiaoMap.put("[给力]", R.drawable.ar5);

        toutiaoMap.put("[删除]", R.drawable.au2);


        toutiaoList.add("[捂脸]");
        toutiaoList.add("[大笑]");
        toutiaoList.add("[坏笑]");
        toutiaoList.add("[色]");
        toutiaoList.add("[流泪]");
        toutiaoList.add("[害羞]");
        toutiaoList.add("[灵光一闪]");
        toutiaoList.add("[发怒]");
        toutiaoList.add("[抠鼻]");
        toutiaoList.add("[酷拽]");
        toutiaoList.add("[耶]");
        toutiaoList.add("[可爱]");
        toutiaoList.add("[机智]");
        toutiaoList.add("[打脸]");
        toutiaoList.add("[笑哭]");
        toutiaoList.add("[我想静静]");
        toutiaoList.add("[泪奔]");
        toutiaoList.add("[赞]");
        toutiaoList.add("[祈祷]");
        toutiaoList.add("[玫瑰]");
        /*********** 第二页 **********/
        toutiaoList.add("[微笑]");
        toutiaoList.add("[哈欠]");
        toutiaoList.add("[震惊]");
        toutiaoList.add("[送心]");
        toutiaoList.add("[困]");
        toutiaoList.add("[what]");
        toutiaoList.add("[泣不成声]");
        toutiaoList.add("[小鼓掌]");
        toutiaoList.add("[大金牙]");
        toutiaoList.add("[偷笑]");
        toutiaoList.add("[石化]");
        toutiaoList.add("[思考]");
        toutiaoList.add("[吐血]");
        toutiaoList.add("[可怜]");
        toutiaoList.add("[嘘]");
        toutiaoList.add("[撇嘴]");
        toutiaoList.add("[黑线]");
        toutiaoList.add("[鼾睡]");
        toutiaoList.add("[雾霾]");
        toutiaoList.add("[奸笑]");

        toutiaoList.add("[得意]");
        toutiaoList.add("[憨笑]");
        toutiaoList.add("[抓狂]");
        toutiaoList.add("[惊呆]");
        toutiaoList.add("[钱]");
        toutiaoList.add("[吻]");
        toutiaoList.add("[恐惧]");
        toutiaoList.add("[笑]");
        toutiaoList.add("[快哭了]");
        toutiaoList.add("[翻白眼]");
        toutiaoList.add("[互粉]");
        toutiaoList.add("[皱眉]");
        toutiaoList.add("[擦汗]");
        toutiaoList.add("[红脸]");
        toutiaoList.add("[做鬼脸]");
        toutiaoList.add("[尬笑]");
        toutiaoList.add("[汗]");
        toutiaoList.add("[吐]");
        toutiaoList.add("[惊喜]");
        toutiaoList.add("[摸头]");

        toutiaoList.add("[来看我]");
        toutiaoList.add("[委屈]");
        toutiaoList.add("[舔屏]");
        toutiaoList.add("[鄙视]");
        toutiaoList.add("[飞吻]");
        toutiaoList.add("[紫薇别走]");
        toutiaoList.add("[吐彩虹]");
        toutiaoList.add("[听歌]");
        toutiaoList.add("[求抱抱]");
        toutiaoList.add("[周冬雨的凝视]");
        toutiaoList.add("[马思纯的微笑]");
        toutiaoList.add("[敲打]");
        toutiaoList.add("[绿帽子]");//
        toutiaoList.add("[再见]");
        toutiaoList.add("[吃瓜群众]");
        toutiaoList.add("[强]");
        toutiaoList.add("[如花]");
        toutiaoList.add("[奋斗]");
        toutiaoList.add("[衰]");
        toutiaoList.add("[闭嘴]");
        toutiaoList.add("[晕]");
        toutiaoList.add("[大哭]");
        toutiaoList.add("[左上]");
        toutiaoList.add("[good]");
        toutiaoList.add("[握手]");
        toutiaoList.add("[比心]");
        toutiaoList.add("[加油]");

        toutiaoList.add("[碰拳]");
        toutiaoList.add("[ok]");
        toutiaoList.add("[击掌]");
        toutiaoList.add("[kiss]");
        toutiaoList.add("[去污粉]");
        toutiaoList.add("[666]");
        toutiaoList.add("[胡瓜]");

        toutiaoList.add("[撒花]");
        toutiaoList.add("[啤酒]");
        toutiaoList.add("[心]");
        toutiaoList.add("[伤心]");
        toutiaoList.add("[屎]");
        toutiaoList.add("[18禁]");

        toutiaoList.add("[吐舌]");
        toutiaoList.add("[呆无辜]");
        toutiaoList.add("[看]");
        toutiaoList.add("[白眼]");
        toutiaoList.add("[熊吉]");
        toutiaoList.add("[不看]");
        toutiaoList.add("[黑脸]");

        toutiaoList.add("[骷髅]");
        toutiaoList.add("[炸弹]");
        toutiaoList.add("[蛋糕]");
        toutiaoList.add("[礼物]");
        toutiaoList.add("[刀]");
        toutiaoList.add("[V5]");
        toutiaoList.add("[给力]");


        allRes.addAll(toutiaoList);
        allMap.putAll((Map<? extends String, ? extends Integer>) toutiaoMap);

    }

    public static List<String> getResList(int type) {
                return toutiaoList;
    }

    public static List<String> getAllRes() {
        return allRes;
    }

    public static ArrayMap<String, Integer> getEmoJiMap(int type) {
                return toutiaoMap;
    }

    /**
     * 解析EmoJi表情
     * @return
     */
    public static SpannableString parseEmoJi(TextView editText, Context context, String content) {

        SpannableString spannable = new SpannableString(content);
        String reg = "\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]";//校验表情正则
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String regEmoJi = matcher.group();//获取匹配到的emoji字符串
            int start = matcher.start();//匹配到字符串的开始位置
            int end = matcher.end();//匹配到字符串的结束位置
            Integer resId = allMap.get(regEmoJi);//通过emoji名获取对应的表情id

            if (resId != null) {

                Drawable drawable = context.getResources().getDrawable(resId);
              //  drawable.setBounds(5, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
               // drawable.setBounds(5, 0, getTextHeight(editText)+5, getTextHeight(editText));
                CenterImageSpan imageSpan = new CenterImageSpan(context,resId);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannable;
    }
    private static  int getTextHeight(TextView editText){
        Paint.FontMetricsInt fontMetricsInt =  editText.getPaint().getFontMetricsInt();
        return   fontMetricsInt.descent - fontMetricsInt.ascent;
    }
}
