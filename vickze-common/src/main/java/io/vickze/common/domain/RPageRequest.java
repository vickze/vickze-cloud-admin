package io.vickze.common.domain;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RPageRequest  extends AbstractPageRequest {

    private static final long serialVersionUID = -4541509938956089562L;

    private final Sort sort;

    public RPageRequest(int page, int size, Sort sort) {
        super(page, size);

        this.sort = sort;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new RPageRequest(getPageNumber() + 1, getPageSize(), getSort());
    }

    @Override
    public Pageable previous() {
        return getPageNumber() == 0 ? this : new RPageRequest(getPageNumber() - 1, getPageSize(), getSort());
    }

    @Override
    public Pageable first() {
        return new RPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public long getOffset() {
        return getPageNumber();
    }


    public static RPageRequest of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    public static RPageRequest of(int page, int size, Sort sort) {
        return new RPageRequest(page, size, sort);
    }

    public static RPageRequest of(int page, int size, Sort.Direction direction, String... properties) {
        return of(page, size, Sort.by(direction, properties));
    }

}
