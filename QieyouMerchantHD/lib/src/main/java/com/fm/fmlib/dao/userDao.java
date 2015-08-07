package com.fm.fmlib.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.fm.fmlib.dao.user;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER.
*/
public class userDao extends AbstractDao<user, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity user.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Account = new Property(0, String.class, "account", false, "ACCOUNT");
        public final static Property Password = new Property(1, String.class, "password", false, "PASSWORD");
        public final static Property Token = new Property(2, String.class, "token", false, "TOKEN");
        public final static Property Role = new Property(3, String.class, "role", false, "ROLE");
        public final static Property State = new Property(4, String.class, "state", false, "STATE");
        public final static Property Islogin = new Property(5, Boolean.class, "islogin", false, "ISLOGIN");
        public final static Property Id = new Property(6, Long.class, "id", true, "_id");
    };


    public userDao(DaoConfig config) {
        super(config);
    }
    
    public userDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER' (" + //
                "'ACCOUNT' TEXT NOT NULL ," + // 0: account
                "'PASSWORD' TEXT NOT NULL ," + // 1: password
                "'TOKEN' TEXT," + // 2: token
                "'ROLE' TEXT," + // 3: role
                "'STATE' TEXT," + // 4: state
                "'ISLOGIN' INTEGER," + // 5: islogin
                "'_id' INTEGER PRIMARY KEY );"); // 6: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, user entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getAccount());
        stmt.bindString(2, entity.getPassword());
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(3, token);
        }
 
        String role = entity.getRole();
        if (role != null) {
            stmt.bindString(4, role);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(5, state);
        }
 
        Boolean islogin = entity.getIslogin();
        if (islogin != null) {
            stmt.bindLong(6, islogin ? 1l: 0l);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(7, id);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6);
    }    

    /** @inheritdoc */
    @Override
    public user readEntity(Cursor cursor, int offset) {
        user entity = new user( //
            cursor.getString(offset + 0), // account
            cursor.getString(offset + 1), // password
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // token
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // role
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // state
            cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0, // islogin
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, user entity, int offset) {
        entity.setAccount(cursor.getString(offset + 0));
        entity.setPassword(cursor.getString(offset + 1));
        entity.setToken(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRole(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setState(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setIslogin(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        entity.setId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(user entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(user entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
