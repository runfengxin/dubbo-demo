package com.xrf.dubbo.provider.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    private static final String isDelete = "isDelete";
    private static final String updateUserId = "updateUserId";
    private static final String deleteUserId = "deleteUserId";
    private static final String updateTime = "updateTime";
    private static final String deleteTime = "deleteTime";

    @Override
    public void insertFill(MetaObject metaObject) {
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 软删除时，需要外面先 setIsDelete(true)，否则无法识别是更新还是软删除操作
        if (metaObject.hasGetter(isDelete) && metaObject.getValue(isDelete) == Boolean.TRUE) {
//            this.strictUpdateFill(metaObject, deleteUserId, TransmittableThreadLocalDemo::getId, String.class);
            this.strictUpdateFill(metaObject, deleteTime, Date::new, Date.class);
        } else {
//            this.strictUpdateFill(metaObject, updateUserId, TransmittableThreadLocalDemo::getId, String.class);
            this.strictUpdateFill(metaObject, updateTime, Date::new, Date.class);
        }
    }

    /**
     * 严格模式填充策略,默认有值不覆盖,如果提供的值为null也不填充
     *
     * @param metaObject metaObject meta object parameter
     * @param fieldName  java bean property name
     * @param fieldVal   java bean property value of Supplier
     * @return this
     * @since 3.3.0
     */
//    @Override
//    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
//        // 但当自动填充字段为 updateTime 时，始终更新它
//        if (fieldName.equals(updateTime)) {
//            Object obj = fieldVal.get();
//            metaObject.setValue(fieldName, obj);
//        } else if (metaObject.getValue(fieldName) == null) { // 这个 if 是源码中的
//            Object obj = fieldVal.get();
//            if (Objects.nonNull(obj)) {
//                metaObject.setValue(fieldName, obj);
//            }
//        }
//        return this;
//    }
}