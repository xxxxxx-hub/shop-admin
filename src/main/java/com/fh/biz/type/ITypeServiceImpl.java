package com.fh.biz.type;


import com.fh.mapper.ITypeMapper;
import com.fh.po.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private ITypeMapper typeMapper;

    @Override
    public void addType(Type type) {
        typeMapper.addType(type);
    }

    @Override
    public void deleteType(List<Long> ids) {
        typeMapper.deleteType(ids);
    }

    @Override
    public Type findTypeById(Long id) {
        return typeMapper.findTypeById(id);
    }

    @Override
    public void editType(Type type) {
        typeMapper.editType(type);
    }

    @Override
    public List<Type> findType() {
        return typeMapper.findType();
    }

    @Override
    public List<Type> findTypeByPid(Long pid) {
        return typeMapper.findTypeByPid(pid);
    }
}
