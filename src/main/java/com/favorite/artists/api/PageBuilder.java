package com.favorite.artists.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Service
public class PageBuilder<T> {

    public Page<T> buildPageFrom(int page, int size, List<T> entities) {
        PageRequest pageRequest = PageRequest.of(page, size);

        int total = entities.size();
        int start = toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);

        List<T> output = new ArrayList<>();

        if (start <= end) {
            output = entities.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageRequest,
                total
        );
    }
}
