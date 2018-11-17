package site.hearen.customer.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import site.hearen.customer.entity.Customer;

public class CustomerSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public CustomerSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public CustomerSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Customer> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Customer>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new CustomerSpecification(param));
        }

        Specification<Customer> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
