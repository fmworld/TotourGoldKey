package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.dao.ProductTagDao;
import com.fm.fmlib.models.component.DaggerDaoSessionComponent;
import com.fm.fmlib.models.component.DaoSessionComponent;
import com.fm.fmlib.utils.MappingUtil;
import com.fm.fmlib.models.provider.DaoSessionProvider;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
@Singleton
public class ProductTagDaoProvider {
    @Inject
    DaoSession mDaoSession;

    private ProductTagDao mProductTagDao;

    public ProductTagDaoProvider(Context context) {
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();
        mProductTagDao = mDaoSession.getProductTagDao();
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
                return tags.iterator();
            }
        });
    }

    public List<ProductTag> getProductTags(){
        QueryBuilder<ProductTag> queryBuilder = mProductTagDao.queryBuilder();
        return queryBuilder.list();
    }
}
