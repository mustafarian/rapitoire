package com.drestive.rapitoire.services;

import com.drestive.rapitoire.annotations.Element;
import com.drestive.rapitoire.dtos.SelectOptionDto;

import java.util.List;

/**
 * Created by mustafa on 21/10/2016.
 */

public interface SelectOptionsGenerator {
    List<SelectOptionDto> getInputElementOptions(Element element);
}


