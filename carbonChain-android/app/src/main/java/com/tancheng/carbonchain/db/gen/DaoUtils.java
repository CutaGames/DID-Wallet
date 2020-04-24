package com.tancheng.carbonchain.db.gen;

import android.content.Context;
import android.util.Log;
import com.tancheng.carbonchain.bean.UserInfoBean;
import java.util.List;

/**
 * dao工具类
 */
public class DaoUtils {

    private static final String TAG = DaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public DaoUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成userInfoBean记录的插入，如果表未创建，先创建Meizi表
     * @param userInfoBean
     * @return
     */
    public boolean insertUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        flag = mManager.getDaoSession().getUserInfoBeanDao().insert(userInfoBean) == -1 ? false : true;
        Log.i(TAG, "insert userInfoBean :" + flag + "-->" + userInfoBean.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param userInfoBeanList
     * @return
     */
    public boolean insertMultUserInfo(final List<UserInfoBean> userInfoBeanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserInfoBean userInfoBean : userInfoBeanList) {
                        mManager.getDaoSession().insertOrReplace(userInfoBean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param userInfoBean
     * @return
     */
    public boolean updateUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(userInfoBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param userInfoBean
     * @return
     */
    public boolean deleteUserInfo(UserInfoBean userInfoBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(userInfoBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(UserInfoBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<UserInfoBean> queryAllUserInfo(){
        return mManager.getDaoSession().loadAll(UserInfoBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public UserInfoBean queryUserInfoById(long key){
        return mManager.getDaoSession().load(UserInfoBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserInfoBean> queryUserInfoByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(UserInfoBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
//    public List<UserInfoBean> queryMeiziByQueryBuilder(long id){
//        QueryBuilder<UserInfoBean> queryBuilder = mManager.getDaoSession().queryBuilder(UserInfoBean.class);
//        return queryBuilder.where(UserInfoBean.._id.eq(id)).list();
////        return queryBuilder.where(MeiziDao.Properties._id.ge(id)).list();
//    }


}
