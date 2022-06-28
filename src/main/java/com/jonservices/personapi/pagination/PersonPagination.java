package com.jonservices.personapi.pagination;

import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

@Setter
public class PersonPagination {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_LIMIT = 20;
    private static final Direction DEFAULT_DIRECTION = Direction.ASC;
    private static final String DEFAULT_CRITERION = "id";
    private static final List<String> SORT_CRITERIA = List.of("id", "firstName", "birthDate");

    public static Pageable getPage(int pageNumber, int limit, String orderBy, String criteria) {
        pageNumber = validatePageNumber(pageNumber);
        criteria = validateCriteria(criteria);
        limit = validateLimit(limit);
        final Direction direction = validateDirection(orderBy);
        return PageRequest.of(pageNumber, limit, Sort.by(direction, criteria));
    }

    private static int validatePageNumber(int pageNumber) {
        return pageNumber <= DEFAULT_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : --pageNumber;
    }

    private static int validateLimit(int limit) {
        return limit > 30 || limit < 1 ? DEFAULT_LIMIT : limit;
    }

    private static String validateCriteria(String criteria) {
        criteria = criteria.equalsIgnoreCase("name") ? "firstName" : criteria;
        criteria = criteria.equalsIgnoreCase("birth") ? "birthDate" : criteria;
        return SORT_CRITERIA.contains(criteria) ? criteria : DEFAULT_CRITERION;
    }

    private static Direction validateDirection(String orderBy) {
        return orderBy.equalsIgnoreCase("desc") ? Direction.DESC : DEFAULT_DIRECTION;
    }

}
