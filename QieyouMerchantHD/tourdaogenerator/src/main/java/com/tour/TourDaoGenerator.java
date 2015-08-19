package com.tour;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class TourDaoGenerator {

    public static void main(String[] args) throws Exception {
//        testTourServerApi();
        initDataFile();
    }

    private static void testTourServerApi() {
        TourTest tourTest = new TourTest();
//        tourTest.changePassword();
    }

    private static void initDataFile() {
        TourDBConfig dbConfig = new TourDBConfig();
        Schema schema = new Schema(dbConfig.getDBVersion(), dbConfig.getSourceStructure());

        DbTableBuilder dbTableBuilder = new DbTableBuilder(schema);
        addUser(dbTableBuilder);

        addInn(dbTableBuilder);

        addProperty(dbTableBuilder);

        addTag(dbTableBuilder);

        addCategoryTitle(dbTableBuilder);

        addCategoryList(dbTableBuilder);

        addLocalTitle(dbTableBuilder);

        addLocalList(dbTableBuilder);

        addProductBreviaryTable(dbTableBuilder);
        try {
            new DaoGenerator().generateAll(schema, dbConfig.getSourceDir());
        } catch (Exception e) {
        }

    }

    public static void addProduct(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.MyProperty.toString())
                .addIdProperty()
                .addStringPropertyNotNull(TourDBConfig.PropertyTable.key.toString())
                .addStringProperty(TourDBConfig.PropertyTable.value.toString())
                .build();
    }

    public static void addTag(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.ProductTag.toString())
                .addIdProperty()
                .addStringPropertyNotNull(TourDBConfig.TagTable.tag_id.toString())
                .addStringPropertyNotNull(TourDBConfig.TagTable.tag_name.toString())
                .addStringProperty(TourDBConfig.TagTable.item_count.toString())
                .addStringProperty(TourDBConfig.TagTable.item_seq.toString())
                .addStringProperty(TourDBConfig.TagTable.tag_seq.toString())
                .build();

    }

    public static void addUser(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.User.toString())
                .addIdProperty()
                .addStringPropertyNotNull(TourDBConfig.UserTable.account.toString())
                .addStringPropertyNotNull(TourDBConfig.UserTable.password.toString())
                .addBooleanProperty(TourDBConfig.UserTable.islogin.toString())
                .addStringProperty(TourDBConfig.UserTable.token.toString())
                .addStringProperty(TourDBConfig.UserTable.role.toString())
                .addStringProperty(TourDBConfig.UserTable.state.toString())
                .addStringProperty(TourDBConfig.UserTable.userMobile.toString())
                .addStringProperty(TourDBConfig.UserTable.innerHead.toString())
                .addStringProperty(TourDBConfig.UserTable.innerName.toString())
                .build();
    }

    public static void addInn(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.Inn.toString())
                .addIdProperty()
                .addStringProperty(TourDBConfig.InnTable.innId.toString())
                .addStringProperty(TourDBConfig.InnTable.innerContact.toString())
                .addStringProperty(TourDBConfig.InnTable.innerHead.toString())
                .addStringProperty(TourDBConfig.InnTable.innerIdentify.toString())
                .addStringProperty(TourDBConfig.InnTable.innerMoblie.toString())
                .addStringProperty(TourDBConfig.InnTable.innerScore.toString())
                .addStringProperty(TourDBConfig.InnTable.innerServe.toString())
                .addStringProperty(TourDBConfig.InnTable.innerTodayServe.toString())
                .addStringProperty(TourDBConfig.InnTable.innHead.toString())
                .addStringProperty(TourDBConfig.InnTable.innProducts.toString())
                .addStringProperty(TourDBConfig.InnTable.innName.toString())
                .build();
    }

    public static void addProperty(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.MyProperty.toString())
                .addIdProperty()
                .addStringPropertyNotNull(TourDBConfig.PropertyTable.key.toString())
                .addStringProperty(TourDBConfig.PropertyTable.value.toString())
                .build();
    }

    public static void addCategoryTitle(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.CategoryTitle.toString())
                .addStringPropertyNotNull(TourDBConfig.CategoryTitleTable.id.toString())
                .addStringProperty(TourDBConfig.CategoryTitleTable.name.toString())
                .build();
    }

    public static void addCategoryList(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.CategoryList.toString())
                .addStringPropertyNotNull(TourDBConfig.CategoryListTable.category.toString())
                .addStringPropertyNotNull(TourDBConfig.CategoryListTable.category_id.toString())
                .addStringPropertyNotNull(TourDBConfig.CategoryListTable.name.toString())
                .build();
    }

    public static void addLocalTitle(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.LocalTitle.toString())
                .addStringPropertyNotNull(TourDBConfig.LocalTitleTable.dest_id.toString())
                .addStringPropertyNotNull(TourDBConfig.LocalTitleTable.dest_name.toString())
                .build();
    }

    public static void addLocalList(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.LocalList.toString())
                .addStringPropertyNotNull(TourDBConfig.LocalListTable.dest_id.toString())
                .addStringPropertyNotNull(TourDBConfig.LocalListTable.local_id.toString())
                .addStringPropertyNotNull(TourDBConfig.LocalListTable.local_name.toString())
                .build();
    }

    public static void addProductBreviaryTable(DbTableBuilder dbTableBuilder){
        dbTableBuilder.prepareTable(TourDBConfig.TableName.ProductBreviary.toString())
                .addStringPropertyNotNull(TourDBConfig.ProductBreviaryTable.product_id.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.price.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.old_price.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.product_name.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.quantity.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.score.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.state.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.thumb.toString())
                .addStringProperty(TourDBConfig.ProductBreviaryTable.tuan_end_time.toString())
                .build();
    }




}
