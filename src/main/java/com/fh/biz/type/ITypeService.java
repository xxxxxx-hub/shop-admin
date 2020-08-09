package com.fh.biz.type;

;

import com.fh.po.type.Type;

import java.util.List;

public interface ITypeService {
    void addType(Type type);

    void deleteType(List<Long> ids);

    Type findTypeById(Long id);

    void editType(Type type);

    List<Type> findType();

    List<Type> findTypeByPid(Long pid);
}
