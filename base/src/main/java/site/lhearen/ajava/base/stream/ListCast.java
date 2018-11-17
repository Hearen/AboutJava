package site.lhearen.ajava.base.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListCast {
    public static void main(String... args) {
        Map<String, Object> HOSTING1 = new HashMap<>();
        HOSTING1.put("1", "linode.com");
        HOSTING1.put("2", "heroku.com");
        HOSTING1.put("3", "digitalocean.com");
        HOSTING1.put("4", "aws.amazon.com");
        final List<Tag> customTags = HOSTING1.entrySet().stream().filter(entry -> {
            List<String> tagList = Arrays.asList("1", "2", "3");
            return tagList.contains(entry.getKey());
        }).map(tag ->
                new Tag() {
                    public String getValue() {
                        System.out.println("Value for the attached tag is: {}" + tag.getValue());
                        return (String) tag.getValue();
                    }

                    public String getKey() {
                        System.out.println("Key for the attached tag is: {}" + tag.getKey());
                        return (String) tag.getKey();
                    }

                }).collect(Collectors.toList());
        for (Tag customTag : customTags) {
            System.out.println(customTag.toPrettyString());
        }
    }

    interface Tag {
        String getValue();

        String getKey();

        default String toPrettyString() {
            return String.format("%s: %s", getKey(), getValue());
        }

    }

    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
