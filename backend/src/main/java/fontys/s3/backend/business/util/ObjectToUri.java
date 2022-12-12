package fontys.s3.backend.business.util;

import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

public class ObjectToUri {
    public static Map<String, String> convert(Object object) {

        Map<String, String> map = new HashMap<>();

        ReflectionUtils.doWithFields(object.getClass(), field -> {
            field.setAccessible(true);
            if (field.get(object) != null) {
                map.put(field.getName(), field.get(object).toString());
            }
        });
        return map;
    }
}
