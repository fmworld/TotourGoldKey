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

//        addNote(schema);
//        addCustomerOrder(schema);
        DbTableBuilder dbTableBuilder = new DbTableBuilder(schema);
        dbTableBuilder.prepareTable(TourDBConfig.TableName.user.toString())
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

        dbTableBuilder.prepareTable(TourDBConfig.TableName.inn.toString())
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

        try {
            new DaoGenerator().generateAll(schema, dbConfig.getSourceDir());
        } catch (Exception e) {
        }

    }

    private static void addUser(Schema schema, TourDBConfig dbConfig) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
