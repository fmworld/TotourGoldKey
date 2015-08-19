package com.fm.fmlib.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.fm.fmlib.dao.ProductBreviary;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRODUCT_BREVIARY.
*/
public class ProductBreviaryDao extends AbstractDao<ProductBreviary, Void> {

    public static final String TABLENAME = "PRODUCT_BREVIARY";

    /**
     * Properties of entity ProductBreviary.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Product_id = new Property(0, String.class, "product_id", false, "PRODUCT_ID");
        public final static Property Price = new Property(1, String.class, "price", false, "PRICE");
        public final static Property Old_price = new Property(2, String.class, "old_price", false, "OLD_PRICE");
        public final static Property Product_name = new Property(3, String.class, "product_name", false, "PRODUCT_NAME");
        public final static Property Quantity = new Property(4, String.class, "quantity", false, "QUANTITY");
        public final static Property Score = new Property(5, String.class, "score", false, "SCORE");
        public final static Property State = new Property(6, String.class, "state", false, "STATE");
        public final static Property Thumb = new Property(7, String.class, "thumb", false, "THUMB");
        public final static Property Tuan_end_time = new Property(8, String.class, "tuan_end_time", false, "TUAN_END_TIME");
    };


    public ProductBreviaryDao(DaoConfig config) {
        super(config);
    }
    
    public ProductBreviaryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRODUCT_BREVIARY' (" + //
                "'PRODUCT_ID' TEXT NOT NULL ," + // 0: product_id
                "'PRICE' TEXT," + // 1: price
                "'OLD_PRICE' TEXT," + // 2: old_price
                "'PRODUCT_NAME' TEXT," + // 3: product_name
                "'QUANTITY' TEXT," + // 4: quantity
                "'SCORE' TEXT," + // 5: score
                "'STATE' TEXT," + // 6: state
                "'THUMB' TEXT," + // 7: thumb
                "'TUAN_END_TIME' TEXT);"); // 8: tuan_end_time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRODUCT_BREVIARY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ProductBreviary entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getProduct_id());
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(2, price);
        }
 
        String old_price = entity.getOld_price();
        if (old_price != null) {
            stmt.bindString(3, old_price);
        }
 
        String product_name = entity.getProduct_name();
        if (product_name != null) {
            stmt.bindString(4, product_name);
        }
 
        String quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindString(5, quantity);
        }
 
        String score = entity.getScore();
        if (score != null) {
            stmt.bindString(6, score);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(7, state);
        }
 
        String thumb = entity.getThumb();
        if (thumb != null) {
            stmt.bindString(8, thumb);
        }
 
        String tuan_end_time = entity.getTuan_end_time();
        if (tuan_end_time != null) {
            stmt.bindString(9, tuan_end_time);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public ProductBreviary readEntity(Cursor cursor, int offset) {
        ProductBreviary entity = new ProductBreviary( //
            cursor.getString(offset + 0), // product_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // price
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // old_price
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // product_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // quantity
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // score
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // state
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // thumb
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // tuan_end_time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ProductBreviary entity, int offset) {
        entity.setProduct_id(cursor.getString(offset + 0));
        entity.setPrice(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOld_price(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setProduct_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setQuantity(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setScore(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setState(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setThumb(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTuan_end_time(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(ProductBreviary entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(ProductBreviary entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
