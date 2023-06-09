package xyz.fycz.myreader.application;

import android.util.Log;

import xyz.fycz.myreader.R;
import xyz.fycz.myreader.common.APPCONST;
import xyz.fycz.myreader.entity.Setting;
import xyz.fycz.myreader.enums.BookcaseStyle;
import xyz.fycz.myreader.enums.LocalBookSource;
import xyz.fycz.myreader.greendao.service.BookGroupService;
import xyz.fycz.myreader.util.CacheHelper;
import xyz.fycz.myreader.util.SharedPreUtils;
import xyz.fycz.myreader.webapi.crawler.ReadCrawlerUtil;

import static xyz.fycz.myreader.application.App.getVersionCode;


public class SysManager {

    private static Setting mSetting;

    /**
     * 获取设置
     *
     * @return
     */
    public static Setting getSetting() {
        if (mSetting != null) {
            return mSetting;
        }
        mSetting = (Setting) CacheHelper.readObject(APPCONST.FILE_NAME_SETTING);
        if (mSetting == null) {
            mSetting = getDefaultSetting();
            saveSetting(mSetting);
        }
        return mSetting;
    }

    public static Setting getNewSetting() {
        Setting setting = (Setting) CacheHelper.readObject(APPCONST.FILE_NAME_SETTING);
        if (setting == null) {
            setting = getDefaultSetting();
            saveSetting(setting);
        }
        return setting;
    }


    /**
     * 保存设置
     *
     * @param setting
     */
    public static void saveSetting(Setting setting) {
        CacheHelper.saveObject(setting, APPCONST.FILE_NAME_SETTING);
    }


    /**
     * 默认设置
     *
     * @return
     */
    private static Setting getDefaultSetting() {
        Setting setting = new Setting();
        setting.setDayStyle(true);
        setting.setBookcaseStyle(BookcaseStyle.listMode);
        setting.setNewestVersionCode(getVersionCode());
        setting.setAutoSyn(false);
        setting.setMatchChapter(true);
        setting.setMatchChapterSuitability(0.7f);
        setting.setCatheGap(150);
        setting.setRefreshWhenStart(true);
        setting.setOpenBookStore(true);
        setting.setSettingVersion(APPCONST.SETTING_VERSION);
        setting.setSourceVersion(APPCONST.SOURCE_VERSION);
        setting.setHorizontalScreen(false);
        setting.initReadStyle();
        setting.setCurReadStyleIndex(1);
        return setting;
    }

    public static void regetmSetting() {
        mSetting = (Setting) CacheHelper.readObject(APPCONST.FILE_NAME_SETTING);
    }


    /**
     * 重置设置
     */

    public static void resetSetting() {
        Setting setting = getSetting();
        switch (setting.getSettingVersion()) {
            case 10:
            default:
                setting.initReadStyle();
                setting.setCurReadStyleIndex(1);
                setting.setSharedLayout(true);
                Log.d("SettingVersion", "" + 10);
            case 11:
                Log.d("SettingVersion", "" + 11);
            case 12:
                Log.d("SettingVersion", "" + 12);
        }
        setting.setSettingVersion(APPCONST.SETTING_VERSION);
        saveSetting(setting);
    }

    public static void resetSource() {
        Setting setting = getSetting();
        switch (setting.getSourceVersion()) {
            case 0:
            default:
                ReadCrawlerUtil.addReadCrawler(LocalBookSource.miaobi, LocalBookSource.dstq, LocalBookSource.xs7, LocalBookSource.du1du, LocalBookSource.paiotian);
                ReadCrawlerUtil.removeReadCrawler("cangshu99");
                Log.d("SourceVersion", "" + 0);
            case 1:
                ReadCrawlerUtil.addReadCrawler(LocalBookSource.laoyao, LocalBookSource.xingxing, LocalBookSource.shiguang, LocalBookSource.xiagu, LocalBookSource.hongchen);
                Log.d("SourceVersion", "" + 1);
            case 2:
                //ReadCrawlerUtil.addReadCrawler(LocalBookSource.rexue, LocalBookSource.chuanqi);
                ReadCrawlerUtil.addReadCrawler(LocalBookSource.chuanqi);
                Log.d("SourceVersion", "" + 2);
            case 3:
                ReadCrawlerUtil.resetReadCrawlers();
                Log.d("SourceVersion", "" + 3);
            case 4:
                ReadCrawlerUtil.removeReadCrawler("qiqi", "rexue", "pinshu");
                ReadCrawlerUtil.addReadCrawler(LocalBookSource.bijian, LocalBookSource.yanqinglou, LocalBookSource.wolong);
                Log.d("SourceVersion", "" + 4);
            case 5:
                ReadCrawlerUtil.addReadCrawler(LocalBookSource.ewenxue, LocalBookSource.shuhaige,
                        LocalBookSource.luoqiu, LocalBookSource.zw37, LocalBookSource.xbiquge,
                        LocalBookSource.zaishuyuan);
                Log.d("SourceVersion", "" + 5);
            case 6:
                SharedPreUtils.getInstance().putString(App.getmContext().getString(R.string.searchSource), "");
                Log.d("SourceVersion", "" + 5);
                break;
        }
        setting.setSourceVersion(APPCONST.SOURCE_VERSION);
        saveSetting(setting);
    }
}
