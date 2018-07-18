package com.saahas.demo.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.saahas.demo.commands.CategoryCommand;
import com.saahas.demo.domain.Category;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

    @Synchronized
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        log.info("source Id: " + source.getId());
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
