package com.fh.mapper;



import com.fh.po.type.Type;

import java.util.List;

public interface ITypeMapper {
    List<Type> findType();

    void editType(Type type);

    Type findTypeById(Long id);

    void deleteType(List<Long> ids);

    void addType(Type type);

    List<Type> findTypeByPid(Long pid);
}
