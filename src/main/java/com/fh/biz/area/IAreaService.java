package com.fh.biz.area;


import com.fh.common.ServerResponse;
import com.fh.po.area.Area;

import java.util.List;

public interface IAreaService {
    List<Area> findAreaList();

    ServerResponse addArea(Area area);

    ServerResponse deleteArea(Long[] ids);

    ServerResponse queryAreaById(Long id);

    ServerResponse updateArea(Area area);

    ServerResponse findChilds(Long id);
}
