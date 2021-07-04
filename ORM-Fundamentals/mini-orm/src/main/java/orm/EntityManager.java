package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

public class EntityManager<E> implements DbContext<E>{
    private Connection connection;

    public EntityManager(Connection connection){
        this.connection = connection;
    }

    public boolean persist(E entity) throws IllegalAccessException {
        Field id = this.getId(entity.getClass());
        id.setAccessible(true);
        Object value = id.get(entity);

        if(value == null || (long) value <= 0){
           return doInsert(entity,id);
        }
        return doUpdate(entity,id);
    }

    private boolean doInsert(E entity, Field primaryKey) {
        String query = "INSERT INTO " + this.getTableName(entity.getClass());
        String columns = "(";
        String values = ")";

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
        }
        return false;
    }

    private boolean doUpdate(E entity, Field primaryKey) {
        return false;
    }


    public Iterable<E> find(Class<E> table) {
        return null;
    }

    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    public E findFirst(Class<E> table) {
        return null;
    }

    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getId(Class<?> entity){
        return Arrays.stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() ->
        new UnsupportedOperationException("Entity does not have primary key"));
    }

    private String getTableName(Class entity){
        String tableName = "";
        tableName =((Entity)entity.getAnnotation(Entity.class)).name();

        if(!tableName.equals("")){
            tableName = entity.getSimpleName();
        }

        return tableName;
    }

    private String getColumnName(Field field){
      String columnName = field.getAnnotation(Column.class).name();

        if(columnName.isEmpty()){
            columnName = field.getName();
        }
        return columnName;
    }
}
