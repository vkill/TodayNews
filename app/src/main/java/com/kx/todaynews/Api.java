package com.kx.todaynews;


import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.bean.article.ArticleReplyDiggListBean;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.bean.article.TextDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by admin on 2018/7/22.
 */
public interface Api {
    @Headers({"Domain-Name: 1f"}) // Add the Domain-Name header
    @GET("api/news/feed/v88/?support_rn=4&category=news_hot&refer=1&refresh_reason=1&count=20&loc_mode=7&latitude=30.480589784929037&longitude=114.41191059527866&city=%E6%AD%A6%E6%B1%89%E5%B8%82&tt_from=pull&plugin_enable=3&iid=44307940948&device_id=57557651831&ac=wifi&channel=oppo-cpa&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=504794%2C467893%2C499728%2C496463%2C239098%2C500089%2C170988%2C493249%2C398175%2C498276%2C442123%2C374119%2C495950%2C488058%2C265169%2C489317%2C501960%2C491677%2C276203%2C471716%2C458950%2C435214%2C459650%2C459993%2C494638%2C494151%2C377093%2C502846%2C416055%2C392485%2C470631%2C488347%2C496453%2C496908%2C378450%2C492327%2C471406%2C496428%2C506343%2C271178%2C424178%2C326524%2C326532%2C496389%2C497176%2C345191%2C504890%2C506916%2C424606%2C455644%2C493304%2C458724%2C424176%2C472442%2C214069%2C505147%2C507002%2C506641%2C442255%2C507511%2C492491%2C464116%2C507429%2C489510%2C280448%2C281300%2C325612%2C507721%2C481567%2C498707%2C498551%2C386892%2C498375%2C397993%2C467513%2C444464%2C506751%2C502085%2C507011%2C425530%2C497693%2C500152%2C486951%2C261579%2C403271%2C497530%2C491727%2C293033%2C457481%2C502679%2C487523%2C491257%2C502992&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_group=94563%2C102756%2C181428&ab_feature=94563%2C102756&abflag=3&ssmix=a&device_type=OPPO+A83&device_brand=OPPO&language=zh&os_api=25&os_version=7.1.1&uuid=869322038192058&openudid=a7e11d80096ec1b5&manifest_version_code=691&update_version_code=69111&fp=DlT_c2UuFYFIFlwOPlU1F2Kecrcb&tma_jssdk_version=1.1.0.9&pos=5r_-9Onkv6e_ezA7eywUeCUfv7G_8fLz-vTp6Pn4v6esrKmzqayspKytqKSor6qlq6uxv_H86fTp6Pn4v6eurbOppa2opaSqpamkr6Strqqxv_zw_O3e9Onkv6e_ezA7eywUeCUfv7G__PD87dHy8_r06ej5-L-nrKyps6mtqK-lqbG__PD87dH86fTp6Pn4v6eurbOpqqmlrKrg&rom_version=coloros_v3.2_a83_11_a.23_180718&plugin=26958&as=a2b5b53a5e535b01901081&mas=004a116c0d32358e9c361a6145cdb63236445ccd44ccd445e2&cp=50bfaa075a13dq1")
           // "http://lf.snssdk.com/api/news/feed/v88/?list_count=7&support_rn=4&category=news_hot&refer=1&refresh_reason=1&session_refresh_idx=3&count=20&min_behot_time=1537233099&last_refresh_sub_entrance_interval=1537233213&loc_mode=7&loc_time=1537233088&latitude=30.480589784929037&longitude=114.41191059527866&city=%E6%AD%A6%E6%B1%89%E5%B8%82&tt_from=pull&plugin_enable=3&iid=44307940948&device_id=57557651831&ac=wifi&channel=oppo-cpa&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=504794%2C467893%2C499728%2C496463%2C239098%2C500089%2C170988%2C493249%2C398175%2C498276%2C442123%2C374119%2C495950%2C488058%2C265169%2C489317%2C501960%2C491677%2C276203%2C471716%2C458950%2C435214%2C459650%2C459993%2C494638%2C494151%2C377093%2C502846%2C416055%2C392485%2C470631%2C488347%2C496453%2C496908%2C378450%2C492327%2C471406%2C496428%2C506343%2C271178%2C424178%2C326524%2C326532%2C496389%2C497176%2C345191%2C504890%2C506916%2C424606%2C455644%2C493304%2C458724%2C424176%2C472442%2C214069%2C505147%2C507002%2C506641%2C442255%2C507511%2C492491%2C464116%2C507429%2C489510%2C280448%2C281300%2C325612%2C507721%2C481567%2C498707%2C498551%2C386892%2C498375%2C397993%2C467513%2C444464%2C506751%2C502085%2C507011%2C425530%2C497693%2C500152%2C486951%2C261579%2C403271%2C497530%2C491727%2C293033%2C457481%2C502679%2C487523%2C491257%2C502992&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_group=94563%2C102756%2C181428&ab_feature=94563%2C102756&abflag=3&ssmix=a&device_type=OPPO+A83&device_brand=OPPO&language=zh&os_api=25&os_version=7.1.1&uuid=869322038192058&openudid=a7e11d80096ec1b5&manifest_version_code=691&resolution=720*1344&dpi=320&update_version_code=69111&_rticket=1537233213989&fp=DlT_c2UuFYFIFlwOPlU1F2Kecrcb&tma_jssdk_version=1.1.0.9&pos=5r_-9Onkv6e_ezA7eywUeCUfv7G_8fLz-vTp6Pn4v6esrKmzqayspKytqKSor6qlq6uxv_H86fTp6Pn4v6eurbOppa2opaSqpamkr6Strqqxv_zw_O3e9Onkv6e_ezA7eywUeCUfv7G__PD87dHy8_r06ej5-L-nrKyps6mtqK-lqbG__PD87dH86fTp6Pn4v6eurbOpqqmlrKrg&rom_version=coloros_v3.2_a83_11_a.23_180718&plugin=26958&ts=1537233214&as=a2b5b53a5e535b01901081&mas=004a116c0d32358e9c361a6145cdb63236445ccd44ccd445e2&cp=50bfaa075a13dq1")
    Observable<HotBean> getHotData(
            @Query("category")String category,
            @Query("session_refresh_idx")int session_refresh_idx,
            @Query("list_count")int list_count,
            @Query("min_behot_time") long min_behot_time ,
            @Query("last_refresh_sub_entrance_interval") long last_refresh_sub_entrance_interval ,
            @Query("_rticket") long _rticket,
            @Query("ts") long ts,
           // @Query("loc_time") long loc_time,
            @Query("resolution") String resolution ,
            @Query("dpi") String dpi
    );

 /**
  * 查询 新闻详情 Html 数据的接口
  *    content   查询新闻id的具体内容 主要是取content数据(即Html页面数据)    full   查询新闻的全部信息
  */
    @Headers({"Domain-Name: a3"}) // Add the Domain-Name header
 @GET("article/content/21/1/{group_id}/{group_id}/1/0?iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&" +
            "app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&" +
            "ab_version=425530%2C511489%2C486952%2C442428%2C500130%2C504794%2C494121%2C511895%2C499728%2C478963%2C496464%2C239097%2C500092%2C170988%2C493249%2C480607%2C374117%2C495946%2C478532%2C265169%2C489312%2C501963%2C509852%2C508959%2C276206%2C453560%2C435216%2C459650%2C459993%2C511225%2C502846%2C500386%2C416055%2C510641%2C512161%2C392484%2C511164%2C488347%2C496452%2C495897%2C378451%2C471406%2C510754%2C512054%2C512070%2C508932%2C509307%2C468954%2C271178%2C424178%2C326524%2C326532%2C476036%2C511779%2C509819%2C496389%2C345191%2C504889%2C512336%2C512047%2C504723%2C424606%2C512074%2C455643%2C511272%2C424177%2C214069%2C507002%2C442255%2C511870%2C489509%2C280449%2C281299%2C511104%2C325618%2C508560%2C510116%2C511146%2C498551%2C509887%2C508594%2C386889%2C498375%2C511556%2C397995%2C467514%2C512007%2C444464%2C506751%2C509800%2C261578%2C403270%2C502709%2C491728%2C491265%2C293032%2C457481%2C502679%2C510535%2C491255%2C507368&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&" +
            "ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0" +
            "&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&" +
            "fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&tma_jssdk_version=1.1.0.9&rom_version=miui_v9_v9.6.2.0.ncfcnfd&plugin=26958&" +
            "as=a2855ada5ae82b51f39825&mas=000ebce37e555de37d0a275deae30fef724ea6a258dd8560b0"
    )
    Observable<TextDetailInfo> getArticleDetail(
            @Path("group_id") String group_id1,
            @Path("group_id") String group_id2,
            @Query("_rticket") long _rticket,
            @Query("ts") long ts
   );

    /**
     * 获取新闻评论数据
     */
    //  http://lf.snssdk.com/article/v3/tab_comments/?group_id=6603942533846270472&item_id=6603942533846270472&aggr_type=1&count=20&offset=0&tab_index=0&fold=1&iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=468954%2C271178%2C424178%2C326524%2C326532%2C476036%2C513126%2C496389%2C345191%2C504889%2C512336%2C512047%2C504723%2C513201%2C424606%2C514035%2C455643%2C424177%2C214069%2C513805%2C507002%2C442255%2C511870%2C512958%2C489509%2C280449%2C281299%2C513401%2C511104%2C325618%2C508560%2C510116%2C512807%2C498551%2C509887%2C508594%2C386889%2C498375%2C397995%2C467514%2C513891%2C512007%2C444464%2C506751%2C512806%2C425530%2C511489%2C512678%2C486952%2C442428%2C500130%2C504794%2C494121%2C499728%2C478963%2C496464%2C239097%2C500092%2C170988%2C493249%2C480607%2C374117%2C495946%2C478532%2C489312%2C501963%2C509852%2C513538%2C276206%2C453560%2C435216%2C459650%2C459993%2C511225%2C500386%2C416055%2C510641%2C392484%2C511164%2C495897%2C378451%2C471406%2C510754%2C513728%2C508932%2C509307%2C512915%2C261578%2C403270%2C491728%2C491265%2C293032%2C457481%2C502679%2C510535%2C491255%2C507368&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&_rticket=1537609732549&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&tma_jssdk_version=1.2.2.4&pos=5r_-9Onkv6e_ezA7eywUeCUfv7G_8fLz-vTp6Pn4v6esrKmzqaqqrKupsb_x_On06ej5-L-nrq2zqK2tqqmusb_88Pzt3vTp5L-nv3swO3ssFHglH7-xv_zw_O3R8vP69Ono-fi_p6ysqbOppa-lra6xv_zw_O3R_On06ej5-L-nrq2zqaSkpa2k4A%3D%3D&rom_version=miui_v9_v9.6.2.0.ncfcnfd&plugin=26958&ts=1537609732&as=a245b18ae4b0cbc0a68110&mas=0014a6a332b1c033c57c10923e9a967fc0684e0cd2454cccde
    @Headers({"Domain-Name: 1f"}) // Add the Domain-Name header
    @GET("article/v3/tab_comments/?&aggr_type=1&count=20&fold=1&iid=44267707161&device_id=57548705831&ac=wifi" +
            "&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&" +
            "ab_version=468954%2C271178%2C424178%2C326524%2C326532%2C476036%2C513126%2C496389%2C345191%2C504889%2C512336%2C512047%2C504723%2C513201%2C424606%2C514035%2C455643%2C424177%2C214069%2C513805%2C507002%2C442255%2C511870%2C512958%2C489509%2C280449%2C281299%2C513401%2C511104%2C325618%2C508560%2C510116%2C512807%2C498551%2C509887%2C508594%2C386889%2C498375%2C397995%2C467514%2C513891%2C512007%2C444464%2C506751%2C512806%2C425530%2C511489%2C512678%2C486952%2C442428%2C500130%2C504794%2C494121%2C499728%2C478963%2C496464%2C239097%2C500092%2C170988%2C493249%2C480607%2C374117%2C495946%2C478532%2C489312%2C501963%2C509852%2C513538%2C276206%2C453560%2C435216%2C459650%2C459993%2C511225%2C500386%2C416055%2C510641%2C392484%2C511164%2C495897%2C378451%2C471406%2C510754%2C513728%2C508932%2C509307%2C512915%2C261578%2C403270%2C491728%2C491265%2C293032%2C457481%2C502679%2C510535%2C491255%2C507368&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0" +
            "&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1" +
            "&tma_jssdk_version=1.2.2.4&pos=5r_-9Onkv6e_ezA7eywUeCUfv7G_8fLz-vTp6Pn4v6esrKmzqaqqrKupsb_x_On06ej5-L-nrq2zqK2tqqmusb_88Pzt3vTp5L-nv3swO3ssFHglH7-xv_zw_O3R8vP69Ono-fi_p6ysqbOppa-lra6xv_zw_O3R_On06ej5-L-nrq2zqaSkpa2k4A%3D%3D" +
            "&rom_version=miui_v9_v9.6.2.0.ncfcnfd&plugin=26958&&as=a245b18ae4b0cbc0a68110&mas=0014a6a332b1c033c57c10923e9a967fc0684e0cd2454cccde")
    Observable<ArticleTabCommentsBean> getTabComments(
            @Query("offset") int offset,
            @Query("group_id") String groupId,
            @Query("item_id") String itemId,
            @Query("_rticket") long rTicket,
            @Query("ts") long ts);

    /**
     *  获取回复列表数据
     */
    @Headers({"Domain-Name: 1f"}) // Add the Domain-Name header
     @GET("2/comment/v3/reply_list/?count=20&iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&" +
             "ab_version=496389%2C536182%2C531432%2C518641%2C504723%2C513201%2C523417%2C525310%2C424177%2C214069%2C442255%2C519256%2C523095%2C280449%2C523503%2C281299%2C513401%2C523456%2C325618%2C526720%2C524588%2C535577%2C498375%2C467514%2C515673%2C444464%2C425530%2C511489%2C486952%2C442428%2C494121%2C239097%2C500092%2C170988%2C493249%2C523525%2C374117%2C495946%2C478532%2C516058%2C517715%2C489312%2C501963%2C276206%2C533728%2C533847%2C435216%2C459650%2C459993%2C536020%2C416055%2C533534%2C392484%2C470731%2C520076%2C378451%2C471406%2C522904%2C519795%2C523156%2C509307%2C512915%2C468954%2C271178%2C424178%2C536459%2C326524%2C523980%2C326532%2C535661%2C261578%2C403270%2C293032%2C457481%2C510535%2C491255%2C525040&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111" +
             "&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&tma_jssdk_version=1.2.2.4&rom_version=miui_v9_v9.6.2.0.ncfcnfd&plugin=26958&" +
             "as=a205fb9b1709bb2cdc5971&mas=0005d62e1bdfda633339ae93acf52100860a42acc85e2d4a90")
    Observable<ArticleReplyListBean> getArticleReplyList(
            @Query("offset") int offset,
            @Query("id") String replyId,
            @Query("_rticket") long rTicket,
            @Query("ts") long ts);

    /**
     *  获取回复数据点赞列表
     */
    @Headers({"Domain-Name: 1f"}) // Add the Domain-Name header
    @GET("2/comment/v1/digg_list/?count=20&&iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=425530%2C537722%2C538539%2C486952%2C442428%2C494121%2C537120%2C239097%2C500092%2C170988%2C493249%2C523525%2C374117%2C495946%2C478532%2C516058%2C517715%2C489312%2C501963%2C276206%2C537597%2C533847%2C435216%2C537153%2C459650%2C459993%2C538044%2C536020%2C416055%2C392484%2C470731%2C520076%2C378451%2C471406%2C522904%2C519795%2C523156%2C509307%2C512915%2C468954%2C271178%2C424178%2C536459%2C326524%2C537133%2C326532%2C537903%2C496389%2C537857%2C518641%2C504723%2C513201%2C523417%2C537553%2C525310%2C424177%2C214069%2C538015%2C442255%2C523095%2C537165%2C280449%2C523503%2C281299%2C523456%2C325618%2C526720%2C524588%2C537141%2C498375%2C467514%2C515673%2C444464%2C538429%2C491255%2C525040%2C261578%2C403270%2C293032%2C457481&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111" +
            "&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&tma_jssdk_version=1.2.2.4&rom_version=miui_v9_v9.6.2.0.ncfcnfd&plugin=26958" +
            "&as=a2250f5bca1deb8ddd7336&mas=0028a47499749589e0f4583e76d4628b3fef4c4c860a08a839")
    Observable<ArticleReplyDiggListBean> getArticleReplyDiggList(
            @Query("id") String diggId,
            @Query("_rticket") long rTicket,
            @Query("ts") long ts);


}
