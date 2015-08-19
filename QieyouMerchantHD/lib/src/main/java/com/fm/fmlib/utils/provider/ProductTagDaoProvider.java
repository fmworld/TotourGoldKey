package com.fm.fmlib.utils.provider;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.dao.ProductTagDao;
import com.fm.fmlib.utils.MappingUtil;

import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class ProductTagDaoProvider {
    private ProductTagDao mProductTagDao;

    public ProductTagDaoProvider(DaoSession session) {
        mProductTagDao = session.getProductTagDao();
    }

    public boolean saveProductTag(ProductTag info){
        QueryBuilder<ProductTag> queryBuilder = mProductTagDao.queryBuilder();
        queryBuilder.where(ProductTagDao.Properties.Tag_id.eq(info.getTag_id()));
        ProductTag pt = info;
        if (0 < queryBuilder.list().size()) {
            pt = queryBuilder.list().get(0);
            MappingUtil.tag2tag(info, pt);
        }
        return mProductTagDao.insertOrReplace(pt)>0;
    }

    public void saveProductTags(final List<ProductTag> tags){
        mProductTagDao.deleteAll();
        mProductTagDao.insertInTx(new Iterable<ProductTag>() {
            @Override
            public Iterator<ProductTag> iterator() {
                // TODO Auto-generated method stub
                return tags.iterator();
            }
        });
    }

    public List<ProductTag> getProductTags(){
        QueryBuilder<ProductTag> queryBuilder = mProductTagDao.queryBuilder();
        return queryBuilder.list();
    }
}
