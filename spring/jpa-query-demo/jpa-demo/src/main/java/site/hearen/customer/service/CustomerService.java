package site.hearen.customer.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import site.hearen.customer.dao.CustomerRepository;
import site.hearen.customer.entity.Customer;
import site.hearen.customer.util.CustomerSpecificationsBuilder;

@Service
public class CustomerService {
    // https://stackoverflow.com/questions/3153546/how-does-autowiring-work-in-spring
    // https://stackoverflow.com/questions/30188262/spring-autowired-for-setter-methods-vs-non-setter-methods
    // http://www.baeldung.com/spring-autowire
    // https://www.javatpoint.com/autowiring-in-spring
    // After bean initialization, Spring will auto-initialize the autowired whether in field, setter, constructor
    // or any other method;
    // Try not to introduce ambiguous beans - using @Component("aSpecialName") or @Service("aSpecialName") to solve;
    // @Qualifier is actually not required in most cases;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll(String search) {
        CustomerSpecificationsBuilder builder = new CustomerSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Customer> spec = builder.build();
        List<Customer> results = customerRepository.findAll(spec);
        return results;
    }

}
