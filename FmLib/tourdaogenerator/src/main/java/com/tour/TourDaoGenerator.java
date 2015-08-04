package com.tour;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class TourDaoGenerator {

    public static void main(String[] args) throws Exception {
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
                .build();

        new DaoGenerator().generateAll(schema, dbConfig.getSourceDir());
    }

    private static  void addUser(Schema schema, TourDBConfig dbConfig) {
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
