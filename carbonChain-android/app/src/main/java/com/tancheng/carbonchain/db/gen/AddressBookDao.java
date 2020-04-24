package com.tancheng.carbonchain.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tancheng.carbonchain.activities.asset.wallet.db.entity.AddressBook;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADDRESS_BOOK".
*/
public class AddressBookDao extends AbstractDao<AddressBook, Long> {

    public static final String TABLENAME = "ADDRESS_BOOK";

    /**
     * Properties of entity AddressBook.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Remark = new Property(2, String.class, "remark", false, "REMARK");
        public final static Property Address = new Property(3, String.class, "address", false, "ADDRESS");
        public final static Property WalletType = new Property(4, int.class, "walletType", false, "WALLET_TYPE");
    }


    public AddressBookDao(DaoConfig config) {
        super(config);
    }
    
    public AddressBookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADDRESS_BOOK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"REMARK\" TEXT," + // 2: remark
                "\"ADDRESS\" TEXT," + // 3: address
                "\"WALLET_TYPE\" INTEGER NOT NULL );"); // 4: walletType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADDRESS_BOOK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AddressBook entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(3, remark);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
        stmt.bindLong(5, entity.getWalletType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AddressBook entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(3, remark);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
        stmt.bindLong(5, entity.getWalletType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AddressBook readEntity(Cursor cursor, int offset) {
        AddressBook entity = new AddressBook( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // remark
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // address
            cursor.getInt(offset + 4) // walletType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AddressBook entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRemark(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setWalletType(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AddressBook entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AddressBook entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AddressBook entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
