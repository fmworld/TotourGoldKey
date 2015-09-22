package com.fm.fmlib.models;

import android.content.Context;

import com.fm.fmlib.dao.CategoryList;
import com.fm.fmlib.dao.CategoryListDao;
import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.dao.CategoryTitleDao;
import com.fm.fmlib.dao.DaoSession;
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
public class CategoryDaoProvider {
    @Inject
    DaoSession mDaoSession;

    private CategoryTitleDao mCategoryTitleDao;
    private CategoryListDao mCategoryListDao;
    public CategoryDaoProvider(Context context) {
        DaoSessionComponent sessionComponent = DaggerDaoSessionComponent.builder()
                .daoSessionProvider(new DaoSessionProvider(context)).build();
        mDaoSession = sessionComponent.provideSession();

        mCategoryTitleDao = mDaoSession.getCategoryTitleDao();
        mCategoryListDao = mDaoSession.getCategoryListDao();
    }

    public boolean saveCategoryTitle(CategoryTitle title){
        QueryBuilder<CategoryTitle> queryBuilder = mCategoryTitleDao.queryBuilder();
        queryBuilder.where(CategoryTitleDao.Properties.Id.eq(title.getId()));
        CategoryTitle ct = title;
        if (0 < queryBuilder.list().size()) {
            ct = queryBuilder.list().get(0);
            MappingUtil.cTitle2cTitle(title, ct);
        }
        return mCategoryTitleDao.insertOrReplace(ct)>0;
    }

    public void saveCategoryTitles(final List<CategoryTitle> tags){
        mCategoryTitleDao.deleteAll();
        mCategoryTitleDao.insertInTx(new Iterable<CategoryTitle>() {
            @Override
            public Iterator<CategoryTitle> iterator() {
                return tags.iterator();
            }
        });
    }

    public List<CategoryTitle> getCategoryTitles(){
        QueryBuilder<CategoryTitle> queryBuilder = mCategoryTitleDao.queryBuilder();
        return queryBuilder.list();
    }

    public boolean saveCategoryList(CategoryList cList){
        QueryBuilder<CategoryList> queryBuilder = mCategoryListDao.queryBuilder();
        queryBuilder.where(CategoryListDao.Properties.Category_id.eq(cList.getCategory_id()));
        CategoryList ct = cList;
        if (0 < queryBuilder.list().size()) {
            ct = queryBuilder.list().get(0);
            MappingUtil.cList2cList(cList, ct);
        }
        return mCategoryListDao.insertOrReplace(ct)>0;
    }

    public void saveCategoryLists(final List<CategoryList> tags){
        mCategoryListDao.deleteAll();
        mCategoryListDao.insertInTx(new Iterable<CategoryList>() {
            @Override
            public Iterator<CategoryList> iterator() {
                return tags.iterator();
            }
        });
    }

    public List<CategoryList> getCategoryLists(String title_id){
        QueryBuilder<CategoryList> queryBuilder = mCategoryListDao.queryBuilder();
        queryBuilder.where(CategoryListDao.Properties.Category.eq(title_id));
        return queryBuilder.list();
    }
}
