package com.toyoda.fipe.api.application.port.output.http;

import com.toyoda.fipe.api.domain.model.BrandInfo;
import java.util.List;

public interface BrandClientPort {

    List<BrandInfo> getBrands();
}
