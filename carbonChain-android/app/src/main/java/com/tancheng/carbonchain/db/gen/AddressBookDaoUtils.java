package com.tancheng.carbonchain.db.gen;

import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.AddressBook;

import java.util.List;

/**
 * created by tc_collin on 2020/3/28 12:23
 * Description: 地址簿Dao
 * version: 1.0
 */
public class AddressBookDaoUtils {

    AddressBookDao bookDao;
    private static AddressBookDaoUtils bookDaoUtils;

    public static AddressBookDaoUtils getIntence() {
        if (bookDaoUtils == null) {
            bookDaoUtils = new AddressBookDaoUtils();
        }
        return bookDaoUtils;
    }

    private AddressBookDaoUtils() {
        bookDao = MyApplication.getmDaoSession().getAddressBookDao();
    }

    /**
     * 插入数据
     *
     * @param addressBook
     * @return
     */
    public boolean insertBook(AddressBook addressBook) {
        long insert = bookDao.insert(addressBook);
        boolean isSuccess = insert > 0 ? true : false;
        return isSuccess;
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public AddressBook getAddressBookInfo(long id) {
        List<AddressBook> list = bookDao.queryBuilder().where(AddressBookDao.Properties.Id.eq(id)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 已创建的地址簿
     *
     * @return
     */
    public List<AddressBook> getBooks() {
        return bookDao.queryBuilder().list();
    }


    public void updateBook(AddressBook addressBook) {
        bookDao.update(addressBook);
    }

    /**
     * 删除
     * @param bookId
     */
    public void delBook(long bookId) {
        bookDao.deleteByKey(bookId);
    }

    /**
     * 名称是否已存在
     *
     * @param walletName
     * @return
     */
    public boolean bookNameIsExist(String walletName) {
        List<AddressBook> list = bookDao.queryBuilder().list();
        for (AddressBook book : list) {
            boolean equals = book.getName().equals(walletName);
            if (equals) {
                return true;
            }
        }
        return false;
    }

}
